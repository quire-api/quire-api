# Introduction
Welcome to the reference for the Quire REST API!

The Quire REST API provides a broad set of operations and resources that:

  * Consistently do repetitive or tedious tasks.
  * Chain a process together for your team’s process and workflow.
    * Pull information from other locations such as email and <a href="https://evernote.com/" target="_blank">Evernote</a> into Quire.
    * Push information from Quire to other locations such as email and <a href="https://zapier.com/" target="_blank">Zapier</a>. 

Want to share your thoughts on how Quire API works for you? <a href="/feedback">Tell us how you feel </a>about using our API and what we can do to make it better.

> <a href="http://en.wikipedia.org/wiki/REST_API" target="_blank">REST</a> is a web-service protocol for rapid development by using HTTP and JSON technology.

# API Changelog

You can find the <a href="https://github.com/quire-api/quire-api/blob/master/CHANGES.md" target="_blank">Changelog</a> of the API Reference in the Quire Community.

# Authentication

## OAuth v2.0

Quire uses <a href="https://tools.ietf.org/html/rfc6749">OAuth v2.0</a> to authenticate your app to access the Quire REST API on behalf of users without getting their password.

Authenticating via OAuth2 requires the following steps:

1. Register Your Application on Quire
2. Ask a Quire User to Grant Access to Your Application
3. Retrieve an Access Token
4. Make Authenticated Requests

### Register Your Application on Quire

![Quire API](https://d12y7sg0iam4lc.cloudfront.net/s/img/tutorial/openAPI.png)

**1. Give your app a cool name**

Your app deserves a cool name that lives up to its wide broad of great features. 

All of the app users will see this name in public, so think carefully!

**2. Choose the Quire Organization that your app belongs to**

You can choose an organization in Quire that your app belongs to.

If one day you decide to leave the organization, you will lose the authority to manage the app. 

**3. Redirect URL**

When users grant your app authorization request, users will be directed to the configured URL that you’ve set. 

**4. Choose permission scopes** 

You can set permission on what your app can do with Quire. There are several options for you to choose from. 

*Note: If none of the options is selected, the app can only read user’s data.*
 
**5. Development Client ID and Client Secret**

![Quire Open API](https://d12y7sg0iam4lc.cloudfront.net/s/img/tutorial/client-id-client-secret.png)

The Client ID and Client secret will be automatically generated as you create an app. 

The Client ID is a unique ID to identify your app.

You should keep your client secret safe, which means you should never share your client secret with anyone. If you choose to regenerate the client secret, the old one will immediately become invalid. 

**6. Update your App**

If your app hasn’t been published to Quire App Directory, it will remain as unpublished status. You can still use the configured shareable link in the Developer App Console Distribution to share the app with other users for testing or integration. 

When you make changes to the app, you can use the shareable link to access the development copy as well. Working on your development copy will not affect your live App Directory app. When your updated app is ready to be published and replaced the old version on Quire App Directory, your published app will have a different Client ID to the unpublished one.

There are two sets of Client ID and Client Secret. 

 * `Development set` - should be used during developing and testing internally of the app. 
 * `Production set` - should be used once your app is ready and published on Quire App Directory.

## Fulfill Authorization Request

### Ask a Quire User to Grant Access to Your Application

Once registering your application, you can ask your user to grant access to your application.

The authorization endpoint lets users grant your app access to the requested permissions. 

The authorization endpoint should look like this:

`https://quire.io/oauth?client_id=your-client-ID&redirect_uri=your-redirect-uri`

![Quire Grant Access](https://d12y7sg0iam4lc.cloudfront.net/s/img/tutorial/Quire-API-Authorization.png)

After your user clicks `Allow`, the access will be granted, and he will be redirected to the URL you specified in the `redirect_uri` parameter with an authorization code provided as a query parameter called `code`.

After your app is granted, you can have an authorization code to exchange access token for access Quire API. The `redirect_uri` is optional. If not being specified, we will automatically use the one that is previously detected in the app. If specified, the redirect URL must start with the prefix of the one that was previously detected in the app.

| Parameter | Value |
| --- | --- |
| client\_id | {your-client-ID} |
| redirect\_uri | Optional. The redirect URL after granted. If specified, it must match the redirect URL specified in your app's config. Otherwise, the configured URL will be used. |
| state | Optional. A random string generated by your app to protect from XSRF. |

### Retrieve Access Token

To retrieve the access token, you have to post a request to `https://quire.io/oauth/token` with the following data:

| Parameter | Value |
| --- | --- |
| grant\_type | authorization\_code |
| code | {your-authorization-code} |
| client\_id | {your-client-ID} |
| client\_secret | {your-client-secret} |

Then, the access token will be returned in the response's body.

```json
{
  "access_token":"ACCESS_TOKEN",
  "token_type": "bearer",
  "expires_in":2592000,
  "refresh_token":"REFRESH_TOKEN"
}
```
The token should be kept carefully and permanently since you need it to access every Quire API.

### Use Access Token to Access Quire API

In each request, the access token must be put in the header. The header name is `Authorization` and the value is `Bearer your_token`.

After you exchange the access token, your app can make requests to Quire API on behalf of the authorized users.

```bash
curl -H 'Authorization: Bearer {access_token}' \
https://quire.io/api/user/id/me
```
```json
{
  "email": "john@gmail.cc",
  "website": "https://coolwebsites.com",
  "id": "My_ID",
  "description": "This is *cool*!",
  "url": "https://quire.io/u/My_ID",
  "nameText": "My Name",
  "nameHtml": "My Name",
  "descriptionText": "This is cool!",
  "descriptionHtml": "This is <i>cool</i>!",
  "image": "https://quire.s3.amazonaws.com/oid/image.jpg",
  "iconColor": "37",
  "name": "My Name",
  "oid": "Dyh2YkFcu9uLgLFIeN1kB4Ld"
}
```
### Token Expiration

A refresh token might stop working for one of these reasons:

* The user has revoked your app's access.
* The refresh token has not been used for 6 months.

# Publish App

![Quire Publish App](https://d12y7sg0iam4lc.cloudfront.net/s/img/tutorial/Quire-API-Publish-App.png)

By default, your app will be set as Private. You can change the app distribution to Public so that other Quire users can install your app to their workspace as well.

If your app is made available on Quire App Directory and you want to delete the app, you should communicate with your users first before depreciating the app.

# WebHook

When one event on Quire is triggered, the system will send a payload to the webhook's configured URL.

> A webhook is used by Quire to call an app, while Quire API is used by an app to call Quire.
> To receive these events, you have to specify a valid URL for Webhooks when configuring your app.

## System Events

A system event is used to notify your app about system or app's activities.

### Token Expiration

When the user has revoked your app's access, or the token has been expired, an event will be sent. You can clean up your storage if ncessary.

```
{
  "type": "system",
  "token": "hook-token-defined-by-you",
  "secret": "secret-defined-by-you",
  "data": {
    "type": "token-revocation",
    "token": the-refresh-token
  }
}
```

## Notification Events

A notification is the information about a update (aka., an activity). Here is an example:

```
{
  "type": "notification",
  "token": "hook-token-defined-by-you",
  "secret": "secret-defined-by-you",
  "data": {
    "type": 0, //activity's type
    "when": "2019-09-30T08:20:12.000Z",
    "what": {
      "oid": "YxjapXXRCOYxoaiCT4tT3OQm", //OID of a task, project, or organization depending on type
      "id": 101,
      "name": "Brand new start"
    },
    "user": {
      "oid": "1AbDEFed2A5031BEDDweqmde", //OID of the user
      "id": "john.doer",
      "name": "John Doer"
    },
    "message": "<a href=\"https://quire.io/u/john.doer\">John Doer</a> added <a href=\"https://quire.io/w/MyProjects/101\">Brand new start</a>",
    "text": "John Doer added Brand new start",
    "url": "https://quire.io/w/MyProjects/101"
  } 
}
```

> There is an optional field called `value` in the map of the `data` field. It carries the detailed information in a map instance. For example, it carries the assignee's ID, name and URL if it is an assignment.
> Also, if the notification is about *start* or *due*, there will be another field called `due` in the map of the `data` field. It is the date time formatted in user's locale and time zone.

## Registration for notifications

If the app wants to receive notifications of a specific projects or tasks, it can *follow* the projects or apps by sending a `PUT` request to the URL. To add a follower, the body of the request can be:

```
{
  "addFollowers": ["app"]
}
```

Where `app` is a keyword. It indicates that the app would like to add to its followers.

In additions, you can specify additional information that will be passed as part of a notification int the following syntaxes.

**Syntax 1**

```
"app|team|channel"
```

where `app` is a keyword while `team` and `channel` are application specific. That is, you can pass any value to `team` and `channel`. For example,

```
{
  "addFollowers": ["app|extra101"]
}
```

Then, the notification will carry additional field called `team` with the value `"extra101"`:

```
{
  "type": "notification",
  "team": "extra101",
  "data": {
    //refer the Notifications section for details
  }
}
```

Another example:

```
{
  "addFollowers": ["app|extra101|channel9"]
}
```

You'll get:

```
{
  "type": "notification",
  "team": "extra101",
  "channel": "channel9",
  "data": {
    //refer the Notifications section for details
  }
}
```

**Syntax 2**

```
"app|/path"
```

where `app` is a keyword, and `/path` is application specific. The path will be appended to the app's hook URL. For example, assume the app's hook URL is `"https://super.app/hooks/standard"`, and the follower "app|/soc/id279/channel51". Then, the notification will be posted the following URL: `"https://super.app/hooks/standard/soc/id279/channel51"`.

If you'd like to pass additional information in this syntax, you can append it as follows.

```
"app|/path|channel"
```

For example, `app|/soc/id8|box51`. Then, `box51` will be part of the JON object sent to the hook URL.

```
{
  "type": "notification"
  "channel": "box51"
  "data": {
    //refer the Notifications section for details
  }
}
```

# Rate limits

To protect the stability of the API and keep it available to all users, Quire enforces multiple kinds of rate limiting. 
Requests that hit any of our rate limits will receive a `429 Too Many Requests` response.
We may change these quotas or add new quotas in the future.

Here are the limits for free plans.

| Plan | Maximum requests per organization, per *minute* | Maximum requests per organization, per *hour*
|---------|------|-------
| Free | 25  | 120

> Note: the limit is per-organization. It sums up the total number of all accesses from all applications for each organization.
> For more quota, please refer to [https://quire.io](Pricing).

## Size limits

The size of each request can't be larger than 2MB. Requests that hit this limit will receive a `413 Payload too large` response.

# Status codes

| Code | Meaning               | Description                                               
|------|-----------------------|--------------------------------------------------------------------------
| 200  | Success               | Request successful
| 400  | Bad Request           | You're using a wrong parameter, or passing incorrect data.
| 401  | Unauthorized          | Invalid or expired token.
| 403  | Forbidden             | Not authorized to access the resource.
| 404  | Not Found             | The specified resource could not be found.
| 405  | Method not Allowed    | Method not allowed or supported.
| 409  | Conflict              | There is already a resource with the same criteria.
| 429  | Too Many Requests     | Exceeded the [rate limit](#section/Rate-limits) for API calls
| 500  | Internal Server Error | There is an unexpected error.
| 503  | Service Unavailable   | Server is down for maintenance.

## Error responses

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
