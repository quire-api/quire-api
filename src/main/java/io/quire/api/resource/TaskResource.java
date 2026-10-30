package io.quire.api.resource;

import io.quire.api.model.*;
import io.quire.api.model.approval.*;
import io.quire.api.model.task.*;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/task")
@Api(
    value = "task",
    description = "Tasks are the basic units of work you and your team collaborate on."
)
@Produces({"application/json"})
public class TaskResource {

    // The approve and bulk-approve notes share this.
    private static final String APPROVAL_COMMENT_HEADER =
        "The header is a plain `<category>: <status>` line: the category's "
      + "name, left out for the default category, then the status in the "
      + "authorizing user's language (`Awaiting approval`, `Approved`, "
      + "`Rejected` or `Request changes` in English). Don't parse it: its "
      + "wording depends on the language.";

    // The bulk endpoints share this.
    private static final String BULK_CAP =
        "**Batch size**: at most 300 items per call, and no more than the "
      + "plan's per-minute rate limit — e.g. 50 on the Free plan, or 25 in "
      + "a personal project on the Free plan (see [Rate Limits](#rate-limits)). "
      + "A batch over the plan's cap is rejected with `402` (`code` `469`), "
      + "one over 300 with `400`; split it.";

    // The task-create endpoints share this.
    private static final String ADD_NESTED =
        "**Nested `tasks`**: subtasks created in the same call. Each one "
      + "costs 1 rate-limit unit, like a separate call, and a call creates "
      + "at most 300 tasks, nested ones included, and no more than the "
      + "plan's per-minute rate limit — e.g. 50 on the Free plan, or 25 in "
      + "a personal project on the Free plan (see [Rate Limits](#rate-limits)). "
      + "Over the plan's cap is rejected with `402` (`code` `469`), over 300 "
      + "with `400`; split it.";

    // The search endpoints share this.
    private static final String SOURCE_REF_PARAM =
        "Return only tasks whose `sourceRef` has this key, whatever its "
      + "value: `github` finds the tasks created with `\"sourceRef\": {\"github\": {\"repo\": "
      + "\"acme/web\", \"issue\": 42}}`. `sourceRef` is a JSON object an app "
      + "stores when it creates or updates a task: its own data under a key "
      + "named after the app, plus an optional `text` that Quire shows as "
      + "the task's source line. This parameter's former name, `sourceRef`, "
      + "still works.";

    // The organization and folder searches share these; the project
    // searches spell out the same loop in their notes.
    private static final String SEARCH_PAGING =
        "**Pagination**: a page holds up to `?limit=` tasks (30 by "
      + "default). When more matches remain, the page's last item carries "
      + "a `cursor`; pass it back as `?cursor=` with the same `?limit=` "
      + "and filters for the next page, until a page's last item has none. "
      + "The token is opaque — use it verbatim. Paging reaches at most "
      + "1,000,000 rows deep; a deeper `cursor` is rejected with 400 — "
      + "narrow the search with filters instead.";

    private static final String SEARCH_CURSOR =
        "Continuation token from the previous page's last item. See "
      + "**Pagination** in the operation notes for the loop. At most "
      + "1,000,000 rows deep.";

    // The search endpoints share these.
    private static final String SEARCH_COST =
        "**Rate-limit cost**: `max(1, ceil(N / 100))` units, where `N` is "
      + "the number of tasks returned — e.g. 1 for a page of 100 or fewer, "
      + "10 for 1,000. See [Rate Limits](#rate-limits).";

    private static final String SEARCH_TEXT =
        "Full-text query against task name, description, attachments, "
      + "and the values of the custom fields you can view. At most 256 "
      + "characters.\n"
      + "Note: does not include comment content or comment attachments.\n"
      + "Indexing can take ~10 seconds or more after updates.";

    private static final String SEARCH_NAME =
        "Task name filter, matched against the name's Markdown source. A "
      + "bare value matches the whole name exactly (case-sensitive). "
      + "Prefix with `~` for a regex, `~*` for a case-insensitive regex; "
      + "an invalid regex is rejected with `400`. At most 256 characters. "
      + "Use `text` for full-text search.";

    private static final String SEARCH_DESCRIPTION =
        "Task description filter, matched against the description's "
      + "Markdown source. A bare value matches the whole description "
      + "exactly (case-sensitive). Prefix with `~` for a regex, `~*` for a "
      + "case-insensitive regex; an invalid regex is rejected with `400`. "
      + "At most 256 characters.";

    private static final String SEARCH_DESCRIPTION_EXAMPLE =
        "~*john@example\\.com";

    private static final String SEARCH_STATUS =
        "Task status filter: `active` (not completed), `completed`, or an "
      + "integer `0`..`100` for that status, where `100` matches every "
      + "completed status, like `completed`. Any other value returns `400`.";

    private static final String SEARCH_SCHEDULED =
        "Return only scheduled tasks (start or due is set). "
      + "If `scheduled=false`, returns only tasks where neither start nor "
      + "due is set.";

    private static final String SEARCH_MINE =
        "Return only \"My Tasks\" (assigned to me, or created by me and "
      + "scheduled but unassigned).";

    private static final String SEARCH_ASSIGNEE =
        "Filter by assignee. Each value is a user OID, user ID, "
      + "or email.\n\n"
      + "Combine values with `,` (AND), `|` (OR), and `!` (NOT). "
      + "AND binds tighter than OR.\n\n"
      + "Examples:\n"
      + "- `alice` — a single user (by ID)\n"
      + "- `alice@example.com` — by email\n"
      + "- `alice,bob` — assigned to both\n"
      + "- `alice|bob` — assigned to either\n"
      + "- `alice,!bob` — assigned to alice but not bob\n"
      + "- `alice,bob|carol` — `(alice AND bob) OR carol`";

    private static final String SEARCH_ASSIGNOR =
        "Filter by assignor (the user who set the assignment). "
      + "Each value is a user OID, user ID, or email. Combine "
      + "with `,` (AND), `|` (OR), `!` (NOT); AND binds tighter "
      + "than OR.\n\n"
      + "Examples: `alice`, `alice,!bob`, `alice|bob`.";

    private static final String SEARCH_FOLLOWER =
        "Filter by follower. Each value is a user OID, user ID, "
      + "or email. Combine with `,` (AND), `|` (OR), `!` (NOT); "
      + "AND binds tighter than OR.\n\n"
      + "Examples: `alice`, `alice,!bob`, `alice|bob`.";

    private static final String SEARCH_TAG =
        "Filter by tag. Each value is a tag OID or a tag name "
      + "(names are resolved within the search's "
      + "organization).\n\n"
      + "Combine with `,` (AND), `|` (OR), `!` (NOT); AND binds "
      + "tighter than OR.\n\n"
      + "Names containing `,`, `|`, `!`, `\"`, or whitespace "
      + "must be wrapped in `\"...\"`. Inside quotes, write "
      + "`\\\"` for a literal `\"` and `\\\\` for a literal "
      + "`\\`; any other `\\x` is preserved as two literal "
      + "characters.\n\n"
      + "Examples:\n"
      + "- `Legal` — a single tag\n"
      + "- `Legal,!Draft` — has `Legal` but not `Draft`\n"
      + "- `\"In Progress\",!Done` — quoted name with space\n"
      + "- `\"Foo\\\"s Go\"|Other` — quoted with escaped `\"`";

    private static final String SEARCH_MODIFIED =
        "Return only tasks created or modified within the given period: "
      + "a number of days (`7`), or a number with the suffix `d` (days), "
      + "`h` (hours), or `m` (minutes), e.g. `8h`. An empty value means "
      + "7 days; `false` turns the filter off.";

    private static final String SEARCH_COMMENTED =
        "Return only tasks commented on within the given period: a "
      + "number of days (`7`), or a number with the suffix `d` (days), "
      + "`h` (hours), or `m` (minutes), e.g. `8h`. An empty value means "
      + "7 days; `false` turns the filter off.";

    private static final String SEARCH_CREATED =
        "Filter by the task's `createdAt` timestamp. "
      + "Value grammar:\n"
      + "- Keyword: `past`, `yesterday`, `today`, `tomorrow`, `upcoming`, `last7d`, `next7d`, `lastWeek`, `thisWeek`, `nextWeek`\n"
      + "- Value op: `{ge|gt|le|lt|eq|ne}:<timestamp>` or `{between|notBetween}:<timestamp>,<timestamp>`\n"
      + "- `<timestamp>` is ISO 8601 `YYYY-MM-DDTHH:MM:SSZ` (UTC); a date-only operand such as `2026-04-01` is rejected with `400`\n"
      + "- `isNull` / `isNotNull` are rejected with `400`, since `createdAt` is never null\n"
      + "- Day boundaries resolve in the caller's timezone; week start follows the caller's locale\n"
      + "- `past` on this field is `< now()`\n"
      + "- Tokens and keywords are case-insensitive\n\n"
      + "Examples: `today`, `last7d`, `ge:2026-04-01T00:00:00Z`, "
      + "`between:2026-04-01T00:00:00Z,2026-04-30T23:59:59Z`.";

    private static final String SEARCH_EDITED =
        "Filter by the task's `editedAt` timestamp. Same grammar "
      + "as `created`, timestamps only, and `isNull` / `isNotNull` are "
      + "rejected with `400`. Note: `editedAt` is `NULL` for tasks that "
      + "have never been edited after creation; such tasks are "
      + "excluded by every op (use `created` to filter by creation "
      + "time).\n\n"
      + "Examples: `today`, `last7d`, `ge:2026-04-01T00:00:00Z`.";

    private static final String SEARCH_ARCHIVED =
        "Filter by the task's `archivedAt` timestamp. Same grammar "
      + "as `created` (timestamps only), plus null ops since the field "
      + "is nullable:\n"
      + "- `isNull` — not archived\n"
      + "- `isNotNull` — archived (any time)\n\n"
      + "Examples: `isNotNull`, `today`, `ge:2026-04-01T00:00:00Z`.";

    private static final String SEARCH_UNARCHIVED =
        "Filter by the task's `reshowAt` timestamp (scheduled "
      + "un-archive). Same grammar as `archived` (timestamps only; "
      + "supports `isNull` / `isNotNull`).\n\n"
      + "Examples: `isNotNull`, `today`, `between:2026-04-01T00:00:00Z,2026-04-30T23:59:59Z`.";

    private static final String SEARCH_TOGGLED =
        "Filter by the task's `toggledAt` timestamp (when its "
      + "completion status was last changed). Same grammar as "
      + "`archived` (timestamps only; supports `isNull` / `isNotNull`).\n\n"
      + "Examples: `today`, `isNotNull`, `ge:2026-04-01T00:00:00Z`.";

    private static final String SEARCH_START =
        "Filter by the task's `start` date/time. Value grammar:\n"
      + "- Keyword: `past`, `yesterday`, `today`, `tomorrow`, `upcoming`, `last7d`, `next7d`, `lastWeek`, `thisWeek`, `nextWeek`\n"
      + "- Value op: `{ge|gt|le|lt|eq|ne}:<value>` or `{between|notBetween}:<value>,<value>`\n"
      + "- Null op: `isNull`, `isNotNull`\n"
      + "- `<value>` is either a date (`YYYY-MM-DD`) or a full ISO 8601 timestamp (`YYYY-MM-DDTHH:MM:SSZ`)\n"
      + "- A date operand expands to a whole-day window in the caller's timezone (`ge:2026-04-01` means \"on or after Apr 1 in the caller's calendar\"); `gt:D`/`le:D` use end-of-day, `lt:D`/`ge:D` use start-of-day\n"
      + "- `past` on `start`/`due` is `< today 00:00` in the caller's timezone (not `< now()`)\n"
      + "- `upcoming` on `start`/`due` is the window `[tomorrow, today + 7 days)`\n\n"
      + "Examples: `today`, `upcoming`, `ge:2026-04-01`, `between:2026-04-01,2026-04-30`, `isNull`.";

    private static final String SEARCH_DUE =
        "Filter by the task's `due` date/time. Same grammar and "
      + "semantics as `start` (accepts date-only operands, "
      + "`isNull`/`isNotNull`, and the same keyword/value ops).\n\n"
      + "Examples: `today`, `upcoming`, `le:2026-04-30`, `isNotNull`.";

    private static final String SEARCH_PRIORITY =
        "Filter by task priority. Each value is an integer "
      + "(`-1`..`2`) or a label (`low`, `medium`, `high`, `urgent`; "
      + "case-insensitive): `-1` Low, `0` Medium, `1` High, `2` Urgent. "
      + "Any other value, such as `none`, is rejected with `400`.\n\n"
      + "Combine with `,` (AND), `|` (OR), `!` (NOT). AND of "
      + "two distinct positives is the empty set (a task has "
      + "exactly one priority); negation works as expected.\n\n"
      + "Examples: `high`, `1`, `high|urgent`, `!low`.";

    private static final String SEARCH_TYPE =
        "Filter by task type. Values: `normal` (or `task`), "
      + "`section`, `milestone`. Combine with `,` / `|` / `!`. "
      + "Useful for structural navigation (e.g., listing only "
      + "sections in a project).\n\n"
      + "Examples: `section`, `section|milestone`, `!section`.";

    private static final String SEARCH_CREATED_BY =
        "Filter by the user who created the task. Each value "
      + "is a user OID, user ID, or email. Combine with `,` / "
      + "`|` / `!` (same grammar as `assignee`). This parameter's "
      + "former name, `createdBy`, still works.\n\n"
      + "Examples: `alice`, `alice|bob`, `!alice`.";

    private static final String SEARCH_RECURRING =
        "Filter on whether the task has a recurrence configured. "
      + "`true` (or empty) returns only recurring tasks; "
      + "`false` returns only non-recurring tasks. Any other "
      + "value returns `400 Bad Request`.";

    private static final String SEARCH_LIMIT =
        "Maximum number of tasks per page. Default: 30. "
      + "Use `no` to return all matches.\n\n"
      + "On the Free plan, a `limit` over 30, or `no`, is rejected with "
      + "`402` (`code` `469`).";

    // The project searches share these.
    private static final String SEARCH_PRJ_NOTES =
        "Returns tasks that match the specified criteria in the project, "
      + "most recently edited first (best match first with `text`). Pass "
      + "at least one query parameter; none is rejected with `400`.\n\n"
      + "### Custom Field Filtering\n\n"
      + "You can filter by custom fields by passing the field name as a query parameter "
      + "(case-insensitive). For example, `?cost=13` or `?approvedBy=john.doe`. "
      + "A field you can't view is an unknown query parameter.\n\n"
      + "Supported field types and value formats:\n\n"
      + "**Operator grammar** — Number, Money, Duration, and Date fields share a common set of "
      + "operators (case-insensitive): `ge:<v>`, `gt:<v>`, `le:<v>`, `lt:<v>`, `eq:<v>`, `ne:<v>`, "
      + "`between:<v1>,<v2>` (inclusive on both ends), `notBetween:<v1>,<v2>`, and `isNull` / `isNotNull`. "
      + "Number, Money, and Duration also accept a bare value as an exact match (same as `eq:`). "
      + "Date instead accepts keyword ops (`today`, `past`, `last7d`, ...) and requires an operator or "
      + "keyword — a bare date is not accepted. The `<v>` operand format per type is below.\n\n"
      + "| Type | Value format | Example |\n"
      + "| --- | --- | --- |\n"
      + "| Number, Money | `<v>` is a number | `cost=13`, `cost=ge:100`, `cost=between:50,150`, `cost=isNull` |\n"
      + "| Checkbox | `true` or `false` | `done=true` |\n"
      + "| Select | Option name | `stage=Review` |\n"
      + "| User | User ID, email, or OID | `approvedBy=john.doe` |\n"
      + "| Task | Task ID (integer) or OID | `blockedBy=42` |\n"
      + "| Email, Hyperlink | Exact value, or prefix with `~` for regex, `~*` for case-insensitive regex | `website=~example\\.com` |\n"
      + "| Duration | `<v>` is `<n>` seconds, or `<n>` with suffix `d` (days) / `h` (hours) / `m` (minutes) | `estimate=2h`, `estimate=ge:2h`, `estimate=between:8h,40h`, `estimate=isNull` |\n"
      + "| Date | `<v>` is `YYYY-MM-DD` or ISO 8601 `YYYY-MM-DDTHH:MM:SSZ` (a timestamp operand requires a `withTime: true` field). Same grammar as the `start` / `due` query params. | `approvedAt=today`, `approvedAt=ge:2026-04-01`, `approvedAt=between:2026-04-01,2026-04-30`, `approvedAt=isNull` |\n\n"
      + "Not supported: Text (use `text` for full-text search instead), Formula, File, Lookup.\n\n"
      + "A query parameter spelled exactly like a built-in one, e.g. "
      + "`priority`, is the built-in filter, even if a custom field has "
      + "that name.\n\n"

      + "### Pagination\n\n"
      + "A page holds up to `?limit=N` tasks (30 by default). When more "
      + "matches remain, the **last item carries a `cursor` field**; pass "
      + "its value back as `?cursor=<token>` (along with the same "
      + "`?limit=` and any filter) to fetch the next page. End of stream "
      + "is signalled by the absence of `cursor` on the last item.\n\n"
      + "Example loop:\n"
      + "```\n"
      + "GET /task/search/id/my_project?status=active&limit=30\n"
      + "    → 30 items; last has cursor: \"crsr1u\"\n"
      + "GET /task/search/id/my_project?status=active&limit=30&cursor=crsr1u\n"
      + "    → 30 items; last has cursor: \"crsr11k\"\n"
      + "GET /task/search/id/my_project?status=active&limit=30&cursor=crsr11k\n"
      + "    → 12 items; no cursor on last item → stop\n"
      + "```\n"
      + "With `?sublist=`, no item carries a `cursor`: pass a `limit` big "
      + "enough (or `no`) to get every match. Passing `?cursor=` with it is "
      + "rejected with 400. The cursor token is opaque — "
      + "use it verbatim from the previous response. Paging reaches at most "
      + "1,000,000 rows deep; a deeper `cursor` is rejected with 400 — narrow "
      + "the search with filters instead.\n\n"
      + SEARCH_COST;

    private static final String SEARCH_PRJ_SUBLIST =
        "Return only tasks in this sublist, by its ID or OID. No item "
      + "then carries a `cursor`: pass a `limit` big enough (or `no`) to "
      + "get every match.";

    private static final String SEARCH_PRJ_CURSOR =
        SEARCH_CURSOR + " Cannot be combined with `?sublist=`.";

    private static final String SEARCH_PRJ_OK =
        "OK — matching tasks (may be empty). When more matches remain, "
      + "the last item carries a `cursor` field (never with `?sublist=`).";

    private static final String SEARCH_PRJ_400 =
        "Bad Request — no query parameter, or an invalid or unknown one "
      + "(incl. a malformed or too deep `cursor`, or `?cursor=` with "
      + "`?sublist=`).";

    private static final String SEARCH_PRJ_402 =
        "Payment Required (`code` `469`) — on the Free plan, `limit` is "
      + "over 30 or `no`.";

    // The organization and folder searches share these.
    private static final String SEARCH_ORG_SCOPE =
        "Tasks in archived projects are excluded. Pass at least one query "
      + "parameter; none is rejected with `400`. Custom-field filters "
      + "and `sublist` aren't supported here (`400`); search a project "
      + "for those.\n\n"
      + "> Available for Professional plans and above; on the Free plan "
      + "it is rejected with `402` (`code` `469`).\n\n"
      + SEARCH_PAGING + "\n\n"
      + SEARCH_COST;

    private static final String SEARCH_ORG_OK =
        "OK — matching tasks (may be empty). When more matches remain, "
      + "the last item carries a `cursor` field.";

    private static final String SEARCH_ORG_400 =
        "Bad Request — no query parameter, or an invalid or unknown one "
      + "(incl. a custom field, `sublist`, or a malformed or too deep "
      + "`cursor`).";

    private static final String SEARCH_ORG_402 =
        "Payment Required (`code` `469`) — the organization is on the "
      + "Free plan.";

    // The task endpoints with `?return=` share this.
    private static final String RETURN_TASK =
        "(Optional) Response shape:\n"
      + "- `full` (default): the full task record.\n"
      + "- `compact`: only `{\"oid\": …, \"id\": …}` — the task's OID "
      + "and ID. Saves payload, tokens, and a server-side reload; enough "
      + "to chain follow-up calls.\n\n"
      + "Any other value returns `400 Bad Request`.";

    // The bulk endpoints with `?return=` share this.
    private static final String RETURN_BULK =
        "(Optional) Response shape: `full` (default) gives each item's "
      + "full task record; `compact` gives only `{\"oid\": …, \"id\": …}` "
      + "per item. Recommended for large batches. Any other value returns "
      + "`400 Bad Request`.";

    // The approve endpoints share this.
    private static final String RETURN_APPROVAL =
        "(Optional) Response shape: `full` (default) returns the task's "
      + "[approval](#definition-Approval) record; `compact` returns only "
      + "the task's `{\"oid\": …, \"id\": …}`. Any other value returns "
      + "`400 Bad Request`.";

    // The task-list endpoints share these.
    private static final String LIST_COST =
        "**Rate-limit cost**: `max(1, ceil(N / 100))` units, where `N` is "
      + "the number of tasks returned. See [Rate Limits](#rate-limits).";

    private static final String RETURN_LIST =
        "(Optional) Response shape: `full` (default) for the standard task "
      + "records, or `compact` for identifier-only items, "
      + "`{oid, id, cursor?}`. Any other value returns `400 Bad Request`.";

    private static final String RETURN_LIST_DEPTH =
        " With `?depth>1`, a `compact` item is `{oid, id, tasks?, cropped?}`.";

    private static final String LIST_STATUS =
        "Task status filter. "
      + "Specify a status value (an integer `0`..`100`; `100` matches "
      + "every completed status), or use \"active\" for active tasks or "
      + "\"completed\" for completed tasks. Any other value returns `400`.";

    private static final String LIST_STATUS_DEPTH =
        " With `?depth>1`, a task that doesn't match is left out with its "
      + "whole subtree, even subtasks that match.";

    // The task-list and single-task GET endpoints share this.
    private static final String READ_403 =
        "Forbidden — the user can't access it, or didn't authorize it for "
      + "this app.";

    private static final String LIST_DEPTH_402 =
        "Payment Required (`code` `469`) — `?depth>1` requires the "
      + "Professional plan or above.";

    private static final String SUBTREE_NOTES =
        "**When to use which:**\n"
      + "- Default (no `?depth`): you only need the immediate children.\n"
      + "- `?depth=2` or `3`: small subtree where you know the shape.\n"
      + "- `?depth=full`: full subtree snapshot — pair with `?return=compact` "
      + "for navigation/lookup workloads to keep the payload (and LLM-token cost) small.\n\n"

      + "**Per-tier total-nodes cap when `?depth>1`:** "
      + "Free → rejected (`402`), Pro → 500, Premium → 2,000, "
      + "Enterprise → unlimited (bounded only by the global response-size ceiling). "
      + "When the cap fires mid-walk, the partial subtree is returned and the "
      + "**last emitted sibling at the cropped level carries `\"cropped\": true`**. "
      + "Recovery: list that sibling's parent again without `?depth` "
      + "to fetch the rest of that level — flat single-level lists have "
      + "no cap.\n\n"

      + "**Tree shape signals for the agent:**\n"
      + "- Node has a `tasks` array → its children were walked. Empty array means a true leaf.\n"
      + "- Node has no `tasks` field → its children were not walked (depth limit "
      + "reached, or cap fired before reaching it). Drill in if you need them.\n"
      + "- Node has `\"cropped\": true` → more siblings of this node exist at the same level.\n\n"

      + "**`?status=` with `?depth>1`:** a task that doesn't match is "
      + "left out with its whole subtree, so a matching subtask under a "
      + "non-matching task is not returned. Use the search endpoints to "
      + "find matches at any depth.\n\n"

      + "**Pagination** (flat path only, cannot be combined with `?depth>1`): "
      + "pass `?limit=N` (1..1000) to opt into paginated chunks. When more results "
      + "exist, the LAST item carries `\"cursor\": \"<token>\"` — pass it back as "
      + "`?cursor=<token>` (plus the same `?limit=` and `?status=`) for the next "
      + "page. Without `?limit=` / `?cursor=`, the response is the full bare array. "
      + "`?limit=no` is an explicit \"return everything\". Composes with "
      + "`?return=compact`.\n\n"

      + "**Notes:** Subtree responses (`?depth>1`) omit the per-task `childCount` "
      + "field — the `tasks` array is the canonical signal. The flat default "
      + "(`?depth=1`) and single-task `GET /task/{oid}` still include `childCount`. "
      + "`?depth>1` cannot be combined with `?limit=` or `?cursor=` "
      + "(returns `400`).\n\n"

      + LIST_COST + " With `?depth>1`, `N` counts every task in the "
      + "returned subtree.";

    // The task-create endpoints share this.
    private static final String CREATE_402 =
        "Payment Required (`code` `469`) — the plan's task limit is "
      + "reached, or the call creates more tasks than the plan allows per "
      + "call (see **Nested `tasks`**).";

    // The update endpoints share this.
    private static final String UPDATE_NOTES =
        "Updates an existing task and returns the updated record.\n\n"
      + "Only the fields you include are updated; omitted fields are "
      + "left unchanged. Example — complete the task, add a tag, and "
      + "remove an assignee:\n\n"
      + "```json\n"
      + "{\n"
      + "  \"status\": 100,\n"
      + "  \"addTags\": [\"done\"],\n"
      + "  \"removeAssignees\": [\"alice@example.com\"]\n"
      + "}\n"
      + "```\n\n"
      + "Tags must exist already, as tags of the project or global tags "
      + "of its organization: an unknown tag, like `done` here if the "
      + "project has none, is rejected with `400`, not created.\n\n"
      + "> Note the replace-vs-incremental pattern: `tags` replaces the "
      + "entire tag set; `addTags` / `removeTags` modify the set in place. "
      + "Same for `assignees` / `addAssignees` / `removeAssignees`, and "
      + "`followers` / `successors` / etc. Mix whichever suits the intent.\n\n"
      + "To archive (hide indefinitely) or snooze a task, set the `peekaboo` "
      + "field: `true` hides it indefinitely, a positive integer hides it for "
      + "that many days, `false` unhides.";

    // The move endpoints share these.
    private static final String MOVE_NOTES =
        "Moves an existing task, with its subtasks, under another task as "
      + "a subtask, reorders it among siblings, or sends it to the root "
      + "level, within its project. To move it to another project, use "
      + "the transfer endpoints.\n\n"
      + "Use `?task=` to name the reference task and `?position=` to "
      + "choose placement (`parent` / `before` / `after`); `?task=root` "
      + "moves it to the root level.\n\n"
      + "Returns the updated task record. A `?task=` that isn't found, or "
      + "is in another project, is rejected with `400`, as is moving a "
      + "task under one of its own subtasks.";

    private static final String MOVE_EXAMPLES =
        "Examples:\n"
      + "- `PUT /task/move/id/my_project/42?task=99` — reparent (default: child of task 99).\n"
      + "- `PUT /task/move/id/my_project/42?task=99&position=before` — place task 42 before task 99.\n"
      + "- `PUT /task/move/id/my_project/42?task=99&position=after` — place task 42 after task 99.\n"
      + "- `PUT /task/move/id/my_project/42?task=root` — move to root level.\n"
      + "- `PUT /task/move/iuRRiKoyrxdBFhFTTo?task=0Mg3VQ8kWeiVbLH1JjvzUcP7&position=after` "
      + "— the same by OID: in this form, `?task=` is a task OID.";

    private static final String MOVE_400 =
        "Bad Request — `task` missing, not found, or in another project; "
      + "invalid `position`; or the new parent is one of the task's "
      + "subtasks.";

    // The transfer endpoints share these.
    private static final String TRANSFER_NOTES =
        "Transfers an existing task, with its subtasks, to another "
      + "project, optionally positioning it relative to an existing task "
      + "in the target (`?task=` + `?position=`). The task keeps its OID "
      + "but gets an ID in the target project, usually a new one — read "
      + "it from the response. The target can't be the task's own project "
      + "(`400`; use move instead).\n\n"
      + "`?invite`, `?tag`, `?status`, and `?custom-field` (all on by "
      + "default) bring the assignees, tags, statuses, and custom fields "
      + "the task uses into the target project.\n\n"
      + "Reminders on the task and its subtasks move with it. If the "
      + "target's plan doesn't include reminders, or the transfer would "
      + "take the target past its plan's task or storage quota, the "
      + "call is rejected with `402` (`code` `469`) and nothing changes.\n\n"
      + "Returns the updated task record.";

    private static final String TRANSFER_EXAMPLES =
        "Examples:\n"
      + "- `PUT /task/transfer/id/my_project/42?project=archive` — transfer to root of target project.\n"
      + "- `PUT /task/transfer/id/my_project/42?project=archive&task=99` — transfer as a child of task 99 in target.\n"
      + "- `PUT /task/transfer/id/my_project/42?project=archive&task=99&position=before` — transfer and place before task 99.\n"
      + "- `PUT /task/transfer/id/my_project/42?project=-` — transfer to your personal tasks.\n"
      + "- `PUT /task/transfer/iuRRiKoyrxdBFhFTTo?project=0Mg3VQ8kWeiVbLH1PrjzUc89` "
      + "— the same by OID: in this form, `?project=` and `?task=` are OIDs.";

    private static final String TRANSFER_400 =
        "Bad Request — `project` missing or the task's own project; "
      + "`task` not found in the target project; or invalid `position`.";

    private static final String TRANSFER_402 =
        "Payment Required (`code` `469`) — the target would go over its "
      + "plan's task or storage quota, or the task (or a subtask) has "
      + "reminders and the target's plan doesn't include reminders.";

    // The transfer endpoints share these flags.
    private static final String TRANSFER_FLAG_ON =
        "\n\nOn by default: omitted, `true`, `1`, or an empty value keeps "
      + "it on; any other value, e.g. `false`, turns it off.";

    private static final String TRANSFER_INVITE =
        "(Optional) Whether to invite assignees to the target project if "
      + "they are not already members. It applies only if you are an "
      + "admin of the target project (never when the target is `-`, "
      + "your personal tasks); otherwise nobody is invited."
      + TRANSFER_FLAG_ON;

    private static final String TRANSFER_TAG =
        "(Optional) Whether to add tags to the target project if they are "
      + "not already present." + TRANSFER_FLAG_ON;

    private static final String TRANSFER_STATUS =
        "(Optional) Whether to add statuses to the target project if they "
      + "are not already present." + TRANSFER_FLAG_ON;

    private static final String TRANSFER_CUSTOM_FIELD =
        "(Optional) Whether to add non-empty custom fields to the target "
      + "project if they are not already present." + TRANSFER_FLAG_ON;

    // The attach endpoints share these.
    private static final String ATTACH_NOTES =
        "Uploads an attachment to an existing task. The request body is "
      + "the raw file content.\n\n"
      + "Executable files (`.exe`, `.com`, `.bat`) are rejected unless the "
      + "project has enabled uploading executable files.\n\n"
      + "A file over the plan's per-file size limit, or one that would "
      + "take the organization past its storage quota, is rejected with "
      + "`402` (`code` `469`).";

    private static final String ATTACH_402 =
        "Payment Required (`code` `469`) — the file is over the plan's "
      + "per-file size limit, or the storage quota is used up.";

    private static final String ATTACH_FILENAME =
        "Attachment file name, e.g., `readme.txt`.\n\n"
      + "Percent-encode it, e.g., `My%20Report.pdf` for `My Report.pdf`. "
      + "It can't contain `/` or `\\`.\n\n"
      + "Provide a meaningful extension so the browser can recognize the MIME type "
      + "(e.g., `revenue.pdf`, `contacts.json`).\n\n"
      + "Alternatively, set the MIME type via the `Content-Type` header.";

    // The undo-remove endpoints share this.
    private static final String UNDO_REMOVE_NOTES =
        "Restores a previously-removed task, with the subtasks removed "
      + "along with it, and its removed ancestors. "
      + "Idempotent: if the task is not currently removed, this is a "
      + "no-op and returns the current task record.\n\n"
      + "Restored tasks count toward the plan's task limit, as created "
      + "ones do: if restoring them (subtasks and ancestors included) "
      + "would go past it, nothing is restored and the call returns "
      + "`402` (`code` `469`).";

    private static final String UNDO_REMOVE_402 =
        "Payment Required (`code` `469`) — restoring would go past the "
      + "plan's task limit; nothing is restored.";

    // The delete endpoints share this.
    private static final String DELETE_NOTES =
        "Deletes an existing task and all of its subtasks. Restore them "
      + "with `PUT /task/undo-remove/id/{projectId}/{taskId}` (or "
      + "`PUT /task/undo-remove/{oid}`).\n\n"
      + "> Note: Returns `204 No Content` regardless of whether the task exists.";

    // The approve endpoints share these.
    private static final String APPROVE_NOTES =
        "Sets the task's [approval](#definition-Approval), or "
      + "transitions it from its current state. The `?state=` "
      + "query parameter selects the transition:\n"
      + "- `request` — request approval, or roll forward from "
      + "`approved` / `rejected` / `changes` back to `awaiting`.\n"
      + "- `approve` / `reject` / `change` — approver's decision.\n\n"
      + "The caller must be a claimer (for `request`) or an "
      + "approver (for `approve` / `reject` / `change`) of the "
      + "category. The original requester is preserved across "
      + "`approve` / `reject` / `change` transitions.\n\n"
      + "**Request body (optional).** Supply a JSON object with a "
      + "`description` field to post a companion comment on the "
      + "task as a side effect of the approval call. Same shape as "
      + "[`POST /comment`](#operation--comment--taskOid--post): "
      + "`{\"description\": \"<markdown>\", \"pinned\": false, "
      + "\"asUser\": false}`. `description` is required when the "
      + "body is supplied; `pinned` and `asUser` are optional.\n\n"
      + "The stored comment is a header, a blank line, then the "
      + "supplied `description`. " + APPROVAL_COMMENT_HEADER + " "
      + "For example, `{\"description\": \"Please tweak the "
      + "wording.\"}` on a `state=change` call for the category "
      + "`legal`, named `Legal Review`, stores "
      + "`Legal Review: Request changes\\n\\nPlease tweak the wording.` "
      + "Don't include the header in `description` yourself.\n\n"
      + "Without a body, `approve` / `reject` / `change` still log "
      + "a comment holding only the header, as the apps do; "
      + "`request` logs nothing. The created comment is **not** "
      + "included in the response — call "
      + "`GET /comment/list/{taskOid}` afterward to fetch it.\n\n"
      + "To cancel an approval, use "
      + "`DELETE /task/revoke-approval/id/{projectId}/{taskId}` "
      + "(or `DELETE /task/revoke-approval/{oid}`) — "
      + "not this endpoint.\n\n"
      + "Returns `400 Bad Request` if `?state=` is missing or "
      + "unknown, or the body has unknown fields, an empty "
      + "`description`, or a non-object shape; `403 Forbidden` "
      + "if the caller isn't a claimer / approver; `404 Not Found` "
      + "if the task or `?category=` doesn't exist.";

    private static final String APPROVE_EXAMPLES =
        "Examples:\n"
      + "```\n"
      + "POST /task/approve/id/my_project/42?category=legal&state=approve\n"
      + "(empty body — records the approval and logs a header-only comment)\n"
      + "\n"
      + "POST /task/approve/id/my_project/42?category=legal&state=change\n"
      + "{\"description\": \"Please tweak the wording.\"}\n"
      + "\n"
      + "POST /task/approve/iuRRiKoyrxdBFhFTTo?state=request\n"
      + "(by OID — requests approval in the default category)\n"
      + "```";

    private static final String APPROVE_STATE =
        "Approval transition to apply: `request` requests approval (or "
      + "rolls forward from a decided state back to `awaiting`); "
      + "`approve` / `reject` / `change` records the approver's decision.";

    private static final String APPROVE_CATEGORY =
        "(Optional) Approval category ID. Defaults to the "
      + "project's default category (id `\"\"`).";

    // The revoke-approval endpoints share these.
    private static final String REVOKE_NOTES =
        "Revokes the task's current approval. The effect depends "
      + "on the current state:\n"
      + "- `approved` / `rejected` → rolls the decision back to "
      + "`awaiting`. Only an approver of the category can do it; the "
      + "caller becomes the approval's `approver`, and the original "
      + "requester is preserved. The approval can then be decided again "
      + "via `POST /task/approve/...`.\n"
      + "- `awaiting` / `changes` → clears the approval entirely. Only "
      + "the requester can do it, and only while a claimer of the "
      + "category; once the requester has left the project, any claimer "
      + "can.\n\n"
      + "A category's approvers and claimers are its `approvers` / "
      + "`claimers`: anyone in the project if unset, admins only if "
      + "`[]`, or the listed users. Anyone else gets `403`.\n\n"
      + "Idempotent: returns `204 No Content` even when no "
      + "approval is set, or the task doesn't exist.";

    private static final String REVOKE_403 =
        "Forbidden — the caller can't revoke this approval (see the rules "
      + "above).";

    // The timelog endpoints share this.
    private static final String TIMELOG_HIDDEN =
        "If the project hides time logs from guests (its default), a "
      + "guest member or a non-member gets an empty array, as `timelogs` "
      + "is omitted from the task records they get.";

    // The add-timelog endpoints share this.
    private static final String TIMELOG_ADD_NOTES =
        "Adds a [time log](#definition-Timelog) to the task. The new log "
      + "is credited to the "
      + "authenticated caller unless `user` is supplied in the body.\n\n"
      + "Identity of a log is the triple `(user, start, end)`. Sub-second "
      + "precision in `start` / `end` is truncated to whole seconds.\n\n"
      + "On success returns the task's full timelogs array. "
      + TIMELOG_HIDDEN + " Example:\n\n"
      + "```json\n"
      + "[\n"
      + "  {\n"
      + "    \"start\": \"2026-04-26T09:00:00.000Z\",\n"
      + "    \"end\": \"2026-04-26T10:30:00.000Z\",\n"
      + "    \"billable\": true,\n"
      + "    \"note\": \"Pair-programming session\",\n"
      + "    \"user\": { \"oid\": \"u-123\", \"name\": \"John Doe\" }\n"
      + "  }\n"
      + "]\n"
      + "```";

    // The update-timelog endpoints share this.
    private static final String TIMELOG_UPDATE_NOTES =
        "Updates an existing [time log](#definition-Timelog) on the "
      + "task. The log to update is "
      + "located by the `start`, `end`, and (optional) `user` query "
      + "parameters — that triple is the log's identity. The body "
      + "carries new values; any field omitted **or set to `null`** "
      + "preserves the existing value (so an agent that always "
      + "includes every field with `null` for unchanged ones is "
      + "safe). To clear `note`, send an empty string.\n\n"
      + "Sub-second precision in time values is truncated to whole "
      + "seconds (in both query params and body).\n\n"
      + "On success returns the task's full timelogs array. "
      + TIMELOG_HIDDEN + " Example:\n\n"
      + "```json\n"
      + "[\n"
      + "  {\n"
      + "    \"start\": \"2026-04-26T09:15:00.000Z\",\n"
      + "    \"end\": \"2026-04-26T10:45:00.000Z\",\n"
      + "    \"billable\": true,\n"
      + "    \"note\": \"Updated note\",\n"
      + "    \"user\": { \"oid\": \"u-123\", \"name\": \"John Doe\" }\n"
      + "  }\n"
      + "]\n"
      + "```";

    // The remove-timelog endpoints share this.
    private static final String TIMELOG_REMOVE_NOTES =
        "Removes the [time log](#definition-Timelog) identified by "
      + "the `(user, start, end)` triple from the task. Sub-second "
      + "precision in the query params "
      + "is truncated to whole seconds before matching.\n\n"
      + "On success returns the task's remaining timelogs array "
      + "(empty when no logs remain). " + TIMELOG_HIDDEN + " Example "
      + "after removing the "
      + "only log:\n\n"
      + "```json\n"
      + "[]\n"
      + "```\n\n"
      + "> Note: Like deleting a task, returns `204 No Content` if the "
      + "task or the project doesn't exist, or the task has been "
      + "deleted. Nothing is changed. Unlike deleting a task, it returns "
      + "`404` if the task exists but no log matches the triple.";

    // The timelog endpoints share these.
    private static final String TIMELOG_USER =
        "(Optional) User the existing log is credited to (OID, "
      + "ID, or email). Omitted defaults to the authenticated "
      + "caller.";

    private static final String TIMELOG_ADD_400 =
        "Bad Request — `start` / `end` missing or malformed, `end` "
      + "precedes `start`, `user` is unknown, or a field has the wrong "
      + "type.";

    private static final String TIMELOG_UPDATE_400 =
        "Bad Request — `start` / `end` query params missing or "
      + "malformed, `end` precedes `start` (in the query or the new "
      + "values), a `user` (in the query or the body) is unknown, or a "
      + "body field has the wrong type.";

    private static final String TIMELOG_REMOVE_400 =
        "Bad Request — `start` / `end` query params missing or "
      + "malformed, `end` precedes `start`, or `user` is unknown.";

    private static final String TIMELOG_403 =
        "Forbidden — the caller can't change this task's time logs "
      + "(checked before the log is looked up).";

    // The bulk endpoints share these.
    private static final String BULK_COST =
        "**Rate-limit cost**: 1 unit per item, charged upfront — for "
      + "`bulk-add`, per task created, nested `tasks` included. "
      + "`?dry-run=true` costs `ceil(N / 2)` units (minimum 1). Skipped "
      + "items are charged too. See [Rate Limits](#rate-limits).";

    private static final String BULK_DRY_RUN =
        "**Dry-run**: pass `?dry-run=true` to preview the call "
      + "without persisting any changes. Same per-item validation "
      + "and permission checks as a real call; same response "
      + "body shape on success and same `{code, message}` (with "
      + "`items[i]:` prefix) on failure. A `200` from a dry-run "
      + "is a strong signal that re-issuing the identical body "
      + "without `?dry-run` will succeed (subject to concurrent "
      + "edits between the two calls). Only `true`, `1`, or an empty "
      + "value turns it on; any other value, even `yes`, makes a real "
      + "write.";

    private static final String DRY_RUN_PARAM =
        "(Optional) Set to `true` to preview the call "
      + "without persisting any changes. Same validation, "
      + "permissions, and response shape as a real call, at half the "
      + "rate-limit cost (`ceil(N / 2)` units). Only `true`, `1`, or an "
      + "empty value turns it on; any other value, even `yes`, makes a "
      + "real write.";

    private static final String BULK_ATOMIC =
        "**Atomic**: a per-item validation, permission, or DB error rolls "
      + "back the whole batch and returns `{code, message}` with an "
      + "`items[i]: ` prefix naming the offending item. Fix the value and "
      + "retry.";

    private static final String BULK_SKIP =
        "**Skip-not-found**: a ref that doesn't resolve to a live task in "
      + "the project (already removed, never existed, a mistyped OID, "
      + "cascade-removed by an earlier item, etc.) is skipped — its "
      + "response slot is `null` and the rest of the batch proceeds. This "
      + "makes the endpoint **idempotent** for agents — safe to re-submit "
      + "after a partial network failure without diffing state first. "
      + "Only a ref that is neither a string nor an integer is rejected "
      + "with `400`.";

    private static final String BULK_REFS =
        "Task references. Each element is a task OID "
      + "(string), an integer ID, or a `\"#<id>\"` string. "
      + "Mixed forms allowed.";

    // The bulk-add endpoints share this.
    private static final String BULK_ADD_NOTES =
        "Body is a top-level JSON array of `CreateTaskBody` items "
      + "(no envelope) — same shape as single-task `POST /task/...`. "
      + "Items can include nested `tasks` to create subtrees in the "
      + "same call; only the **root** of each item is echoed in the "
      + "response (use `GET /task/list/id/{projectId}/{rootId}` to "
      + "enumerate the created subtree).\n\n"
      + "Example request body:\n\n"
      + "```json\n"
      + "[\n"
      + "  {\n"
      + "    \"name\": \"Design new logo\",\n"
      + "    \"priority\": 2,\n"
      + "    \"tags\": [\"design\"],\n"
      + "    \"assignees\": [\"me\"]\n"
      + "  },\n"
      + "  { \"name\": \"Write spec\", \"due\": \"2026-05-10\" },\n"
      + "  { \"name\": \"Review\" }\n"
      + "]\n"
      + "```\n\n"
      + "Example response (full mode, abbreviated):\n\n"
      + "```json\n"
      + "[\n"
      + "  { \"oid\": \"iuRRiKoyrxdBFhFTTo\", \"id\": 42, \"name\": \"Design new logo\", \"priority\": {\"value\": 2, \"name\": \"Urgent\"} },\n"
      + "  { \"oid\": \"abcXyzPqRs\",        \"id\": 43, \"name\": \"Write spec\" },\n"
      + "  { \"oid\": \"defLmnQrSt\",        \"id\": 44, \"name\": \"Review\" }\n"
      + "]\n"
      + "```\n\n"
      + "With `?return=compact`, each element is just `{oid, id}`.\n\n"
      + "Submitted order is preserved, whatever `?position=`: given "
      + "`[A, B, C]`, the tasks end up as `A, B, C` from top to bottom.\n\n"
      + BULK_ATOMIC + " Example error:\n\n"
      + "```json\n"
      + "{ \"code\": 400, \"message\": \"items[1]: Invalid value for `priority`: 99 (expected -1, 0, 1, or 2 (or one of Low/Medium/High/Urgent))\" }\n"
      + "```\n\n"
      + BULK_DRY_RUN + "\n\n"
      + "**Caveat — returned identifiers are not stable**: the "
      + "`oid` and `id` values in a dry-run response are "
      + "discarded along with everything else. A follow-up real "
      + "call allocates fresh identifiers, so don't persist or "
      + "reference dry-run OIDs / IDs across calls.\n\n"
      + BULK_COST + "\n\n"
      + BULK_CAP + " Nested `tasks` count as items too, for both "
      + "the cost and the batch size.";

    private static final String BULK_ADD_POSITION =
        "(Optional) Placement of the new tasks relative to the anchor "
      + "task: `parent` (default) makes them its children; `before` / "
      + "`after` make them its siblings, immediately before / after it. "
      + "Any other value returns `400 Bad Request`.";

    private static final String BULK_ADD_402 =
        "Payment Required (`code` `469`) — the batch would exceed the "
      + "plan's task limit, or holds more items than the plan allows per "
      + "call (see **Batch size**).";

    // The bulk-update, bulk-remove, bulk-move and bulk-approve endpoints
    // share this.
    private static final String BULK_402 =
        "Payment Required (`code` `469`) — the batch holds more items "
      + "than the plan allows per call (see **Batch size**).";

    // The bulk-update endpoints share this.
    private static final String BULK_UPDATE_NOTES =
        "Updates up to **300 tasks** in one transaction. Body is "
      + "a top-level JSON array of `BulkUpdateTaskItem` — each item "
      + "carries exactly one of `oid` / `id` — `id` may be an integer or "
      + "a `\"#42\"` string — plus the `UpdateTaskBody`-shape fields to "
      + "apply. Both or neither is rejected with `400`.\n\n"
      + "Request body:\n\n"
      + "```json\n"
      + "[\n"
      + "  { \"id\": 42, \"status\": 100, \"addTags\": [\"done\"] },\n"
      + "  { \"id\": 99, \"addAssignees\": [\"alice@example.com\"] },\n"
      + "  { \"id\": 17, \"name\": \"Renamed\" }\n"
      + "]\n"
      + "```\n\n"
      + "Tags must exist already in the project (or be global tags of its "
      + "organization); an unknown tag is rejected with `400`.\n\n"
      + "Response (compact mode; slot for a not-found item is `null`):\n\n"
      + "```json\n"
      + "[\n"
      + "  { \"oid\": \"iuRRiKoyrxdBFhFTTo\", \"id\": 42 },\n"
      + "  null,\n"
      + "  { \"oid\": \"defLmnQrSt\", \"id\": 17 }\n"
      + "]\n"
      + "```\n\n"
      + "In full mode, each slot is the full updated task record, "
      + "`parent` included.\n\n"
      + BULK_ATOMIC + " Example error:\n\n"
      + "```json\n"
      + "{ \"code\": 400, \"message\": \"items[1]: Invalid value for `priority`: 99 (expected -1, 0, 1, or 2 (or one of Low/Medium/High/Urgent))\" }\n"
      + "```\n\n"
      + BULK_SKIP + "\n\n"
      + BULK_DRY_RUN + "\n\n"
      + BULK_COST + "\n\n"
      + BULK_CAP;

    // The bulk-remove endpoints share this.
    private static final String BULK_REMOVE_NOTES =
        "Removes up to **300 tasks**, with their subtasks, in one "
      + "transaction. Restore any of them with "
      + "`PUT /task/undo-remove/...`. Body is "
      + "a top-level JSON array of task references — each element "
      + "is one of:\n"
      + "- a task OID (string),\n"
      + "- an integer task ID, or\n"
      + "- a `\"#<id>\"` string (the ID-with-hash form).\n\n"
      + "Mixed forms in the same batch are allowed.\n\n"
      + "Request body (mixed forms — all three reference shapes "
      + "in one batch):\n\n"
      + "```json\n"
      + "[ 42, \"#99\", \"iuRRiKoyrxdBFhFTTo\" ]\n"
      + "```\n\n"
      + "Response — always `{oid}` per item, whatever `?return=` (slot 1 "
      + "didn't resolve — already removed — so it's `null`):\n\n"
      + "```json\n"
      + "[\n"
      + "  { \"oid\": \"abc1Foo\" },\n"
      + "  null,\n"
      + "  { \"oid\": \"iuRRiKoyrxdBFhFTTo\" }\n"
      + "]\n"
      + "```\n\n"
      + BULK_ATOMIC + "\n\n"
      + BULK_SKIP + " If the project itself doesn't exist, it returns "
      + "`204 No Content` with no body, like deleting a task.\n\n"
      + BULK_DRY_RUN + "\n\n"
      + BULK_COST + "\n\n"
      + BULK_CAP;

    // The bulk-move endpoints share this.
    private static final String BULK_MOVE_NOTES =
        "Repositions up to **300 tasks** (with their subtasks) within a "
      + "project to the same destination anchor in one transaction. Same "
      + "query-param grammar as single-task `PUT /task/move/...`:\n"
      + "- `?task=root` — move items to project root (only valid "
      + "with default `?position=parent`).\n"
      + "- `?task={ref}&position=parent` (default) — items become "
      + "children of the reference task.\n"
      + "- `?task={ref}&position=before` — items become siblings "
      + "immediately before the reference.\n"
      + "- `?task={ref}&position=after` — items become siblings "
      + "immediately after the reference.\n\n"
      + "`{ref}` follows the URL grammar (same as single-task "
      + "move): the by-ID URL form expects an integer task ID "
      + "(or `root`); the by-OID form expects a task OID. A `{ref}` "
      + "that isn't found, or is in another project, is rejected with "
      + "`400`. Items in the **body** use the bulk mixed-form convention "
      + "(OID / integer ID / `#<id>` string) — same as bulk-remove.\n\n"
      + "Request body (mixed-form refs):\n\n"
      + "```json\n"
      + "[ 42, \"#17\", \"iuRRiKoyrxdBFhFTTo\" ]\n"
      + "```\n\n"
      + "Response (compact mode; slot 1 was already removed by another caller, so it's `null`):\n\n"
      + "```json\n"
      + "[\n"
      + "  { \"oid\": \"abc1Foo\", \"id\": 42 },\n"
      + "  null,\n"
      + "  { \"oid\": \"iuRRiKoyrxdBFhFTTo\", \"id\": 88 }\n"
      + "]\n"
      + "```\n\n"
      + BULK_ATOMIC + " Moving a task under one of its own subtasks is "
      + "such an error (`400`).\n\n"
      + BULK_SKIP + " An item in another project is skipped too.\n\n"
      + "**Order preservation (sliding chain)**: items are moved "
      + "in submitted order. Items 1..N-1 are positioned *after "
      + "the previous successful move*, regardless of the URL "
      + "`?position=` — without this, `?position=after R` would "
      + "reverse the batch.\n\n"
      + BULK_DRY_RUN + "\n\n"
      + BULK_COST + "\n\n"
      + BULK_CAP + "\n\n"
      + "All items must belong to the project in the URL. "
      + "Cross-project moves go through bulk-transfer.";

    private static final String BULK_MOVE_400 =
        "Bad Request — body not a JSON "
      + "array, empty, over the 300-item cap, item shape error, "
      + "`?task=` missing, not found, or in another project, invalid "
      + "`?position=`, `?task=root&position=before|after`, or an item "
      + "moved under its own subtask. Whole batch rolled back.";

    // The bulk-transfer endpoints share this.
    private static final String BULK_TRANSFER_NOTES =
        "Transfers up to **300 tasks**, with their subtasks, from the "
      + "source project (URL path) to a different target project "
      + "(`?project=`) in one transaction. Same query-param grammar as "
      + "single-task `PUT /task/transfer/...`:\n"
      + "- `?project={target}` (required) — target project, "
      + "or `-` for your personal tasks.\n"
      + "- `?task={ref}` (optional) — destination anchor in the "
      + "target project. Omitted, empty, or `root` → items "
      + "become root tasks of the target. A `{ref}` that isn't found "
      + "there is rejected with `400`.\n"
      + "- `?position=` — `parent` (default), `before`, or `after`.\n"
      + "- `?invite`, `?tag`, `?status`, `?custom-field` — auto-"
      + "remap flags, all on by default; any value but `true`, `1`, or "
      + "empty turns one off.\n\n"
      + "In the by-ID URL form, `?project=` and `?task=` are IDs; in the "
      + "by-OID form, OIDs. Items in the **body** use the bulk mixed-form "
      + "convention (OID / integer ID / `#<id>` string) — same as "
      + "bulk-remove / bulk-move.\n\n"
      + "Request body (mixed-form refs):\n\n"
      + "```json\n"
      + "[ 42, \"#99\", \"iuRRiKoyrxdBFhFTTo\" ]\n"
      + "```\n\n"
      + "Response (compact mode; OIDs are preserved across "
      + "transfer, IDs are reassigned from the target's counter):\n\n"
      + "```json\n"
      + "[\n"
      + "  { \"oid\": \"abc1Foo\",            \"id\": 7 },\n"
      + "  null,\n"
      + "  { \"oid\": \"iuRRiKoyrxdBFhFTTo\", \"id\": 8 }\n"
      + "]\n"
      + "```\n\n"
      + BULK_ATOMIC + " Both projects are rolled back.\n\n"
      + BULK_SKIP + " An item not in the source project (already in "
      + "the target, or in another project) is skipped too.\n\n"
      + "**Source == target**: if `?project=` resolves to the "
      + "same project as the URL source, the whole batch is "
      + "rejected with `400` (use `bulk-move` for within-project "
      + "reorder).\n\n"
      + "**Order preservation (sliding chain)**: items are "
      + "transferred in submitted order — items 1..N-1 land "
      + "*after the previous successful transfer*, regardless "
      + "of `?position=`.\n\n"
      + BULK_DRY_RUN + " Nothing is persisted in either project.\n\n"
      + BULK_COST + "\n\n"
      + BULK_CAP;

    private static final String BULK_TRANSFER_400 =
        "Bad Request — body not a JSON "
      + "array, empty, over the 300-item cap, item shape error, "
      + "missing `?project=`, `?task=` not found in the target, invalid "
      + "`?position=`, `?task=root&position=before|after`, `?project=` "
      + "resolves to the URL source project, or any per-item validation "
      + "failure. Whole batch rolled back.";

    private static final String BULK_TRANSFER_402 =
        "Payment Required (`code` `469`) — the batch "
      + "holds more items than the plan allows per call (see "
      + "**Batch size**); the target would go over its plan's task or "
      + "storage quota; or a task (or a subtask) has reminders and the "
      + "target's plan doesn't include reminders. Whole batch rolled back.";

    // The bulk-approve endpoints share this.
    private static final String BULK_APPROVE_NOTES =
        "Applies a single approval transition (`request` / "
      + "`approve` / `reject` / `change`) to all tasks in the "
      + "batch. Body is a top-level JSON array of task references "
      + "(OID string, integer ID, or `\"#<id>\"` string; mixed "
      + "forms allowed). Up to **300** items per call; see "
      + "**Batch size** below.\n\n"
      + "Response: one slot per item, in order — the task's full record "
      + "(with its `approval`), or `{oid, id}` with `?return=compact`.\n\n"
      + "**Permissions** — same per-task role checks as the "
      + "single-task `POST /task/approve/...`: the caller must be a "
      + "claimer (for `request`) or an approver (for the others) of the "
      + "category.\n\n"
      + "**Comments** — like the single-task endpoint, `approve` / "
      + "`reject` / `change` log a comment on each task holding "
      + "only a header; `request` logs nothing. There is no body "
      + "field for comment text. " + APPROVAL_COMMENT_HEADER + "\n\n"
      + BULK_ATOMIC + " A per-item permission failure is such an "
      + "error.\n\n"
      + BULK_SKIP + "\n\n"
      + BULK_DRY_RUN + "\n\n"
      + BULK_COST + "\n\n"
      + BULK_CAP;

    // The endpoints that take `-` for a project share this.
    private static final String PERSONAL_TASKS =
        "Specify `-` for your personal tasks (those in My Tasks that "
      + "belong to no project).";

    @POST
    @Path("/{oid}")
    @ApiOperation(
        value = "Add a new root task, or relative to another task.",
        notes = "Adds a new task to a project, under another task, or before or after another task.\n\n"
              + ADD_NESTED,
        response = Task.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — newly created task.", response = Task.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation "
              + "failed, or `position` is `before` / `after` while `oid` is "
              + "a project."),
        @ApiResponse(code = 402, message = CREATE_402),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to add a task here."),
        @ApiResponse(code = 404, message = "Not Found — project or parent task does not exist.")
    })
    public Response createTask(
        @ApiParam(
            value = "OID of the project or task to which the new task will be added. "
                  + "If the OID refers to a project, the new task becomes a root task. "
                  + "If it refers to a task, the new task becomes its subtask. "
                  + PERSONAL_TASKS,
            required = true
        )
        @PathParam("oid") String oid,

        @ApiParam(value = "Task to create.", required = true)
        CreateTaskBody data,

        @ApiParam(
            value = "(Optional) Placement of the new task relative to the "
                + "task referenced by `oid`:\n"
                + "- `parent` (default): child of the referenced task.\n"
                + "- `before`: sibling immediately before the referenced task.\n"
                + "- `after`: sibling immediately after the referenced task.\n\n"
                + "Omitted or empty → same as `parent`. Any other value "
                + "returns `400 Bad Request`, and so do `before` / `after` "
                + "when `oid` refers to a project.",
            example = "before",
            allowableValues = "parent, before, after"
        )
        @QueryParam("position") String position,

        @ApiParam(
            value = RETURN_TASK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @POST
    @Path("/id/{projectId}")
    @ApiOperation(
        value = "Add a new root task.",
        notes = "Adds a new root task to a project.\n\n"
              + "Minimal request body only needs `name`. A fuller example:\n\n"
              + "```json\n"
              + "{\n"
              + "  \"name\": \"Design new logo\",\n"
              + "  \"description\": \"Needs to match the brand guide.\",\n"
              + "  \"priority\": 2,\n"
              + "  \"tags\": [\"design\", \"urgent\"],\n"
              + "  \"assignees\": [\"me\"],\n"
              + "  \"start\": \"2026-05-01\",\n"
              + "  \"due\": \"2026-05-10\",\n"
              + "  \"Priority\": 3,\n"
              + "  \"Owners\": [\"alice@example.com\"]\n"
              + "}\n"
              + "```\n\n"
              + "> `Priority` / `Owners` in this example are **custom-field** keys "
              + "from the project's own `/project/add-field` definitions — they are "
              + "not fixed API fields and are only recognised by name. See "
              + "`CreateTaskBody.yourField` for the rules on each custom-field type.\n\n"
              + "Tags must exist already, as tags of the project or global "
              + "tags of its organization; an unknown tag is rejected with "
              + "`400`, not created.\n\n"
              + ADD_NESTED,
        response = Task.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — newly created task.", response = Task.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 402, message = CREATE_402),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to add a task to this project."),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response createTaskByProjectId(
        @ApiParam(
            value = "ID of the project to which this new task will be added. "
                  + "The task will be created as a root task. Specify \"-\" "
                  + "to add it to personal tasks (in My Tasks).",
            required = true,
            example = "my_project"
        )
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Task to create", required = true)
        CreateTaskBody data,

        @ApiParam(
            value = RETURN_TASK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @POST
    @Path("/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Add a new task relative to another task.",
        notes = "Adds a new task under another task, or before or after another task. "
              + "See `POST /task/id/{projectId}` for a full request-body example — "
              + "the body shape is identical.\n\n"
              + ADD_NESTED,
        response = Task.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — newly created task.", response = Task.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 402, message = CREATE_402),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to add a task here."),
        @ApiResponse(code = 404, message = "Not Found — project or parent task does not exist.")
    })
    public Response createTaskByTaskId(
        @ApiParam(
            value = "ID of the project to which the new task will be added. "
                  + "Specify \"-\" to add it to personal tasks in My Tasks.",
            required = true,
            example = "my_project"
        )
        @PathParam("projectId") String projectId,

        @ApiParam(value = "ID of the referenced task.", required = true, example = "42")
        @PathParam("taskId") int taskId,

        @ApiParam(value = "Task to create.", required = true)
        CreateTaskBody data,

        @ApiParam(
            value = "(Optional) Placement of the new task relative to the "
                + "task referenced by `taskId`:\n"
                + "- `parent` (default): child of the referenced task.\n"
                + "- `before`: sibling immediately before the referenced task.\n"
                + "- `after`: sibling immediately after the referenced task.\n\n"
                + "Omitted or empty → same as `parent`. Any other value "
                + "returns `400 Bad Request`.",
            example = "before",
            allowableValues = "parent, before, after"
        )
        @QueryParam("position") String position,

        @ApiParam(
            value = RETURN_TASK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @GET
    @Path("/list/{oid}")
    @ApiOperation(
        value = "Get all root tasks of the given project or all subtasks of the given task.",
        notes = "Returns all root tasks of a project or all direct subtasks of a task by OID. "
              + "If the OID is a project, root tasks are returned. If it is a task, its direct subtasks are returned.\n\n"
              + "One level is returned by default. When the OID is a **task**, `?depth=N` / "
              + "`?depth=full` walks the subtree in a single call — each task in the "
              + "response then carries a nested `tasks` field with its walked children, "
              + "recursively. `?depth>1` is not "
              + "available when the OID is a project (there is no parent task to walk from); "
              + "use the flat list per level instead.\n\n"
              + SUBTREE_NOTES,
        response = Task.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of tasks (may be empty).",
            response = Task.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request — invalid `?status=` / `?depth=` / `?limit=` / `?return=` value, `?depth>1` when the OID is a project, `?depth>1` combined with `?limit`/`?cursor`, or `?cursor=` doesn't belong to this list."),
        @ApiResponse(code = 402, message = LIST_DEPTH_402),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — project or parent task does not exist.")
    })
    public Response getTasksByOid(
        @ApiParam(
            value = "OID of the project or parent task. " + PERSONAL_TASKS,
            required = true
        )
        @PathParam("oid") String oid,

        @ApiParam(
            value = LIST_STATUS + LIST_STATUS_DEPTH,
            example = "active",
            required = false
        )
        @QueryParam("status") String status,

        @ApiParam(
            value = "Subtree depth, only when the OID is a task. Omit or `1` for the "
                  + "default flat single-level list. Any positive integer returns a "
                  + "multi-level subtree capped at that depth; `full` walks every level "
                  + "(bounded by the per-tier total-nodes cap). `?depth>1` requires the Professional "
                  + "plan or above and cannot be combined with `?limit`/`?cursor`.",
            example = "2",
            required = false
        )
        @QueryParam("depth") String depth,

        @ApiParam(
            value = "Page size (1..1000). Omit for the default unpaginated full list, "
                  + "or pass `no` to be explicit. When set and more results exist, "
                  + "the last item in the response carries a `cursor` field.",
            example = "100",
            required = false
        )
        @QueryParam("limit") String limit,

        @ApiParam(
            value = "Continuation token. Pass back the `cursor` value from the last "
                  + "item of the previous page to fetch the next page. Re-pass any "
                  + "filter (`?status=`) and `?limit=` on the same call.",
            required = false
        )
        @QueryParam("cursor") String cursor,

        @ApiParam(
            value = RETURN_LIST + RETURN_LIST_DEPTH,
            example = "compact",
            allowableValues = "full, compact",
            required = false
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @GET
    @Path("/list/id/{projectId}")
    @ApiOperation(
        value = "Get all root tasks of the given project.",
        notes = "Returns all root tasks of the project as a bare JSON array.\n\n"

              + "**Pagination is opt-in** via `?limit=N` (1..1000). Without `?limit=` "
              + "or `?cursor=`, the response is the full bare array. When `?limit=N` "
              + "is set and more results exist, the LAST item carries "
              + "`\"cursor\": \"<token>\"` — pass it back as `?cursor=<token>` "
              + "(plus the same `?limit=` and any filter like `?status=`) to fetch the "
              + "next page. End of stream is signalled by the absence of `cursor` on "
              + "the last item. `?limit=no` is an explicit \"return everything\". "
              + "Same pagination grammar applies to the subtasks endpoint "
              + "`GET /task/list/id/{projectId}/{taskId}`.\n\n"

              + "Pair with `?return=compact` to render each item as `{oid, id, cursor?}` "
              + "for navigation/lookup workloads at minimal token cost.\n\n"

              + LIST_COST + "\n\n"

              + "`?depth=` isn't available here: only `1`, the default, is "
              + "accepted. To walk a subtree, list a task's subtasks with "
              + "`GET /task/list/id/{projectId}/{taskId}?depth=…`. "
              + "To retrieve all tasks (including all subtasks), use the search API.",
        response = Task.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — bare array of root tasks (may be empty). With `?limit=N`, the last item may carry a `cursor` field if more results exist.",
            response = Task.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request — invalid `?status=` / `?limit=` / `?return=` value, `?depth=` other than `1`, or `?cursor=` doesn't belong to this list."),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response getRootTasks(
        @ApiParam(
            value = "ID of the task's project. "
                  + "Specify \"-\" for personal tasks in My Tasks.",
            required = true,
            example = "my_project"
        )
        @PathParam("projectId") String projectId,

        @ApiParam(
            value = LIST_STATUS,
            example = "active",
            required = false
        )
        @QueryParam("status") String status,

        @ApiParam(
            value = "Page size (1..1000). Omit for the default unpaginated full list, "
                  + "or pass `no` to be explicit. When set and more results exist, "
                  + "the last item in the response carries a `cursor` field.",
            example = "100",
            required = false
        )
        @QueryParam("limit") String limit,

        @ApiParam(
            value = "Continuation token. Pass back the `cursor` value from the last "
                  + "item of the previous page to fetch the next page. Re-pass any "
                  + "filter (`?status=`) and `?limit=` on the same call.",
            required = false
        )
        @QueryParam("cursor") String cursor,

        @ApiParam(
            value = RETURN_LIST,
            example = "compact",
            allowableValues = "full, compact",
            required = false
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @GET
    @Path("/list/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Get the subtasks of the given task.",
        notes = "By default returns only the direct subtasks of `{taskId}` (one level). "
              + "To fetch a whole subtree in a single call, set `?depth=N` or `?depth=full` — "
              + "each task in the response then carries a nested `tasks` field with its "
              + "walked children, recursively.\n\n"
              + SUBTREE_NOTES,
        response = Task.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of subtasks (may be empty). With `?depth>1`, each entry may carry nested `tasks` + `cropped` fields.",
            response = Task.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request — invalid `?status=` / `?depth=` / `?limit=` / `?return=` value, `?depth>1` combined with `?limit`/`?cursor`, or `?cursor=` doesn't belong to this list."),
        @ApiResponse(code = 402, message = LIST_DEPTH_402 + " Use `?depth=1` (or omit `?depth`) for unrestricted flat per-level listing."),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — project or parent task does not exist.")
    })
    public Response getSubtasks(
        @ApiParam(
            value = "ID of the task's project. "
                  + "Specify \"-\" for personal tasks in My Tasks.",
            required = true,
            example = "my_project"
        )
        @PathParam("projectId") String projectId,

        @ApiParam(value = "Parent task ID.", required = true, example = "42")
        @PathParam("taskId") int taskId,

        @ApiParam(
            value = LIST_STATUS + LIST_STATUS_DEPTH,
            example = "active",
            required = false
        )
        @QueryParam("status") String status,

        @ApiParam(
            value = "Subtree depth. Omit or `1` for the default flat single-level list "
                  + "(immediate children only). Any positive integer returns a multi-level "
                  + "subtree capped at the given depth; `full` walks every level (bounded by "
                  + "the per-tier total-nodes cap). `?depth>1` requires the Professional plan "
                  + "or above and cannot be combined with `?limit`/`?cursor`.",
            example = "2",
            required = false
        )
        @QueryParam("depth") String depth,

        @ApiParam(
            value = "Page size for the flat path (1..1000). Omit for the default unpaginated "
                  + "full list. Pass `no` to be explicit. When set and more results exist, "
                  + "the last item in the response carries a `cursor` field. "
                  + "Cannot be combined with `?depth>1`.",
            example = "100",
            required = false
        )
        @QueryParam("limit") String limit,

        @ApiParam(
            value = "Continuation token. Pass back the `cursor` value from the last item of "
                  + "the previous page to fetch the next page. Re-pass `?limit=` and any "
                  + "filter (`?status=`) on the same call. Cannot be combined with `?depth>1`.",
            required = false
        )
        @QueryParam("cursor") String cursor,

        @ApiParam(
            value = RETURN_LIST + RETURN_LIST_DEPTH + " Pair "
                  + "with `?depth=full` (subtree) or `?limit=N` (paginated) to fetch a "
                  + "whole subtree's / page's identifier graph at minimal token cost; the "
                  + "agent then `GET /task/{oid}` for only the items it wants in detail.",
            example = "compact",
            allowableValues = "full, compact",
            required = false
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get an existing task by its OID.",
        notes = "Returns the full task record.",
        response = TaskWithParentInfo.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — task record.", response = TaskWithParentInfo.class),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — task does not exist.")
    })
    public Response getTask(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @GET
    @Path("/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Get an existing task by its ID.",
        notes = "Returns the full task record.",
        response = TaskWithParentInfo.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — task record.", response = TaskWithParentInfo.class),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — task does not exist.")
    })
    public Response getTaskById(
        @ApiParam(
            value = "ID of the task's project. "
                  + "Specify \"-\" for personal tasks in My Tasks.",
            required = true,
            example = "my_project"
        )
        @PathParam("projectId") String projectId,

        @ApiParam(value = "Task ID.", required = true, example = "42")
        @PathParam("taskId") int taskId
    ) { return null; }

    @PUT
    @Path("/{oid}")
    @ApiOperation(
        value = "Update an existing task by its OID.",
        notes = UPDATE_NOTES,
        response = TaskWithParentInfo.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated task record.", response = TaskWithParentInfo.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to update this task."),
        @ApiResponse(code = 404, message = "Not Found — task does not exist."),
        @ApiResponse(code = 409, message = "Conflict — project settings block completing the task (an open predecessor, or a pending approval). Resolve it, then retry.")
    })
    public Response updateTask(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Updated task content.", required = true)
        UpdateTaskBody data,

        @ApiParam(
            value = RETURN_TASK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Update an existing task by its ID.",
        notes = UPDATE_NOTES,
        response = TaskWithParentInfo.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated task record.", response = TaskWithParentInfo.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to update this task."),
        @ApiResponse(code = 404, message = "Not Found — task does not exist."),
        @ApiResponse(code = 409, message = "Conflict — project settings block completing the task (an open predecessor, or a pending approval). Resolve it, then retry.")
    })
    public Response updateTaskById(
        @ApiParam(
            value = "ID of the task's project. "
                  + "Specify \"-\" for personal tasks in My Tasks.",
            required = true,
            example = "my_project"
        )
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Task ID.", required = true, example = "42")

        @PathParam("taskId") int taskId,
        @ApiParam(value = "Updated task content.", required = true)
        UpdateTaskBody data,

        @ApiParam(
            value = RETURN_TASK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/move/{oid}")
    @ApiOperation(
        value = "Move an existing task by its OID.",
        notes = MOVE_NOTES + "\n\n" + MOVE_EXAMPLES,
        response = TaskWithParentInfo.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated task record.", response = TaskWithParentInfo.class),
        @ApiResponse(code = 400, message = MOVE_400),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to move this task."),
        @ApiResponse(code = 404, message = "Not Found — task does not exist.")
    })
    public Response moveTask(
        @ApiParam(value = "Task OID to be moved.", required = true)
        @PathParam("oid") String oid,

        @ApiParam(
            value = "Reference task OID. How it's used depends on `position`:\n"
                + "- `parent` (default): the moved task becomes a child of `task`.\n"
                + "- `before` / `after`: the moved task is placed as a sibling, "
                + "under `task`'s parent.\n\n"
                + "Specify `root` to move to the root level (only valid with the "
                + "default `parent` position).",
            required = true,
            example = "0Mg3VQ8kWeiVbLH1JjvzUcP7"
        )
        @QueryParam("task") String task,

        @ApiParam(
            value = "(Optional) Placement relative to `task`:\n"
                + "- `parent` (default): child of `task`.\n"
                + "- `before`: sibling immediately before `task`.\n"
                + "- `after`: sibling immediately after `task`.\n\n"
                + "Omitted or empty → same as `parent`. Any other value returns "
                + "`400 Bad Request`. `before` / `after` require `task` to refer "
                + "to a task (not `root`).",
            example = "before",
            allowableValues = "parent, before, after"
        )
        @QueryParam("position") String position,

        @ApiParam(
            value = RETURN_TASK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/move/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Move an existing task by its ID.",
        notes = MOVE_NOTES + "\n\n" + MOVE_EXAMPLES,
        response = TaskWithParentInfo.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated task record.", response = TaskWithParentInfo.class),
        @ApiResponse(code = 400, message = MOVE_400),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to move this task."),
        @ApiResponse(code = 404, message = "Not Found — task does not exist.")
    })
    public Response moveTaskById(
        @ApiParam(
            value = "ID of the task's project. "
                  + "Specify \"-\" for personal tasks in My Tasks.",
            required = true,
            example = "my_project"
        )
        @PathParam("projectId") String projectId,

        @ApiParam(value = "Task ID to be moved.", required = true, example = "42")
        @PathParam("taskId") int taskId,

        @ApiParam(
            value = "Reference task ID. How it's used depends on `position`:\n"
                + "- `parent` (default): the moved task becomes a child of `task`.\n"
                + "- `before` / `after`: the moved task is placed as a sibling, "
                + "under `task`'s parent.\n\n"
                + "Specify `root` to move to the root level (only valid with the "
                + "default `parent` position).",
            required = true,
            example = "253"
        )
        @QueryParam("task") String task,

        @ApiParam(
            value = "(Optional) Placement relative to `task`:\n"
                + "- `parent` (default): child of `task`.\n"
                + "- `before`: sibling immediately before `task`.\n"
                + "- `after`: sibling immediately after `task`.\n\n"
                + "Omitted or empty → same as `parent`. Any other value returns "
                + "`400 Bad Request`. `before` / `after` require `task` to refer "
                + "to a task (not `root`).",
            example = "before",
            allowableValues = "parent, before, after"
        )
        @QueryParam("position") String position,

        @ApiParam(
            value = RETURN_TASK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/transfer/{oid}")
    @ApiOperation(
        value = "Transfer an existing task by its OID to another project.",
        notes = TRANSFER_NOTES + "\n\n" + TRANSFER_EXAMPLES,
        response = TaskWithParentInfo.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated task record.", response = TaskWithParentInfo.class),
        @ApiResponse(code = 400, message = TRANSFER_400),
        @ApiResponse(code = 402, message = TRANSFER_402),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to transfer this task."),
        @ApiResponse(code = 404, message = "Not Found — task or target project does not exist.")
    })
    public Response transferTask(
        @ApiParam(value = "Task OID to be transferred.", required = true)
        @PathParam("oid") String oid,

        @ApiParam(
            value = "OID of the target project to which the task will be transferred. "
                  + PERSONAL_TASKS,
            required = true,
            example = "0Mg3VQ8kWeiVbLH1PrjzUc89"
        )
        @QueryParam("project") String project,

        @ApiParam(
            value = "(Optional) OID of a reference task in the **target** "
                + "project. How it's used depends on `position`:\n"
                + "- `parent` (default): the transferred task becomes a "
                + "child of `task`.\n"
                + "- `before` / `after`: the transferred task is placed as "
                + "a sibling, under `task`'s parent.\n\n"
                + "If omitted (or `root`), the transferred task becomes a "
                + "root task in the target project.",
            example = "0Mg3VQ8kWeiVbLH1JjvzUcP7"
        )
        @QueryParam("task") String task,

        @ApiParam(
            value = "(Optional) Placement relative to `task` in the target "
                + "project:\n"
                + "- `parent` (default): child of `task`.\n"
                + "- `before`: sibling immediately before `task`.\n"
                + "- `after`: sibling immediately after `task`.\n\n"
                + "Omitted or empty → same as `parent`. Any other value "
                + "returns `400 Bad Request`. `before` / `after` require "
                + "`task` to refer to a task (not omitted, not `root`).",
            example = "before",
            allowableValues = "parent, before, after"
        )
        @QueryParam("position") String position,

        @ApiParam(
            value = TRANSFER_INVITE,
            example = "true"
        )
        @QueryParam("invite") String invite,
        @ApiParam(
            value = TRANSFER_TAG,
            example = "true"
        )
        @QueryParam("tag") String tag,
        @ApiParam(
            value = TRANSFER_STATUS,
            example = "true"
        )
        @QueryParam("status") String status,
        @ApiParam(
            value = TRANSFER_CUSTOM_FIELD,
            example = "true"
        )
        @QueryParam("custom-field") String customField,

        @ApiParam(
            value = RETURN_TASK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/transfer/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Transfer an existing task by its ID to another project.",
        notes = TRANSFER_NOTES + "\n\n" + TRANSFER_EXAMPLES,
        response = TaskWithParentInfo.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated task record.", response = TaskWithParentInfo.class),
        @ApiResponse(code = 400, message = TRANSFER_400),
        @ApiResponse(code = 402, message = TRANSFER_402),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to transfer this task."),
        @ApiResponse(code = 404, message = "Not Found — task or target project does not exist.")
    })
    public Response transferTaskById(
        @ApiParam(
            value = "ID of the task's project. "
                  + "Specify \"-\" for personal tasks in My Tasks.",
            required = true,
            example = "my_project"
        )
        @PathParam("projectId") String projectId,

        @ApiParam(value = "Task ID to be transferred.", required = true, example = "42")
        @PathParam("taskId") int taskId,

        @ApiParam(
            value = "ID of the target project to which the task will be transferred. "
                  + PERSONAL_TASKS,
            required = true,
            example = "my_archive"
        )
        @QueryParam("project") String project,

        @ApiParam(
            value = "(Optional) ID of a reference task in the **target** "
                + "project. How it's used depends on `position`:\n"
                + "- `parent` (default): the transferred task becomes a "
                + "child of `task`.\n"
                + "- `before` / `after`: the transferred task is placed as "
                + "a sibling, under `task`'s parent.\n\n"
                + "If omitted (or `root`), the transferred task becomes a "
                + "root task in the target project.",
            example = "253"
        )
        @QueryParam("task") String task,

        @ApiParam(
            value = "(Optional) Placement relative to `task` in the target "
                + "project:\n"
                + "- `parent` (default): child of `task`.\n"
                + "- `before`: sibling immediately before `task`.\n"
                + "- `after`: sibling immediately after `task`.\n\n"
                + "Omitted or empty → same as `parent`. Any other value "
                + "returns `400 Bad Request`. `before` / `after` require "
                + "`task` to refer to a task (not omitted, not `root`).",
            example = "before",
            allowableValues = "parent, before, after"
        )
        @QueryParam("position") String position,

        @ApiParam(
            value = TRANSFER_INVITE,
            example = "true"
        )
        @QueryParam("invite") String invite,
        @ApiParam(
            value = TRANSFER_TAG,
            example = "true"
        )
        @QueryParam("tag") String tag,
        @ApiParam(
            value = TRANSFER_STATUS,
            example = "true"
        )
        @QueryParam("status") String status,
        @ApiParam(
            value = TRANSFER_CUSTOM_FIELD,
            example = "true"
        )
        @QueryParam("custom-field") String customField,

        @ApiParam(
            value = RETURN_TASK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @POST
    @Path("/attach/{taskOid}/{filename}")
    @ApiOperation(
        value = "Upload an attachment to a task by OID.",
        notes = ATTACH_NOTES,
        response = SimpleAttachment.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — newly created attachment.", response = SimpleAttachment.class),
        @ApiResponse(code = 400, message = "Bad Request — filename or payload invalid, or an executable file is not allowed for this project."),
        @ApiResponse(code = 402, message = ATTACH_402),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to attach to this task."),
        @ApiResponse(code = 404, message = "Not Found — task does not exist.")
    })
    public Response attachTaskByOid(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("taskOid") String taskOid,
        @ApiParam(
            value = ATTACH_FILENAME,
            required = true,
            example = "readme.txt"
        )
        @PathParam("filename") String filename,
        @ApiParam(
            value = "Attachment content (raw bytes). For images, the body is the image itself.",
            required = true
        )
        Object data
    ) { return null; }

    @POST
    @Path("/attach/id/{projectId}/{taskId}/{filename}")
    @ApiOperation(
        value = "Upload an attachment to a task by ID.",
        notes = ATTACH_NOTES,
        response = SimpleAttachment.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — newly created attachment.", response = SimpleAttachment.class),
        @ApiResponse(code = 400, message = "Bad Request — filename or payload invalid, or an executable file is not allowed for this project."),
        @ApiResponse(code = 402, message = ATTACH_402),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to attach to this task."),
        @ApiResponse(code = 404, message = "Not Found — task does not exist.")
    })
    public Response attachTaskById(
        @ApiParam(
            value = "ID of the task's project. "
                  + "Specify \"-\" for personal tasks in My Tasks.",
            required = true,
            example = "my_project"
        )
        @PathParam("projectId") String projectId,

        @ApiParam(value = "Task ID.", required = true, example = "42")
        @PathParam("taskId") int taskId,

        @ApiParam(
            value = ATTACH_FILENAME,
            required = true,
            example = "readme.txt"
        )
        @PathParam("filename") String filename,

        @ApiParam(
            value = "Attachment content (raw bytes). For images, the body is the image itself.",
            required = true
        )
        Object data
    ) { return null; }

    @PUT
    @Path("/undo-remove/{oid}")
    @ApiOperation(
        value = "Undo the removal of a task by its OID.",
        notes = UNDO_REMOVE_NOTES,
        response = TaskWithParentInfo.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — restored task record.", response = TaskWithParentInfo.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to restore this task."),
        @ApiResponse(code = 404, message = "Not Found — task does not exist."),
        @ApiResponse(code = 402, message = UNDO_REMOVE_402)
    })
    public Response undoRemoveTaskByOid(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("oid") String oid,

        @ApiParam(
            value = RETURN_TASK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/undo-remove/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Undo the removal of a task by its ID.",
        notes = UNDO_REMOVE_NOTES,
        response = TaskWithParentInfo.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — restored task record.", response = TaskWithParentInfo.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to restore this task."),
        @ApiResponse(code = 404, message = "Not Found — task does not exist."),
        @ApiResponse(code = 402, message = UNDO_REMOVE_402)
    })
    public Response undoRemoveTaskById(
        @ApiParam(
            value = "Project ID. " + PERSONAL_TASKS,
            required = true,
            example = "my_project"
        )
        @PathParam("projectId") String projectId,

        @ApiParam(value = "Task ID.", required = true, example = "42")
        @PathParam("taskId") int taskId,

        @ApiParam(
            value = RETURN_TASK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    // -------- Approval --------

    @POST
    @Path("/approve/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Set or transition a task's approval state.",
        notes = APPROVE_NOTES + "\n\n" + APPROVE_EXAMPLES,
        response = Approval.class
    )
    public Response approveTaskById(
        @ApiParam(value = "Project ID.", required = true,
            example = "my_project")
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Task ID.", required = true, example = "42")
        @PathParam("taskId") int taskId,
        @ApiParam(
            value = "(Optional) Companion comment to post on the task "
                  + "after the approval transition. Omit (or send an "
                  + "empty body) for no comment text; `approve` / "
                  + "`reject` / `change` still log a header-only comment.",
            required = false
        )
        ApproveTaskBody body,
        @ApiParam(
            value = APPROVE_STATE,
            required = true,
            example = "approve",
            allowableValues = "request, approve, reject, change"
        )
        @QueryParam("state") String state,
        @ApiParam(
            value = APPROVE_CATEGORY,
            example = "legal"
        )
        @QueryParam("category") String category,
        @ApiParam(
            value = RETURN_APPROVAL,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @POST
    @Path("/approve/{oid}")
    @ApiOperation(
        value = "Set or transition a task's approval state by OID.",
        notes = APPROVE_NOTES + "\n\n" + APPROVE_EXAMPLES,
        response = Approval.class
    )
    public Response approveTaskByOid(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(
            value = "(Optional) Companion comment to post on the task "
                  + "after the approval transition. Omit (or send an "
                  + "empty body) for no comment text; `approve` / "
                  + "`reject` / `change` still log a header-only comment.",
            required = false
        )
        ApproveTaskBody body,
        @ApiParam(
            value = APPROVE_STATE,
            required = true,
            example = "approve",
            allowableValues = "request, approve, reject, change"
        )
        @QueryParam("state") String state,
        @ApiParam(
            value = APPROVE_CATEGORY,
            example = "legal"
        )
        @QueryParam("category") String category,
        @ApiParam(
            value = RETURN_APPROVAL,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @DELETE
    @Path("/revoke-approval/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Cancel a task's approval.",
        notes = REVOKE_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content — approval revoked (or already absent)."),
        @ApiResponse(code = 403, message = REVOKE_403)
    })
    public Response revokeTaskApprovalById(
        @ApiParam(value = "Project ID.", required = true,
            example = "my_project")
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Task ID.", required = true, example = "42")
        @PathParam("taskId") int taskId
    ) { return null; }

    @DELETE
    @Path("/revoke-approval/{oid}")
    @ApiOperation(
        value = "Cancel a task's approval by OID.",
        notes = REVOKE_NOTES + "\n\n"
              + "See also the by-ID form "
              + "`DELETE /task/revoke-approval/id/{projectId}/{taskId}`."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content — approval revoked (or already absent)."),
        @ApiResponse(code = 403, message = REVOKE_403)
    })
    public Response revokeTaskApprovalByOid(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    // -------- Timelog --------

    @POST
    @Path("/add-timelog/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Add a time log to a task by ID.",
        notes = TIMELOG_ADD_NOTES,
        response = Timelog.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — task's full timelogs array.",
            response = Timelog.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = TIMELOG_ADD_400,
            response = ErrorResponse.class),
        @ApiResponse(code = 403, message = TIMELOG_403,
            response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found — task does not exist.",
            response = ErrorResponse.class),
        @ApiResponse(code = 409, message = "Conflict — a log with the same "
              + "`(user, start, end)` already exists; use `PUT /task/update-timelog/...` "
              + "to amend it.",
            response = ErrorResponse.class)
    })
    public Response addTaskTimelogById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Task ID.", required = true, example = "42")
        @PathParam("taskId") int taskId,
        @ApiParam(value = "Time log to add.", required = true)
        AddTimelogBody data
    ) { return null; }

    @POST
    @Path("/add-timelog/{oid}")
    @ApiOperation(
        value = "Add a time log to a task by OID.",
        notes = TIMELOG_ADD_NOTES + "\n\n"
              + "See also the by-ID form "
              + "`POST /task/add-timelog/id/{projectId}/{taskId}`.",
        response = Timelog.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — task's full timelogs array.",
            response = Timelog.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = TIMELOG_ADD_400,
            response = ErrorResponse.class),
        @ApiResponse(code = 403, message = TIMELOG_403,
            response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found — task does not exist.",
            response = ErrorResponse.class),
        @ApiResponse(code = 409, message = "Conflict — a log with the same "
              + "`(user, start, end)` already exists; use `PUT /task/update-timelog/...` "
              + "to amend it.",
            response = ErrorResponse.class)
    })
    public Response addTaskTimelogByOid(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Time log to add.", required = true)
        AddTimelogBody data
    ) { return null; }

    @PUT
    @Path("/update-timelog/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Update an existing time log on a task by ID.",
        notes = TIMELOG_UPDATE_NOTES,
        response = Timelog.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — task's full timelogs array.",
            response = Timelog.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = TIMELOG_UPDATE_400),
        @ApiResponse(code = 403, message = TIMELOG_403),
        @ApiResponse(code = 404, message = "Not Found — no log matches the "
              + "identifying triple, or the task does not exist.")
    })
    public Response updateTaskTimelogById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Task ID.", required = true, example = "42")
        @PathParam("taskId") int taskId,
        @ApiParam(
            value = "Start timestamp of the existing log (identifies the log "
                  + "together with `end` and `user`). ISO 8601, UTC.",
            example = "2026-04-26T09:00:00Z",
            required = true
        )
        @QueryParam("start") String start,
        @ApiParam(
            value = "End timestamp of the existing log. ISO 8601, UTC.",
            example = "2026-04-26T10:30:00Z",
            required = true
        )
        @QueryParam("end") String end,
        @ApiParam(
            value = TIMELOG_USER,
            example = "john.doe@example.com"
        )
        @QueryParam("user") String user,
        @ApiParam(value = "Fields to change.", required = true)
        UpdateTimelogBody data
    ) { return null; }

    @PUT
    @Path("/update-timelog/{oid}")
    @ApiOperation(
        value = "Update an existing time log on a task by OID.",
        notes = TIMELOG_UPDATE_NOTES + "\n\n"
              + "See also the by-ID form "
              + "`PUT /task/update-timelog/id/{projectId}/{taskId}`.",
        response = Timelog.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — task's full timelogs array.",
            response = Timelog.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = TIMELOG_UPDATE_400),
        @ApiResponse(code = 403, message = TIMELOG_403),
        @ApiResponse(code = 404, message = "Not Found — no log matches the "
              + "identifying triple, or the task does not exist.")
    })
    public Response updateTaskTimelogByOid(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(
            value = "Start timestamp of the existing log (identifies the log "
                  + "together with `end` and `user`). ISO 8601, UTC.",
            example = "2026-04-26T09:00:00Z",
            required = true
        )
        @QueryParam("start") String start,
        @ApiParam(
            value = "End timestamp of the existing log. ISO 8601, UTC.",
            example = "2026-04-26T10:30:00Z",
            required = true
        )
        @QueryParam("end") String end,
        @ApiParam(
            value = TIMELOG_USER,
            example = "john.doe@example.com"
        )
        @QueryParam("user") String user,
        @ApiParam(value = "Fields to change.", required = true)
        UpdateTimelogBody data
    ) { return null; }

    @DELETE
    @Path("/remove-timelog/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Remove a time log from a task by ID.",
        notes = TIMELOG_REMOVE_NOTES,
        response = Timelog.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — task's remaining timelogs "
              + "array (empty when none remain).",
            response = Timelog.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = TIMELOG_REMOVE_400),
        @ApiResponse(code = 204, message = "No Content — the task doesn't "
              + "exist, or has been deleted; nothing is changed."),
        @ApiResponse(code = 403, message = TIMELOG_403),
        @ApiResponse(code = 404, message = "Not Found — no log matches the "
              + "triple.")
    })
    public Response removeTaskTimelogById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Task ID.", required = true, example = "42")
        @PathParam("taskId") int taskId,
        @ApiParam(
            value = "Start timestamp of the log to remove. ISO 8601, UTC.",
            example = "2026-04-26T09:00:00Z",
            required = true
        )
        @QueryParam("start") String start,
        @ApiParam(
            value = "End timestamp of the log to remove. ISO 8601, UTC.",
            example = "2026-04-26T10:30:00Z",
            required = true
        )
        @QueryParam("end") String end,
        @ApiParam(
            value = TIMELOG_USER,
            example = "john.doe@example.com"
        )
        @QueryParam("user") String user
    ) { return null; }

    @DELETE
    @Path("/remove-timelog/{oid}")
    @ApiOperation(
        value = "Remove a time log from a task by OID.",
        notes = TIMELOG_REMOVE_NOTES + "\n\n"
              + "See also the by-ID form "
              + "`DELETE /task/remove-timelog/id/{projectId}/{taskId}`.",
        response = Timelog.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — task's remaining timelogs "
              + "array (empty when none remain).",
            response = Timelog.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = TIMELOG_REMOVE_400),
        @ApiResponse(code = 204, message = "No Content — the task doesn't "
              + "exist, or has been deleted; nothing is changed."),
        @ApiResponse(code = 403, message = TIMELOG_403),
        @ApiResponse(code = 404, message = "Not Found — no log matches the "
              + "triple.")
    })
    public Response removeTaskTimelogByOid(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(
            value = "Start timestamp of the log to remove. ISO 8601, UTC.",
            example = "2026-04-26T09:00:00Z",
            required = true
        )
        @QueryParam("start") String start,
        @ApiParam(
            value = "End timestamp of the log to remove. ISO 8601, UTC.",
            example = "2026-04-26T10:30:00Z",
            required = true
        )
        @QueryParam("end") String end,
        @ApiParam(
            value = TIMELOG_USER,
            example = "john.doe@example.com"
        )
        @QueryParam("user") String user
    ) { return null; }

    // -------- Bulk (#24486) --------

    @POST
    @Path("/bulk-add/id/{projectId}")
    @ApiOperation(
        value = "Bulk-add N root tasks to a project by ID.",
        notes = "Creates up to **300 root tasks** in one transaction.\n\n"
              + "Example: `POST /task/bulk-add/id/my_project?return=compact`\n\n"
              + BULK_ADD_NOTES,
        response = Task.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — array of created tasks "
              + "(one per submitted item, root only for nested subtrees).",
            response = Task.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request — body not a JSON "
              + "array, empty, over the 300-item cap, or any per-item "
              + "validation failure (whole batch rolled back).",
            response = ErrorResponse.class),
        @ApiResponse(code = 402, message = BULK_ADD_402,
            response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks "
              + "permission to add tasks to the project.",
            response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.",
            response = ErrorResponse.class),
        @ApiResponse(code = 413, message = "Payload Too Large — body "
              + "exceeds the API packet size limit.",
            response = ErrorResponse.class),
        @ApiResponse(code = 429, message = "Too Many Requests — the caller's "
              + "per-minute / per-hour API quota is already used up "
              + "(see [Rate Limits](#rate-limits)).",
            response = ErrorResponse.class)
    })
    public Response bulkAddTaskById(
        @ApiParam(value = "Project ID. " + PERSONAL_TASKS, required = true,
            example = "my_project")
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Tasks to be created.", required = true)
        java.util.List<CreateTaskBody> data,
        @ApiParam(
            value = RETURN_BULK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode,
        @ApiParam(
            value = DRY_RUN_PARAM,
            example = "true",
            allowableValues = "true, false"
        )
        @QueryParam("dry-run") String dryRun
    ) { return null; }

    @POST
    @Path("/bulk-add/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Bulk-add N tasks relative to a task by ID.",
        notes = "Creates up to **300 tasks** anchored at the task identified "
              + "by `projectId` + `taskId`. The optional `?position=` query "
              + "parameter mirrors single-task `POST /task/...` semantics:\n"
              + "- `parent` (default): children of the anchor task.\n"
              + "- `before`: siblings immediately before the anchor.\n"
              + "- `after`: siblings immediately after the anchor.\n\n"
              + "Example: `POST /task/bulk-add/id/my_project/42?position=after&return=compact`\n\n"
              + "Request body:\n\n"
              + "```json\n"
              + "[\n"
              + "  { \"name\": \"Subtask 1\" },\n"
              + "  { \"name\": \"Subtask 2\" },\n"
              + "  { \"name\": \"Subtask 3\" }\n"
              + "]\n"
              + "```\n\n"
              + "Response (compact mode):\n\n"
              + "```json\n"
              + "[\n"
              + "  { \"oid\": \"abc1\", \"id\": 100 },\n"
              + "  { \"oid\": \"abc2\", \"id\": 101 },\n"
              + "  { \"oid\": \"abc3\", \"id\": 102 }\n"
              + "]\n"
              + "```\n\n"
              + "All three become siblings of task `42`, in submitted order, "
              + "immediately after task `42`.\n\n"
              + BULK_ADD_NOTES,
        response = Task.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — array of created tasks "
              + "(one per submitted item, root only for nested subtrees).",
            response = Task.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request — body not a JSON "
              + "array, empty, over the 300-item cap, invalid `?position=`, "
              + "or any per-item validation failure (whole batch rolled back).",
            response = ErrorResponse.class),
        @ApiResponse(code = 402, message = BULK_ADD_402,
            response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks "
              + "permission to add tasks here.",
            response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found — project or anchor "
              + "task does not exist.",
            response = ErrorResponse.class),
        @ApiResponse(code = 413, message = "Payload Too Large — body "
              + "exceeds the API packet size limit.",
            response = ErrorResponse.class),
        @ApiResponse(code = 429, message = "Too Many Requests — the caller's "
              + "per-minute / per-hour API quota is already used up "
              + "(see [Rate Limits](#rate-limits)).",
            response = ErrorResponse.class)
    })
    public Response bulkAddTaskAtTaskById(
        @ApiParam(value = "Project ID. " + PERSONAL_TASKS, required = true,
            example = "my_project")
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Anchor task ID.", required = true, example = "42")
        @PathParam("taskId") int taskId,
        @ApiParam(value = "Tasks to be created.", required = true)
        java.util.List<CreateTaskBody> data,
        @ApiParam(
            value = BULK_ADD_POSITION,
            example = "after",
            allowableValues = "parent, before, after"
        )
        @QueryParam("position") String position,
        @ApiParam(
            value = RETURN_BULK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode,
        @ApiParam(
            value = DRY_RUN_PARAM,
            example = "true",
            allowableValues = "true, false"
        )
        @QueryParam("dry-run") String dryRun
    ) { return null; }

    @POST
    @Path("/bulk-add/{oid}")
    @ApiOperation(
        value = "Bulk-add N tasks by OID (project or task anchor).",
        notes = "Creates up to **300 tasks** anchored at the entity "
              + "identified by `oid` — a project OID creates root tasks, "
              + "a task OID creates subtasks (or siblings, depending on "
              + "`?position=`).\n\n"
              + "Examples: `POST /task/bulk-add/0Mg3VQ8kWeiVbLH1PrjzUc89?return=compact` "
              + "adds root tasks to that project; "
              + "`POST /task/bulk-add/iuRRiKoyrxdBFhFTTo?position=after&return=compact` "
              + "adds them right after that task, in submitted order.\n\n"
              + BULK_ADD_NOTES,
        response = Task.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — array of created tasks "
              + "(one per submitted item, root only for nested subtrees).",
            response = Task.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request — body not a JSON "
              + "array, empty, over the 300-item cap, invalid `?position=` "
              + "(or `before` / `after` with a project OID), or any per-item "
              + "validation failure (whole batch rolled back).",
            response = ErrorResponse.class),
        @ApiResponse(code = 402, message = BULK_ADD_402,
            response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks "
              + "permission to add tasks here.",
            response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found — anchor does not exist.",
            response = ErrorResponse.class),
        @ApiResponse(code = 413, message = "Payload Too Large — body "
              + "exceeds the API packet size limit.",
            response = ErrorResponse.class),
        @ApiResponse(code = 429, message = "Too Many Requests — the caller's "
              + "per-minute / per-hour API quota is already used up "
              + "(see [Rate Limits](#rate-limits)).",
            response = ErrorResponse.class)
    })
    public Response bulkAddTaskByOid(
        @ApiParam(
            value = "OID of the project (root tasks) or anchor task "
                + "(subtasks / siblings, per `?position=`). " + PERSONAL_TASKS,
            required = true
        )
        @PathParam("oid") String oid,
        @ApiParam(value = "Tasks to be created.", required = true)
        java.util.List<CreateTaskBody> data,
        @ApiParam(
            value = BULK_ADD_POSITION + " With a project OID, only `parent` "
                + "applies; `before` / `after` are rejected with `400`.",
            example = "after",
            allowableValues = "parent, before, after"
        )
        @QueryParam("position") String position,
        @ApiParam(
            value = RETURN_BULK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode,
        @ApiParam(
            value = DRY_RUN_PARAM,
            example = "true",
            allowableValues = "true, false"
        )
        @QueryParam("dry-run") String dryRun
    ) { return null; }

    @PUT
    @Path("/bulk-update/id/{projectId}")
    @ApiOperation(
        value = "Bulk-update N tasks in a project by ID.",
        notes = "Example: `PUT /task/bulk-update/id/my_project?return=compact`\n\n"
              + BULK_UPDATE_NOTES,
        response = TaskWithParentInfo.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — array of updated tasks "
              + "(slots for not-found items are `null`).",
            response = TaskWithParentInfo.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request — body not a JSON "
              + "array, empty, over the 300-item cap, item shape error "
              + "(both/neither `oid` and `id`, or a ref that is neither a "
              + "string nor an integer), or any per-item "
              + "validation failure (whole batch rolled back).",
            response = ErrorResponse.class),
        @ApiResponse(code = 402, message = BULK_402,
            response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks "
              + "permission on the project or any task in the batch "
              + "(whole batch rolled back).",
            response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found — project does not "
              + "exist. (Item-level not-found is silent skip.)",
            response = ErrorResponse.class),
        @ApiResponse(code = 409, message = "Conflict — project settings "
              + "block completing an item (an open predecessor, or a "
              + "pending approval) (whole batch rolled back).",
            response = ErrorResponse.class),
        @ApiResponse(code = 413, message = "Payload Too Large — body "
              + "exceeds the API packet size limit.",
            response = ErrorResponse.class),
        @ApiResponse(code = 429, message = "Too Many Requests — the caller's "
              + "per-minute / per-hour API quota is already used up "
              + "(see [Rate Limits](#rate-limits)).",
            response = ErrorResponse.class)
    })
    public Response bulkUpdateTaskById(
        @ApiParam(value = "Project ID. " + PERSONAL_TASKS, required = true,
            example = "my_project")
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Tasks to be updated.", required = true)
        java.util.List<BulkUpdateTaskItem> data,
        @ApiParam(
            value = RETURN_BULK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode,
        @ApiParam(
            value = DRY_RUN_PARAM,
            example = "true",
            allowableValues = "true, false"
        )
        @QueryParam("dry-run") String dryRun
    ) { return null; }

    @PUT
    @Path("/bulk-update/{projectOid}")
    @ApiOperation(
        value = "Bulk-update N tasks in a project by OID.",
        notes = "Example: `PUT /task/bulk-update/0Mg3VQ8kWeiVbLH1PrjzUc89?return=compact`\n\n"
              + BULK_UPDATE_NOTES,
        response = TaskWithParentInfo.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — array of updated tasks "
              + "(slots for not-found items are `null`).",
            response = TaskWithParentInfo.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request — body not a JSON "
              + "array, empty, over the 300-item cap, item shape error "
              + "(both/neither `oid` and `id`, or a ref that is neither a "
              + "string nor an integer), or any per-item "
              + "validation failure (whole batch rolled back).",
            response = ErrorResponse.class),
        @ApiResponse(code = 402, message = BULK_402,
            response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks "
              + "permission on the project or any task in the batch "
              + "(whole batch rolled back).",
            response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found — project does not "
              + "exist. (Item-level not-found is silent skip.)",
            response = ErrorResponse.class),
        @ApiResponse(code = 409, message = "Conflict — project settings "
              + "block completing an item (an open predecessor, or a "
              + "pending approval) (whole batch rolled back).",
            response = ErrorResponse.class),
        @ApiResponse(code = 413, message = "Payload Too Large — body "
              + "exceeds the API packet size limit.",
            response = ErrorResponse.class),
        @ApiResponse(code = 429, message = "Too Many Requests — the caller's "
              + "per-minute / per-hour API quota is already used up "
              + "(see [Rate Limits](#rate-limits)).",
            response = ErrorResponse.class)
    })
    public Response bulkUpdateTaskByOid(
        @ApiParam(value = "Project OID. " + PERSONAL_TASKS, required = true)
        @PathParam("projectOid") String projectOid,
        @ApiParam(value = "Tasks to be updated.", required = true)
        java.util.List<BulkUpdateTaskItem> data,
        @ApiParam(
            value = RETURN_BULK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode,
        @ApiParam(
            value = DRY_RUN_PARAM,
            example = "true",
            allowableValues = "true, false"
        )
        @QueryParam("dry-run") String dryRun
    ) { return null; }

    @DELETE
    @Path("/bulk-remove/id/{projectId}")
    @ApiOperation(
        value = "Bulk-remove N tasks from a project by ID.",
        notes = "Example: `DELETE /task/bulk-remove/id/my_project`\n\n"
              + BULK_REMOVE_NOTES,
        response = Object.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — array of `{oid}` "
              + "elements (slots for not-found items are `null`).",
            response = Object.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request — body not a JSON "
              + "array, empty, over the 300-item cap, or any item is "
              + "neither a string nor an integer (whole batch rolled back).",
            response = ErrorResponse.class),
        @ApiResponse(code = 402, message = BULK_402,
            response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks "
              + "permission on the project or any task in the batch "
              + "(whole batch rolled back).",
            response = ErrorResponse.class),
        @ApiResponse(code = 204, message = "No Content — the project does "
              + "not exist; nothing is removed. (Item-level not-found is "
              + "silent skip.)"),
        @ApiResponse(code = 413, message = "Payload Too Large — body "
              + "exceeds the API packet size limit.",
            response = ErrorResponse.class),
        @ApiResponse(code = 429, message = "Too Many Requests — the caller's "
              + "per-minute / per-hour API quota is already used up "
              + "(see [Rate Limits](#rate-limits)).",
            response = ErrorResponse.class)
    })
    public Response bulkRemoveTaskById(
        @ApiParam(value = "Project ID. " + PERSONAL_TASKS, required = true,
            example = "my_project")
        @PathParam("projectId") String projectId,
        @ApiParam(
            value = BULK_REFS,
            required = true
        )
        java.util.List<Object> data,
        @ApiParam(
            value = DRY_RUN_PARAM,
            example = "true",
            allowableValues = "true, false"
        )
        @QueryParam("dry-run") String dryRun
    ) { return null; }

    @DELETE
    @Path("/bulk-remove/{projectOid}")
    @ApiOperation(
        value = "Bulk-remove N tasks from a project by OID.",
        notes = "Example: `DELETE /task/bulk-remove/0Mg3VQ8kWeiVbLH1PrjzUc89`\n\n"
              + BULK_REMOVE_NOTES,
        response = Object.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — array of `{oid}` "
              + "elements (slots for not-found items are `null`).",
            response = Object.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request — body not a JSON "
              + "array, empty, over the 300-item cap, or any item is "
              + "neither a string nor an integer (whole batch rolled back).",
            response = ErrorResponse.class),
        @ApiResponse(code = 402, message = BULK_402,
            response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks "
              + "permission on the project or any task in the batch "
              + "(whole batch rolled back).",
            response = ErrorResponse.class),
        @ApiResponse(code = 204, message = "No Content — the project does "
              + "not exist; nothing is removed. (Item-level not-found is "
              + "silent skip.)"),
        @ApiResponse(code = 413, message = "Payload Too Large — body "
              + "exceeds the API packet size limit.",
            response = ErrorResponse.class),
        @ApiResponse(code = 429, message = "Too Many Requests — the caller's "
              + "per-minute / per-hour API quota is already used up "
              + "(see [Rate Limits](#rate-limits)).",
            response = ErrorResponse.class)
    })
    public Response bulkRemoveTaskByOid(
        @ApiParam(value = "Project OID. " + PERSONAL_TASKS, required = true)
        @PathParam("projectOid") String projectOid,
        @ApiParam(
            value = BULK_REFS,
            required = true
        )
        java.util.List<Object> data,
        @ApiParam(
            value = DRY_RUN_PARAM,
            example = "true",
            allowableValues = "true, false"
        )
        @QueryParam("dry-run") String dryRun
    ) { return null; }

    // -------- Bulk move (#24554) --------

    @PUT
    @Path("/bulk-move/id/{projectId}")
    @ApiOperation(
        value = "Bulk-move N tasks within a project by ID.",
        notes = "Example: `PUT /task/bulk-move/id/my_project?task=99&return=compact`\n\n"
              + BULK_MOVE_NOTES,
        response = TaskWithParentInfo.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — array of moved tasks "
              + "(slots for not-found items are `null`).",
            response = TaskWithParentInfo.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = BULK_MOVE_400,
            response = ErrorResponse.class),
        @ApiResponse(code = 402, message = BULK_402,
            response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks "
              + "permission on the project or any task in the batch "
              + "(whole batch rolled back).",
            response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found — project does not "
              + "exist. (Item-level not-found is silent skip.)",
            response = ErrorResponse.class),
        @ApiResponse(code = 413, message = "Payload Too Large — body "
              + "exceeds the API packet size limit.",
            response = ErrorResponse.class),
        @ApiResponse(code = 429, message = "Too Many Requests — the caller's "
              + "per-minute / per-hour API quota is already used up "
              + "(see [Rate Limits](#rate-limits)).",
            response = ErrorResponse.class)
    })
    public Response bulkMoveTaskById(
        @ApiParam(value = "Project ID. " + PERSONAL_TASKS, required = true,
            example = "my_project")
        @PathParam("projectId") String projectId,
        @ApiParam(
            value = BULK_REFS,
            required = true
        )
        java.util.List<Object> data,
        @ApiParam(
            value = "Reference task ID (the destination anchor for the "
                  + "whole batch), or `root` to move items to the project "
                  + "root.",
            required = true,
            example = "99"
        )
        @QueryParam("task") String task,
        @ApiParam(
            value = "(Optional) Placement of moved items relative to the "
                  + "reference: `parent` (default), `before`, or `after`. "
                  + "`before` / `after` require `?task=` to refer to a "
                  + "task (not `root`).",
            example = "after",
            allowableValues = "parent, before, after"
        )
        @QueryParam("position") String position,
        @ApiParam(
            value = RETURN_BULK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode,
        @ApiParam(
            value = DRY_RUN_PARAM,
            example = "true",
            allowableValues = "true, false"
        )
        @QueryParam("dry-run") String dryRun
    ) { return null; }

    @PUT
    @Path("/bulk-move/{projectOid}")
    @ApiOperation(
        value = "Bulk-move N tasks within a project by OID.",
        notes = "Example: `PUT /task/bulk-move/0Mg3VQ8kWeiVbLH1PrjzUc89?task=iuRRiKoyrxdBFhFTTo&return=compact`\n\n"
              + BULK_MOVE_NOTES,
        response = TaskWithParentInfo.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — array of moved tasks "
              + "(slots for not-found items are `null`).",
            response = TaskWithParentInfo.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = BULK_MOVE_400,
            response = ErrorResponse.class),
        @ApiResponse(code = 402, message = BULK_402,
            response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks "
              + "permission on the project or any task in the batch "
              + "(whole batch rolled back).",
            response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found — project does not "
              + "exist. (Item-level not-found is silent skip.)",
            response = ErrorResponse.class),
        @ApiResponse(code = 413, message = "Payload Too Large — body "
              + "exceeds the API packet size limit.",
            response = ErrorResponse.class),
        @ApiResponse(code = 429, message = "Too Many Requests — the caller's "
              + "per-minute / per-hour API quota is already used up "
              + "(see [Rate Limits](#rate-limits)).",
            response = ErrorResponse.class)
    })
    public Response bulkMoveTaskByOid(
        @ApiParam(value = "Project OID. " + PERSONAL_TASKS, required = true)
        @PathParam("projectOid") String projectOid,
        @ApiParam(
            value = BULK_REFS,
            required = true
        )
        java.util.List<Object> data,
        @ApiParam(
            value = "Reference task OID (the destination anchor for the "
                  + "whole batch), or `root` to move items to the project "
                  + "root.",
            required = true,
            example = "iuRRiKoyrxdBFhFTTo"
        )
        @QueryParam("task") String task,
        @ApiParam(
            value = "(Optional) Placement of moved items relative to the "
                  + "reference: `parent` (default), `before`, or `after`. "
                  + "`before` / `after` require `?task=` to refer to a "
                  + "task (not `root`).",
            example = "after",
            allowableValues = "parent, before, after"
        )
        @QueryParam("position") String position,
        @ApiParam(
            value = RETURN_BULK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode,
        @ApiParam(
            value = DRY_RUN_PARAM,
            example = "true",
            allowableValues = "true, false"
        )
        @QueryParam("dry-run") String dryRun
    ) { return null; }

    // -------- Bulk transfer (#24555) --------

    @PUT
    @Path("/bulk-transfer/id/{sourceProjectId}")
    @ApiOperation(
        value = "Bulk-transfer N tasks across projects by ID.",
        notes = "Example: `PUT /task/bulk-transfer/id/my_project?project=archive_2026&task=root&return=compact`\n\n"
              + BULK_TRANSFER_NOTES,
        response = TaskWithParentInfo.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — array of transferred "
              + "tasks (slots for not-found items are `null`).",
            response = TaskWithParentInfo.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = BULK_TRANSFER_400,
            response = ErrorResponse.class),
        @ApiResponse(code = 402, message = BULK_TRANSFER_402,
            response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks "
              + "permission on the source or target project, or on any "
              + "individual task in the batch. Whole batch rolled back.",
            response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found — source or target "
              + "project does not exist. (Item-level not-found is silent "
              + "skip.)",
            response = ErrorResponse.class),
        @ApiResponse(code = 413, message = "Payload Too Large — body "
              + "exceeds the API packet size limit.",
            response = ErrorResponse.class),
        @ApiResponse(code = 429, message = "Too Many Requests — the caller's "
              + "per-minute / per-hour API quota is already used up "
              + "(see [Rate Limits](#rate-limits)).",
            response = ErrorResponse.class)
    })
    public Response bulkTransferTaskById(
        @ApiParam(value = "Source project ID. " + PERSONAL_TASKS,
            required = true, example = "my_project")
        @PathParam("sourceProjectId") String sourceProjectId,
        @ApiParam(
            value = BULK_REFS,
            required = true
        )
        java.util.List<Object> data,
        @ApiParam(
            value = "Target project ID. " + PERSONAL_TASKS,
            required = true,
            example = "archive_2026"
        )
        @QueryParam("project") String project,
        @ApiParam(
            value = "(Optional) Reference task ID in the target project "
                  + "(destination anchor for the whole batch), or `root` "
                  + "to transfer items to the target project's root.",
            example = "500"
        )
        @QueryParam("task") String task,
        @ApiParam(
            value = "(Optional) Placement of transferred items relative "
                  + "to the reference: `parent` (default), `before`, or "
                  + "`after`. `before` / `after` require `?task=` to "
                  + "refer to a task (not `root`).",
            example = "parent",
            allowableValues = "parent, before, after"
        )
        @QueryParam("position") String position,
        @ApiParam(value = TRANSFER_INVITE, example = "true")
        @QueryParam("invite") String invite,
        @ApiParam(value = TRANSFER_TAG, example = "true")
        @QueryParam("tag") String tag,
        @ApiParam(value = TRANSFER_STATUS, example = "true")
        @QueryParam("status") String status,
        @ApiParam(value = TRANSFER_CUSTOM_FIELD, example = "true")
        @QueryParam("custom-field") String customField,
        @ApiParam(
            value = RETURN_BULK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode,
        @ApiParam(
            value = DRY_RUN_PARAM,
            example = "true",
            allowableValues = "true, false"
        )
        @QueryParam("dry-run") String dryRun
    ) { return null; }

    @PUT
    @Path("/bulk-transfer/{sourceProjectOid}")
    @ApiOperation(
        value = "Bulk-transfer N tasks across projects by OID.",
        notes = "Example: `PUT /task/bulk-transfer/0Mg3VQ8kWeiVbLH1PrjzUc89?project=0Mg3VQ8kWeiVbLH1ArcvUc12&task=root&return=compact`\n\n"
              + BULK_TRANSFER_NOTES,
        response = TaskWithParentInfo.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — array of transferred "
              + "tasks (slots for not-found items are `null`).",
            response = TaskWithParentInfo.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = BULK_TRANSFER_400,
            response = ErrorResponse.class),
        @ApiResponse(code = 402, message = BULK_TRANSFER_402,
            response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks "
              + "permission on the source or target project, or on any "
              + "individual task in the batch. Whole batch rolled back.",
            response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found — source or target "
              + "project does not exist. (Item-level not-found is silent "
              + "skip.)",
            response = ErrorResponse.class),
        @ApiResponse(code = 413, message = "Payload Too Large — body "
              + "exceeds the API packet size limit.",
            response = ErrorResponse.class),
        @ApiResponse(code = 429, message = "Too Many Requests — the caller's "
              + "per-minute / per-hour API quota is already used up "
              + "(see [Rate Limits](#rate-limits)).",
            response = ErrorResponse.class)
    })
    public Response bulkTransferTaskByOid(
        @ApiParam(value = "Source project OID. " + PERSONAL_TASKS,
            required = true)
        @PathParam("sourceProjectOid") String sourceProjectOid,
        @ApiParam(
            value = BULK_REFS,
            required = true
        )
        java.util.List<Object> data,
        @ApiParam(
            value = "Target project OID. " + PERSONAL_TASKS,
            required = true,
            example = "0Mg3VQ8kWeiVbLH1PrjzUc89"
        )
        @QueryParam("project") String project,
        @ApiParam(
            value = "(Optional) Reference task OID in the target project "
                  + "(destination anchor for the whole batch), or `root` "
                  + "to transfer items to the target project's root.",
            example = "iuRRiKoyrxdBFhFTTo"
        )
        @QueryParam("task") String task,
        @ApiParam(
            value = "(Optional) Placement of transferred items relative "
                  + "to the reference: `parent` (default), `before`, or "
                  + "`after`. `before` / `after` require `?task=` to "
                  + "refer to a task (not `root`).",
            example = "parent",
            allowableValues = "parent, before, after"
        )
        @QueryParam("position") String position,
        @ApiParam(value = TRANSFER_INVITE, example = "true")
        @QueryParam("invite") String invite,
        @ApiParam(value = TRANSFER_TAG, example = "true")
        @QueryParam("tag") String tag,
        @ApiParam(value = TRANSFER_STATUS, example = "true")
        @QueryParam("status") String status,
        @ApiParam(value = TRANSFER_CUSTOM_FIELD, example = "true")
        @QueryParam("custom-field") String customField,
        @ApiParam(
            value = RETURN_BULK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode,
        @ApiParam(
            value = DRY_RUN_PARAM,
            example = "true",
            allowableValues = "true, false"
        )
        @QueryParam("dry-run") String dryRun
    ) { return null; }

    // -------- Bulk approve --------

    @POST
    @Path("/bulk-approve/id/{projectId}")
    @ApiOperation(
        value = "Bulk-approve N tasks in a project by ID.",
        notes = BULK_APPROVE_NOTES + "\n\n"
              + "Example:\n"
              + "```\n"
              + "POST /task/bulk-approve/id/my_project?category=legal&state=approve\n"
              + "Body: [42, \"#43\", \"iuRRiKoyrxdBFhFTTo\"]\n"
              + "```",
        response = TaskWithParentInfo.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — array of updated tasks "
              + "(same length as the request body; `null` for skipped "
              + "items whose task wasn't found).",
            response = TaskWithParentInfo.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request — body shape, "
              + "missing or invalid `?state=`, an item that is neither a "
              + "string nor an integer, or a "
              + "per-item validation error (with `items[i]:` prefix).",
            response = ErrorResponse.class),
        @ApiResponse(code = 402, message = BULK_402,
            response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks "
              + "claimer / approver role on at least one item (whole "
              + "batch rolled back).",
            response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found — project or "
              + "approval `?category=` does not exist.",
            response = ErrorResponse.class),
        @ApiResponse(code = 429, message = "Too Many Requests — the caller's "
              + "per-minute / per-hour API quota is already used up "
              + "(see [Rate Limits](#rate-limits)).",
            response = ErrorResponse.class)
    })
    public Response bulkApproveTaskById(
        @ApiParam(value = "Project ID.", required = true,
            example = "my_project")
        @PathParam("projectId") String projectId,
        @ApiParam(
            value = BULK_REFS + " Up to 300 items.",
            required = true
        )
        java.util.List<Object> data,
        @ApiParam(
            value = APPROVE_STATE + " Applied to every task in the batch.",
            required = true,
            example = "approve",
            allowableValues = "request, approve, reject, change"
        )
        @QueryParam("state") String state,
        @ApiParam(
            value = APPROVE_CATEGORY,
            example = "legal"
        )
        @QueryParam("category") String category,
        @ApiParam(
            value = RETURN_BULK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode,
        @ApiParam(
            value = DRY_RUN_PARAM,
            example = "true",
            allowableValues = "true, false"
        )
        @QueryParam("dry-run") String dryRun
    ) { return null; }

    @POST
    @Path("/bulk-approve/{projectOid}")
    @ApiOperation(
        value = "Bulk-approve N tasks in a project by OID.",
        notes = BULK_APPROVE_NOTES + "\n\n"
              + "Example:\n"
              + "```\n"
              + "POST /task/bulk-approve/0Mg3VQ8kWeiVbLH1PrjzUc89?category=legal&state=approve\n"
              + "Body: [42, \"#43\", \"iuRRiKoyrxdBFhFTTo\"]\n"
              + "```",
        response = TaskWithParentInfo.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — array of updated tasks "
              + "(same length as the request body; `null` for skipped "
              + "items whose task wasn't found).",
            response = TaskWithParentInfo.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request — body shape, "
              + "missing or invalid `?state=`, an item that is neither a "
              + "string nor an integer, or a "
              + "per-item validation error (with `items[i]:` prefix).",
            response = ErrorResponse.class),
        @ApiResponse(code = 402, message = BULK_402,
            response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks "
              + "claimer / approver role on at least one item (whole "
              + "batch rolled back).",
            response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found — project or "
              + "approval `?category=` does not exist.",
            response = ErrorResponse.class),
        @ApiResponse(code = 429, message = "Too Many Requests — the caller's "
              + "per-minute / per-hour API quota is already used up "
              + "(see [Rate Limits](#rate-limits)).",
            response = ErrorResponse.class)
    })
    public Response bulkApproveTaskByOid(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("projectOid") String projectOid,
        @ApiParam(
            value = BULK_REFS + " Up to 300 items.",
            required = true
        )
        java.util.List<Object> data,
        @ApiParam(
            value = APPROVE_STATE + " Applied to every task in the batch.",
            required = true,
            example = "approve",
            allowableValues = "request, approve, reject, change"
        )
        @QueryParam("state") String state,
        @ApiParam(
            value = APPROVE_CATEGORY,
            example = "legal"
        )
        @QueryParam("category") String category,
        @ApiParam(
            value = RETURN_BULK,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode,
        @ApiParam(
            value = DRY_RUN_PARAM,
            example = "true",
            allowableValues = "true, false"
        )
        @QueryParam("dry-run") String dryRun
    ) { return null; }

    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete a task and all of its subtasks by its OID.",
        notes = DELETE_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to delete this task.")
    })
    public Response deleteTaskByOid(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("oid") String oid
    ) {
        return null;
    }

    @DELETE
    @Path("/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Delete a task and all of its subtasks by its ID.",
        notes = DELETE_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to delete this task.")
    })
    public Response deleteTaskById(
        @ApiParam(
            value = "Project ID. Specify \"-\" to remove from personal tasks in My Tasks.",
            required = true,
            example = "my_project"
        )
        @PathParam("projectId") String projectId,

        @ApiParam(value = "Task ID.", required = true, example = "42")
        @PathParam("taskId") int taskId
    ) {
        return null;
    }

    @GET
    @Path("/search/{projectOid}")
    @ApiOperation(
        value = "Search tasks in a project by project OID.",
        notes = SEARCH_PRJ_NOTES + "\n\n"
              + "See also the by-ID form `GET /task/search/id/{projectId}`.",
        response = TaskWithParentInfo.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = SEARCH_PRJ_OK,
            response = TaskWithParentInfo.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = SEARCH_PRJ_400),
        @ApiResponse(code = 402, message = SEARCH_PRJ_402),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to search this project."),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response searchTasksByOid(
        @ApiParam(
            value = "Project OID. " + PERSONAL_TASKS,
            required = true
        )
        @PathParam("projectOid") String projectOid,
        @ApiParam(value = SEARCH_TEXT)
        @QueryParam("text") String text,
        @ApiParam(value = SEARCH_NAME, example = "My first task")
        @QueryParam("name") String name,
        @ApiParam(value = SEARCH_DESCRIPTION, example = SEARCH_DESCRIPTION_EXAMPLE)
        @QueryParam("description") String description,
        @ApiParam(value = SEARCH_PRJ_SUBLIST, example = "Top")
        @QueryParam("sublist") String sublist,
        @ApiParam(value = SEARCH_STATUS, example = "active")
        @QueryParam("status") String status,
        @ApiParam(value = SEARCH_SCHEDULED, example = "true")
        @QueryParam("scheduled") boolean scheduled,
        @ApiParam(value = SEARCH_MINE, example = "true")
        @QueryParam("mine") boolean mine,
        @ApiParam(value = SEARCH_ASSIGNEE, example = "alice,!bob")
        @QueryParam("assignee") String assignee,
        @ApiParam(value = SEARCH_ASSIGNOR, example = "alice")
        @QueryParam("assignor") String assignor,
        @ApiParam(value = SEARCH_FOLLOWER, example = "alice|bob")
        @QueryParam("follower") String follower,
        @ApiParam(value = SEARCH_TAG, example = "Legal,!Draft")
        @QueryParam("tag") String tag,
        @ApiParam(value = SEARCH_MODIFIED, example = "7")
        @QueryParam("modified") String modified,
        @ApiParam(value = SEARCH_COMMENTED, example = "7")
        @QueryParam("commented") String commented,
        @ApiParam(value = SOURCE_REF_PARAM, example = "github")
        @QueryParam("source-ref") String sourceRef,
        @ApiParam(value = SEARCH_CREATED, example = "ge:2026-04-01T00:00:00Z")
        @QueryParam("created") String created,
        @ApiParam(value = SEARCH_EDITED, example = "last7d")
        @QueryParam("edited") String edited,
        @ApiParam(value = SEARCH_ARCHIVED, example = "isNotNull")
        @QueryParam("archived") String archived,
        @ApiParam(value = SEARCH_UNARCHIVED, example = "isNotNull")
        @QueryParam("unarchived") String unarchived,
        @ApiParam(value = SEARCH_TOGGLED, example = "today")
        @QueryParam("toggled") String toggled,
        @ApiParam(value = SEARCH_START, example = "ge:2026-04-01")
        @QueryParam("start") String start,
        @ApiParam(value = SEARCH_DUE, example = "today")
        @QueryParam("due") String due,
        @ApiParam(value = SEARCH_PRIORITY, example = "high|urgent")
        @QueryParam("priority") String priority,
        @ApiParam(value = SEARCH_TYPE, example = "section|milestone")
        @QueryParam("type") String type,
        @ApiParam(value = SEARCH_CREATED_BY, example = "alice")
        @QueryParam("created-by") String createdBy,
        @ApiParam(value = SEARCH_RECURRING, example = "true")
        @QueryParam("recurring") String recurring,
        @ApiParam(value = SEARCH_LIMIT, example = "no")
        @QueryParam("limit") String limit,
        @ApiParam(value = SEARCH_PRJ_CURSOR, required = false)
        @QueryParam("cursor") String cursor
    ) { return null; }

    @GET
    @Path("/search/id/{projectId}")
    @ApiOperation(
        value = "Search tasks in a project by project ID.",
        notes = SEARCH_PRJ_NOTES,
        response = TaskWithParentInfo.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = SEARCH_PRJ_OK,
            response = TaskWithParentInfo.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = SEARCH_PRJ_400),
        @ApiResponse(code = 402, message = SEARCH_PRJ_402),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to search this project."),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response searchTasksById(
        @ApiParam(
            value = "ID of the task's project. " + PERSONAL_TASKS,
            required = true,
            example = "my_project"
        )
        @PathParam("projectId") String projectId,
        @ApiParam(value = SEARCH_TEXT)
        @QueryParam("text") String text,
        @ApiParam(value = SEARCH_NAME, example = "My first task")
        @QueryParam("name") String name,
        @ApiParam(value = SEARCH_DESCRIPTION, example = SEARCH_DESCRIPTION_EXAMPLE)
        @QueryParam("description") String description,
        @ApiParam(value = SEARCH_PRJ_SUBLIST, example = "Top")
        @QueryParam("sublist") String sublist,
        @ApiParam(value = SEARCH_STATUS, example = "active")
        @QueryParam("status") String status,
        @ApiParam(value = SEARCH_SCHEDULED, example = "true")
        @QueryParam("scheduled") boolean scheduled,
        @ApiParam(value = SEARCH_MINE, example = "true")
        @QueryParam("mine") boolean mine,
        @ApiParam(value = SEARCH_ASSIGNEE, example = "alice,!bob")
        @QueryParam("assignee") String assignee,
        @ApiParam(value = SEARCH_ASSIGNOR, example = "alice")
        @QueryParam("assignor") String assignor,
        @ApiParam(value = SEARCH_FOLLOWER, example = "alice|bob")
        @QueryParam("follower") String follower,
        @ApiParam(value = SEARCH_TAG, example = "Legal,!Draft")
        @QueryParam("tag") String tag,
        @ApiParam(value = SEARCH_MODIFIED, example = "7")
        @QueryParam("modified") String modified,
        @ApiParam(value = SEARCH_COMMENTED, example = "7")
        @QueryParam("commented") String commented,
        @ApiParam(value = SOURCE_REF_PARAM, example = "github")
        @QueryParam("source-ref") String sourceRef,
        @ApiParam(value = SEARCH_CREATED, example = "ge:2026-04-01T00:00:00Z")
        @QueryParam("created") String created,
        @ApiParam(value = SEARCH_EDITED, example = "last7d")
        @QueryParam("edited") String edited,
        @ApiParam(value = SEARCH_ARCHIVED, example = "isNotNull")
        @QueryParam("archived") String archived,
        @ApiParam(value = SEARCH_UNARCHIVED, example = "isNotNull")
        @QueryParam("unarchived") String unarchived,
        @ApiParam(value = SEARCH_TOGGLED, example = "today")
        @QueryParam("toggled") String toggled,
        @ApiParam(value = SEARCH_START, example = "ge:2026-04-01")
        @QueryParam("start") String start,
        @ApiParam(value = SEARCH_DUE, example = "today")
        @QueryParam("due") String due,
        @ApiParam(value = SEARCH_PRIORITY, example = "high|urgent")
        @QueryParam("priority") String priority,
        @ApiParam(value = SEARCH_TYPE, example = "section|milestone")
        @QueryParam("type") String type,
        @ApiParam(value = SEARCH_CREATED_BY, example = "alice")
        @QueryParam("created-by") String createdBy,
        @ApiParam(value = SEARCH_RECURRING, example = "true")
        @QueryParam("recurring") String recurring,
        @ApiParam(value = SEARCH_LIMIT, example = "no")
        @QueryParam("limit") String limit,
        @ApiParam(value = SEARCH_PRJ_CURSOR, required = false)
        @QueryParam("cursor") String cursor
    ) { return null; }

    @GET
    @Path("/search-organization/{organizationOid}")
    @ApiOperation(
        value = "Search tasks in an organization by organization OID.",
        notes = "Returns tasks that match the specified criteria in the given "
              + "organization, most recently edited first (best match first "
              + "with `text`). " + SEARCH_ORG_SCOPE + "\n\n"
              + "See also the by-ID form "
              + "`GET /task/search-organization/id/{organizationId}`.",
        response = TaskWithProjectParentInfo.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = SEARCH_ORG_OK,
            response = TaskWithProjectParentInfo.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = SEARCH_ORG_400),
        @ApiResponse(code = 402, message = SEARCH_ORG_402),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to search this organization."),
        @ApiResponse(code = 404, message = "Not Found — organization does not exist.")
    })
    public Response searchTasksByOrgOid(
        @ApiParam(value = "Organization OID.", required = true)
        @PathParam("organizationOid") String organizationOid,
        @ApiParam(value = SEARCH_TEXT)
        @QueryParam("text") String text,
        @ApiParam(value = SEARCH_NAME, example = "My first task")
        @QueryParam("name") String name,
        @ApiParam(value = SEARCH_DESCRIPTION, example = SEARCH_DESCRIPTION_EXAMPLE)
        @QueryParam("description") String description,
        @ApiParam(value = SEARCH_STATUS, example = "active")
        @QueryParam("status") String status,
        @ApiParam(value = SEARCH_SCHEDULED, example = "true")
        @QueryParam("scheduled") boolean scheduled,
        @ApiParam(value = SEARCH_MINE, example = "true")
        @QueryParam("mine") boolean mine,
        @ApiParam(value = SEARCH_ASSIGNEE, example = "alice,!bob")
        @QueryParam("assignee") String assignee,
        @ApiParam(value = SEARCH_ASSIGNOR, example = "alice")
        @QueryParam("assignor") String assignor,
        @ApiParam(value = SEARCH_FOLLOWER, example = "alice|bob")
        @QueryParam("follower") String follower,
        @ApiParam(value = SEARCH_TAG, example = "Legal,!Draft")
        @QueryParam("tag") String tag,
        @ApiParam(value = SEARCH_MODIFIED, example = "7")
        @QueryParam("modified") String modified,
        @ApiParam(value = SEARCH_COMMENTED, example = "7")
        @QueryParam("commented") String commented,
        @ApiParam(value = SOURCE_REF_PARAM, example = "github")
        @QueryParam("source-ref") String sourceRef,
        @ApiParam(value = SEARCH_CREATED, example = "ge:2026-04-01T00:00:00Z")
        @QueryParam("created") String created,
        @ApiParam(value = SEARCH_EDITED, example = "last7d")
        @QueryParam("edited") String edited,
        @ApiParam(value = SEARCH_ARCHIVED, example = "isNotNull")
        @QueryParam("archived") String archived,
        @ApiParam(value = SEARCH_UNARCHIVED, example = "isNotNull")
        @QueryParam("unarchived") String unarchived,
        @ApiParam(value = SEARCH_TOGGLED, example = "today")
        @QueryParam("toggled") String toggled,
        @ApiParam(value = SEARCH_START, example = "ge:2026-04-01")
        @QueryParam("start") String start,
        @ApiParam(value = SEARCH_DUE, example = "today")
        @QueryParam("due") String due,
        @ApiParam(value = SEARCH_PRIORITY, example = "high|urgent")
        @QueryParam("priority") String priority,
        @ApiParam(value = SEARCH_TYPE, example = "section|milestone")
        @QueryParam("type") String type,
        @ApiParam(value = SEARCH_CREATED_BY, example = "alice")
        @QueryParam("created-by") String createdBy,
        @ApiParam(value = SEARCH_RECURRING, example = "true")
        @QueryParam("recurring") String recurring,
        @ApiParam(value = SEARCH_LIMIT, example = "no")
        @QueryParam("limit") String limit,
        @ApiParam(value = SEARCH_CURSOR, required = false)
        @QueryParam("cursor") String cursor
    ) { return null; }

    @GET
    @Path("/search-organization/id/{organizationId}")
    @ApiOperation(
        value = "Search tasks in an organization by organization ID.",
        notes = "Returns tasks that match the specified criteria in the given "
              + "organization, most recently edited first (best match first "
              + "with `text`). " + SEARCH_ORG_SCOPE,
        response = TaskWithProjectParentInfo.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = SEARCH_ORG_OK,
            response = TaskWithProjectParentInfo.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = SEARCH_ORG_400),
        @ApiResponse(code = 402, message = SEARCH_ORG_402),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to search this organization."),
        @ApiResponse(code = 404, message = "Not Found — organization does not exist.")
    })
    public Response searchTasksByOrgId(
        @ApiParam(value = "Organization ID.", required = true, example = "my_org")
        @PathParam("organizationId") String organizationId,
        @ApiParam(value = SEARCH_TEXT)
        @QueryParam("text") String text,
        @ApiParam(value = SEARCH_NAME, example = "My first task")
        @QueryParam("name") String name,
        @ApiParam(value = SEARCH_DESCRIPTION, example = SEARCH_DESCRIPTION_EXAMPLE)
        @QueryParam("description") String description,
        @ApiParam(value = SEARCH_STATUS, example = "active")
        @QueryParam("status") String status,
        @ApiParam(value = SEARCH_SCHEDULED, example = "true")
        @QueryParam("scheduled") boolean scheduled,
        @ApiParam(value = SEARCH_MINE, example = "true")
        @QueryParam("mine") boolean mine,
        @ApiParam(value = SEARCH_ASSIGNEE, example = "alice,!bob")
        @QueryParam("assignee") String assignee,
        @ApiParam(value = SEARCH_ASSIGNOR, example = "alice")
        @QueryParam("assignor") String assignor,
        @ApiParam(value = SEARCH_FOLLOWER, example = "alice|bob")
        @QueryParam("follower") String follower,
        @ApiParam(value = SEARCH_TAG, example = "Legal,!Draft")
        @QueryParam("tag") String tag,
        @ApiParam(value = SEARCH_MODIFIED, example = "7")
        @QueryParam("modified") String modified,
        @ApiParam(value = SEARCH_COMMENTED, example = "7")
        @QueryParam("commented") String commented,
        @ApiParam(value = SOURCE_REF_PARAM, example = "github")
        @QueryParam("source-ref") String sourceRef,
        @ApiParam(value = SEARCH_CREATED, example = "ge:2026-04-01T00:00:00Z")
        @QueryParam("created") String created,
        @ApiParam(value = SEARCH_EDITED, example = "last7d")
        @QueryParam("edited") String edited,
        @ApiParam(value = SEARCH_ARCHIVED, example = "isNotNull")
        @QueryParam("archived") String archived,
        @ApiParam(value = SEARCH_UNARCHIVED, example = "isNotNull")
        @QueryParam("unarchived") String unarchived,
        @ApiParam(value = SEARCH_TOGGLED, example = "today")
        @QueryParam("toggled") String toggled,
        @ApiParam(value = SEARCH_START, example = "ge:2026-04-01")
        @QueryParam("start") String start,
        @ApiParam(value = SEARCH_DUE, example = "today")
        @QueryParam("due") String due,
        @ApiParam(value = SEARCH_PRIORITY, example = "high|urgent")
        @QueryParam("priority") String priority,
        @ApiParam(value = SEARCH_TYPE, example = "section|milestone")
        @QueryParam("type") String type,
        @ApiParam(value = SEARCH_CREATED_BY, example = "alice")
        @QueryParam("created-by") String createdBy,
        @ApiParam(value = SEARCH_RECURRING, example = "true")
        @QueryParam("recurring") String recurring,
        @ApiParam(value = SEARCH_LIMIT, example = "no")
        @QueryParam("limit") String limit,
        @ApiParam(value = SEARCH_CURSOR, required = false)
        @QueryParam("cursor") String cursor
    ) { return null; }

    @GET
    @Path("/search-folder/{folderOid}")
    @ApiOperation(
        value = "Search tasks in a folder by folder OID.",
        notes = "Returns tasks that match the specified criteria in the given "
              + "folder, most recently edited first (best match first with "
              + "`text`). " + SEARCH_ORG_SCOPE + "\n\n"
              + "See also the by-ID form `GET /task/search-folder/id/{folderId}`.",
        response = TaskWithProjectParentInfo.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = SEARCH_ORG_OK,
            response = TaskWithProjectParentInfo.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = SEARCH_ORG_400),
        @ApiResponse(code = 402, message = SEARCH_ORG_402),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to search this folder."),
        @ApiResponse(code = 404, message = "Not Found — folder does not exist.")
    })
    public Response searchTasksByFolderOid(
        @ApiParam(value = "Folder OID.", required = true)
        @PathParam("folderOid") String folderOid,
        @ApiParam(value = SEARCH_TEXT)
        @QueryParam("text") String text,
        @ApiParam(value = SEARCH_NAME, example = "My first task")
        @QueryParam("name") String name,
        @ApiParam(value = SEARCH_DESCRIPTION, example = SEARCH_DESCRIPTION_EXAMPLE)
        @QueryParam("description") String description,
        @ApiParam(value = SEARCH_STATUS, example = "active")
        @QueryParam("status") String status,
        @ApiParam(value = SEARCH_SCHEDULED, example = "true")
        @QueryParam("scheduled") boolean scheduled,
        @ApiParam(value = SEARCH_MINE, example = "true")
        @QueryParam("mine") boolean mine,
        @ApiParam(value = SEARCH_ASSIGNEE, example = "alice,!bob")
        @QueryParam("assignee") String assignee,
        @ApiParam(value = SEARCH_ASSIGNOR, example = "alice")
        @QueryParam("assignor") String assignor,
        @ApiParam(value = SEARCH_FOLLOWER, example = "alice|bob")
        @QueryParam("follower") String follower,
        @ApiParam(value = SEARCH_TAG, example = "Legal,!Draft")
        @QueryParam("tag") String tag,
        @ApiParam(value = SEARCH_MODIFIED, example = "7")
        @QueryParam("modified") String modified,
        @ApiParam(value = SEARCH_COMMENTED, example = "7")
        @QueryParam("commented") String commented,
        @ApiParam(value = SOURCE_REF_PARAM, example = "github")
        @QueryParam("source-ref") String sourceRef,
        @ApiParam(value = SEARCH_CREATED, example = "ge:2026-04-01T00:00:00Z")
        @QueryParam("created") String created,
        @ApiParam(value = SEARCH_EDITED, example = "last7d")
        @QueryParam("edited") String edited,
        @ApiParam(value = SEARCH_ARCHIVED, example = "isNotNull")
        @QueryParam("archived") String archived,
        @ApiParam(value = SEARCH_UNARCHIVED, example = "isNotNull")
        @QueryParam("unarchived") String unarchived,
        @ApiParam(value = SEARCH_TOGGLED, example = "today")
        @QueryParam("toggled") String toggled,
        @ApiParam(value = SEARCH_START, example = "ge:2026-04-01")
        @QueryParam("start") String start,
        @ApiParam(value = SEARCH_DUE, example = "today")
        @QueryParam("due") String due,
        @ApiParam(value = SEARCH_PRIORITY, example = "high|urgent")
        @QueryParam("priority") String priority,
        @ApiParam(value = SEARCH_TYPE, example = "section|milestone")
        @QueryParam("type") String type,
        @ApiParam(value = SEARCH_CREATED_BY, example = "alice")
        @QueryParam("created-by") String createdBy,
        @ApiParam(value = SEARCH_RECURRING, example = "true")
        @QueryParam("recurring") String recurring,
        @ApiParam(value = SEARCH_LIMIT, example = "no")
        @QueryParam("limit") String limit,
        @ApiParam(value = SEARCH_CURSOR, required = false)
        @QueryParam("cursor") String cursor
    ) { return null; }

    @GET
    @Path("/search-folder/id/{folderId}")
    @ApiOperation(
        value = "Search tasks in a folder by folder ID.",
        notes = "Returns tasks that match the specified criteria in the given "
              + "folder, most recently edited first (best match first with "
              + "`text`). " + SEARCH_ORG_SCOPE,
        response = TaskWithProjectParentInfo.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = SEARCH_ORG_OK,
            response = TaskWithProjectParentInfo.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = SEARCH_ORG_400),
        @ApiResponse(code = 402, message = SEARCH_ORG_402),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to search this folder."),
        @ApiResponse(code = 404, message = "Not Found — folder does not exist.")
    })
    public Response searchTasksByFolderId(
        @ApiParam(value = "Folder ID.", required = true, example = "my_folder")
        @PathParam("folderId") String folderId,
        @ApiParam(value = SEARCH_TEXT)
        @QueryParam("text") String text,
        @ApiParam(value = SEARCH_NAME, example = "My first task")
        @QueryParam("name") String name,
        @ApiParam(value = SEARCH_DESCRIPTION, example = SEARCH_DESCRIPTION_EXAMPLE)
        @QueryParam("description") String description,
        @ApiParam(value = SEARCH_STATUS, example = "active")
        @QueryParam("status") String status,
        @ApiParam(value = SEARCH_SCHEDULED, example = "true")
        @QueryParam("scheduled") boolean scheduled,
        @ApiParam(value = SEARCH_MINE, example = "true")
        @QueryParam("mine") boolean mine,
        @ApiParam(value = SEARCH_ASSIGNEE, example = "alice,!bob")
        @QueryParam("assignee") String assignee,
        @ApiParam(value = SEARCH_ASSIGNOR, example = "alice")
        @QueryParam("assignor") String assignor,
        @ApiParam(value = SEARCH_FOLLOWER, example = "alice|bob")
        @QueryParam("follower") String follower,
        @ApiParam(value = SEARCH_TAG, example = "Legal,!Draft")
        @QueryParam("tag") String tag,
        @ApiParam(value = SEARCH_MODIFIED, example = "7")
        @QueryParam("modified") String modified,
        @ApiParam(value = SEARCH_COMMENTED, example = "7")
        @QueryParam("commented") String commented,
        @ApiParam(value = SOURCE_REF_PARAM, example = "github")
        @QueryParam("source-ref") String sourceRef,
        @ApiParam(value = SEARCH_CREATED, example = "ge:2026-04-01T00:00:00Z")
        @QueryParam("created") String created,
        @ApiParam(value = SEARCH_EDITED, example = "last7d")
        @QueryParam("edited") String edited,
        @ApiParam(value = SEARCH_ARCHIVED, example = "isNotNull")
        @QueryParam("archived") String archived,
        @ApiParam(value = SEARCH_UNARCHIVED, example = "isNotNull")
        @QueryParam("unarchived") String unarchived,
        @ApiParam(value = SEARCH_TOGGLED, example = "today")
        @QueryParam("toggled") String toggled,
        @ApiParam(value = SEARCH_START, example = "ge:2026-04-01")
        @QueryParam("start") String start,
        @ApiParam(value = SEARCH_DUE, example = "today")
        @QueryParam("due") String due,
        @ApiParam(value = SEARCH_PRIORITY, example = "high|urgent")
        @QueryParam("priority") String priority,
        @ApiParam(value = SEARCH_TYPE, example = "section|milestone")
        @QueryParam("type") String type,
        @ApiParam(value = SEARCH_CREATED_BY, example = "alice")
        @QueryParam("created-by") String createdBy,
        @ApiParam(value = SEARCH_RECURRING, example = "true")
        @QueryParam("recurring") String recurring,
        @ApiParam(value = SEARCH_LIMIT, example = "no")
        @QueryParam("limit") String limit,
        @ApiParam(value = SEARCH_CURSOR, required = false)
        @QueryParam("cursor") String cursor
    ) { return null; }
}
