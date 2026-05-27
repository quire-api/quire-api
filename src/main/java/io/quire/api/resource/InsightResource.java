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

    // -------- Create --------

    @POST
    @Path("/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "Create an insight view by owner OID.",
        notes = "Adds a new insight view to the specified owner (`project`, `organization`, `folder`, or `smart-folder`).",
        response = Insight.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created insight record.", response = Insight.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist."),
        @ApiResponse(code = 429, message = "Too Many Requests — insight quota reached.")
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

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @POST
    @Path("/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "Create an insight view by owner ID.",
        notes = "Adds a new insight view to the specified owner by ID (`project`, `organization`, `folder`, or `smart-folder`).",
        response = Insight.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created insight record.", response = Insight.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist."),
        @ApiResponse(code = 429, message = "Too Many Requests — insight quota reached.")
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

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
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
        @ApiResponse(code = 404, message = "Not Found — insight or owner does not exist.")
    })
    public Response getInsightById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false,
            example = "project"
        )
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
        notes = "Returns all insight views for the specified owner.",
        response = Insight.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of insight records (may be empty).", response = Insight.class, responseContainer = "List"),
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
        notes = "Returns all insight views for the specified owner.",
        response = Insight.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of insight records (may be empty).", response = Insight.class, responseContainer = "List"),
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
      + "stored formula and column layout (the `tableCols` field of the "
      + "Insight) drive what's evaluated.\n\n"
      + "**Response shape:** `[[<headers>], [<row>], ...]`. Row 0 is the "
      + "column-label header row; the first column is the synthesized "
      + "group-by column (`assignee` for `?group-by=member`, `section` "
      + "for `?group-by=section`); remaining columns come from the "
      + "Insight's `tableCols`. Header labels are raw field-name strings "
      + "(locale-independent), matching `/task/list`, `/search/*`, and "
      + "`export-json`.\n\n"
      + "Example (group-by=section, two sections plus one sectionless "
      + "task; `score` is a project cfNumber, `f1` is an Insight "
      + "cfFormula):\n"
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
      + "(per-field default, or the user's `tableCols` override):\n"
      + "- `name` → `null` (no aggregation; not applicable)\n"
      + "- `status` → avg, `priority` → max\n"
      + "- `start` → min, `due` / `completedAt` → max\n"
      + "- `cfNumber` / `cfMoney` / `cfDuration` / `cfLookup` → sum\n"
      + "- `cfDate` → max\n"
      + "- `cfFormula` → sum (default)\n\n"
      + "**Cell value types** (how to parse each cell):\n"
      + "- text / number / boolean → as-is JSON.\n"
      + "- date (`start` / `due` / `cfDate`) → `yyyy-MM-dd` when it has "
      + "no time, otherwise an ISO-8601 UTC datetime "
      + "(`2026-06-20T09:00:00Z`).\n"
      + "- money (`cfMoney`) → the numeric amount only (currency symbol "
      + "and decimal count are dropped).\n"
      + "- duration (`cfDuration`) and time-log totals → an integer "
      + "number of seconds.\n"
      + "- entity (member / section / task ref) → `{oid, id, name}`; a "
      + "user's `id` is a string, a task's / section's `id` is an "
      + "integer; `name` is plain text (markdown stripped). Deleted-user "
      + "assignees become a censored placeholder so their group stays "
      + "distinct from the unassigned (`null`) group.\n"
      + "- `null` → empty / not applicable (e.g. the `name` column on an "
      + "aggregated row, or a column with no value for the group).\n\n"
      + "**Project-scoped Insights only.** Other owner types return `400`.\n\n"
      + "**Formula-token limitation:** project-scope task collections — "
      + "`tasks`, `subtasks`, `activetasks`, `completedtasks` — are "
      + "**not supported** in `cfFormula` columns evaluated through "
      + "this endpoint. Evaluating any of them would scan every task in "
      + "the project, which is deliberately not exposed on the public "
      + "API. Cells that reference these tokens render as JSON `null`; "
      + "the rest of the row is unaffected. Workarounds:\n"
      + "- Reference the row's own task fields (e.g. `priority`, `due`, "
      + "or a project-level `cfNumber` / `cfFormula` column).\n"
      + "- Use `subtasks` (or `activesubtasks` / `completedsubtasks`) "
      + "to walk children of the row's task.\n"
      + "- Pre-compute the project-wide aggregate as a `cfFormula` on "
      + "the Project (those are evaluated separately and DO have access "
      + "to project task collections).\n\n"
      + "**Rate limit:** standard 1 unit (via grantLoad) + a soft "
      + "post-charge of `ceil(tasksLoaded / 250) - 1` extra units. "
      + "`tasksLoaded` reflects the `?status=`-filtered task set, so a "
      + "tighter `?status=` narrows the SQL load and lowers the charge.";

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
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight does not exist.")
    })
    public Response updateInsightByOid(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateInsightBody data,

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
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
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight or owner does not exist.")
    })
    public Response updateInsightById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true, example = "insight1")
        @PathParam("insightId") String insightId,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateInsightBody data,

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
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
            + "`429 Too Many Requests` if the plan's insight limit is "
            + "already reached.",
        response = Insight.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — restored insight record.", response = Insight.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight does not exist."),
        @ApiResponse(code = 429, message = "Too Many Requests — insight quota reached.")
    })
    public Response undoRemoveInsightByOid(
        @ApiParam(value = "OID of the insight to restore.", required = true)
        @PathParam("oid") String oid,

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
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
            + "`429 Too Many Requests` if the plan's insight limit is "
            + "already reached.",
        response = Insight.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — restored insight record.", response = Insight.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight or owner does not exist."),
        @ApiResponse(code = 429, message = "Too Many Requests — insight quota reached.")
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

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    // -------- Delete --------

    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete an insight view by OID.",
        notes = "Deletes the specified insight view.\n\n"
              + "> Note: Returns `204 No Content` regardless of whether the insight exists."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content")
    })
    public Response deleteInsightByOid(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @DELETE
    @Path("/id/{ownerType}/{ownerId}/{insightId}")
    @ApiOperation(
        value = "Delete an insight view by ID.",
        notes = "Deletes the specified insight view.\n\n"
              + "> Note: Returns `204 No Content` regardless of whether the insight exists."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content")
    })
    public Response deleteInsightById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true)
        @PathParam("insightId") String insightId
    ) { return null; }

    // -------- Custom fields --------
    //
    // Each field-extension endpoint is offered in two URL forms:
    //   by-OID: /insight/<ext>/{insightOid}[/{fieldName}[/{newName}]]
    //   by-ID:  /insight/<ext>/id/{ownerType}/{ownerId}/{insightId}[/...]
    // Both dispatch through the same handler; pick whichever is more
    // convenient for the caller.

    @POST
    @Path("/add-field/{oid}")
    @ApiOperation(
        value = "Add a custom-field definition to an insight view.",
        notes = "Adds a new [custom-field definition](#definition-FieldDefinition) "
              + "to the insight view (by OID). The response is the created "
              + "field in public form (same shape as entries in "
              + "`Insight.fields`, with an extra `name` key).\n\n"
              + "Only `formula` and `lookup` types are allowed on insight "
              + "views; all other field types are rejected with `400`.\n\n"
              + "Requires the `Admin` scope to invoke.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight does not exist."),
        @ApiResponse(code = 429, message = "Too Many Requests — custom-field quota reached.")
    })
    public Response addInsightField(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Field definition to add.", required = true)
        AddFieldBody data,

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/update-field/{oid}/{fieldName}")
    @ApiOperation(
        value = "Update a custom-field definition on an insight view.",
        notes = "Updates the content of an existing custom field. "
              + "`type` is immutable — if supplied, it must match the existing "
              + "type. Keys that are omitted leave their current values intact "
              + "(including individual flag bits — flags are merged, not replaced).\n\n"
              + "Requires the `Admin` scope to invoke.\n\n"
              + "To rename a field, use `/insight/rename-field/id/{ownerType}/{ownerId}/{insightId}/{fieldName}/{newName}`; "
              + "to reorder, use `/insight/move-field/id/{ownerType}/{ownerId}/{insightId}/{fieldName}`. "
              + "(The `/{oid}/...` URL form is also accepted.)\n\n"
              + "Response body is a `FieldDefinition` with an extra `name` key "
              + "(equal to the field's name), or an empty object if the field does not exist.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight does not exist.")
    })
    public Response updateInsightField(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Name of the field to update.", required = true, example = "Priority")
        @PathParam("fieldName") String fieldName,
        @ApiParam(value = "New field content.", required = true)
        UpdateFieldBody data,

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @DELETE
    @Path("/remove-field/{oid}/{fieldName}")
    @ApiOperation(
        value = "Remove a custom-field definition from an insight view.",
        notes = "Removes the named custom field from the insight view.\n\n"
              + "Requires the `Admin` scope to invoke.\n\n"
              + "> Note: Returns `204 No Content` regardless of whether the field exists."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content — field removed (or already absent).")
    })
    public Response removeInsightField(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Name of the field to remove.", required = true)
        @PathParam("fieldName") String fieldName
    ) { return null; }

    @PUT
    @Path("/rename-field/{oid}/{fieldName}/{newName}")
    @ApiOperation(
        value = "Rename a custom-field definition on an insight view.",
        notes = "Renames the field in place and returns the renamed field. "
              + "The field's content is preserved.\n\n"
              + "Requires the `Admin` scope to invoke.\n\n"
              + "Response body is a `FieldDefinition` with an extra `name` key "
              + "(equal to the new name), or an empty object if the source "
              + "field is missing or the target name is already in use.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — renamed field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight does not exist.")
    })
    public Response renameInsightField(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Current field name.", required = true, example = "Priority")
        @PathParam("fieldName") String fieldName,
        @ApiParam(value = "New field name.", required = true, example = "Importance")
        @PathParam("newName") String newName,

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/move-field/{oid}/{fieldName}")
    @ApiOperation(
        value = "Reorder a custom-field definition on an insight view.",
        notes = "Moves the named field to a new position. By default the "
              + "field is moved to the end; pass `?before={otherName}` to "
              + "place it immediately before another field.\n\n"
              + "Requires the `Admin` scope to invoke.\n\n"
              + "Response body is a `FieldDefinition` with an extra `name` key "
              + "(equal to the field's name), or an empty object if the field "
              + "does not exist or `?before={otherName}` refers to a missing field.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — moved field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight does not exist.")
    })
    public Response moveInsightField(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Name of the field to move.", required = true, example = "Priority")
        @PathParam("fieldName") String fieldName,
        @ApiParam(
            value = "(Optional) Name of the field to insert before. "
                  + "If omitted, the field is moved to the end.",
            example = "before=Priority",
            required = false
        )
        @QueryParam("before") String before,

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    // -------- Custom fields — by-id variants (#24535) --------

    @POST
    @Path("/add-field/id/{ownerType}/{ownerId}/{insightId}")
    @ApiOperation(
        value = "Add a custom-field definition to an insight view by ID.",
        notes = "Adds a custom-field definition to an insight view, "
              + "identifying the insight by owner-type + owner ID + insight ID. "
              + "Only `formula` and `lookup` field types are allowed on "
              + "insight views. See also `/insight/add-field/{oid}` for the OID form.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight or owner does not exist."),
        @ApiResponse(code = 429, message = "Too Many Requests — custom-field quota reached.")
    })
    public Response addInsightFieldById(
        @ApiParam(
            value = "Owner type.",
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

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/update-field/id/{ownerType}/{ownerId}/{insightId}/{fieldName}")
    @ApiOperation(
        value = "Update a custom-field definition on an insight view by ID.",
        notes = "Updates an existing custom-field definition on an insight "
              + "view, identifying the insight by owner-type + owner ID + "
              + "insight ID. `type` is immutable; omitted keys preserve "
              + "current values. See also `/insight/update-field/{oid}/{fieldName}` "
              + "for the OID form.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight or owner does not exist.")
    })
    public Response updateInsightFieldById(
        @ApiParam(
            value = "Owner type.",
            allowableValues = "project, organization, folder, smart-folder",
            required = true,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true, example = "insight1")
        @PathParam("insightId") String insightId,
        @ApiParam(value = "Name of the field to update.", required = true, example = "Priority")
        @PathParam("fieldName") String fieldName,
        @ApiParam(value = "New field content.", required = true)
        UpdateFieldBody data,

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @DELETE
    @Path("/remove-field/id/{ownerType}/{ownerId}/{insightId}/{fieldName}")
    @ApiOperation(
        value = "Remove a custom-field definition from an insight view by ID.",
        notes = "Removes the named custom field from an insight view, "
              + "identifying the insight by owner-type + owner ID + insight "
              + "ID. Returns `204 No Content` regardless of whether the "
              + "field exists. See also `/insight/remove-field/{oid}/{fieldName}` "
              + "for the OID form."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content — field removed (or already absent).")
    })
    public Response removeInsightFieldById(
        @ApiParam(
            value = "Owner type.",
            allowableValues = "project, organization, folder, smart-folder",
            required = true
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true)
        @PathParam("insightId") String insightId,
        @ApiParam(value = "Name of the field to remove.", required = true)
        @PathParam("fieldName") String fieldName
    ) { return null; }

    @PUT
    @Path("/rename-field/id/{ownerType}/{ownerId}/{insightId}/{fieldName}/{newName}")
    @ApiOperation(
        value = "Rename a custom-field definition on an insight view by ID.",
        notes = "Renames a custom-field definition on an insight view, "
              + "identifying the insight by owner-type + owner ID + insight "
              + "ID. The field's content is preserved. See also "
              + "`/insight/rename-field/{oid}/{fieldName}/{newName}` for the OID form.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — renamed field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight or owner does not exist.")
    })
    public Response renameInsightFieldById(
        @ApiParam(
            value = "Owner type.",
            allowableValues = "project, organization, folder, smart-folder",
            required = true,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true, example = "insight1")
        @PathParam("insightId") String insightId,
        @ApiParam(value = "Current field name.", required = true, example = "Priority")
        @PathParam("fieldName") String fieldName,
        @ApiParam(value = "New field name.", required = true, example = "Importance")
        @PathParam("newName") String newName,

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/move-field/id/{ownerType}/{ownerId}/{insightId}/{fieldName}")
    @ApiOperation(
        value = "Reorder a custom-field definition on an insight view by ID.",
        notes = "Reorders a custom-field definition on an insight view, "
              + "identifying the insight by owner-type + owner ID + insight "
              + "ID. Pass `?before={otherName}` to place the field before "
              + "another; omit to move it to the end. See also "
              + "`/insight/move-field/{oid}/{fieldName}` for the OID form.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — moved field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight or owner does not exist.")
    })
    public Response moveInsightFieldById(
        @ApiParam(
            value = "Owner type.",
            allowableValues = "project, organization, folder, smart-folder",
            required = true,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true, example = "insight1")
        @PathParam("insightId") String insightId,
        @ApiParam(value = "Name of the field to move.", required = true, example = "Priority")
        @PathParam("fieldName") String fieldName,
        @ApiParam(
            value = "(Optional) Name of the field to insert before. "
                  + "If omitted, the field is moved to the end.",
            example = "before=Priority",
            required = false
        )
        @QueryParam("before") String before,

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }
}
