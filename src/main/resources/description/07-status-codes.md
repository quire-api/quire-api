# Status Codes

| Code | Meaning                | Description
|------|------------------------|--------------------------------
| 200  | Success                | Request successful
| 400  | Bad Request            | You're using a wrong parameter, or passing incorrect data.
| 401  | Unauthorized           | Invalid or expired token.
| 402  | Payment required       | The organization’s subscription does not permit use of this app.
| 403  | Forbidden              | Not authorized to access the resource.
| 404  | Not Found              | The specified resource could not be found.
| 405  | Method not allowed     | Method not allowed or supported.
| 409  | Conflict               | There is already a resource with the same criteria.
| 413  | Content too large      | The request's content is larger than 1MB.
| 418  | Not valid JSON content | The request's content doesn't appear to be JSON.
| 429  | Too Many Requests      | Exceeded the [rate limit](#rate-limits) for API calls
| 500  | Internal Server Error  | There is an unexpected error.
| 503  | Service Unavailable    | Server is down for maintenance.

## Error Responses

The following JSON data is returned in the response body when an error occurs.

```json
{
	"code": a_number,
  "message": "an error message here"
}
```

| Error Code | Meaning
|-----|-----------------------
| 100 | General authentication error.
| 400 | Bad request including wrong request body, wrong parameter and so on.
| 401 | Invalid or expired token.
| 403 | Forbidden.
| 404 | Resource not found.
| 405 | Method not allowed.
| 413 | Request too large.
| 429 | Too many invocations.
| 469 | Quota exceeded, such as number of projects and number of members.
| 500 | General invocation error. Most likely, an internal error.

