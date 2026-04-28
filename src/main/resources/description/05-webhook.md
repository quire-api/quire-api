# WebHook

When one event on Quire is triggered, the system will send a payload to the webhook's configured URL.

> A webhook is used by Quire to call an app, while Quire API is used by an app to call Quire.
> To receive these events, you have to specify a valid URL for Webhooks when configuring your app.

For a complete guide, see our [blog post](https://quire.io/blog/p/webhook.html).

## System Events

A system event is used to notify your app about system or app's activities.

### Token Expiration

When the token has been expired or revoked, an event will be sent to your app. You can clean up your storage if necessary.

```
{
  "type": "system",
  "token": "hook-token-defined-by-you",
  "secret": "secret-defined-by-you",
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
  "secret": "secret-defined-by-you",
  "data": {
    "type": "host-revocation",
    "token": "the-refresh-token",
    "host": "host-oid",
    "otype": "host-type",
  }
}
```

## Notification Events

A notification is the information about an update (aka., an activity). Here is an example:

```
{
  "type": "notification",
  "token": "hook-token-defined-by-you",
  "secret": "secret-defined-by-you",
  "projectSummary": {
    "oid": "project-oid-where-event-occurred",
    "id": "project-id"
  },
  "organizationSummary": {
    "oid": "organization-where-event-occurred",
    "id": "organization-id"
  },
  "data": {
    "type": 0, //activity's type
    "when": "2019-09-30T08:20:12.000Z",
    "what": {
      "oid": "YxjapXXRCOYxoaiCT4tT3OQm", //OID of a task, project, or organization depending on type
      "id": 101,
      "name": "Brand new start",
      "parent": {
        "oid": "parent-oid",
        "id": "parent-id",
        "parent": {
          "oid": "grand-parent-oid",
          "id": "grand-parent-id",
        }
      }
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

* The `data` map may also include an optional `value` field, which provides detailed information as a map. For example, for an assignment notification, `value` includes the assignee’s ID, name, and URL.

* If the notification is about a *start* or *due* change, the `data` map will include an additional `due` field. This value is a date/time formatted in the user’s locale and time zone.

* If the event notifies a task update and the task has a parent, the parent information is included in the `parent` field. The `parent` field is a `map` containing the task’s oid and id. If the task’s parent also has a parent, the map includes a nested `parent` field as well.

* The `projectSummary` and `organizationSummary` fields provide information about the project and organization where the event occurred.

* There is an additional field, `taskSummaries`, for activities that can affect multiple tasks (for example, removing a task that has subtasks). This field is a list of `map` instances. Each `map` represents a task that was changed. If any of that task's subtasks were also changed, they are included in the map’s `tasks` field.
    - Please refer to [Activity Types | taskSummaries](https://github.com/quire-api/quire-api/blob/master/docs/activity_types.md#tasksummaries).

## Registration for Notifications

> For instructions on updating a project, see [Update a project by OID](#operation--project--oid--put).

If the app wants to receive notifications of a specific projects or tasks, it can *follow* the projects or apps by sending a `PUT` request to the URL. To add a follower, the body of the request can be:

**Syntax 1**

```
{
  "addFollowers": ["app"]
}
```

where `app` is a keyword. It indicates that the app would like to receives the notifications about the given target (a project or a task). That is, it'll add the app into the target's followers.

**Syntax 2**

In additions, you can specify additional information that will be passed as part of a notification in the following syntax.

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

**Syntax 3**

```
"app|team|channel|mine"
```

where both `app` and `mine` are keywords. It is similar to *Syntax 2*, except it receives only notifications that match the notification setting of the user.

If you don't need both `team` and `channel`, you can specify: `"app|||mine`.

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
  "data": {
    //refer the Notifications section for details
  }
}
```

## Responding and Retries

When receiving the notification, your Web Hook shall return a status code between 200 and 299 to indicate success.

If a status code other than above is returned, we will retry 15 minutes later, then 1 hour later and 1 day later.

## Activities Types

* [Activity Types](https://github.com/quire-api/quire-api/blob/master/docs/activity_types.md)

