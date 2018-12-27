# Introduction
Welcome to the reference for the Quire REST API!

<a href="http://en.wikipedia.org/wiki/REST_API" target="_blank">REST</a> is a web-service protocol that lends itself to rapid development by using everyday HTTP and JSON technology.

The Quire REST API provides a broad set of operations and resources that:

  * Consistently doing repetitive or tedious tasks.
  * Chaining a process together by responding to changes.
  * Pulling information from other locations like email or Evernote into Quire.
  * Customizing Asana for your team’s processes and workflows.

Want to share your opinion on how our API works for you? <a href="https://quire.io/w/Quire_Feedbacks" target="_blank">Tell us how you feel </a>about using our API and what we can do to make it better.

# API Changelog
You can find the <a href="https://github.com/quire-api/quire-api" target="_blank">Changelog</a> of the API Reference in the Quire Community.

# Authentication

## OAuth v2.0

Quire recommends that you use OAuth v2.0 to authenticate to the Quire REST API. 

Quire recommends you to create a dedicated API user with API write access on a tenant when authenticating via OAuth, and then create an OAuth client for this user. See <a href="https://knowledgecenter.zuora.com/CF_Users_and_Administrators/A_Administrator_Settings/Manage_Users/Create_an_API_User" target="_blank">Create an API User</a> for how to do this. By creating a dedicated API user, you can control permissions of the API user without affecting other non-API users.

If a user is deactivated, all of the user's OAuth clients will be automatically deactivated.

Authenticating via OAuth requires the following steps:
1. Create a Client
2. Generate a Token
3. Make Authenticated Requests

### Create a Client

You must first [create an OAuth client](https://knowledgecenter.zuora.com/CF_Users_and_Administrators/A_Administrator_Settings/Manage_Users#Create_an_OAuth_Client_for_a_User) in the Zuora UI. To do this, you must be an administrator of your Zuora tenant. This is a one-time operation. You will be provided with a Client ID and a Client Secret. Please note this information down, as it will be required for the next step.

**Note:** The OAuth client will be owned by a Zuora user account. If you want to perform PUT, POST, or DELETE operations using the OAuth client, the owner of the OAuth client must have a Platform role that includes the "\API Write Access" permission.

### Generate a Token

After creating a client, you must make a call to obtain a bearer token using the [Generate an OAuth token](https://www.zuora.com/developer/api-reference/#operation/createToken) operation. This operation requires the following parameters:
- `client_id` - the Client ID displayed when you created the OAuth client in the previous step
- `client_secret` - the Client Secret displayed when you created the OAuth client in the previous step
- `grant_type` - must be set to `client_credentials`

**Note**: The Client ID and Client Secret mentioned above were displayed when you created the OAuth Client in the prior step. The [Generate an OAuth token](https://www.zuora.com/developer/api-reference/#operation/createToken) response specifies how long the bearer token is valid for. Call [Generate an OAuth token](https://www.zuora.com/developer/api-reference/#operation/createToken) again to generate a new bearer token.

### Make Authenticated Requests

To authenticate subsequent API requests, you must provide a valid bearer token in an HTTP header:

`Authorization: Bearer {bearer_token}`

If you have [Zuora Multi-entity](https://www.zuora.com/developer/api-reference/#tag/Entities) enabled, you need to set an additional header to specify the ID of the entity that you want to access. You can use the `scope` field in the [Generate an OAuth token](https://www.zuora.com/developer/api-reference/#operation/createToken) response to determine whether you need to specify an entity ID.

If the `scope` field contains more than one entity ID, you must specify the ID of the entity that you want to access. For example, if the `scope` field contains `entity.1a2b7a37-3e7d-4cb3-b0e2-883de9e766cc` and `entity.c92ed977-510c-4c48-9b51-8d5e848671e9`, specify one of the following headers:
- `Zuora-Entity-Ids: 1a2b7a37-3e7d-4cb3-b0e2-883de9e766cc`
- `Zuora-Entity-Ids: c92ed977-510c-4c48-9b51-8d5e848671e9`

**Note**: For a limited period of time, Zuora will accept the `entityId` header as an alternative to the `Zuora-Entity-Ids` header. If you choose to set the `entityId` header, you must remove all "-" characters from the entity ID in the `scope` field.

If the `scope` field contains a single entity ID, you do not need to specify an entity ID.

## Concurrent Request Limits

Quire enforces tenant-level concurrent request limits. See <a href="https://knowledgecenter.zuora.com/BB_Introducing_Z_Business/Policies/Concurrent_Request_Limits" target="_blank">Concurrent Request Limits</a> for more information.

## Timeout Limit

If a request does not complete within 120 seconds, the request times out and Quire returns a Gateway Timeout error.
