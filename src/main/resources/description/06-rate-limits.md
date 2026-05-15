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

When a rate limit is exceeded, the response includes a standard [`Retry-After`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/Retry-After) header
whose value is the number of seconds to wait before retrying.
The value reflects the time until the offending per-minute or per-hour counter resets, so clients can back off precisely instead of guessing.

```
HTTP/1.1 429 Too Many Requests
Retry-After: 37
Content-Type: application/json

{"code": 429, "message": "..."}
```

## Size limits

The size of each request can't be larger than 1MB. Requests that hit this limit will receive a `413 Content too large` response.

