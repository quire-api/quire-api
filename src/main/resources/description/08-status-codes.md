# Status Codes

| Code | Meaning                | Description
|------|------------------------|--------------------------------
| 200  | Success                | Request successful
| 204  | No Content             | Request successful; the endpoint has no response body to return.
| 400  | Bad Request            | You're using a wrong parameter, or passing incorrect data.
| 401  | Unauthorized           | Invalid or expired token.
| 402  | Payment required       | The organization’s subscription does not permit use of this app, the subscription has expired, or a plan quota/limit has been reached (body `code` `469` or `470` — see below).
| 403  | Forbidden              | Not authorized to access the resource, or temporarily blocked (body `code` `471` — see below).
| 404  | Not Found              | The specified resource could not be found.
| 405  | Method not allowed     | Method not allowed or supported.
| 409  | Conflict               | There is already a resource with the same criteria.
| 413  | Content too large      | The request's content is larger than 1MB.
| 418  | Not valid JSON content | The request's content doesn't appear to be JSON.
| 429  | Too Many Requests      | Exceeded the [rate limit](#rate-limits) for API calls (and only that; carries a `Retry-After` header).
| 500  | Internal Server Error  | There is an unexpected error.
| 503  | Service Unavailable    | Server is down for maintenance.

## Error Responses

All `4xx` and `5xx` responses share a common JSON envelope, represented by the
[`ErrorResponse`](#definition-ErrorResponse) schema:

```json
{ "code": 400, "message": "Invalid value for `priority`: foo (expected -1, 0, 1, or 2)." }
```

- `code` is the HTTP status code, echoed in the body — except for `402`, where it is `469`
  (a plan quota/limit has been reached) or `470` (the subscription has expired), and for
  `403`, where it is `471` if the account is temporarily blocked. These let clients tell
  the cases apart without parsing `message`.
- `message` is a human-readable description, typically naming the offending field and —
  for `400` — the expected format. Long submitted values are truncated.

Each endpoint's `Responses` block lists the specific status codes that endpoint can emit;
the body shape is this same envelope everywhere.

| Error Code | Meaning
|-----|-----------------------
| 100 | General authentication error.
| 400 | Bad request including wrong request body, wrong parameter and so on.
| 401 | Invalid or expired token.
| 403 | Forbidden.
| 404 | Resource not found.
| 405 | Method not allowed.
| 413 | Request too large.
| 429 | Too many invocations (the [rate limit](#rate-limits)).
| 469 | Quota exceeded, such as number of projects, number of members, and other plan limits. Returned with HTTP `402`.
| 470 | Subscription expired. Returned with HTTP `402`.
| 471 | Temporarily blocked; try again later. Returned with HTTP `403`.
| 500 | General invocation error. Most likely, an internal error.

