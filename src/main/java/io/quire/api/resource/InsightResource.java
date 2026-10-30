package io.quire.api.resource;

import io.quire.api.model.*;
import io.quire.api.model.field.*;
import io.quire.api.model.insight.*;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.List;

@Path("/insight")
@Api(
    value = "insight",
    description = "Insight views — configurable task views with filters and columns."
)
@Produces({"application/json"})
public class InsightResource {

    // The read endpoints share this.
    private static final String READ_403 =
        "Forbidden — the user can't access it, or didn't authorize it for this app.";

    // The create endpoints share this.
    private static final String CREATE_NOTES =
        "Adds a new insight view to the specified owner (`project`, `organization`, `folder`, or `smart-folder`) "
      + "and returns it.\n\n"
      + "Example — `POST /insight/id/project/my_project`:\n\n"
      + "```json\n"
      + "{\n"
      + "  \"name\": \"Workload\",\n"
      + "  \"image\": \"icon-view-insight\"\n"
      + "}\n"
      + "```\n\n"
      + "To add formula or lookup columns, call "
      + "`POST /insight/add-field/id/{ownerType}/{ownerId}/{insightId}` afterwards.\n\n"
      + "Returns `402` if the plan's insight limit is reached.";

    // The list endpoints share this.
    private static final String LIST_NOTES =
        "Returns the owner's insight views that the caller can see; one shown to "
      + "selected members only is left out for the others. Archived ones "
      + "are included (they carry `archivedAt`); removed ones are not.";

    // The delete endpoints share this.
    private static final String DELETE_NOTES =
        "Removes the insight view. Restore it with "
      + "`PUT /insight/undo-remove/id/{ownerType}/{ownerId}/{insightId}` "
      + "(or `/insight/undo-remove/{oid}`).\n\n"
      + "Returns `204 No Content`, also when the insight does not exist.";

    // The by-ID get, update and delete endpoints share this.
    private static final String OWNER_TYPE_BY_ID =
        "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
      + "If omitted, `project` is assumed; e.g., `/id/my_project/insight1` "
      + "is equivalent to `/id/project/my_project/insight1`.";

    // The create, update and undo-remove endpoints share this.
    private static final String RETURN_MODE =
        "(Optional) Response shape: `full` (default) returns the full insight "
      + "record; `compact` returns only `{\"oid\": \"…\", \"id\": \"…\"}`, "
      + "which is cheaper — use it when you don't need the record back. "
      + "Any other value returns `400 Bad Request`.";

    // -------- Create --------

    @POST
    @Path("/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "Create an insight view by owner OID.",
        notes = CREATE_NOTES,
        response = Insight.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created insight record.", response = Insight.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed, for example a `partner` that isn't an external team of the project (or any, when the owner isn't a project), or none from an external team member sharing with every member."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to create an insight under this owner."),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist."),
        @ApiResponse(code = 402, message = "Payment Required — insight quota reached.")
    })
    public Response createInsight(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/abc123` is equivalent to `/project/abc123`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner OID.", required = true)
        @PathParam("ownerOid") String ownerOid,
        @ApiParam(value = "Insight view to create.", required = true)
        CreateInsightBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @POST
    @Path("/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "Create an insight view by owner ID.",
        notes = CREATE_NOTES,
        response = Insight.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created insight record.", response = Insight.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed, for example a `partner` that isn't an external team of the project (or any, when the owner isn't a project), or none from an external team member sharing with every member."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to create an insight under this owner."),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist."),
        @ApiResponse(code = 402, message = "Payment Required — insight quota reached.")
    })
    public Response createInsightById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight view to create.", required = true)
        CreateInsightBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    // -------- Read --------

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get an insight view by OID.",
        notes = "Returns the full insight record.",
        response = Insight.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — insight record.", response = Insight.class),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — insight does not exist.")
    })
    public Response getInsight(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @GET
    @Path("/id/{ownerType}/{ownerId}/{insightId}")
    @ApiOperation(
        value = "Get an insight view by ID.",
        notes = "Returns the full insight record for the given owner and insight ID.",
        response = Insight.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — insight record.", response = Insight.class),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — insight or owner does not exist.")
    })
    public Response getInsightById(
        @ApiParam(value = OWNER_TYPE_BY_ID, required = false, example = "project")
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true, example = "insight1")
        @PathParam("insightId") String insightId
    ) { return null; }

    @GET
    @Path("/list/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "List insight views by owner OID.",
        notes = LIST_NOTES,
        response = Insight.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of insight records (may be empty).", response = Insight.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist.")
    })
    public Response getInsightsByOwnerOid(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/list/abc123` is equivalent to `/list/project/abc123`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner OID.", required = true)
        @PathParam("ownerOid") String ownerOid
    ) { return null; }

    @GET
    @Path("/list/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "List insight views by owner ID.",
        notes = LIST_NOTES,
        response = Insight.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of insight records (may be empty).", response = Insight.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist.")
    })
    public Response getInsightsByOwnerId(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/list/id/foo` is equivalent to `/list/id/project/foo`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId
    ) { return null; }

    // -------- Run --------
    //
    // Both URL forms share the same notes (intentional duplication — keeps
    // the by-id form self-contained for MCP / agent consumers, which may
    // see only one endpoint at a time).

    private static final String RUN_NOTES =
        "Runs the Insight and returns its **computed** result — one "
      + "aggregated row per group, as a JSON 2D array. The Insight's "
      + "formulas and its column layout (set in Quire; the layout is not "
      + "exposed through the API) drive what's evaluated.\n\n"
      + "**Response shape:** `[[<headers>], [<row>], ...]`. Row 0 is the "
      + "column-label header row; the first column is the synthesized "
      + "group-by column (`assignee` for `?group-by=member`, `section` "
      + "for `?group-by=section`); remaining columns are the Insight's "
      + "columns as laid out in Quire, without the custom fields the caller "
      + "can't view. Header labels are raw field-name strings "
      + "(locale-independent), matching `/task/list`, `/search/*`, and "
      + "`export-json`.\n\n"
      + "Example (group-by=section, two sections plus one sectionless "
      + "task; `score` is a project `number` field, `f1` an Insight "
      + "`formula` field):\n"
      + "```json\n"
      + "[\n"
      + "  [\"section\", \"name\", \"priority\", \"score\", \"f1\"],\n"
      + "  [{\"oid\":\"T1\",\"id\":7,\"name\":\"Backend\"}, null, 2, 30, 63],\n"
      + "  [{\"oid\":\"T2\",\"id\":8,\"name\":\"Frontend\"}, null, 1, 70, 142],\n"
      + "  [null, null, 2, 50, 102]\n"
      + "]\n"
      + "```\n\n"
      + "**Row order:** alphabetical by the group entity's name (case-"
      + "insensitive, simple-markdown stripped); `null` group (unassigned "
      + "/ sectionless tasks) sorts last.\n\n"
      + "**Aggregation per column** is driven by the field's calc type "
      + "(the field's default, or the calculation chosen for the column "
      + "in Quire):\n"
      + "- `name` → `null` (no aggregation; not applicable)\n"
      + "- `status` → avg, `priority` → max\n"
      + "- `start` → min, `due` / `completedAt` → max\n"
      + "- a `number`, `money`, `duration` or `lookup` field → sum\n"
      + "- a `date` field → max\n"
      + "- a `formula` field → sum (default)\n\n"
      + "**Cell value types** (how to parse each cell):\n"
      + "- text / number / boolean → as-is JSON.\n"
      + "- date (`start`, `due` or a `date` field) → `yyyy-MM-dd` when it has "
      + "no time, otherwise an ISO-8601 UTC datetime "
      + "(`2026-06-20T09:00:00Z`).\n"
      + "- money (a `money` field) → the numeric amount only (currency symbol "
      + "and decimal count are dropped).\n"
      + "- duration (a `duration` field) and time-log totals → an integer "
      + "number of seconds.\n"
      + "- entity (member / section / task ref) → `{oid, id, name}`; a "
      + "user's `id` is a string, a task's / section's `id` is an "
      + "integer; `name` is plain text (markdown stripped). Deleted-user "
      + "assignees become a censored placeholder so their group stays "
      + "distinct from the unassigned (`null`) group.\n"
      + "- `null` → empty / not applicable (e.g. the `name` column on an "
      + "aggregated row, or a column with no value for the group).\n\n"
      + "**Project-scoped Insights only.** Other owner types return `400`.\n\n"
      + "**Formula-token limitation:** the project's task collections — "
      + "`project.tasks`, `project.activetasks`, `project.completedtasks` "
      + "(and `project.subtasks`, which means `project.tasks`) — are "
      + "**not supported** through this endpoint, in an Insight's `formula` "
      + "field and in a project's `formula` field alike. Evaluating any of them "
      + "would scan every task in the project, which is deliberately not "
      + "exposed on the public API. Cells that reference them render as "
      + "JSON `null`; the rest of the row is unaffected. Workarounds:\n"
      + "- Reference the row's own task fields (e.g. `priority`, `due`, "
      + "or a project's `number` field).\n"
      + "- Use the row task's `subtasks` (or `activesubtasks` / "
      + "`completedsubtasks`) to walk its children; these are supported.\n\n"
      + "**Rate-limit cost:** `max(1, ceil(tasks / 250))` units, where "
      + "`tasks` is the number of tasks loaded after the `?status=` filter — "
      + "1 for up to 250 tasks, 4 for 1,000. A narrower `?status=` costs "
      + "less. See also [Rate Limits](#rate-limits).";

    @GET
    @Path("/run/{oid}")
    @ApiOperation(
        value = "Run an Insight and return its computed result (by OID).",
        notes = RUN_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — JSON 2D array (header row + data rows; data rows may be empty).", responseContainer = "List", response = List.class),
        @ApiResponse(code = 400, message = "Bad Request — unknown `?group-by` / `?status` value, or non-project-scoped Insight."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks read permission on the Insight."),
        @ApiResponse(code = 404, message = "Not Found — Insight does not exist."),
        @ApiResponse(code = 429, message = "Too Many Requests — rate-limit exceeded.")
    })
    public Response getInsightResult(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(
            value = "(Optional) Row grouping. `member` produces one row "
                  + "per assignee (multi-assignee tasks contribute to "
                  + "every group their assignees are in; unassigned tasks "
                  + "fall into a `null` group). `section` produces one "
                  + "row per section ancestor (tasks not under any "
                  + "section fall into a `null` group). Case-insensitive.",
            example = "member",
            allowableValues = "member, section",
            defaultValue = "member"
        )
        @QueryParam("group-by") String groupBy,
        @ApiParam(
            value = "(Optional) Task-state filter, applied at the SQL "
                  + "load layer (narrower queries cost less — see "
                  + "rate-limit note). `active` = not-yet-completed "
                  + "tasks (state < completed). `completed` = completed "
                  + "tasks. `all` = no filter. Case-insensitive.",
            example = "active",
            allowableValues = "active, completed, all",
            defaultValue = "active"
        )
        @QueryParam("status") String status
    ) { return null; }

    @GET
    @Path("/run/id/{ownerType}/{ownerId}/{insightId}")
    @ApiOperation(
        value = "Run an Insight and return its computed result (by ID).",
        notes = RUN_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — JSON 2D array (header row + data rows; data rows may be empty).", responseContainer = "List", response = List.class),
        @ApiResponse(code = 400, message = "Bad Request — unknown `?group-by` / `?status` value, or non-project-scoped Insight."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks read permission on the Insight."),
        @ApiResponse(code = 404, message = "Not Found — Insight or owner does not exist."),
        @ApiResponse(code = 429, message = "Too Many Requests — rate-limit exceeded.")
    })
    public Response getInsightResultById(
        @ApiParam(
            value = "Owner type. Only `project` is supported on this "
                  + "endpoint; other types return `400`.",
            allowableValues = "project",
            required = true,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Project ID (the owner ID).",
                  required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true, example = "insight1")
        @PathParam("insightId") String insightId,
        @ApiParam(
            value = "(Optional) Row grouping. `member` produces one row "
                  + "per assignee (multi-assignee tasks contribute to "
                  + "every group their assignees are in; unassigned tasks "
                  + "fall into a `null` group). `section` produces one "
                  + "row per section ancestor (tasks not under any "
                  + "section fall into a `null` group). Case-insensitive.",
            example = "member",
            allowableValues = "member, section",
            defaultValue = "member"
        )
        @QueryParam("group-by") String groupBy,
        @ApiParam(
            value = "(Optional) Task-state filter, applied at the SQL "
                  + "load layer (narrower queries cost less — see "
                  + "rate-limit note). `active` = not-yet-completed "
                  + "tasks (state < completed). `completed` = completed "
                  + "tasks. `all` = no filter. Case-insensitive.",
            example = "active",
            allowableValues = "active, completed, all",
            defaultValue = "active"
        )
        @QueryParam("status") String status
    ) { return null; }

    // -------- Update --------

    @PUT
    @Path("/{oid}")
    @ApiOperation(
        value = "Update an insight view by OID.",
        notes = "Updates an existing insight view and returns the updated record.\n\n"
              + "To archive or unarchive, set `archived: true` or `archived: false` in the body.",
        response = Insight.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated insight record.", response = Insight.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to modify this insight."),
        @ApiResponse(code = 404, message = "Not Found — insight does not exist.")
    })
    public Response updateInsightByOid(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateInsightBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/id/{ownerType}/{ownerId}/{insightId}")
    @ApiOperation(
        value = "Update an insight view by ID.",
        notes = "Updates an existing insight view and returns the updated record.\n\n"
              + "To archive or unarchive, set `archived: true` or `archived: false` in the body.",
        response = Insight.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated insight record.", response = Insight.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to modify this insight."),
        @ApiResponse(code = 404, message = "Not Found — insight or owner does not exist.")
    })
    public Response updateInsightById(
        @ApiParam(value = OWNER_TYPE_BY_ID, required = false, example = "project")
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true, example = "insight1")
        @PathParam("insightId") String insightId,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateInsightBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    // -------- Undo remove --------

    @PUT
    @Path("/undo-remove/{oid}")
    @ApiOperation(
        value = "Undo the removal of an insight view by OID.",
        notes = "Restores a previously-removed insight view. "
            + "Idempotent: if the insight is not currently removed, this is "
            + "a no-op and returns the current insight record.\n\n"
            + "Subject to the insight-per-owner quota: may return "
            + "`402 Payment Required` if the plan's insight limit is "
            + "already reached.",
        response = Insight.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — restored insight record.", response = Insight.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to restore this insight."),
        @ApiResponse(code = 404, message = "Not Found — insight does not exist."),
        @ApiResponse(code = 402, message = "Payment Required — insight quota reached.")
    })
    public Response undoRemoveInsightByOid(
        @ApiParam(value = "OID of the insight to restore.", required = true)
        @PathParam("oid") String oid,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/undo-remove/id/{ownerType}/{ownerId}/{insightId}")
    @ApiOperation(
        value = "Undo the removal of an insight view by ID.",
        notes = "Restores a previously-removed insight view. "
            + "Idempotent: if the insight is not currently removed, this is "
            + "a no-op and returns the current insight record.\n\n"
            + "Subject to the insight-per-owner quota: may return "
            + "`402 Payment Required` if the plan's insight limit is "
            + "already reached.",
        response = Insight.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — restored insight record.", response = Insight.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to restore this insight."),
        @ApiResponse(code = 404, message = "Not Found — insight or owner does not exist."),
        @ApiResponse(code = 402, message = "Payment Required — insight quota reached.")
    })
    public Response undoRemoveInsightById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/undo-remove/id/foo/bar` "
                  + "is equivalent to `/undo-remove/id/project/foo/bar`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true, example = "insight1")
        @PathParam("insightId") String insightId,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    // -------- Delete --------

    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete an insight view by OID.",
        notes = DELETE_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to remove this insight.")
    })
    public Response deleteInsightByOid(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @DELETE
    @Path("/id/{ownerType}/{ownerId}/{insightId}")
    @ApiOperation(
        value = "Delete an insight view by ID.",
        notes = DELETE_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to remove this insight.")
    })
    public Response deleteInsightById(
        @ApiParam(value = OWNER_TYPE_BY_ID, required = false, example = "project")
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true, example = "insight1")
        @PathParam("insightId") String insightId
    ) { return null; }

    // -------- Custom fields --------
    //
    // Each field-extension endpoint is offered in two URL forms:
    //   by-OID: /insight/<ext>/{insightOid}[?name={fieldName}[&new-name={newName}]]
    //   by-ID:  /insight/<ext>/id/{ownerType}/{ownerId}/{insightId}[?...]
    // Both dispatch through the same handler and share the same notes,
    // since MCP / agent consumers may see only one endpoint at a time.

    // The field name goes in the query (`?name=`, rename's `&new-name=`), so
    // it may contain `/`. Shared by every field endpoint.
    private static final String FIELD_NAME_ENCODING =
        "Percent-encode it, e.g. `Billable%20Amount` for `Billable Amount`; "
      + "a `+` in the name must be `%2B`, since `+` means a space in a query.";

    // The field endpoints share this.
    private static final String FIELD_ADMIN =
        "Requires admin rights on the insight's owner (its project, "
      + "organization, folder or smart folder) and an app with the "
      + "*Manage profiles* permission; otherwise `403`. External-team "
      + "members always get `403`.";

    // The field endpoints share this.
    private static final String FIELD_FORBIDDEN =
        "Forbidden — caller is not an admin of the insight's owner, or the app lacks the *Manage profiles* permission.";

    // The add, update and move field endpoints share this.
    private static final String FIELD_RESPONSE =
        "Returns the field's definition (the shape of an entry in the "
      + "insight's `fields`) plus its `name`; with `?return=compact`, only "
      + "`{\"name\": \"…\"}`.";

    // The by-ID field endpoints share this.
    private static final String FIELD_OWNER_TYPE =
        "Owner type of the insight. Required here: it is not assumed to be `project`.";

    // The add, update, rename and move field endpoints share this.
    private static final String FIELD_RETURN_MODE =
        "(Optional) Response shape: `full` (default) returns the field's "
      + "definition plus its `name`; `compact` returns only "
      + "`{\"name\": \"…\"}` (for rename, the new name). "
      + "Any other value returns `400 Bad Request`.";

    // The add-field endpoints share this.
    private static final String ADD_FIELD_NOTES =
        "Adds a [custom-field definition](#definition-FieldDefinition) "
      + "to the insight view.\n\n"
      + "Only `formula` and `lookup` types are allowed on insight views; "
      + "any other `type` returns `400`, as do an invalid body and a "
      + "missing `name`.\n\n"
      + FIELD_ADMIN + "\n\n"
      + FIELD_RESPONSE + "\n\n"
      + "A name already in use returns `409`; reaching the plan's "
      + "custom-field limit returns `402`.";

    // The update-field endpoints share this.
    private static final String UPDATE_FIELD_NOTES =
        "Updates the content of an existing custom field of the insight view.\n\n"
      + "`type` is immutable — if supplied, it must match the existing "
      + "type, or `400`. Keys that are omitted leave their current values "
      + "intact, and so does each boolean option you omit or send as "
      + "`null` (`hidden`, `locked`, `private`, …): they are merged one by "
      + "one, not replaced as a set. An invalid body or a missing `name` "
      + "query parameter returns `400`; a missing field, `404`.\n\n"
      + FIELD_ADMIN + "\n\n"
      + FIELD_RESPONSE + "\n\n"
      + "To rename a field, use `/insight/rename-field/id/{ownerType}/{ownerId}/{insightId}?name={fieldName}&new-name={newName}`; "
      + "to reorder, use `/insight/move-field/id/{ownerType}/{ownerId}/{insightId}?name={fieldName}`. "
      + "(The `/{oid}` URL form, e.g. `/insight/rename-field/{oid}?…`, is also accepted.)";

    // The remove-field endpoints share this.
    private static final String REMOVE_FIELD_NOTES =
        "Removes the named custom field from the insight view.\n\n"
      + FIELD_ADMIN + "\n\n"
      + "Returns `204 No Content`, also when the field does not exist. "
      + "A missing `name` query parameter returns `400`.";

    // The rename-field endpoints share this.
    private static final String RENAME_FIELD_NOTES =
        "Renames the field in place and returns the renamed field. "
      + "The field's content is preserved.\n\n"
      + FIELD_ADMIN + "\n\n"
      + "Returns the field's definition plus its new `name`; with "
      + "`?return=compact`, only `{\"name\": \"<new name>\"}`.\n\n"
      + "A missing field returns `404`; a `new-name` already in use, `409`; "
      + "a missing `name` or `new-name`, or an invalid `new-name`, `400` — "
      + "`new-name` must be non-empty, without `}`, `\"` or `\\`, and at "
      + "most 1,280 characters.";

    // The move-field endpoints share this.
    private static final String MOVE_FIELD_NOTES =
        "Moves the named field to a new position. By default the "
      + "field is moved to the end; pass `&before={otherName}` to "
      + "place it immediately before another field.\n\n"
      + FIELD_ADMIN + "\n\n"
      + FIELD_RESPONSE + "\n\n"
      + "A missing field, or a missing `&before={otherName}` field, returns "
      + "`404`; a missing `name` query parameter, `400`.";

    @POST
    @Path("/add-field/{oid}")
    @ApiOperation(
        value = "Add a custom-field definition to an insight view.",
        notes = ADD_FIELD_NOTES,
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = FIELD_FORBIDDEN),
        @ApiResponse(code = 404, message = "Not Found — insight does not exist."),
        @ApiResponse(code = 409, message = "Conflict — a field with this name already exists."),
        @ApiResponse(code = 402, message = "Payment Required — custom-field quota reached.")
    })
    public Response addInsightField(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Field definition to add.", required = true)
        AddFieldBody data,

        @ApiParam(value = FIELD_RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/update-field/{oid}")
    @ApiOperation(
        value = "Update a custom-field definition on an insight view.",
        notes = UPDATE_FIELD_NOTES,
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed, or `name` missing."),
        @ApiResponse(code = 403, message = FIELD_FORBIDDEN),
        @ApiResponse(code = 404, message = "Not Found — insight or field does not exist.")
    })
    public Response updateInsightField(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Name of the field to update. " + FIELD_NAME_ENCODING, required = true, example = "Priority")
        @QueryParam("name") String name,
        @ApiParam(value = "New field content.", required = true)
        UpdateFieldBody data,

        @ApiParam(value = FIELD_RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @DELETE
    @Path("/remove-field/{oid}")
    @ApiOperation(
        value = "Remove a custom-field definition from an insight view.",
        notes = REMOVE_FIELD_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content — field removed (or already absent)."),
        @ApiResponse(code = 400, message = "Bad Request — `name` missing."),
        @ApiResponse(code = 403, message = FIELD_FORBIDDEN)
    })
    public Response removeInsightField(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Name of the field to remove. " + FIELD_NAME_ENCODING, required = true, example = "Priority")
        @QueryParam("name") String name
    ) { return null; }

    @PUT
    @Path("/rename-field/{oid}")
    @ApiOperation(
        value = "Rename a custom-field definition on an insight view.",
        notes = RENAME_FIELD_NOTES,
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — renamed field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — `name` or `new-name` missing, or `new-name` invalid."),
        @ApiResponse(code = 403, message = FIELD_FORBIDDEN),
        @ApiResponse(code = 404, message = "Not Found — insight or field does not exist."),
        @ApiResponse(code = 409, message = "Conflict — `new-name` is already in use.")
    })
    public Response renameInsightField(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Current field name. " + FIELD_NAME_ENCODING, required = true, example = "Priority")
        @QueryParam("name") String name,
        @ApiParam(value = "New field name. " + FIELD_NAME_ENCODING, required = true, example = "Importance")
        @QueryParam("new-name") String newName,

        @ApiParam(value = FIELD_RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/move-field/{oid}")
    @ApiOperation(
        value = "Reorder a custom-field definition on an insight view.",
        notes = MOVE_FIELD_NOTES,
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — moved field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — `name` missing."),
        @ApiResponse(code = 403, message = FIELD_FORBIDDEN),
        @ApiResponse(code = 404, message = "Not Found — insight, field or `before` field does not exist.")
    })
    public Response moveInsightField(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Name of the field to move. " + FIELD_NAME_ENCODING, required = true, example = "Priority")
        @QueryParam("name") String name,
        @ApiParam(
            value = "(Optional) Name of the field to insert before. "
                  + "If omitted, the field is moved to the end. " + FIELD_NAME_ENCODING,
            example = "Status",
            required = false
        )
        @QueryParam("before") String before,

        @ApiParam(value = FIELD_RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    // -------- Custom fields — by-id variants (#24535) --------

    @POST
    @Path("/add-field/id/{ownerType}/{ownerId}/{insightId}")
    @ApiOperation(
        value = "Add a custom-field definition to an insight view by ID.",
        notes = ADD_FIELD_NOTES,
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = FIELD_FORBIDDEN),
        @ApiResponse(code = 404, message = "Not Found — insight or owner does not exist."),
        @ApiResponse(code = 409, message = "Conflict — a field with this name already exists."),
        @ApiResponse(code = 402, message = "Payment Required — custom-field quota reached.")
    })
    public Response addInsightFieldById(
        @ApiParam(
            value = FIELD_OWNER_TYPE,
            allowableValues = "project, organization, folder, smart-folder",
            required = true,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true, example = "insight1")
        @PathParam("insightId") String insightId,
        @ApiParam(value = "Field definition to add.", required = true)
        AddFieldBody data,

        @ApiParam(value = FIELD_RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/update-field/id/{ownerType}/{ownerId}/{insightId}")
    @ApiOperation(
        value = "Update a custom-field definition on an insight view by ID.",
        notes = UPDATE_FIELD_NOTES,
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed, or `name` missing."),
        @ApiResponse(code = 403, message = FIELD_FORBIDDEN),
        @ApiResponse(code = 404, message = "Not Found — insight, owner or field does not exist.")
    })
    public Response updateInsightFieldById(
        @ApiParam(
            value = FIELD_OWNER_TYPE,
            allowableValues = "project, organization, folder, smart-folder",
            required = true,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true, example = "insight1")
        @PathParam("insightId") String insightId,
        @ApiParam(value = "Name of the field to update. " + FIELD_NAME_ENCODING, required = true, example = "Priority")
        @QueryParam("name") String name,
        @ApiParam(value = "New field content.", required = true)
        UpdateFieldBody data,

        @ApiParam(value = FIELD_RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @DELETE
    @Path("/remove-field/id/{ownerType}/{ownerId}/{insightId}")
    @ApiOperation(
        value = "Remove a custom-field definition from an insight view by ID.",
        notes = REMOVE_FIELD_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content — field removed (or already absent)."),
        @ApiResponse(code = 400, message = "Bad Request — `name` missing."),
        @ApiResponse(code = 403, message = FIELD_FORBIDDEN)
    })
    public Response removeInsightFieldById(
        @ApiParam(
            value = FIELD_OWNER_TYPE,
            allowableValues = "project, organization, folder, smart-folder",
            required = true,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true, example = "insight1")
        @PathParam("insightId") String insightId,
        @ApiParam(value = "Name of the field to remove. " + FIELD_NAME_ENCODING, required = true, example = "Priority")
        @QueryParam("name") String name
    ) { return null; }

    @PUT
    @Path("/rename-field/id/{ownerType}/{ownerId}/{insightId}")
    @ApiOperation(
        value = "Rename a custom-field definition on an insight view by ID.",
        notes = RENAME_FIELD_NOTES,
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — renamed field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — `name` or `new-name` missing, or `new-name` invalid."),
        @ApiResponse(code = 403, message = FIELD_FORBIDDEN),
        @ApiResponse(code = 404, message = "Not Found — insight, owner or field does not exist."),
        @ApiResponse(code = 409, message = "Conflict — `new-name` is already in use.")
    })
    public Response renameInsightFieldById(
        @ApiParam(
            value = FIELD_OWNER_TYPE,
            allowableValues = "project, organization, folder, smart-folder",
            required = true,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true, example = "insight1")
        @PathParam("insightId") String insightId,
        @ApiParam(value = "Current field name. " + FIELD_NAME_ENCODING, required = true, example = "Priority")
        @QueryParam("name") String name,
        @ApiParam(value = "New field name. " + FIELD_NAME_ENCODING, required = true, example = "Importance")
        @QueryParam("new-name") String newName,

        @ApiParam(value = FIELD_RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/move-field/id/{ownerType}/{ownerId}/{insightId}")
    @ApiOperation(
        value = "Reorder a custom-field definition on an insight view by ID.",
        notes = MOVE_FIELD_NOTES,
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — moved field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — `name` missing."),
        @ApiResponse(code = 403, message = FIELD_FORBIDDEN),
        @ApiResponse(code = 404, message = "Not Found — insight, owner, field or `before` field does not exist.")
    })
    public Response moveInsightFieldById(
        @ApiParam(
            value = FIELD_OWNER_TYPE,
            allowableValues = "project, organization, folder, smart-folder",
            required = true,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true, example = "insight1")
        @PathParam("insightId") String insightId,
        @ApiParam(value = "Name of the field to move. " + FIELD_NAME_ENCODING, required = true, example = "Priority")
        @QueryParam("name") String name,
        @ApiParam(
            value = "(Optional) Name of the field to insert before. "
                  + "If omitted, the field is moved to the end. " + FIELD_NAME_ENCODING,
            example = "Status",
            required = false
        )
        @QueryParam("before") String before,

        @ApiParam(value = FIELD_RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }
}
