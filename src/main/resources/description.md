# Introduction
Welcome to the reference for the Quire REST API!

The Quire REST API provides a broad set of operations and resources that:

  * Consistently doing repetitive or tedious tasks.
  * Chaining a process together for your team’s processes and workflows.
    * Pulling information from other locations like email and <a href="https://evernote.com/" target="_blank">Evernote</a> into Quire.
    * Pushing information from Quire into other locations like email and <a href="https://zapier.com/" target="_blank">Zapier</a>. 

Want to share your opinion on how our API works for you? <a href="/feedback">Tell us how you feel </a>about using our API and what we can do to make it better.

> <a href="http://en.wikipedia.org/wiki/REST_API" target="_blank">REST</a> is a web-service protocol for rapid development by using HTTP and JSON technology.

# API Changelog
You can find the <a href="https://github.com/quire-api/quire-api/blob/master/CHANGES.md" target="_blank">Changelog</a> of the API Reference in the Quire Community.

# Authentication
## OAuth v2.0

Quire recommends that you use OAuth v2.0 to authenticate to the Quire REST API. 

Quire recommends you to create a dedicated API user with API write access on a tenant when authenticating via OAuth, and then create an OAuth client for this user. By creating a dedicated API user, you can control permissions of the API user without affecting other non-API users.

If a user is deactivated, all of the user's OAuth clients will be automatically deactivated.

Authenticating via OAuth requires the following steps:
1. Create a Client
2. Generate a Token
3. Make Authenticated Requests

### Create a Client

*TBD*

### Generate a Token

*TBD*

### Make Authenticated Requests

*TBD*

# Rate limits

To protect the stability of the API and keep it available to all users, Quire enforces multiple kinds of rate limiting. 
Requests that hit any of our rate limits will receive a `429 Too Many Requests` response.
We may change these quotas or add new quotas in the future.

| Plan    | Maximum requests per minute | Maximum requests per hour
|---------|-----------------------------|-----------------------------
| Free    | 120                         | 350

# Status codes

| Code | Meaning               | Description                                               
|------|-----------------------|--------------------------------------------------------------------------
| 200  | Success               | Request successful
| 400  | Bad Request           | You're using a wrong parameter, or passing incorrect data.
| 401  | Unauthorized          | Your API key is wrong.
| 403  | Forbidden             | Not authorized to access the resource.
| 404  | Not Found             | The specified resource could not be found.
| 409  | Conflict              | There is already a resource with the same criteria.
| 429  | Too Many Requests     | Exceeded the [rate limit](#section/Rate-limits) for API calls
| 500  | Internal Server Error | There is an unexpected error.
| 503  | Service Unavailable   | Server is down for maintenance.

## Error responses
The following JSON data is returned in the response body when an error occurs.
```json
{
  "message": "Not found"
}
```