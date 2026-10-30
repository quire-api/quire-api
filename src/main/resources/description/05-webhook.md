# WebHook

When one event on Quire is triggered, the system will send a payload to the webhook's configured URL.

> A webhook is used by Quire to call an app, while Quire API is used by an app to call Quire.
> To receive these events, you have to specify a valid URL for Webhooks when configuring your app.

For a complete guide, see our [blog post](https://quire.io/blog/p/webhook.html).

Every payload carries `token`, the Webhook Token you set when configuring your app, and `secret`, your app's Client Secret. Check both before trusting a payload, and ignore it if either doesn't match.

## System Events

A system event is used to notify your app about system or app's activities.

### Token Expiration

When the token has been expired or revoked, an event will be sent to your app. You can clean up your storage if necessary.

```
{
  "type": "system",
  "token": "hook-token-defined-by-you",
  "secret": "your-client-secret",
  "data": {
    "type": "token-revocation",
    "token": "the-refresh-token"
  }
}
```

### Host Revocation

When the user revokes the grant to a host (either a project or an organization), an event will be sent to your app. You can clean up your storage if necessary.

```
{
  "type": "system",
  "token": "hook-token-defined-by-you",
  "secret": "your-client-secret",
  "data": {
    "type": "host-revocation",
    "token": "the-refresh-token",
    "host": "host-oid",
    "otype": "Project"
  }
}
```

* `host` is the OID of the project or organization, and `otype` is `"Project"` or `"Organization"`.
* `host` is `"*"`, with no `otype`, when the user revokes a grant that covered all their organizations and projects but still has other grants for your app.

## Notification Events

A notification is the information about an update (aka., an activity). Here is an example:

```
{
  "type": "notification",
  "token": "hook-token-defined-by-you",
  "secret": "your-client-secret",
  "projectSummary": {
    "oid": "project-oid-where-event-occurred",
    "id": "project-id"
  },
  "organizationSummary": {
    "oid": "organization-where-event-occurred",
    "id": "organization-id"
  },
  "data": {
    "type": 0,
    "when": 1569831612000,
    "what": {
      "oid": "YxjapXXRCOYxoaiCT4tT3OQm",
      "id": 101,
      "name": "Brand new start",
      "parent": {
        "oid": "parent-oid",
        "id": 100,
        "parent": {
          "oid": "grand-parent-oid",
          "id": 99
        }
      }
    },
    "user": {
      "oid": "1AbDEFed2A5031BEDDweqmde",
      "id": "john.doer",
      "name": "John Doer"
    },
    "message": "<a href=\"https://quire.io/u/john.doer\">John Doer</a> added <a href=\"https://quire.io/w/MyProjects/101\">Brand new start</a>",
    "text": "John Doer added Brand new start",
    "url": "https://quire.io/w/MyProjects/101"
  } 
}
```

* `type` is the activity's type (see [Activity Types](https://github.com/quire-api/quire-api/blob/master/docs/activity_types.md)), and `when` is when it happened, in milliseconds since the epoch.

* `what` is the task, project, or organization the activity is about, depending on `type`. It is the full record, as [Get an existing task by its ID](#operation--task-id--projectId---taskId--get), [Get a project by ID](#operation--project-id--id--get) or [Get an organization by ID](#operation--organization-id--id--get) returns it, except that a task carries no formula field values.

* `user` is the user who did it.

* The `data` map may also include an optional `value` field, which provides detailed information as a map. For example, for an assignment notification, `value` includes the assignee’s ID, name, and URL.

* For a reminder's change (types `84`, `87`, `88`, `192`, `193` and `194` in [Activity Types](https://github.com/quire-api/quire-api/blob/master/docs/activity_types.md)), `value` is the reminder itself, as [Get a reminder by OID](#operation--reminder--oid--get) returns it, or only its `oid` once removed.

* If the notification is about a *start* or *due* change, the `data` map will include an additional `due` field. This value is a date/time formatted in the user’s locale and time zone.

* If `what` is a task, its `parent` field holds the parent's `oid` and `id`, or `null` if the task has no parent. If the task’s parent also has a parent, the map includes a nested `parent` field as well.

* The `projectSummary` and `organizationSummary` fields provide information about the project and organization where the event occurred.

* There is an additional field, `taskSummaries`, for activities that can affect multiple tasks (for example, removing a task that has subtasks). This field is a list of `map` instances. Each `map` represents a task that was changed. If any of that task's subtasks were also changed, they are included in the map’s `tasks` field.
    - Please refer to [Activity Types | taskSummaries](https://github.com/quire-api/quire-api/blob/master/docs/activity_types.md#tasksummaries).

## Registration for Notifications

> For instructions on updating a project or a task, see [Update a project by ID](#operation--project-id--id--put) and [Update an existing task by its ID](#operation--task-id--projectId---taskId--put).

If the app wants to receive notifications of specific projects or tasks, it can *follow* the projects or tasks by sending a `PUT` request to the URL. To add a follower, the body of the request can be:

**Syntax 1**

```
{
  "addFollowers": ["app"]
}
```

where `app` is a keyword. It indicates that the app would like to receive the notifications about the given target (a project or a task). That is, it'll add the app into the target's followers.

**Syntax 2**

In addition, you can specify additional information that will be passed as part of a notification in the following syntax.

```
"app|team|channel"
```

where `app` is a keyword while `team` and `channel` are application specific. That is, you can pass any value to `team` and `channel`.

> Note: `team` and `channel` can not contain `'|'`.

For example,

```
{
  "addFollowers": ["app|extra101"]
}
```

Then, the notification will carry an additional field called `team` with the value `"extra101"` (`data` is the same as without it; it's left empty in these examples):

```
{
  "type": "notification",
  "team": "extra101",
  "data": {}
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
  "data": {}
}
```

**Syntax 3**

```
"app|team|channel|mine"
```

where both `app` and `mine` are keywords. It is similar to *Syntax 2*, except it receives only notifications that match the notification setting of the user.

If you don't need both `team` and `channel`, you can specify: `"app|||mine"`.

> The notification setting can be found at `https://quire.io/w/YourProject?view=setting&tab=options#notifications`

**Syntax 4**

```
"app|/path"
```

where `app` is a keyword, and `/path` is application specific. The path will be appended to the app's hook URL. For example, assume the app's hook URL is `"https://super.app/hooks/standard"`, and the follower "app|/soc/id279/channel51". Then, the notification will be posted the following URL: `"https://super.app/hooks/standard/soc/id279/channel51"`.

**Syntax 5**

If you'd like to pass additional information in this syntax, you can append it as follows.

```
"app|/path|channel"
```

For example, `app|/soc/id8|box51`. Then, `box51` will be part of the JSON object sent to the hook URL.

```
{
  "type": "notification",
  "channel": "box51",
  "data": {}
}
```

## Responding and Retries

When receiving the notification, your Web Hook shall return a status code between 200 and 299 within 1 minute to indicate success.

If a status code other than above is returned, or there is no response within 1 minute, we will retry 2 hours later, then 1 day later. If the last retry fails too, the payload is dropped.

Payloads to the same URL are posted one at a time. After 5 failures in a row, the URL is treated as down: new payloads to it wait for the retries above instead of being posted right away, and the admins of your app's organization get an email (at most once a day). Quire keeps probing the URL and resumes delivery once it returns a 2xx status again.

## Activity Types

* [Activity Types](https://github.com/quire-api/quire-api/blob/master/docs/activity_types.md)

