# Authentication

> For full setup instructions, see our [Complete Setup Guide](https://quire.io/blog/p/Create-Your-Quire-App-with-Quire-API.html) on the Quire blog.

## OAuth v2.0

Quire uses <a href="https://tools.ietf.org/html/rfc6749">OAuth v2.0</a> to authenticate your app and access the Quire REST API on a user’s behalf—without requiring the user’s password.

Authenticating via OAuth2 requires the following steps:

1. Register Your Application on Quire
2. Ask a Quire User to Grant Access to Your Application
3. Retrieve an Access Token
4. Make Authenticated Requests

### Register Your Application on Quire

![Quire API](https://d12y7sg0iam4lc.cloudfront.net/s/img/tutorial/openAPI.png)

**1. Give your app a cool name**

Your app deserves a cool name that lives up to its broad range of great features. 

All of the app users will see this name in public, so think carefully!

**2. Choose the Quire Organization that your app belongs to**

You can choose an organization in Quire that your app belongs to.

If one day you decide to leave the organization, you will lose the authority to manage the app. 

**3. Redirect URL**

When users grant your app authorization request, users will be directed to the configured URL that you’ve set. 

**4. Choose permission scopes** 

You can set permission on what your app can do with Quire. There are several options for you to choose from. 

| Scope | Allows the app to |
| --- | --- |
| Manage profiles | Edit project and organization settings. |
| Create tasks | Add new tasks. |
| Manage tasks | Edit, assign, and schedule tasks and sublists. |
| Delete tasks | Delete tasks. |
| Manage tags | Add, edit, and remove tags. |
| Manage statuses | Add, edit, and remove statuses. |
| Manage notifications | Send notifications to users. |

*Note: Every app can read the organizations and projects a user authorized. If none of the options is selected, the app can only read user’s data, and Quire shows it as **Read Only**.*

The scopes are fixed when a user grants access. If you change them later, a user gets the new ones only after authorizing your app again. The same goes for the Webhook URL: following a project or task with `app` (see [WebHook](#webhook)) works only if your app had a Webhook URL when the user granted access (`403` otherwise).
 
**5. Development Client ID and Client Secret**

![Quire Open API](https://d12y7sg0iam4lc.cloudfront.net/s/img/tutorial/client-id-client-secret.png)

The Client ID and Client secret will be automatically generated as you create an app. 

The Client ID is a unique ID to identify your app.

You should keep your client secret safe, which means you should never share your client secret with anyone. If you choose to regenerate the client secret, the old one will immediately become invalid. 

**6. Update your App**

If your app hasn't been published to Quire App Directory, it will remain as unpublished status. You can still use the configured shareable link in the Developer App Console Distribution to share the app with other users for testing or integration. 

When you make changes to the app, you can use the shareable link to access the development copy as well. Working on your development copy will not affect your live App Directory app. When your updated app is ready to be published and replace the old version on Quire App Directory, your published app will have a different Client ID to the unpublished one.

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

After your app is granted, you can have an authorization code to exchange access token for access Quire API. The `redirect_uri` is optional. If omitted, the Redirect URL configured for your app is used. If specified, it must start with the configured Redirect URL.

| Parameter | Value |
| --- | --- |
| client\_id | {your-client-ID} |
| redirect\_uri | Optional. The redirect URL after granted. If specified, it must start with the Redirect URL configured for your app (for a loopback URL, any port is accepted; see below). Otherwise, the configured URL will be used. |
| state | Optional. A random string generated by your app to protect from XSRF. |

#### Loopback redirect URIs (native / CLI apps)

When the registered redirect URI's host is a loopback address — `127.0.0.1`, `[::1]`, or `localhost` — Quire accepts **any port** at request time, per <a href="https://datatracker.ietf.org/doc/html/rfc8252#section-7.3">RFC 8252 §7.3</a>. Scheme, host, and path must still match the registered URI exactly.

For example, if your app registers `http://127.0.0.1/callback`, all of these are valid `redirect_uri` values at the authorize step (and must be echoed verbatim when exchanging the code):

```
http://127.0.0.1/callback
http://127.0.0.1:8866/callback
http://127.0.0.1:55123/callback
```

This lets installed apps (CLIs, desktop tools) bind to any free ephemeral port on the loopback interface and still receive the OAuth callback, without requiring users to keep a fixed port free. The rule applies only to loopback hosts — non-loopback redirect URIs continue to require a prefix match.

### Retrieve Access Token

To retrieve the access token, you have to post a request to `https://quire.io/oauth/token` with the following data:

| Parameter | Value |
| --- | --- |
| grant\_type | authorization\_code |
| code | {your-authorization-code} |
| client\_id | {your-client-ID} |
| client\_secret | {your-client-secret} |
| redirect\_uri | Required if you specified `redirect_uri` in the previous authorization step. The value must be identical to the one used there. |

```bash
curl -X POST \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=authorization_code&code=Your_code_from_previous_step&client_id=Your_app_client_id&client_secret=Your_app_client_secret&redirect_uri=Your_redirect_uri" \
  https://quire.io/oauth/token
```

The authorization code expires in 10 minutes and can be used only once.

Then, the access token will be returned in the response's body.

```json
{
  "access_token":"ACCESS_TOKEN",
  "token_type": "bearer",
  "expires_in":3600,
  "refresh_token":"REFRESH_TOKEN"
}
```

The access token expires in 1 hour (`expires_in` is in seconds), and each request made with it extends it. Once it expires, use the refresh token to get a new one. The refresh token should be kept carefully and permanently since you need it to access every Quire API.

If a request to `https://quire.io/oauth/token` fails, the response is `400` with an OAuth error, for example:

```json
{
  "error": "invalid_grant",
  "error_description": "The 'code' provided was invalid."
}
```

### Refresh Access Token

To get a new access token, post a request to `https://quire.io/oauth/token` with the following data:

| Parameter | Value |
| --- | --- |
| grant\_type | refresh\_token |
| refresh\_token | {your-refresh-token} |
| client\_id | {your-client-ID} |
| client\_secret | {your-client-secret} |

```bash
curl -X POST \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=refresh_token&refresh_token=Your_refresh_token&client_id=Your_app_client_id&client_secret=Your_app_client_secret" \
  https://quire.io/oauth/token
```

The response has the same shape as above, with a new `access_token`. The `refresh_token` stays the same, so keep using it.

### PKCE Support

Quire supports <a href="https://tools.ietf.org/html/rfc7636">PKCE (RFC 7636)</a> for public clients such as single-page apps and installed CLIs. To use it:

1. Add `code_challenge` and `code_challenge_method=S256` to the authorization request.
2. Send `code_verifier` instead of `client_secret` when exchanging the authorization code for a token.

> **Note:** Only `S256` is supported. The `redirect_uri` rule above also applies — if you specified one at the authorization step, you must echo the same value when exchanging the code.

#### Access token lifetime

PKCE-issued access tokens are valid for **7 days** (vs. 1 hour for confidential clients) and aren't extended by use. The response does **not** include a `refresh_token` — public clients have no `client_secret` with which to authenticate the refresh-token grant. When the access token expires, run the authorization flow again to obtain a new one.

This applies whenever `code_challenge` is sent, even by a confidential client. If your app needs a refresh token, don't use PKCE.

The `expires_in` field of the token response reflects the actual remaining lifetime, in seconds.

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
* The user's account has been deleted.
* The user granted only some organizations and projects, and all of them have been deleted.
* Your app has been deleted.

Also, requests are rejected with `401` if the user hasn't signed in to Quire for more than 400 days, unless one of the user's organizations is on the Professional plan or above.

