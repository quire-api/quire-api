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

Authenticating via OAuth2 requires the following steps:
1. Register Your Application on Quire
2. Ask a Quire User to Grant Access to Your Application
3. Retrieve an Access Token
4. Make Authenticated Requests

### Register Your Application on Quire

*TBD*

### Ask a Quire User to Grant Access to Your Application

Once registering your application, you can ask your user to grant access to your application.

Your user can grant the access of an organization or a project by clicking the `Integer | Other` menu-item on the context menu, and then select your application. 

*Image*

After your user clicks `Accept`, the access will be granted, and he will be redirected to the URL you specified in the `redirect_uri` parameter.

Alternatively, you can prepare a link on your website for your users to click. Once your user clicks it, he will be redirected to Quire and start the same authorization flow as shown above.

The syntax of the link is as follows:

`https://quire.io/oauth?client_id=your-client-ID&redirect_uri=your-redirect-uri`

| Parameter Name | Description
|------|------
| `client_id` | Your client ID.
| `redirect_uri` | If the access is being granted by user, this is where the user will be redirected after authorization.
| `state` | Optional. It can be any string and will be returned with `redirect_uri`.

### Retrieve an Access Token

To access Quire API, you need an access token. You can retrieve the token as follows.

1. Retrieve the access code.

As described in the previous section, your user will be redirected to the URL you specified in `redirect_uri` once he granted the access. The URL will carry an access code in the `code` parameter. You can retrieve the access code from it.

2. Use the access code to retrieve the access token.

To retrieve an access token, you have to <a href="https://www.w3schools.com/jquery/ajax_post.asp" target="_blank">post</a> a request to `https://quire.io/oauth/token` with the following data.

`client_id=your-client-ID&grant_type=authorization_code&code=your-access-code&client_secret=your-client-secret`

Then, the access token will be returned in the response's body. You shall save the token carefully and permanently. You need to access each Quire API.

### Make Authenticated Requests

*TBD*

# Rate limits

To protect the stability of the API and keep it available to all users, Quire enforces multiple kinds of rate limiting. 
Requests that hit any of our rate limits will receive a `429 Too Many Requests` response.
We may change these quotas or add new quotas in the future.

| Plan | Maximum requests per organization, per *minute* | Maximum requests per organization, per *hour*
|---------|------|-------
| Free | 120  | 500

> Note: the limit is per-organization. It sums up the total number of all accesses from all applications for each organizaton.

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