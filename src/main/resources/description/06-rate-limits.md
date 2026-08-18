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
| Enterprise | 100 &times; members | 500 &times; members

> Note: the limit is per-organization. It sums up the total number of all accesses from all applications for each organization.
> Enterprise quotas scale with the number of paid members in the organization.
> For more quota, please refer to [Pricing](/pricing).

> Note: a **personal project** (the one behind My Tasks) is metered against its own bucket at a
> quarter of the plan's limits — e.g. 12 per minute / 50 per hour on the Free plan.

When a rate limit is exceeded, the response includes a standard [`Retry-After`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/Retry-After) header
whose value is the number of seconds to wait before retrying.
The value reflects the time until the offending per-minute or per-hour counter resets, so clients can back off precisely instead of guessing.

```
HTTP/1.1 429 Too Many Requests
Retry-After: 37
Content-Type: application/json

{"code": 429, "message": "..."}
```

To inspect current usage without consuming budget, call [`GET /rate-limit/{oid}`](#operation--rate-limit--oid--get)
(or `/rate-limit/id/{id}`). Calls to that endpoint are free — they never count against the quota.

## Request cost

Most calls cost **1 unit** against the per-minute / per-hour budget above — a single-item
`GET` / `POST` / `PUT` / `DELETE` on one record, a storage call, and so on.
A few endpoint families scale their cost with how much work they actually do:

| Endpoint family | Cost |
|---|---|
| Task `list`, subtree, and `search` (`GET`) | `max(1, ceil(items / 100))` units, where `items` is the number of tasks returned. So `?limit=100` (or fewer) costs 1, `?limit=1000` costs 10, `?limit=no` returning 5,000 items costs 50. |
| Bulk write — `bulk-add`, `bulk-update`, `bulk-remove`, `bulk-move`, `bulk-transfer`, `bulk-approve` | `N` units, where `N` is `items.length` in the request body — the same total cost as `N` equivalent single-task calls. `?dry-run=true` halves it to `ceil(N / 2)` (minimum 1). |
| Project CSV / JSON export | `5 + max(1, ceil(tasks / 100))` units — the same per-100 read cost as a list, plus a flat **5-unit export surcharge**. `tasks` counts only those matching the `?status=` filter, not the project total. So a 100-task export costs 6, a 1,000-task export costs 15, and a 5,000-task export costs 55. |
| `POST /notification` | `ceil(recipients / 10)` units (minimum 1), counted from the resolved recipient list. Charged only after the recipients are resolved, so a call rejected with `404` costs nothing. |
| `GET /insight/run/{oid}` | 1 unit, plus `ceil(tasksLoaded / 250) - 1` when more than 250 tasks are loaded. `tasksLoaded` reflects the `?status=`-filtered task set, so a tighter filter lowers the charge. |
| `GET /rate-limit/...` | Free — never counted. |

### How the cost is applied

Bulk writes are charged upfront, before any item is processed — so a batch rejected with
`429` has changed nothing. For list, search, export, and Insight runs, the size-dependent
part of the charge is settled once the number of items is known.

The check rejects only when the counter is **already** at the limit; it does not reject a
call whose cost would push it past. So a single expensive call issued just under the
ceiling still completes in full, and the overshoot is settled by rejecting the calls that
follow. Budget from the costs above rather than expecting a `429` to stop you at exactly
the limit.

## Size limits

The size of each request can't be larger than 1MB. Requests that hit this limit will receive a `413 Content too large` response.

