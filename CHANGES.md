# Changelog

## Apr 27, 2026

- **Task API:** Added the bulk-approve endpoint for applying a single approval transition (`request` / `approve` / `reject` / `change`) to N tasks in one call. Same body shape, atomic / skip-not-found / `items[i]:` error / rate-limit conventions as the bulk-add / bulk-update / bulk-remove / bulk-move / bulk-transfer endpoints.
    - [`POST /task/bulk-approve/id/{projectId}?state=<state>[&category=<catId>]`](https://quire.io/dev/api/#operation--task-bulk-approve-id--projectId--post) — body is a top-level array of task references (OID, integer ID, or `"#<id>"` string; mixed forms allowed). The OID-form `POST /task/bulk-approve/{projectOid}` is also available.
    - `?state=` (required) follows the single-task `POST /task/approve/id/{projectId}/{taskId}` vocabulary; `?category=` (optional) defaults to the project's default category.
    - `?return=compact` returns `{oid, id}` per item (vs. the full task record in default mode).
    - Per-task role checks match the single-task endpoint — any item failing the claimer/approver check rolls the whole batch back with an `items[i]: ...` error.
- **Task API:** [`POST /task/approve/id/{projectId}/{taskId}`](https://quire.io/dev/api/#operation--task-approve-id--projectId---taskId--post) (and the by-OID form) now takes `state` and `category` as **query parameters** instead of body fields — same grammar as the bulk-approve endpoint. Example: `POST /task/approve/id/my_project/42?category=Legal&state=approve`. The request body is unused and may be empty. `?return=compact` also now returns `{oid, id}` (was `{oid}` only) so it matches every other compact response.
- **Task search:** opt-in cursor pagination via `?cursor=<token>` on [`GET /task/search/id/{projectId}`](https://quire.io/dev/api/#operation--task-search-id--projectId--get) (and the search-organization / search-folder endpoints). With `?limit=N` and more results to come, the **last item carries `"cursor": "<token>"`** — pass it back as `?cursor=<token>` (with the same `?limit=` and any filter) for the next page. End of stream is signalled by the absence of `cursor` on the last item of a page. Same shape (bare array) on every page — no envelope. Example:
    ```
    GET /task/search/id/my_project?status=active&limit=30
    GET /task/search/id/my_project?status=active&limit=30&cursor=<token-from-page-1's-last-item>
    ```
    - Applies to `/task/search/...`, `/task/search-organization/...`, and `/task/search-folder/...` (and all OID-form variants).
    - Cannot be combined with `?sublist=` (returns `400`).
- **Task search:** added `?recurring=` query parameter on [`GET /task/search/id/{projectId}`](https://quire.io/dev/api/#operation--task-search-id--projectId--get). Boolean: `true` (or empty, i.e. `?recurring`) returns only recurring tasks (those with a recurrence configured); `false` returns only non-recurring tasks; any other value returns `400 Bad Request`. Implemented as `is [not] null` on the underlying recurrence column.
    - Future work: a richer `?recurrence=<expression>` (filter by next-due window, frequency / interval, weekday set, until-date, etc.) is tracked separately and not part of this round.
- **Task search:** three new query parameters on `/task/search/...`. Each accepts the `,` (AND) / `|` (OR) / `!` (NOT) grammar already used by `assignee` / `tag`.
    - `?priority=` — filter by task priority. Values are integers (`-1`..`2`) or labels (`low`, `medium`, `high`, `urgent`, `none`; case-insensitive). Examples: `?priority=high`, `?priority=high|urgent`, `?priority=!low`.
    - `?type=` — filter by task type: `normal` (alias `task`), `section`, `milestone`. Example: `?type=section|milestone`.
    - `?createdBy=` — filter by the user who created the task. Each value is a user OID, ID, or email (same resolution as `assignee`). Example: `?createdBy=alice|bob`.
- **Task API:** `/task/list/...` gained opt-in pagination via `?limit=N` and `?cursor=<token>`. Without either param, the response stays a bare full array. With `?limit=N` (1..1000) and more results to come, the **last item in the response carries `"cursor": "<token>"`** — pass it back as `?cursor=<token>` on the next call (along with `?limit=` and any filter like `?status=`) to fetch the next page; end of stream is signalled by the absence of `cursor` on the last item. `?limit=no` is the explicit "return everything". Same shape (bare array) on every page.
    - Applies to both [`GET /task/list/id/{projectId}`](https://quire.io/dev/api/#operation--task-list-id--projectId--get) (project root tasks) and [`GET /task/list/id/{projectId}/{taskId}`](https://quire.io/dev/api/#operation--task-list-id--projectId---taskId--get) (subtasks of a task), and to the OID-form variants.
    - Cannot be combined with `?depth>1` (returns `400`). Composes with `?status=` filter and with `?return=compact` (which renders each item as `{oid, id, cursor?}`).
- **Task API:** [`GET /task/list/id/{projectId}/{taskId}`](https://quire.io/dev/api/#operation--task-list-id--projectId---taskId--get) gained an opt-in [`?depth=N`](https://quire.io/dev/api/#operation--task-list-id--projectId---taskId--get) parameter for fetching a whole subtree in one call. Omit (or `?depth=1`) for the flat single-level behavior; any positive `?depth=N` returns a multi-level subtree capped at the given depth; `?depth=full` walks every level (bounded by the per-tier total-nodes cap). Each task carries a nested `tasks` field for its walked children, recursively.
    - **Anchor required** — `?depth>1` rejects the whole-project case (`?depth>1` without a task in the URL → `400`); use `?depth=1` (or omit) on `/task/list/id/{projectId}` for root tasks.
    - **Plan-tier total-nodes cap** when `?depth>1`: Free → `402 Payment Required` (Professional plan or above required), Pro → **500**, Premium → **2,000**, Enterprise → unlimited (bounded only by the global response-size ceiling). When the cap is reached, the partial subtree is returned and the **last emitted sibling at the cropped level carries `"cropped": true`** — agent drills into that level via `/task/list/id/{prj}/{parentId}` to fetch the rest. (No `childCount` is included on subtree responses — the `tasks` array is the canonical signal; existing `?depth=1` and single-task responses still include `childCount`.)
    - **`?return=compact` composes** — records are rendered as `{oid, id, tasks?, cropped?}` per node; the tree shape is unchanged.
    - **`?status=` filter applies at every level** (all-or-nothing per branch): a child that doesn't match the filter is excluded along with its descendants. The per-tier cap counts only post-filter nodes.
    - The response shape is **identical to the flat `?depth=1` shape** — a bare list of task maps.
- **Task API:** Added three bulk endpoints for creating, updating, and removing many tasks in one call (up to **300 items per call**, project-scoped, atomic transaction). Body for all three is a top-level JSON array; response is a same-length array (one element per submitted item, with `null` placeholders for skipped items in `bulk-update` / `bulk-remove`). Pair with `?return=compact` for large batches.
    - [`POST /task/bulk-add/id/{projectId}`](https://quire.io/dev/api/#operation--task-bulk-add-id--projectId--post) — N root tasks. Body is `CreateTaskBody[]`. Items can include nested `tasks` to create subtrees in the same call (only the root of each item is echoed in the response).
    - [`POST /task/bulk-add/id/{projectId}/{taskId}?position=…`](https://quire.io/dev/api/#operation--task-bulk-add-id--projectId---taskId--post) — N tasks anchored at `taskId`. `?position=` accepts `parent` (default), `before`, or `after`, mirroring single-task add. Submitted-order is preserved across the batch regardless of `?position=` (the server uses a sliding-chain insert internally).
    - The OID-form `POST /task/bulk-add/{oid}` is also available, accepting either a project or task OID as the anchor.
    - [`PUT /task/bulk-update/id/{projectId}`](https://quire.io/dev/api/#operation--task-bulk-update-id--projectId--put) — body is an array of items, each carrying exactly one of `oid` / `id` plus `UpdateTaskBody`-shape fields. The OID-form `PUT /task/bulk-update/{projectOid}` is also available.
    - [`DELETE /task/bulk-remove/id/{projectId}`](https://quire.io/dev/api/#operation--task-bulk-remove-id--projectId--delete) — body is an array of task references; each element is a task OID (string), an integer ID, or a `"#<id>"` string. Mixed forms allowed in the same batch. The OID-form `DELETE /task/bulk-remove/{projectOid}` is also available.
    - **Atomic semantics for real bugs**: any per-item validation / permission / DB error rolls back the whole batch and returns `{code, message}` with `items[i]:` prefixed so the agent can pinpoint the offending row. Fix the value and retry.
    - **Skip-not-found** (`bulk-update` / `bulk-remove` only): items whose target task can't be found (already removed, never existed, cascade-removed by an earlier item in the same batch, etc.) are silently skipped — the corresponding response slot is `null` and the rest of the batch proceeds. Shape errors (malformed ref, both / neither `oid` and `id`) still return `400` and roll back.
    - **Rate-limit cost**: each bulk call costs `N` units against the per-minute / per-hour API quota, where `N` is the number of submitted items — the same total cost as `N` equivalent single-task calls. Charged upfront; if the cost would exceed the quota, the whole batch is rejected with `429 Too Many Requests` before any item is processed.
    - Bulk-transfer (across projects) is tracked separately as future work.
- **Task API:** Added the bulk-move endpoint for repositioning many tasks within a project in one call. Same body shape, atomic / skip-not-found / `items[i]:` error / sliding-chain / rate-limit conventions as the bulk-add / bulk-update / bulk-remove endpoints.
    - [`PUT /task/bulk-move/id/{projectId}?task={ref}&position=…`](https://quire.io/dev/api/#operation--task-bulk-move-id--projectId--put) — body is a top-level array of task references (OID, integer ID, or `"#<id>"` string; mixed forms allowed). The OID-form `PUT /task/bulk-move/{projectOid}` is also available.
    - `?task=` and `?position=` follow single-task `PUT /task/move/...` grammar — `?task=root` to promote items to project root (only with default `?position=parent`); `?task={ref}&position=before|after` to insert items as siblings of the reference.
    - Sliding-chain insertion preserves submitted order regardless of `?position=` (so `?position=after R` with items `[A, B, C]` lands as `R, A, B, C` — not the naive reverse).
- **Task API:** Added the bulk-transfer endpoint for moving many tasks across projects in one call. Same body shape, atomic / skip-not-found / `items[i]:` error / sliding-chain / rate-limit conventions as the other bulk endpoints, plus the per-aspect remap flags from single-task transfer.
    - [`PUT /task/bulk-transfer/id/{sourceProjectId}?project={target}&task={ref}&position=…`](https://quire.io/dev/api/#operation--task-bulk-transfer-id--sourceProjectId--put) — body is a top-level array of task references (mixed forms allowed). The OID-form `PUT /task/bulk-transfer/{sourceProjectOid}` is also available.
    - `?project=` (required) names the target project. Use `-` for the user's Inbox.
    - `?task=` and `?position=` follow single-task `PUT /task/transfer/...` grammar.
    - `?invite`, `?tag`, `?status`, `?custom-field` (all default `true`) control the auto-remap behavior — same as single-task transfer.
    - Both source and target locks are held for the whole batch; any per-item failure rolls back both. If `?project=` resolves to the URL source project, the whole batch is rejected with `400` (use `bulk-move` for within-project reorder).
    - OIDs are preserved across transfer; IDs are reassigned from the target project's counter.
- **All single-item POST / PUT endpoints:** Added an optional `?return=` query parameter to control the response shape — `full` (default; same as before) returns the complete record, `compact` returns identifiers only. Compact response shape varies by entity:
    - `{oid, id}` for tasks, projects, organizations, chats, docs, insights, sublists.
    - `{oid}` for comments and tags (no separate `id`).
    - `{name}` for custom-field extensions (`add-field` / `update-field` / `rename-field` / `move-field` on both project and insight).
    - `{id}` for approval categories (`add-appv-cat` / `update-appv-cat`).
    - `{value}` for task statuses (`POST /status` / `PUT /status`).
    - `{oid, id}` for `POST /task/approve/...`.
    - Any other `?return=` value returns `400 Bad Request`. `GET`, `DELETE`, list / search, and the timelog and attach endpoints don't accept `?return=` — their response shape is fixed.
- **Task API:** Added three timelog endpoints for managing a task's [time logs](https://quire.io/dev/api/#definition-Timelog) without rewriting the whole task. Identity of a log is the triple `(user, start, end)`; sub-second precision is truncated to whole seconds.
    - [`POST /task/add-timelog/id/{projectId}/{taskId}`](https://quire.io/dev/api/#operation--task-add-timelog-id--projectId---taskId--post) — body carries `start` / `end` (required) and optional `user` / `billable` / `note`. `user` defaults to the caller. Returns `409 Conflict` if a log with the same triple already exists.
    - [`PUT /task/update-timelog/id/{projectId}/{taskId}?start=…&end=…[&user=…]`](https://quire.io/dev/api/#operation--task-update-timelog-id--projectId---taskId--put) — query params identify the log; body carries new values. Omitted (or `null`) body fields preserve existing values; send empty string for `note` to clear. Returns `404 Not Found` when no log matches.
    - [`DELETE /task/remove-timelog/id/{projectId}/{taskId}?start=…&end=…[&user=…]`](https://quire.io/dev/api/#operation--task-remove-timelog-id--projectId---taskId--delete) — removes the matching log. Returns `404 Not Found` when no log matches.
    - All three return HTTP 200 with the task's full timelogs array (same element shape as the `timelogs` field on `GET /task/id/{projectId}/{taskId}`); empty array when no logs remain.
    - The by-OID forms `/task/add-timelog/{taskOid}`, `/task/update-timelog/{taskOid}`, and `/task/remove-timelog/{taskOid}` are also available.
- **Task API:** [`PUT /task/move/id/{projectId}/{taskId}`](https://quire.io/dev/api/#operation--task-move-id--projectId---taskId--put) and [`PUT /task/transfer/id/{projectId}/{taskId}`](https://quire.io/dev/api/#operation--task-transfer-id--projectId---taskId--put) now accept an optional `?position=` query parameter to control sibling ordering — matching the existing `add-task` grammar.
    - `parent` (default; same as omitted) — the moved/transferred task becomes a child of `?task=`.
    - `before` — sibling immediately before `?task=`, under `?task=`'s parent.
    - `after` — sibling immediately after `?task=`, under `?task=`'s parent.
    - Any other value returns `400 Bad Request`.
    - `before` / `after` require `?task=` to refer to an existing task (not `root`, not omitted).
    - Example (move): `PUT /task/move/id/my_project/42?task=99&position=after`.
    - Example (transfer): `PUT /task/transfer/id/my_project/42?project=archive&task=99&position=before`.

## Apr 24, 2026

- **Insight API:** The 5 field-extension endpoints (`add-field`, `update-field`, `remove-field`, `rename-field`, `move-field`) now accept the by-ID URL form in addition to the by-OID form. The by-ID path identifies the insight by owner-type + owner ID + insight ID:
    - `POST /insight/add-field/id/{ownerType}/{ownerId}/{insightId}`
    - `PUT /insight/update-field/id/{ownerType}/{ownerId}/{insightId}/{fieldName}`
    - `DELETE /insight/remove-field/id/{ownerType}/{ownerId}/{insightId}/{fieldName}`
    - `PUT /insight/rename-field/id/{ownerType}/{ownerId}/{insightId}/{fieldName}/{newName}`
    - `PUT /insight/move-field/id/{ownerType}/{ownerId}/{insightId}/{fieldName}[?before={otherName}]`
    - `{ownerType}` is one of `project`, `organization`, `folder`, `smart-folder`.
    - Both URL forms dispatch through the same handler.
- **Task API:** `cfUser` and `cfTask` custom-field values in [`POST /task/id/{projectId}`](https://quire.io/dev/api/#operation--task-id--projectId--post) and [`PUT /task/id/{projectId}/{taskId}`](https://quire.io/dev/api/#operation--task-id--projectId---taskId--put) bodies now accept the same shorthand forms already supported for built-in fields.
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
