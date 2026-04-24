# Changelog

## Apr 24, 2026

- **Insight API:** The 5 field-extension endpoints (`add-field`, `update-field`, `remove-field`, `rename-field`, `move-field`) now accept the by-ID URL form in addition to the by-OID form. The by-ID path identifies the insight by owner-type + owner ID + insight ID:
    - `POST /insight/add-field/id/{ownerType}/{ownerId}/{insightId}`
    - `PUT /insight/update-field/id/{ownerType}/{ownerId}/{insightId}/{fieldName}`
    - `DELETE /insight/remove-field/id/{ownerType}/{ownerId}/{insightId}/{fieldName}`
    - `PUT /insight/rename-field/id/{ownerType}/{ownerId}/{insightId}/{fieldName}/{newName}`
    - `PUT /insight/move-field/id/{ownerType}/{ownerId}/{insightId}/{fieldName}[?before={otherName}]`
    - `{ownerType}` is one of `project`, `organization`, `folder`, `smart-folder`.
    - Both URL forms dispatch through the same handler — pick whichever is more convenient.
- **Task API:** `cfUser` and `cfTask` custom-field values in [`POST /task/id/{projectId}`](https://quire.io/dev/api/#operation--task-id--projectId--post) and [`PUT /task/id/{projectId}/{taskId}`](https://quire.io/dev/api/#operation--task-id--projectId---taskId--put) bodies now accept the same shorthand forms already supported for built-in fields — no more OID-only restriction.
    - **cfUser**: user OID, user ID, or email. Server resolves to the user's OID.
    - **cfTask**: task OID, integer task ID, or `#<id>` string. Server resolves to the task's OID, scoped to the task's project.
    - Multi-value fields (`multiple: true`) accept mixed forms in one list and are de-duplicated by resolved OID: `{"Owners": ["alice", "bob@example.com"], "Depends": [42, "#99"]}`.
    - Unknown values (user or task not found, wrong project) return `400 Bad Request`.
- **Task Search API:** Added `assignee`, `assignor`, `follower`, and `tag` query params to the project, organization, and folder search endpoints.
    - **User refs** (`assignee`, `assignor`, `follower`): each value is a user OID, user ID, or email — same dispatch as the `User` custom-field type.
    - **Tags** (`tag`): value is a tag OID or tag name. Tag-name resolution is scoped to the search — project search matches the project's own tags plus its organization's global tags; organization / folder search matches any tag in the organization.
    - **Boolean grammar** shared by all four params:
        - `a,b,c` — all (AND)
        - `a|b|c` — any (OR)
        - `!a` — not (may appear inside either an AND or OR list)
        - `a,b|c` — `(a AND b) OR c` (AND binds tighter than OR)
    - **Quoting for tag names** with special characters: enclose in `"..."`. Inside quotes, `\"` is a literal `"` and `\\` is a literal `\`; any other `\x` is preserved as two literal characters.
    - Examples: `?assignee=alice,!bob`, `?follower=alice|bob`, `?tag=Legal,!Draft`, `?tag="In Progress",!Done`, `?tag="Foo\"s Go"|Other`.
    - `cfUser` custom-field search additionally accepts user email (already supported — docs updated).
- **Task Search API:** Custom-field filtering now supports `Date` custom fields. The filter grammar matches the built-in `start` / `due` params (keyword, value, null ops; date-only or ISO 8601 timestamp operand). Timestamp operands are accepted only on fields configured with `withTime: true`. For a field named `approvedAt`, example queries: `?approvedAt=today`, `?approvedAt=ge:2026-04-01`, `?approvedAt=between:2026-04-01,2026-04-30`, `?approvedAt=isNull`. Only `Formula`, `File`, `Lookup`, and `Text` custom-field types remain unsupported for search.
- **Task Search API:** Added `created`, `edited`, `archived`, `unarchived`, `toggled`, `start`, and `due` query params to filter by the task's date/time columns. Supported across the project, organization, and folder search endpoints.
    - Keyword ops: `past`, `yesterday`, `today`, `tomorrow`, `upcoming`, `last7d`, `next7d`, `lastWeek`, `thisWeek`, `nextWeek`. Day boundaries resolve in the OAuth user's timezone; week start follows their locale.
    - Value ops (token:value): `ge`, `gt`, `le`, `lt`, `eq`, `ne`, `between`, `notBetween`. Operand is an ISO 8601 timestamp (`YYYY-MM-DDTHH:MM:SSZ`). `between` / `notBetween` are inclusive on both ends.
    - Null ops (nullable fields — `archived`, `unarchived`, `toggled`, `start`, `due`): `isNull`, `isNotNull`.
    - `start` / `due` additionally accept a **date-only** operand (`YYYY-MM-DD`), which expands to a whole-day window in the caller's timezone. `gt:D` / `le:D` use end-of-day, `lt:D` / `ge:D` use start-of-day.
    - `past` on simple timestamp fields (`created`, `edited`, `archived`, `unarchived`, `toggled`) is `< now()`; on `start` / `due` it is `< today 00:00` in the caller's timezone (canonical).
    - Tokens and keywords are case-insensitive.
- **Project API:** Added endpoints to manage [approval categories](https://quire.io/dev/api/#definition-AppvCat) on a project. The project response now includes an `approvalCategories` list.
    - [`POST /project/add-appv-cat/{oid}`](https://quire.io/dev/api/#operation--project-add-appv-cat--oid--post) (and `/add-appv-cat/id/{id}`) to add a category.
    - `PUT /project/update-appv-cat/{oid}/{catId}` to update a category's name / claimers / approvers (partial; omitted keys preserve current values).
    - `DELETE /project/remove-appv-cat/{oid}/{catId}` to remove.
    - Each category carries a `claimers` roster (may `request` approval) and an `approvers` roster (may `approve` / `reject` / `change`). `null` means "anyone"; `[]` means "admins only"; otherwise a list of user OIDs.
- **Task API:** Added endpoints to drive a task's [approval](https://quire.io/dev/api/#definition-Approval) workflow. Task responses now include an `approval` field when set.
    - [`POST /task/approve/{oid}`](https://quire.io/dev/api/#operation--task-approve--oid--post) (and `/approve/id/{projectId}/{taskId}`) to request (`request`), approve (`approve`), reject (`reject`), or request changes (`change`). The same endpoint handles every transition; the body's `state` token (case-insensitive) selects the action. `request` also covers rolling forward from `approved` / `rejected` / `changes` back to `awaiting`.
    - `DELETE /task/revoke-approval/{oid}` (and `/revoke-approval/id/{projectId}/{taskId}`) to cancel. Smart-dispatch per current state: `awaiting` / `changes` → clears; `approved` / `rejected` → rolls back to `awaiting` (original requester preserved). Idempotent.
    - `category` is optional on `POST /task/approve` — omitted / `null` / `""` resolves to the project's implicit default category (id `""`), which is always available. An unknown category id returns `404`.
    - The original requester is preserved across `approve` / `reject` / `change` transitions; the responding user is recorded as `approver`.

## Apr 22, 2026

- **Project & Insight APIs:** Added endpoints to manage [custom-field definitions](https://quire.io/dev/api/#definition-FieldDefinition) one at a time. Both the project and insight responses now include a `fields` map keyed by field name.
    - [`POST /project/add-field/{oid}`](https://quire.io/dev/api/#operation--project-add-field--oid--post) (and `/add-field/id/{id}`) to add a definition.
    - [`PUT /project/update-field/{oid}/{fieldName}`](https://quire.io/dev/api/#operation--project-update-field--oid---fieldName--put) to update an existing field's content — flag keys that are omitted preserve their current value.
    - `DELETE /project/remove-field/{oid}/{fieldName}` to remove.
    - `PUT /project/rename-field/{oid}/{fieldName}/{newName}` to rename.
    - `PUT /project/move-field/{oid}/{fieldName}[?before={otherName}]` to reorder.
    - Equivalent [`/insight/...-field/{insightOid}/...`](https://quire.io/dev/api/#operation--insight-add-field--oid--post) endpoints are available for insight views (OID-addressed only).
    - Field types: `text`, `number`, `money`, `date`, `duration`, `select`, `checkbox`, `user`, `task`, `hyperlink`, `email`, `formula`, `file`, `lookup`. Enum-like values (`type`, `resultType`, `durationFormat`, `lookupType`, and `when`/`op` within `conditionFormat`) are accepted case-insensitively.
- **Project API:** [`PUT /project/{oid}`](https://quire.io/dev/api/#operation--project--oid--put) now accepts `name`, `description`, `start`, `due`, `archived` (bool toggle), and `public` (bool toggle) in addition to follower deltas. The project response now includes `start`, `due`, `archivedAt`, and `publicAt`.
- **Organization API:** [`PUT /organization/{oid}`](https://quire.io/dev/api/#operation--organization--oid--put) now accepts `name` and `description` in addition to follower deltas. The organization response now includes `editedAt`.
- **Rate Limit API:** Added [`GET /rate_limit/{organizationOid}`](https://quire.io/dev/api/#operation--rate_limit--oid--get) (and `GET /rate_limit/id/{organizationId}`) to inspect current per-hour and per-minute API usage for an organization. Calls to this endpoint do not count against the rate limit.
- **Undo-remove APIs:** Added `PUT /{entity}/undo-remove/{oid}` (and `/id/...` variants) to restore a previously-removed [task](https://quire.io/dev/api/#operation--task-undo-remove--oid--put), [comment](https://quire.io/dev/api/#operation--comment-undo-remove--commentOid--put), [sublist](https://quire.io/dev/api/#operation--sublist-undo-remove--oid--put), [document](https://quire.io/dev/api/#operation--doc-undo-remove--oid--put), [chat channel](https://quire.io/dev/api/#operation--chat-undo-remove--oid--put), or [insight view](https://quire.io/dev/api/#operation--insight-undo-remove--oid--put). The endpoints are idempotent, and task/sublist/doc/chat/insight undo-remove is subject to the plan's per-type creation quota.
- **Insight API:** Added the [Insight API](https://quire.io/dev/api/#tag-insight) to create, read, update, and delete insight views, matching the shape of the existing Sublist / Doc / Chat APIs. Custom-field configuration (`tableCols`, `fields`) is not yet exposed; follow-up is planned.

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
