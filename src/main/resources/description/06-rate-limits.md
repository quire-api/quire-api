# Rate Limits

To protect the stability of the API and keep it available to all users, Quire enforces multiple kinds of rate limiting. 
Requests that hit any of our rate limits will receive a `429 Too Many Requests` response.
We may change these quotas or add new quotas in the future.

Here are the limits by plan.

| Plan | Maximum requests per organization, per *minute* | Maximum requests per organization, per *hour*
|---------|------|-------
| Free | 50 | 200
| Professional | 300 | 1,250
| Premium | 1,000 | 5,000
| Enterprise | 100 &times; members, at least 1,000 | 500 &times; members, at least 5,000

> Note: the limit is per-organization. It sums up the total number of all accesses from all applications for each organization.
> Organizations that share one subscription also share one limit: their accesses are summed up together.
> Enterprise quotas scale with the number of paid members in the organization.
> A trial doesn't change these limits; they stay those of the plan before the trial.
> For more quota, please refer to [Pricing](/pricing).

> Note: a **personal project** (the one behind My Tasks) is metered against its own bucket at
> half the limits of the best plan among the user's organizations, trials excluded — e.g. 25 per minute / 100 per hour on the Free plan.
> For Enterprise, it is half the Premium limits: 500 per minute / 2,500 per hour.

When a rate limit is exceeded, the response includes a standard [`Retry-After`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/Retry-After) header
whose value is the number of seconds to wait before retrying.
The value reflects the time until the offending per-minute or per-hour counter resets, so clients can back off precisely instead of guessing.

```
HTTP/1.1 429 Too Many Requests
Retry-After: 37
Content-Type: application/json

{"code": 429, "message": "..."}
```

Apart from the limits above, more than 500 requests in progress at once with the same access token from the same IP address
also receive `429`, with `Retry-After: 30`. Wait for pending requests to complete before sending more.

To inspect current usage without consuming budget, call [`GET /rate-limit/id/{id}`](#operation--rate-limit-id--id--get)
(or `/rate-limit/{oid}`). Calls to that endpoint are free — they never count against the quota.

## Request cost

Most calls cost **1 unit** against the per-minute / per-hour budget above — a single-item
`GET` / `POST` / `PUT` / `DELETE` on one record, a storage call, and so on.
A few endpoint families scale their cost with how much work they actually do:

| Endpoint family | Cost |
|---|---|
| Task `list`, subtree, and `search` (`GET`) | `max(1, ceil(items / 100))` units, where `items` is the number of tasks returned. So `?limit=100` (or fewer) costs 1, `?limit=1000` costs 10, `?limit=no` returning 5,000 items costs 50. |
| Bulk write — `bulk-add`, `bulk-update`, `bulk-remove`, `bulk-move`, `bulk-transfer`, `bulk-approve` | `N` units, where `N` is `items.length` in the request body (for `bulk-add`, every task created, nested `tasks` included) — the same total cost as `N` equivalent single-task calls. `?dry-run=true` halves it to `ceil(N / 2)` (minimum 1). A batch holds at most 300 items, and no more than the per-minute limit — e.g. 50 on the Free plan, or 25 in a personal project on the Free plan; over the plan's limit is rejected with `402` (`code` `469`), over 300 with `400`. |
| `POST /task` with nested `tasks` | 1 unit per task created, nested ones included. Capped like a bulk write: at most 300 tasks, and no more than the per-minute limit. |
| Project CSV / JSON export | `5 + max(1, ceil(tasks / 100))` units — the same per-100 read cost as a list, plus a flat **5-unit export surcharge**. `tasks` counts only those matching the `?status=` filter, not the project total. So a 100-task export costs 6, a 1,000-task export costs 15, and a 5,000-task export costs 55. Only an export that runs costs this: one rejected with `402` for the plan costs 1 unit, like any call. |
| `POST /notification` | `ceil(recipients / 10)` units (minimum 1), counted from the resolved recipient list. Charged only after the recipients are resolved, so a call rejected with `404` costs nothing. |
| `GET /insight/run/{oid}` | 1 unit, plus `ceil(tasksLoaded / 250) - 1` when more than 250 tasks are loaded. `tasksLoaded` reflects the `?status=`-filtered task set, so a tighter filter lowers the charge. |
| `GET /rate-limit/...`, `GET /user/{oid}`, `GET /user/id/{id}`, `GET /user/list`, `GET /project/list` (without an organization), `GET /organization/list` | Free — never counted. |

## Size limits

The body of a request can't be larger than 1 MB; a larger one gets `413 Content too large`. Two exceptions:

- A [storage](#tag-storage) value is at most 4 KiB as JSON; a larger one gets `413`.
- A file uploaded with `attach` is limited by the plan's per-file size and storage quota instead; over either, `402` (`code` `469`).

