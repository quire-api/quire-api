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

Quire uses <a href="https://tools.ietf.org/html/rfc6749">OAuth v2.0</a> to authenticate your app to access the Quire REST API on behalf of users without getting their password.

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

Alternatively, you can prepare a link on your website to redirect your users to Quire for authorization.

The syntax of the link is as follows:

`https://quire.io/oauth?client_id=your-client-ID&redirect_uri=your-redirect-uri`

| Parameter Name | Description
|------|------
| `client_id` | Your client ID.
| `redirect_uri` | URL to redirect user back upon completion (optional).
| `state` | It can be any string and will be passed back upon completion (optional). 

Once your user clicks the link, he will be redirected to Quire and start the same authorization flow as shown above.

The `state` parameter should be used to avoid forgery attacks by passing in a value that's unique to the user you're authenticating and checking it when authorization completes.

If the `redirect_uri` parameter is not specified, the URL defined in the app's registration will be used.

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

In each request, the access token must be put in the header. The header name is `Authorization` and the value is `Bearer your_token`. For example,

```
Authorization: Bearer jdakjo23jf18axbe21z2maewmldjqma12qr912
```

### Token Expiration

A refresh token might stop working for one of these reasons:

* The user has revoked your app's access.
* The refresh token has not been used for 6 months.

# WebHook

When one event on Quire is triggered, the system will send a payload to the webhook's configured URL.

> A hook is used by Quire to call an app, while Quire API is used by an app to call Quire.

## Notifications

A notification is the information about a update (aka., an activity). Here is an example:

```
{
  "type": "notification",
  "token": "a-token-defined-by-you",
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

## Registration for notifications

To receive notifications, the app can *follow* tasks or projects it cares. By sending a `PUT` request to the URL it'd like to follow. To add a follower, the body of the request can be:

```
{
  "addFollowers": ["app"]
}
```

Where `app` is a keyword. It indicates the app would like to add itself to the followers.

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
  "type": "notification"
  "team": "extra101"
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

| Plan | Maximum requests per organization, per *minute* | Maximum requests per organization, per *hour*
|---------|------|-------
| Free | 20  | 800

> Note: the limit is per-organization. It sums up the total number of all accesses from all applications for each organization.

## Size limits

The size of each request can't be larger than 2MB. Requests that hit this limit will receive a `413 Payload too large` response.

# Status codes

| Code | Meaning               | Description                                               
|------|-----------------------|--------------------------------------------------------------------------
| 200  | Success               | Request successful
| 400  | Bad Request           | You're using a wrong parameter, or passing incorrect data.
| 401  | Unauthorized          | Your API key is wrong.
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
| 469 | Quota exceeded.
| 500 | General invocation error. Most likely, an internal error.
