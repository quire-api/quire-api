# Changelog

## Oct 30, 2026

- **Response shapes:** an attachment's `url` is absolute, also in a `file` field; an approval category's `createdBy` is a user object; a comment no longer has `ownerType` (use `owner.type`); a sublist's `start`/`due` are dates (`2026-11-02`).
- **`402` for plan limits:** `undo-remove` beyond the task limit, a transfer beyond the target's limits or with reminders into a plan without them, and `"public": true` without public projects. An export rejected with `402` costs 1 unit.
- **`409` for values in use:** a status value, and an `add-appv-cat` `id`. The body's `code` now equals the status for `409` and `408`.
- **`403` first:** a caller without permission gets `403` before learning whether a record, team, member or plan limit applies.
- **Followers:** with read access, you can follow or unfollow yourself (now also on tasks, docs and chats) but no one else; `followers` (even `["me"]`) needs edit access.
- **Rights:** a project's `start`/`due` need an admin; a global tag needs the organization's tag right; a reminder on a task you can't see returns `404`, and its `members` must all see the task.
- **`partner`:** must be an external team of the project, on tasks, sublists, docs, chats, insights, dashboards and reminders; otherwise `400`.
- **Dates:** a project's `start`/`due` are dates only, like a sublist's; a time returns `400`.
- **Select options:** an option renamed with [`from`](https://quire.io/dev/api/#definition-UpdateFieldOption) keeps its tasks' values; an option without `color` keeps its color; `[]` clears `options` or `conditionFormat`.
- **Other:** `"etc": null` clears the estimate; [recurrence](https://quire.io/dev/api/#definition-Recurrence) `interval` defaults to `1`, `sincelatest` to `false`; [`?status=`](https://quire.io/dev/api/#operation--task-search-id--projectId--get) takes `0`..`100`; search takes `created-by`, `source-ref`; notification `url` rejects `javascript:`; [hidden time logs](https://quire.io/dev/api/#operation--task-add-timelog-id--projectId---taskId--post) return `[]`; `/storage/id/…` returns `404`.
- **Doc corrections** (behavior unchanged): a task's `status` and `priority` are objects; select options are `{name, color}`; a search needs a query parameter; also [`?dry-run`](https://quire.io/dev/api/#operation--task-bulk-add-id--projectId--post) values, [`sourceRef`](https://quire.io/dev/api/#definition-CreateTaskBody), [storage values](https://quire.io/dev/api/#definition-StorageValue), [token lifetime](https://quire.io/dev/api/#retrieve-access-token), and a [webhook](https://quire.io/dev/api/#notification-events)'s `secret` and `when`.

## Oct 29, 2026

- **Followers of a new task, doc or chat:** only those listed in `followers`, and anyone @mentioned in a task's or doc's description, follow it. The current user is no longer added automatically; to have them follow, include `"me"` in `followers`.
- **Following by commenting:** posting a comment makes the authorizing user, not the app, follow the task or chat.
- **`start` and `due` on sublists only:** the doc also listed them for docs, chats, insights and dashboards, which don't return them and reject them with `400` on create or update. The doc now lists them only for a [sublist](https://quire.io/dev/api/#definition-Sublist).

## Oct 23, 2026

- **Time logs hidden from guests:** when a project hides time logs from guests (its default), a guest or a non-member of a public project gets no `timelogs` on a task, and formulas see none, as in the Quire apps; the API used to return them.
- **Field names in the query:** a project's or insight's [`update-field`](https://quire.io/dev/api/#operation--project-update-field-id--id--put), [`rename-field`](https://quire.io/dev/api/#operation--project-rename-field-id--id--put), [`move-field`](https://quire.io/dev/api/#operation--project-move-field-id--id--put) and [`remove-field`](https://quire.io/dev/api/#operation--project-remove-field-id--id--delete) take `?name=` and `new-name`, so a name may contain `/`. The old path form still works, undocumented, and no longer silently misses encoded names.
- **Missing or taken field names:** `update-field`, `rename-field` and `move-field` on a missing field (or `before` field) return `404`, not `200` with `{}`. [`add-field`](https://quire.io/dev/api/#operation--project-add-field-id--id--post) or `rename-field` to a taken name returns `409`; it kept the existing field. `remove-field` stays `204`.
- **Attachment names that need encoding:** [task](https://quire.io/dev/api/#operation--task-attach-id--projectId---taskId---filename--post) and [comment](https://quire.io/dev/api/#operation--comment-attach--commentOid---filename--post) `attach` now decode `{filename}`: `My%20Report.pdf` is saved as `My Report.pdf`, no longer as is. A file name can't contain `/` or `\`.

## Oct 19, 2026

- **`asUser` in bulk calls and nested tasks:** each item of [`bulk-add`](https://quire.io/dev/api/#operation--task-bulk-add-id--projectId--post) and [`bulk-update`](https://quire.io/dev/api/#operation--task-bulk-update-id--projectId--put) now honors its own `asUser`, as documented; it was rejected with `400`. A nested task's `asUser` must match its top-level task's, or `400`.
- **`asUser` on an approval's comment:** [`approve`](https://quire.io/dev/api/#operation--task-approve-id--projectId---taskId--post) with `"asUser": true` now posts the comment as the app, as documented and as `POST /comment` does, no longer as the user. The approval stays the user's.

## Oct 16, 2026

- **Malformed nested `tasks`:** a `tasks` value that isn't a list, or an entry in it that isn't a task object, is rejected with `400` instead of failing with `500`.
- **Personal project rate limits:** a personal project (My Tasks) now gets half of the plan's limits instead of a quarter, e.g. 25 per minute / 100 per hour on the Free plan.
- **Bulk batch size:** a bulk call holds no more items than the plan's per-minute rate limit (e.g. 50 on the Free plan), and at most 300. A larger batch is rejected with `402` (`code` `469`); split it. See [Rate Limits](https://quire.io/dev/api/#rate-limits).
- **Nested tasks count:** [`POST /task`](https://quire.io/dev/api/#operation--task-id--projectId--post) or [`bulk-add`](https://quire.io/dev/api/#operation--task-bulk-add-id--projectId--post) with nested `tasks` costs 1 unit per task created, not per top-level task, and is [capped](https://quire.io/dev/api/#request-cost) like a bulk call: 300 tasks (`400`) and the per-minute limit (`402`).
- **`createdBy` of a comment, tag or partner** is now a user object (`oid`, `id`, `name`, …), as documented and as other records return it, instead of the creator's OID. Read `createdBy.oid` for the OID.
- **Apps in a task's user fields:** an app acting for a user (`asUser`) in a task's `createdBy`, `editedBy`, `followers` and the like is now returned as the app (`"id": "ap|<app ID>"` and the app's name), instead of a placeholder.
- **Approval comments** (doc now matches the server since June): the comment starts with a plain `<category>: <status>` line, no longer bold, in the user's language; don't parse it. [`approve`](https://quire.io/dev/api/#operation--task-approve-id--projectId---taskId--post) logs it on `approve`, `reject` and `change` even without a body.

## Oct 15, 2026

- **Private works:** a sublist, doc, chat, insight or dashboard has a boolean [`private`](https://quire.io/dev/api/#definition-CreateSublistBody): hidden from guests and visitors when true. A choice (default false), and returned, only with `members` and `partner` null; otherwise always true, and `false` returns `400`.
- **Reminders of a public project:** the list is empty for a visitor who is not a member, as the project view is.
- **Reminders and guests:** a guest member sees only the reminders naming them, and creates none (`403`: a reminder takes write access).
- **Who can view and edit a custom field:** a [field definition](https://quire.io/dev/api/#definition-FieldDefinition) has `viewMembers` and `editMembers` (`[]` = admins only); `locked` binds only the Quire apps. The API returns only fields and values the caller can view; writing one they can't edit returns `400`.
- **Task search paging depth:** a task search's `cursor` reaches at most 1,000,000 rows deep; a deeper one is rejected with `400`. Narrow the search with filters instead.
- **Task search with `?sublist=`:** the last item no longer carries a `cursor`, which was rejected with `400` when passed back. Pass a `limit` big enough (or `no`) to get every match.
- **Comment emojis:** a [comment](https://quire.io/dev/api/#operation--comment--commentOid--get) has `emojis`, its reactions, omitted if none. [`add-emoji`](https://quire.io/dev/api/#operation--comment-add-emoji--commentOid---emojis--post) and [`remove-emoji`](https://quire.io/dev/api/#operation--comment-remove-emoji--commentOid---emojis--delete) add and remove the caller's reactions, given as emojis or [names](https://quire.io/dev/api/#emojis); both return `204`.

## Sep 25, 2026

- **Reminder list filters:** [`GET /reminder/list/{ownerType}/{ownerOid}`](https://quire.io/dev/api/#operation--reminder-list--ownerType---ownerOid--get) and its by-ID forms accept `?timing=` (`standalone` or `anchored`) and `?status=` (`active` or `all`). Omitted, the list is unchanged.
- **Reminder webhooks:** adding, editing or removing a project's reminder now notifies its webhooks: types `84`, `87`, `88` on a task, `192`, `193`, `194` without. `value` is the [reminder](https://quire.io/dev/api/#operation--reminder--oid--get), or only its `oid` once removed. See [Activity Types](https://github.com/quire-api/quire-api/blob/master/docs/activity_types.md).

## Sep 21, 2026

- **Reminder API:** Added the [reminder](https://quire.io/dev/api/#tag-reminder) endpoints. A reminder notifies the people it names, either before a task's start or due date or at a time of its own.
    - **Create** — [`POST /reminder/{ownerType}/{ownerOid}`](https://quire.io/dev/api/#operation--reminder--ownerType---ownerOid--post), plus [`POST /reminder/id/{ownerType}/{ownerId}`](https://quire.io/dev/api/#operation--reminder-id--ownerType---ownerId--post) and, for a task, [`POST /reminder/id/task/{projectId}/{taskId}`](https://quire.io/dev/api/#operation--reminder-id-task--projectId---taskId--post) (a task ID is unique only within its project).
    - **Read** — [`GET /reminder/{oid}`](https://quire.io/dev/api/#operation--reminder--oid--get) and [`GET /reminder/list/{ownerType}/{ownerOid}`](https://quire.io/dev/api/#operation--reminder-list--ownerType---ownerOid--get), with the by-ID forms. A project's list includes its tasks' reminders too; narrow it with `ownerType` `task`.
    - **Update** — [`PUT /reminder/{oid}`](https://quire.io/dev/api/#operation--reminder--oid--put); only `when`, `recurrence`, `leads` and `name` can be changed.
    - **Delete** — [`DELETE /reminder/{oid}`](https://quire.io/dev/api/#operation--reminder--oid--delete). A reminder is not moved to the trash and cannot be restored.
    - **`ownerType` is always required**, with no implied `project`: `project`, `organization`, `folder`, `smart-folder` or `task`. Pass `-` as the project for a personal reminder.
    - **What it fires from:** a reminder on a task with a start or due date fires from that date, and `when` or `recurrence` returns `400`; otherwise `when` is required. [`leads`](https://quire.io/dev/api/#definition-ReminderLead) sets when to notify before it.
    - **Paging:** the lists take `?limit=` and `?cursor=`, as [`/task/list`](https://quire.io/dev/api/#operation--task-list-id--projectId--get) does. They're ordered by `oid`: stable, but **not** chronological.
    - **Who sees it:** [`members`](https://quire.io/dev/api/#definition-CreateReminderBody) works as a work's, set only on create, but omitted means yours alone; pass `null` for every member. Anyone besides you (a `partner` too) needs Premium or above (`402`).
- **Members when creating a work:** [sublists](https://quire.io/dev/api/#operation--sublist--ownerType---ownerOid--post), [docs](https://quire.io/dev/api/#operation--doc--ownerType---ownerOid--post), [chat channels](https://quire.io/dev/api/#operation--chat--ownerType---ownerOid--post), [insights](https://quire.io/dev/api/#operation--insight--ownerType---ownerOid--post) and [dashboards](https://quire.io/dev/api/#operation--dashboard--ownerType---ownerOid--post) accept `members` on create, so a record can be visible only to chosen users; it was read-only. It can't be changed later yet.
    - `null` (default) is every member of the owner, `[]` its admins, or a list of users, which must include yourself. A non-member listed returns `400` (a task's `assignees` drop one silently), and so does `members` with `partner`.
- **`members` in responses:** a work's `members` is now a list of user objects, like `followers` and `mutes`, instead of OID strings. `null` (every member) and `[]` (admins only) are both returned explicitly.
- **Error responses:** the HTTP status now reflects the kind of failure, and `429` is reserved for rate limits.
    - **Plan limits and quotas** (task limit, custom fields, dashboard, insight, sublist, chat, doc) now return `402 Payment Required` with body `code` `469`, where they previously returned `429`.
    - **An expired subscription** returns `402` with the new body `code` `470`, distinct from a plan limit. It too returned `429`, though only resubscribing clears it.
    - **`429 Too Many Requests`** is now returned only for the per-minute / per-hour API rate limit, and always carries a `Retry-After` header.
    - **A temporarily blocked account** returns `403 Forbidden` with the new body `code` `471`.
    - **Validation errors** — a missing name, or a duplicate or reserved id, for example — now carry body `code` `400` instead of `469`. `469` appears only alongside `402`.

## Jul 31, 2026

- **Comment API (removal):** Removed long-deprecated URL forms. All of them have supported replacements:
    - `GET`/`PUT`/`DELETE` on `/comment/{projectOid}/{commentOid}` and `/comment/id/{projectId}/{commentOid}` → use [`/comment/{commentOid}`](https://quire.io/dev/api/#operation--comment--commentOid--get); a comment OID is unique on its own. (Supersedes the Apr 27, 2021 entry.)
    - `PUT /comment/undo-remove/id/{projectId}/{commentOid}` → use [`PUT /comment/undo-remove/{commentOid}`](https://quire.io/dev/api/#operation--comment-undo-remove--commentOid--put).
    - Chat-channel comments without the `/chat/` segment (`POST /comment/{chatOid}`, `GET /comment/list/{chatOid}`, and their `/id/` forms) → use [`POST /comment/chat/{chatOid}`](https://quire.io/dev/api/#operation--comment-chat--chatOid--post) and [`GET /comment/list/chat/{chatOid}`](https://quire.io/dev/api/#operation--comment-list-chat--chatOid--get) (or their `/chat/id/` forms).
    - The removed forms now return `400 Bad Request`, except the chat forms without `/chat/`, which return `404 Not Found`.

## Jul 20, 2026

- **Dashboard API:** Added the [dashboard](https://quire.io/dev/api/#tag-dashboard) endpoints — dashboards are collections of widgets owned by a project, organization, or folder. Supports the standard work-item CRUD, in both OID and ID URL forms:
    - **Create** — [`POST /dashboard/{ownerType}/{ownerOid}`](https://quire.io/dev/api/#operation--dashboard--ownerType---ownerOid--post) and the by-ID form.
    - **Read** — [`GET /dashboard/{oid}`](https://quire.io/dev/api/#operation--dashboard--oid--get), the by-ID form, and [`GET /dashboard/list/{ownerType}/{ownerOid}`](https://quire.io/dev/api/#operation--dashboard-list--ownerType---ownerOid--get) (plus its by-ID form).
    - **Update** — [`PUT /dashboard/{oid}`](https://quire.io/dev/api/#operation--dashboard--oid--put) and the by-ID form; set `archived: true` / `false` to archive or unarchive.
    - **Delete** — [`DELETE /dashboard/{oid}`](https://quire.io/dev/api/#operation--dashboard--oid--delete) and the by-ID form.
    - **Undo remove** — [`PUT /dashboard/undo-remove/{oid}`](https://quire.io/dev/api/#operation--dashboard-undo-remove--oid--put) and the by-ID form; idempotent, subject to the dashboard-per-owner quota (`429` when the plan limit is reached).
    - Widget definitions and widget display are not yet part of the public API.

## Jun 4, 2026

- **Chat & Doc API:** chat channels and documents now support follower management, matching the Task API.
    - **Responses** now include `followers` and `mutes` (lists of users) — see [chat](https://quire.io/dev/api/#tag-chat) / [doc](https://quire.io/dev/api/#tag-doc).
    - **Create** ([`POST /chat/{ownerType}/{ownerOid}`](https://quire.io/dev/api/#operation--chat--ownerType---ownerOid--post), [`POST /doc/{ownerType}/{ownerOid}`](https://quire.io/dev/api/#operation--doc--ownerType---ownerOid--post), and the by-ID forms) accepts an optional [`followers`](https://quire.io/dev/api/#definition-CreateChatBody) list. The current user is added to the followers automatically.
    - **Update** ([`PUT /chat/{oid}`](https://quire.io/dev/api/#operation--chat--oid--put), [`PUT /doc/{oid}`](https://quire.io/dev/api/#operation--doc--oid--put), and the by-ID forms) accepts `followers` (replaces the whole list), `addFollowers`, and `removeFollowers`.
    - Documents are followable only when owned by a project; the follower fields on any other document return `400 Bad Request`. (Chat channels are always project-owned.)
- **Task API:** the current user is now added to a new task's `followers` automatically on [`POST /task/{projectOid}`](https://quire.io/dev/api/#operation--task--oid--post) (and the by-ID form) — previously only Quire's own clients added the creator.
- **Webhook (deprecation):** Two legacy Notification Event fields are now **deprecated** and will be **removed after Dec 31, 2027**. Both continue to be sent until the sunset date.
    - `tasks` (added Jul 2023) → use `taskSummaries` (available since Feb 2026), which carries the same per-task detail.
    - `project` (the bare project OID) → use `projectSummary` (available since Feb 2026), the `{oid, id}` form.

## May 27, 2026

- **Insight API:** Added [`GET /insight/run/{insightOid}`](https://quire.io/dev/api/#operation--insight-run--oid--get) (and the by-ID form) to run a project's insight and return its result, one aggregated row per group, as a 2D array. It takes `?group-by=` and `?status=`; big runs cost [extra units](https://quire.io/dev/api/#request-cost).
- **Task API:** [`POST /task/approve/id/{projectId}/{taskId}`](https://quire.io/dev/api/#operation--task-approve-id--projectId---taskId--post) (and the by-OID form) accepts an optional body, shaped as [`POST /comment`](https://quire.io/dev/api/#operation--comment--taskOid--post)'s, to also post a comment on the task, prefixed with the approval's category and status.
- **Task Search API:** Number, Money and Duration custom-field filters on [`GET /task/search/id/{projectId}`](https://quire.io/dev/api/#operation--task-search-id--projectId--get) and the other search forms take the operators of date fields (`ge:`, `between:`, `isNull`, …), not only an exact match. See [Custom Field Filtering](https://quire.io/dev/api/#custom-field-filtering).

## May 22, 2026

- **Rate-limit API:** Renamed from `/rate_limit` to [`/rate-limit`](https://quire.io/dev/api/#tag-rate-limit). `/rate_limit` continues to work as a deprecated alias; it will be retired in a future release.
- **Task API (bulk):** [`bulk-add`](https://quire.io/dev/api/#operation--task-bulk-add-id--projectId--post), [`bulk-update`](https://quire.io/dev/api/#operation--task-bulk-update-id--projectId--put), [`bulk-remove`](https://quire.io/dev/api/#operation--task-bulk-remove-id--projectId--delete), [`bulk-move`](https://quire.io/dev/api/#operation--task-bulk-move-id--projectId--put), [`bulk-transfer`](https://quire.io/dev/api/#operation--task-bulk-transfer-id--sourceProjectId--put) and [`bulk-approve`](https://quire.io/dev/api/#operation--task-bulk-approve-id--projectId--post) accept `?dry-run=true` to preview a call, with the same checks, without saving anything, at half the [cost](https://quire.io/dev/api/#request-cost). A dry-run's `oid` and `id` values are discarded.

## May 15, 2026

- **Notification API:** [`POST /notification`](https://quire.io/dev/api/#operation--notification-post) accepts an optional `recipients`: the authorizing user's colleagues the app can see, or `["*"]` for all of them. Every 10 recipients cost 1 [unit](https://quire.io/dev/api/#request-cost). Omitted, only the authorizing user is notified.
- **Storage API:** storage calls now count against the organization's plan-tiered [rate limit](https://quire.io/dev/api/#rate-limits), shared with the rest of the API and by all apps in it, instead of a flat 200 / minute and 3,000 / hour per app.

## May 8, 2026

- **OAuth (PKCE):** PKCE-issued access tokens now last **7 days** (vs. 1 hour for confidential clients), still with no `refresh_token`: when one expires, run the authorization flow again. `expires_in` is the actual remaining lifetime. See [PKCE Support](https://quire.io/dev/api/#pkce-support).
- **OAuth:** [Loopback redirect URIs](https://quire.io/dev/api/#loopback-redirect-uris-native-cli-apps-) (`127.0.0.1`, `[::1]`, `localhost`) now accept **any port**, per [RFC 8252 §7.3](https://datatracker.ietf.org/doc/html/rfc8252#section-7.3); scheme, host and path must still match. Non-loopback URIs are unchanged.

## May 2, 2026

- **Task / Approval API:** Added `{oid, id}` companion fields alongside existing OID-only response fields, for ID-friendly access. Old fields stay on the wire for backward compatibility; new code should prefer the `*Refs` / `*Ref` form.
    - `SimpleTask`: `successorRefs`, `predecessorRefs` — list of `{oid, id}` task refs (same shape as `?return=compact`).
    - [`AppvCat`](https://quire.io/dev/api/#definition-AppvCat): `claimerRefs`, `approverRefs` — list of `{oid, id}` user refs, with the tri-state of `claimers` / `approvers`.
    - [`Approval`](https://quire.io/dev/api/#definition-Approval): `requesterRef`, `approverRef` — scalar `{oid, id}` user refs. `approverRef` is null while `state` is `awaiting`.
    - No input changes — `successors` / `claimers` / `addSuccessors` / etc. already accept OID, `#id`, integer ID, and (for users) email.

## Apr 27, 2026

- **Task API:** Added [`POST /task/bulk-approve/id/{projectId}`](https://quire.io/dev/api/#operation--task-bulk-approve-id--projectId--post) (and the OID form) to apply one approval transition to many tasks in one call, with the conventions of the other bulk endpoints.
- **Task API:** [`POST /task/approve/id/{projectId}/{taskId}`](https://quire.io/dev/api/#operation--task-approve-id--projectId---taskId--post) (and the by-OID form) takes `state` and `category` as **query parameters** instead of body fields; the body is unused. `?return=compact` returns `{oid, id}`, not just `{oid}`.
- **Task search:** opt-in cursor paging on [`GET /task/search/id/{projectId}`](https://quire.io/dev/api/#operation--task-search-id--projectId--get), `search-organization` and `search-folder`: with `?limit=`, a page's last item carries a `cursor` to pass back. Not with `?sublist=` (`400`). See [Pagination](https://quire.io/dev/api/#pagination).
- **Task search:** added [`?recurring=`](https://quire.io/dev/api/#operation--task-search-id--projectId--get): `true` (or empty) returns only recurring tasks, `false` only non-recurring ones; any other value returns `400`.
- **Task search:** added `?priority=`, `?type=` and `?createdBy=` (now `created-by`) to [`/task/search/...`](https://quire.io/dev/api/#operation--task-search-id--projectId--get), each with the `,` (AND) / `|` (OR) / `!` (NOT) grammar of `assignee` / `tag`.
- **Task API:** [`/task/list/...`](https://quire.io/dev/api/#operation--task-list-id--projectId--get) gained opt-in paging with `?limit=` and `?cursor=`, as task search has, for root tasks and [subtasks](https://quire.io/dev/api/#operation--task-list-id--projectId---taskId--get); without either, it returns every task. Not with `?depth>1` (`400`).
- **Task API:** [`GET /task/list/id/{projectId}/{taskId}`](https://quire.io/dev/api/#operation--task-list-id--projectId---taskId--get) takes `?depth=N` or `full` to return a subtree, each task nesting its children in `tasks`. Free gets `402`; Pro and Premium cap the nodes, marking a cropped level with `"cropped": true`.
- **Task API:** Added [`bulk-add`](https://quire.io/dev/api/#operation--task-bulk-add-id--projectId--post) ([under a task](https://quire.io/dev/api/#operation--task-bulk-add-id--projectId---taskId--post) too), [`bulk-update`](https://quire.io/dev/api/#operation--task-bulk-update-id--projectId--put) and [`bulk-remove`](https://quire.io/dev/api/#operation--task-bulk-remove-id--projectId--delete), and their OID forms, to create, update or remove up to 300 tasks of a project in one atomic call, at 1 rate-limit unit per item.
- **Task API:** Added [`PUT /task/bulk-move/id/{projectId}`](https://quire.io/dev/api/#operation--task-bulk-move-id--projectId--put) (and the OID form) to reposition many tasks within a project in one call, with the conventions of the other bulk endpoints.
- **Task API:** Added [`PUT /task/bulk-transfer/id/{sourceProjectId}`](https://quire.io/dev/api/#operation--task-bulk-transfer-id--sourceProjectId--put) (and the OID form) to move many tasks to another project in one call, with the other bulk endpoints' conventions and single-task transfer's remap flags.
- **All single-item POST / PUT endpoints:** Added an optional `?return=`: `full` (default) for the whole record, or `compact` for identifiers only. See [Compact Responses](https://quire.io/dev/api/#compact-responses).
- **Task API:** Added [`add-timelog`](https://quire.io/dev/api/#operation--task-add-timelog-id--projectId---taskId--post), [`update-timelog`](https://quire.io/dev/api/#operation--task-update-timelog-id--projectId---taskId--put) and [`remove-timelog`](https://quire.io/dev/api/#operation--task-remove-timelog-id--projectId---taskId--delete), and the by-OID forms, to manage a task's [time logs](https://quire.io/dev/api/#definition-Timelog) one at a time, each identified by its user, start and end.
- **Task API:** [`PUT /task/move/id/{projectId}/{taskId}`](https://quire.io/dev/api/#operation--task-move-id--projectId---taskId--put) and [`PUT /task/transfer/id/{projectId}/{taskId}`](https://quire.io/dev/api/#operation--task-transfer-id--projectId---taskId--put) accept `?position=` (`parent`, `before` or `after`), as adding a task does; `before` and `after` need a `?task=`.

## Apr 24, 2026

- **Insight API:** The 5 field endpoints (`add-field`, `update-field`, `remove-field`, `rename-field`, `move-field`) accept the by-ID URL form too, e.g. [`POST /insight/add-field/id/{ownerType}/{ownerId}/{insightId}`](https://quire.io/dev/api/#operation--insight-add-field-id--ownerType---ownerId---insightId--post).
- **Task API:** `cfUser` and `cfTask` custom-field values in [create](https://quire.io/dev/api/#operation--task-id--projectId--post) and [update](https://quire.io/dev/api/#operation--task-id--projectId---taskId--put) bodies accept the shorthand of built-in fields: a user's OID, ID or email; a task's OID, ID or `#<id>`. Unknown values return `400`.
- **Task Search API:** Added `assignee`, `assignor`, `follower` and `tag` to the project, organization and folder [searches](https://quire.io/dev/api/#operation--task-search-id--projectId--get): users by OID, ID or email, tags by OID or name, combined with `,` (AND), `|` (OR) and `!` (NOT).
    - A `cfUser` custom-field filter also takes a user's email (already supported; doc updated).
- **Task Search API:** Custom-field filtering supports `Date` fields, with the grammar of the built-in `start` / `due` (a timestamp needs `withTime: true`). Only `Formula`, `File`, `Lookup` and `Text` remain unsupported. See [Custom Field Filtering](https://quire.io/dev/api/#custom-field-filtering).
- **Task Search API:** Added `created`, `edited`, `archived`, `unarchived`, `toggled`, `start` and `due` to filter the project, organization and folder [searches](https://quire.io/dev/api/#operation--task-search-id--projectId--get) by date, with keyword (`today`, …), value (`ge:`, `between:`, …) and null ops.
- **Project API:** Added [`add-appv-cat`](https://quire.io/dev/api/#operation--project-add-appv-cat--oid--post), [`update-appv-cat`](https://quire.io/dev/api/#operation--project-update-appv-cat--oid---catId--put) and [`remove-appv-cat`](https://quire.io/dev/api/#operation--project-remove-appv-cat--oid---catId--delete) to manage a project's [approval categories](https://quire.io/dev/api/#definition-AppvCat), each with `claimers` and `approvers`. Projects now return `approvalCategories`.
- **Task API:** Added [`POST /task/approve/{oid}`](https://quire.io/dev/api/#operation--task-approve--oid--post) (and the by-ID form) to request, approve, reject or request changes on a task's [approval](https://quire.io/dev/api/#definition-Approval), and [`DELETE /task/revoke-approval/{oid}`](https://quire.io/dev/api/#operation--task-revoke-approval--oid--delete) to cancel it. Tasks now return `approval` when set.

## Apr 22, 2026

- **Project & Insight APIs:** Added [`add-field`](https://quire.io/dev/api/#operation--project-add-field--oid--post), [`update-field`](https://quire.io/dev/api/#operation--project-update-field--oid--put), `remove-field`, `rename-field` and `move-field` to manage [custom-field definitions](https://quire.io/dev/api/#definition-FieldDefinition) one at a time, also on [insights](https://quire.io/dev/api/#operation--insight-add-field--oid--post) (by OID). Both now return a `fields` map.
    - Enum-like values (`type`, `resultType`, `durationFormat`, `lookupType`, and `when`/`op` in `conditionFormat`) are case-insensitive.
- **Project API:** [`PUT /project/{oid}`](https://quire.io/dev/api/#operation--project--oid--put) now accepts `name`, `description`, `start`, `due`, `archived` (bool toggle), and `public` (bool toggle) in addition to follower deltas. The project response now includes `start`, `due`, `archivedAt`, and `publicAt`.
- **Organization API:** [`PUT /organization/{oid}`](https://quire.io/dev/api/#operation--organization--oid--put) now accepts `name` and `description` in addition to follower deltas. The organization response now includes `editedAt`.
- **Rate Limit API:** Added [`GET /rate-limit/{organizationOid}`](https://quire.io/dev/api/#operation--rate-limit--oid--get) (and `GET /rate-limit/id/{organizationId}`) to inspect current per-hour and per-minute API usage for an organization. Calls to this endpoint do not count against the rate limit.
- **Undo-remove APIs:** Added `PUT /{entity}/undo-remove/{oid}` (and `/id/...` forms) to restore a removed [task](https://quire.io/dev/api/#operation--task-undo-remove--oid--put), [comment](https://quire.io/dev/api/#operation--comment-undo-remove--commentOid--put), [sublist](https://quire.io/dev/api/#operation--sublist-undo-remove--oid--put), [document](https://quire.io/dev/api/#operation--doc-undo-remove--oid--put), [chat channel](https://quire.io/dev/api/#operation--chat-undo-remove--oid--put) or [insight view](https://quire.io/dev/api/#operation--insight-undo-remove--oid--put): idempotent, and except a comment's, subject to the plan's creation quota.
- **Insight API:** Added the [Insight API](https://quire.io/dev/api/#tag-insight) to create, read, update, and delete insight views, matching the shape of the existing Sublist / Doc / Chat APIs. Custom-field configuration (`tableCols`, `fields`) is not yet exposed; follow-up is planned.

## Apr 20, 2026

- **Task API:** The `priority` field in [create](https://quire.io/dev/api/#operation--task--oid--post) and [update](https://quire.io/dev/api/#operation--task--oid--put) task endpoints now accepts an English name (case-insensitive) — `Low`, `Medium`, `High`, or `Urgent` — in addition to the integer range `-1` to `2`.

## Apr 18, 2026

- **Rate Limits:** When exceeding the [API rate limit](https://quire.io/dev/api/#rate-limits), the `429 Too Many Requests` response now includes a [`Retry-After`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/Retry-After) header indicating the number of seconds to wait before retrying.

## Apr 15, 2026

- **OAuth:** Added webhook token field to [app config page](https://quire.io/apps/dev). Developers can now set a verification token that is sent with each webhook payload.

## Apr 13, 2026

- **Task API:** Added support for [searching tasks by custom fields](https://quire.io/dev/api/#operation--task-search--projectOid--get) in project-level search. Supported field types: Number, Money, Checkbox, Select, User, Task, Email, Hyperlink, and Duration. Field names are case-insensitive.
- **OAuth:** Added [PKCE](https://quire.io/dev/api/#pkce-support) (RFC 7636) support for single-page apps. Only S256 method is supported. PKCE flows do not issue refresh tokens.
- **OAuth:** Improved error message when `grant_type` is missing from token exchange request.

## Apr 8, 2026

- **Task API:** Enhanced API for [creating a task relative to another task](https://quire.io/dev/api/#operation--task-id--projectId---taskId--post), with `?position=`.
    - *Deprecated*: `POST /task/before` and `POST /task/after`
- **Task API:** Added support for [deleting a task by its ID](https://quire.io/dev/api/#operation--task-id--projectId---taskId--delete)
- `DELETE` endpoints now return `204 No Content` instead of `200 OK`
- **Storage API:** `PUT` endpoints now return `204 No Content` instead of `200 OK`

## Mar 27, 2026

- An additional `predecessors` field is included in the return value of the Task [GET](https://quire.io/dev/api/#operation--task-id--projectId---taskId--get) and [SEARCH](https://quire.io/dev/api/#operation--task-search-id--projectId--get) APIs.

## Mar 23, 2026

- **Task API:** Added support [for transferring tasks](https://quire.io/dev/api/#operation--task-transfer-id--projectId---taskId--put).

## Mar 21, 2026

- **Task API:** Added support [for moving tasks](https://quire.io/dev/api/#operation--task-move-id--projectId---taskId--put).
- An additional `parent` field is included in the return value of the Task [PUT](https://quire.io/dev/api/#operation--task-move-id--projectId---taskId--put) too.

## Feb 28, 2026

- The transfer task event type has been updated to `52` (previously `30`). For this event, the `value` field represents the source project, and the `projectSummary` field represents the target project.
- The transfer project event type has been updated to `109` (previously `108`). For this event, the `value` field represents the source organization, and the `organizationSummary` field represents the target organization.

## Feb 13, 2026

- Additional `projectSummary` and `organizationSummary` fields are included in the data sent to a web hook. See [Notification Event](https://quire.io/dev/api/#notification-events)
- An additional `parent` field is included in the data sent to a web hook to provide parent task information. See [Notification Event](https://quire.io/dev/api/#notification-events)
- An additional `parent` field is included in the return value of the Task [GET](https://quire.io/dev/api/#operation--task-id--projectId---taskId--get) and [SEARCH](https://quire.io/dev/api/#operation--task-search-id--projectId--get) APIs.

## Feb 12, 2026

- An additional `taskSummaries` field is included in the data sent to a web hook, when applicable. See [Activity Types | taskSummaries](https://github.com/quire-api/quire-api/blob/master/docs/activity_types.md#tasksummaries) for more information.

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
- **Storage API:** `GET` endpoints return `404` if not found; they returned `200` with an empty body.

## Apr 27, 2021
- Changed URLs for getting, updating, or deleting a comment.  
  The caller must specify the project’s ID or OID (e.g., `comment/id/{projectId}/{commentOid}`).

## Nov 2020
- Added APIs to manage sublists.

## Oct 30, 2020
- Simplified `due` and `start` formats: `yyyy-mm-ddThh:mmZ` with a time (e.g., `2020-10-30T09:30Z`), or `yyyy-mm-dd` for a date only. All times must be UTC.

## Oct 22, 2020
- Responses now inline detailed information for fields like `assignees`, `tags`, etc.  
  For example, a task’s `project` property includes OID, name, color, image, and URL.  
  - **Note:** Many APIs changed—please update your application accordingly.
- Webhook added field `value` to carry detailed information (e.g., an assignee’s ID, name, and URL for assignment events).

## Oct 13, 2020
- You can [search](https://quire.io/dev/api/#operation--task-search-id--projectId--get) tasks by matching `name` and/or `description` with a regular expression: prefix it with `~`, or `~*` for case-insensitive.

## Oct 8, 2020
- You can add a task to **My Tasks** by specifying `-` as the project ID:  
  `https://quire.io/api/task/id/-`.
- You can add a tag to **My Tasks** by specifying `-` as the project ID as well.

## May 27, 2020
- To retrieve only the projects that the current user can add tasks to, use `add-task=true` on [`GET /project/list`](https://quire.io/dev/api/#operation--project-list-get).
- To include archived projects, use `archived=true`.  
  Unlike `add-task`, non-archived projects are included as well.
