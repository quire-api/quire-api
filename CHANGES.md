# Changelog

## Apr 20, 2026

- **Task API:** The `priority` field in [create](https://quire.io/dev/api/#operation--task--projectOid--post) and [update](https://quire.io/dev/api/#operation--task--taskOid--put) task endpoints now accepts an English name (case-insensitive) — `Low`, `Medium`, `High`, or `Urgent` — in addition to the integer range `-1` to `2`.

## Apr 18, 2026

- **Rate Limits:** When exceeding the [API rate limit](https://quire.io/dev/api/#rate-limits), the `429 Too Many Requests` response now includes a [`Retry-After`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/Retry-After) header indicating the number of seconds to wait before retrying.

## Apr 15, 2026

- **OAuth:** Added webhook token field to [app config page](https://quire.io/apps/dev). Developers can now set a verification token that is sent with each webhook payload.

## Apr 13, 2026

- **Task API:** Added support for [searching tasks by custom fields](https://quire.io/dev/api/#operation--task-search--projectOid--get) in project-level search. Supported field types: Number, Money, Checkbox, Select, User, Task, Email, Hyperlink, and Duration. Field names are case-insensitive.
- **OAuth:** Added [PKCE](https://quire.io/dev/api/#oauth-pkce) (RFC 7636) support for single-page apps. Only S256 method is supported. PKCE flows do not issue refresh tokens.
- **OAuth:** Improved error message when `grant_type` is missing from token exchange request.

## Apr 8, 2026

- **Task API:** Enhanced API for [creating a task relative to another task](https://quire.io/dev/api/#operation--task-id--projectId---taskId--post)
    - Example: `POST /task/id/MyPrj/357?position=before`
    - *Deprecated*: `POST /task/before` and `POST /task/after`
- **Task API:** Added support for [deleting a task by its ID](https://quire.io/dev/api/#operation--task-id--projectId---taskId--delete)
- `DELETE` endpoints now return `204 No Content` instead of `200 OK`
- **Storage API:** `PUT` endpoints now return `204 No Content` instead of `200 OK`

## Mar 27, 2026

- An additional `predecessors` field is included in the return value of the Task [GET](https://quire.io/dev/api/#operation--task-id--projectId---id--get) and [SEARCH](https://quire.io/dev/api/#operation--task-search-id--projectId--get) APIs.

## Mar 23, 2026

- **Task API:** Added support [for transferring tasks](https://quire.io/dev/api/#operation--task-transfer-id--projectId---id--put).

## Mar 21, 2026

- **Task API:** Added support [for moving tasks](https://quire.io/dev/api/#operation--task-move-id--projectId---id--put).
- An additional `parent` field is included in the return value of the Task [PUT](https://quire.io/dev/api/#operation--task-move-id--projectId---id--put) too.

## Feb 28, 2026

- The transfer task event type has been updated to `52` (previously `30`). For this event, the `value` field represents the source project, and the `projectSummary` field represents the target project.
- The transfer project event type has been updated to `109` (previously `108`). For this event, the `value` field represents the source organization, and the `organizationSummary` field represents the target organization.

## Feb 13, 2026

- Addition `projectSummary` and `organizationSummary` fields are included in the data sent to a web hook. See [Notification Event](https://quire.io/dev/api/#notification-events)
- An additional `parent` field is included in the data sent to a web hook to provide parent task information. See [Notification Event](https://quire.io/dev/api/#notification-events)
- An additional `parent` field is included in the return value of the Task [GET](https://quire.io/dev/api/#operation--task-id--projectId---id--get) and [SEARCH](https://quire.io/dev/api/#operation--task-search-id--projectId--get) APIs.

## Feb 12, 2026

- An addition `taskSummaries` field is included in the data sent to a web hook, when applicable. See [Activity Types | taskSummaries](https://github.com/quire-api/quire-api/blob/master/docs/activity_types.md#tasksummaries) for more informaion.

## Jan 13, 2026

- **Task API:** Added support for **milestones**.

## Oct 30, 2025
- **User API:** Responses from the `/api/user/me` endpoint now return the current user's locale and timeZone information.

## Oct 3, 2025
- **Tasks API:** Responses now include `commentedAt` when a task has at least one comment.

## Jul 4, 2025
- Fixed an issue in the Search API where formula fields that reference parent or ancestor values were not evaluated correctly.

## Jun 12, 2025
- Added “[sourceRef](https://quire.io/dev/api/#operation--task-id--projectId--post)” so apps can store app-specific data on tasks.
- Webhook callbacks now include the task’s custom fields.
- Added the series identifier for recurring tasks at `recurrence.seriesId`.

## May 29, 2025
- You can upload an attachment and add it to a task or a comment.
  - [Upload API for tasks](https://quire.io/dev/api/#operation--task-attach--taskOid---filename--post)
  - [Upload API for comments](https://quire.io/dev/api/#operation--comment-attach--commentOid---filename--post)

## May 13, 2025
- Added the [Chat Channel API](https://quire.io/dev/api/#tag-chat).
- The [Comment API](https://quire.io/dev/api/#tag-comment) now supports chat channels.
- **Incompatibility:** Comments can no longer be posted to a project directly. Use chat channels instead.

## Apr 6, 2025
- Added a new [Search API](https://quire.io/dev/api/#operation--task-search-organization-id--organizationId--get) for searching tasks across an organization or a folder.

## Mar 15, 2025
- The [Search API](https://quire.io/dev/api/#operation--task-search-id--projectId--get) now accepts days, hours, and minutes in the `modified` and `commented` parameters.

## Mar 13, 2025
- The [Search API](https://quire.io/dev/api/#operation--task-search-id--projectId--get) introduced `modified` and `commented` parameters for finding tasks updated and/or commented on recently.

## Feb 12, 2025
- Introduced APIs for manipulating [documents](https://quire.io/dev/api/#tag-doc).

## Feb 5, 2025
- You can specify `"inherit"` in followers and assignees to inherit from the parent task.  
  See details [here](https://quire.io/dev/api/#operation--task-id--projectId--post).
- Added support for `interval` in monthly and yearly recurrences.

## Dec 30, 2024
- Added a system event `host-revocation` for revoking a host from a token.

## Sep 19, 2024
- Webhooks: support following/unfollowing an organization.  
  See [Organization Activities](https://github.com/quire-api/quire-api/blob/master/docs/activity_types.md#organization-related-activities).

## Sep 6, 2024
- **Breaking change:** The task field `recurring` was renamed to `recurrence`, and its syntax changed.

## Apr 16, 2024
- Sublist now supports `start` and `due` dates.

## Mar 27, 2024
- You can create sublists for organizations, folders, and smart folders.

## Feb 16, 2024
- Added ability to add or remove successors.

## Sep 26, 2023
- You can add a follower who receives only notifications that match the user’s notification settings: `app|team|channel|mine`.

## Sep 1, 2023
- Activity **84** (see [details](https://github.com/quire-api/quire-api/blob/master/docs/activity_types.md)) was replaced by **11**.
  - Activities 5, 6, and 11 now include two extra fields—`status` and `previousStatus`—to indicate the new and previous status.

## Aug 23, 2023
- Added support for custom fields.

## Aug 12, 2023
- The redirect URL may now be `localhost`.

## Jul 21, 2023
- Added an extra field `tasks` to [Webhook Notification Events](https://quire.io/dev/api/#webhook) for activities that affect multiple tasks. See [Activity Types](https://github.com/quire-api/quire-api/blob/master/docs/activity_types.md).

## Jul 13, 2023
- `peekaboo=false` means the task will **not** be reshown automatically.

## Apr 10, 2023
- Added support for **sections**.

## Mar 26, 2023
- Content size is limited to **1 MB**.

## Feb 24, 2023
- Added **Timelogs**.
- Added **`etc`** (estimated time to complete).

## Mar 22, 2022
- Added API to export a project to CSV: `GET /project/export-csv`.

## Feb 25, 2022
- Added API to export a project to a JSON map: `GET /project/export-json`.

## Jan 17, 2022
- **Storage API:** `GET` endpoints return `404` if not found.  
      - _Previously returned 200 with an empty body._

## Apr 27, 2021
- Changed URLs for getting, updating, or deleting a comment.  
  The caller must specify the project’s ID or OID (e.g., `comment/id/{projectId}/{commentOid}`).

## Nov 2020
- Added APIs to manage sublists.

## Oct 30, 2020
- Simplified `due` and `start` formats.  
  - With time: `yyyy-mm-ddThh:mmZ` (e.g., `2020-10-30T09:30Z`)  
  - Date only: `yyyy-mm-dd` (e.g., `2020-10-30`)  
  - **Note:** All times must be UTC.

## Oct 22, 2020
- Responses now inline detailed information for fields like `assignees`, `tags`, etc.  
  For example, a task’s `project` property includes OID, name, color, image, and URL.  
  - **Note:** Many APIs changed—please update your application accordingly.
- Webhook added field `value` to carry detailed information (e.g., an assignee’s ID, name, and URL for assignment events).

## Oct 13, 2020
- You can search tasks by matching `name` and/or `description` with a regular expression.  
  - Case-insensitive: prefix with `~*`  
  - Case-sensitive: prefix with `~`  
  - Example:  
    `https://quire.io/api/task/search/id/your-project?name=~Foo[s]&description=~*(green|blue)`

## Oct 8, 2020
- You can add a task to **My Tasks** by specifying `-` as the project ID:  
  `https://quire.io/api/task/id/-`.
- You can add a tag to **My Tasks** by specifying `-` as the project ID as well.

## May 27, 2020
- To retrieve only the projects that the current user can add tasks to, use `add-task=true`.  
  Example: `https://quire.io/api/project/list?add-task=true`
- To include archived projects, use `archived=true`.  
  Unlike `add-task`, non-archived projects are included as well.
