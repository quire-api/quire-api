# Changelog

## Oct 30, 2005
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
- Storage API `GET`: now returns **404** if not found.  
  _Previously returned 200 with an empty body._

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
