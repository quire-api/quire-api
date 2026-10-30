package io.quire.api.resource;

import io.quire.api.model.approval.*;
import io.quire.api.model.field.*;
import io.quire.api.model.project.*;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/project")
@Api(
    value = "project",
    description =
        "A project represents a prioritized list of tasks in Quire. "
      + "It belongs to a single organization and is accessible to a subset of users in that organization, "
      + "depending on its permissions."
)
@Produces({"application/json"})
public class ProjectResource {

    // The project list endpoints share these.
    private static final String LIST_ARCHIVED =
        "Whether to include archived projects.\n"
      + "By default, archived projects are excluded.\n"
      + "If the parameter is present without a value, `true` is assumed.";

    private static final String LIST_ADD_TASK =
        "Whether to return only projects to which you can add tasks. Default: `false`.\n"
      + "If the parameter is present without a value, `true` is assumed.";

    // The project read endpoints share this.
    private static final String PROJECT_403 =
        "Forbidden — the user can't access the project, or didn't authorize it for this app.";

    // The project update endpoints share these.
    private static final String RETURN_PROJECT =
        "(Optional) Response shape: `full` (default) for the full record, "
      + "or `compact` for just `{\"oid\": …, \"id\": …}`. "
      + "Any other value returns `400 Bad Request`.";

    // The same text as UpdateProjectBody.FOLLOW_RIGHT.
    private static final String FOLLOW_RIGHT =
        "With read access only, `addFollowers` and `removeFollowers` may "
      + "name only yourself: `\"me\"`, or `\"app\"` to add the app on your "
      + "behalf. Adding or removing anyone else returns `403`, even along "
      + "with yourself. `followers` replaces every follower, so it needs "
      + "edit access even as `[\"me\"]`.";

    private static final String UPDATE_RIGHT =
        "Who may change what: `name`, `description`, `archived`, `public`, "
      + "`start` and `due` need a project admin, and an app with the "
      + "*Manage profiles* permission. Followers need edit access to the "
      + "project (not as an external team member), and an app with the "
      + "*Manage tasks* permission, except for following or unfollowing "
      + "yourself. " + FOLLOW_RIGHT + " A body mixing them needs the "
      + "rights of each; otherwise `403`.";

    private static final String UPDATE_403 =
        "Forbidden — the user lacks the role, or the app the permission, a "
      + "given field needs: an admin and *Manage profiles* for `name`, "
      + "`description`, `archived`, `public`, `start` and `due`; edit access "
      + "and *Manage tasks* for `followers`, or for adding or removing "
      + "anyone else, even along with yourself.";

    // The export endpoints share these.
    private static final String EXPORT_TERMS =
        "Available on the Professional plan and above; on a lower plan it "
      + "returns `402` (`code` `469`). Guests, and the visitors of a public "
      + "project, can't export it (`403`).\n\n"
      + "**Rate-limit cost**: `5 + max(1, ceil(tasks / 100))` units, where "
      + "`tasks` counts only the tasks matching `?status=`, not the project "
      + "total — e.g. 6 for 100 tasks, 15 for 1,000. It applies only to an "
      + "export that runs: one rejected with `402` for the plan costs "
      + "1 unit, like any call. See also [Rate Limits](#rate-limits).";

    private static final String EXPORT_STATUS =
        "Task status filter.\n"
      + "Use `active` for active tasks, `completed` for completed tasks, or `all` for all tasks.\n"
      + "Default: `all`.";

    private static final String EXPORT_CSV_NOTES =
        "Returns a CSV string containing the project, tasks, and related data. "
      + "Columns are separated by `,`, or by `;` if the user's locale uses a "
      + "decimal comma (e.g. `de`, `fr`).\n\n"
      + EXPORT_TERMS;

    private static final String EXPORT_JSON_NOTES =
        "Returns a JSON map containing the project, its tasks, and related "
      + "data: users, tags, external teams, sublists, documents, chats, "
      + "insights, dashboards and reminders.\n\n"
      + EXPORT_TERMS;

    private static final String EXPORT_402 =
        "Payment Required — the plan doesn't include export (`code` `469`).";

    private static final String EXPORT_403 =
        "Forbidden — the user can't access the project, or is a guest.";

/* Not supported yet (security concern)
    @POST
    @ApiOperation(
        value = "Create a project.",
        notes = "Creates a new project in an organization.",
        response = Project.class
    )
    public Response createProject(
        @ApiParam(value = "Project to create.", required = true)
        CreateProjectBody data
    ) { return null; }
*/

    // -------- List --------

    @GET
    @Path("/list")
    @ApiOperation(
        value = "Get all authorized projects.",
        notes = "Returns projects that the current user has authorized for this application. "
              + "If the user authorized My Tasks (personal tasks, not in any project) too, "
              + "it is in the list as well.",
        response = Project.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of authorized projects (may be empty).", response = Project.class, responseContainer = "List")
    })
    public Response getProjects(
        @ApiParam(
            value = LIST_ARCHIVED,
            example = "archived=true",
            required = false
        )
        @QueryParam("archived") boolean archived,

        @ApiParam(
            value = LIST_ADD_TASK,
            example = "add-task=true",
            required = false
        )
        @QueryParam("add-task") boolean addTask
    ) { return null; }

    @GET
    @Path("/list/{organizationOid}")
    @ApiOperation(
        value = "Get authorized projects by organization OID.",
        notes = "Returns projects in the specified organization (by OID) that the current user has authorized.",
        response = Project.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of authorized projects (may be empty).", response = Project.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = "Forbidden — the user can't access the organization."),
        @ApiResponse(code = 404, message = "Not Found — organization does not exist.")
    })
    public Response getProjectsByOrganizationOid(
        @ApiParam(value = "Organization OID.", required = true)
        @PathParam("organizationOid") String organizationOid,

        @ApiParam(
            value = LIST_ARCHIVED,
            example = "archived=true",
            required = false
        )
        @QueryParam("archived") boolean archived,

        @ApiParam(
            value = LIST_ADD_TASK,
            example = "add-task=true",
            required = false
        )
        @QueryParam("add-task") boolean addTask
    ) { return null; }

    @GET
    @Path("/list/id/{organizationId}")
    @ApiOperation(
        value = "Get authorized projects by organization ID.",
        notes = "Returns projects in the specified organization (by ID) that the current user has authorized.",
        response = Project.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of authorized projects (may be empty).", response = Project.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = "Forbidden — the user can't access the organization."),
        @ApiResponse(code = 404, message = "Not Found — organization does not exist.")
    })
    public Response getProjectsByOrganizationId(
        @ApiParam(value = "Organization ID.", required = true, example = "my_org")
        @PathParam("organizationId") String organizationId,

        @ApiParam(
            value = LIST_ARCHIVED,
            example = "archived=true",
            required = false
        )
        @QueryParam("archived") boolean archived,

        @ApiParam(
            value = LIST_ADD_TASK,
            example = "add-task=true",
            required = false
        )
        @QueryParam("add-task") boolean addTask
    ) { return null; }

    // -------- Read --------

    @GET
    @Path("/id/{id}")
    @ApiOperation(
        value = "Get a project by ID.",
        notes = "Returns the complete project record for the given ID. "
              + "Use `-` for the user's My Tasks (personal tasks, not in any project).",
        response = ProjectWithPlan.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — project record.", response = ProjectWithPlan.class),
        @ApiResponse(code = 403, message = PROJECT_403),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response getProjectById(
        @ApiParam(value = "Project ID, or `-` for My Tasks.", required = true, example = "my_project")
        @PathParam("id") String id
    ) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get a project by OID.",
        notes = "Returns the complete project record for the given OID. "
              + "Use `-` for the user's My Tasks (personal tasks, not in any project).",
        response = ProjectWithPlan.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — project record.", response = ProjectWithPlan.class),
        @ApiResponse(code = 403, message = PROJECT_403),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response getProjectByOid(
        @ApiParam(value = "Project OID, or `-` for My Tasks.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    // -------- Export (CSV) --------

    @GET
    @Path("/export-csv/id/{id}")
    @ApiOperation(
        value = "Export a project to CSV by ID.",
        notes = EXPORT_CSV_NOTES,
        response = String.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — CSV export of project.", response = String.class),
        @ApiResponse(code = 402, message = EXPORT_402),
        @ApiResponse(code = 403, message = EXPORT_403),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response getProjectCsvById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("id") String id,

        @ApiParam(
            value = EXPORT_STATUS,
            example = "status=active",
            required = false
        )
        @QueryParam("status") String status,

        @ApiParam(
            value = "Whether to merge multiple values of the same header into one column (e.g., all tags in a single column).\n"
                  + "Default: `false`.",
            example = "merge=true",
            required = false
        )
        @QueryParam("merge") boolean merge
    ) { return null; }

    @GET
    @Path("/export-csv/{oid}")
    @ApiOperation(
        value = "Export a project to CSV by OID.",
        notes = EXPORT_CSV_NOTES,
        response = String.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — CSV export of project.", response = String.class),
        @ApiResponse(code = 402, message = EXPORT_402),
        @ApiResponse(code = 403, message = EXPORT_403),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response getProjectCsvByOid(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid,

        @ApiParam(
            value = EXPORT_STATUS,
            example = "status=active",
            required = false
        )
        @QueryParam("status") String status,

        @ApiParam(
            value = "Whether to merge multiple values of the same header into one column (e.g., all tags in a single column).\n"
                  + "Default: `false`.",
            example = "merge=true",
            required = false
        )
        @QueryParam("merge") boolean merge
    ) { return null; }

    // -------- Export (JSON) --------

    @GET
    @Path("/export-json/id/{id}")
    @ApiOperation(
        value = "Export a project to JSON by ID.",
        notes = EXPORT_JSON_NOTES,
        response = ProjectJsonMap.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — JSON export of project.", response = ProjectJsonMap.class),
        @ApiResponse(code = 402, message = EXPORT_402),
        @ApiResponse(code = 403, message = EXPORT_403),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response getProjectJsonById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("id") String id,

        @ApiParam(
            value = EXPORT_STATUS,
            example = "status=active",
            required = false
        )
        @QueryParam("status") String status
    ) { return null; }

    @GET
    @Path("/export-json/{oid}")
    @ApiOperation(
        value = "Export a project to JSON by OID.",
        notes = EXPORT_JSON_NOTES,
        response = ProjectJsonMap.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — JSON export of project.", response = ProjectJsonMap.class),
        @ApiResponse(code = 402, message = EXPORT_402),
        @ApiResponse(code = 403, message = EXPORT_403),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response getProjectJsonByOid(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid,

        @ApiParam(
            value = EXPORT_STATUS,
            example = "status=active",
            required = false
        )
        @QueryParam("status") String status
    ) { return null; }

    // -------- Update --------

    @PUT
    @Path("/id/{id}")
    @ApiOperation(
        value = "Update a project by ID.",
        notes = "Updates an existing project and returns the complete updated record.\n\n"
              + "To archive or unarchive, set `archived: true` or `archived: false` in the body.\n\n"
              + UPDATE_RIGHT,
        response = Project.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated project record.", response = Project.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 402, message = "Payment Required — `public: true` on a plan without public projects (`code` `469`)."),
        @ApiResponse(code = 403, message = UPDATE_403),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response updateProjectById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("id") String id,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateProjectBody data,

        @ApiParam(
            value = RETURN_PROJECT,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/{oid}")
    @ApiOperation(
        value = "Update a project by OID.",
        notes = "Updates an existing project and returns the complete updated record.\n\n"
              + "To archive or unarchive, set `archived: true` or `archived: false` in the body.\n\n"
              + UPDATE_RIGHT,
        response = Project.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated project record.", response = Project.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 402, message = "Payment Required — `public: true` on a plan without public projects (`code` `469`)."),
        @ApiResponse(code = 403, message = UPDATE_403),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response updateProjectByOid(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateProjectBody data,

        @ApiParam(
            value = RETURN_PROJECT,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

/* Not supported yet (security concern)
    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete a project.",
        notes = "Deletes the specified project.\n\n"
            + "> Note: Returns `204 No Content` regardless of whether the project exists."
    )
    @ApiResponses({
        @ApiResponse(
            code = 204,
            message = "No Content"
        )
    })
    public Response deleteProject(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }
*/

    // -------- Custom fields --------

    // The field and approval-category endpoints share these (MCP / agent
    // consumers may see only one endpoint at a time).
    private static final String ADMIN_ONLY =
        "Requires a user who is an admin of the project, and an app "
      + "authorized with the *Manage profiles* permission; otherwise `403`.";

    private static final String ADMIN_403 =
        "Forbidden — the user isn't an admin of the project, or the app lacks the *Manage profiles* permission.";

    // The field name goes in the query (`?name=`, rename's `&new-name=`), so
    // it may contain `/`. Shared by every field endpoint.
    private static final String FIELD_NAME_ENCODING =
        "Percent-encode it, e.g. `Billable%20Amount` for `Billable Amount`; "
      + "a `+` in the name must be `%2B`, since `+` means a space in a query.";

    // The field endpoints share these.
    private static final String RETURN_FIELD =
        "(Optional) Response shape: `full` (default) for the field "
      + "definition, or `compact` for just `{\"name\": …}`. "
      + "Any other value returns `400 Bad Request`.";

    private static final String FIELD_RESPONSE =
        "returns it as in `Project.fields`, plus its `name`";

    private static final String ADD_FIELD_NOTES =
        "Adds a [custom-field definition](#definition-FieldDefinition) to "
      + "the project and " + FIELD_RESPONSE + ".\n\n"
      + ADMIN_ONLY + "\n\n"
      + "A project field's `type` is one of `text`, `number`, `money`, "
      + "`date`, `duration`, `select`, `checkbox`, `user`, `task`, "
      + "`hyperlink`, `email`, `formula` and `file` (`lookup` is for "
      + "insights only). A key that doesn't apply to the `type`, such as "
      + "`options` on a `number` field, returns `400`.\n\n"
      + "Example — a single-select field with three options; each option is "
      + "an object, not a plain string:\n\n"
      + "```json\n"
      + "{\n"
      + "  \"name\": \"Stage\",\n"
      + "  \"type\": \"select\",\n"
      + "  \"options\": [\n"
      + "    {\"name\": \"Todo\"},\n"
      + "    {\"name\": \"Review\", \"color\": \"13\"},\n"
      + "    {\"name\": \"Done\", \"color\": \"42\"}\n"
      + "  ]\n"
      + "}\n"
      + "```\n\n"
      + "A name already in use returns `409`; a field over the plan's "
      + "custom-field limit returns `402`.";

    private static final String UPDATE_FIELD_NOTES =
        "Updates the named custom field's definition and " + FIELD_RESPONSE + ". "
      + "`type` is immutable: if supplied, it must match the current type. "
      + "Omitted keys, flags such as `hidden` and `locked` included, keep "
      + "their current values. A key that doesn't apply to the field's type "
      + "returns `400`. `options` and `conditionFormat` replace the whole "
      + "list, and `[]` removes every entry.\n\n"
      + "To rename a `select` option, give its current name in `from`; tasks "
      + "set to it get the new name. Example — rename `High` to `Critical` "
      + "and keep `Low`:\n\n"
      + "```json\n"
      + "{\"options\": [{\"name\": \"Low\"}, {\"name\": \"Critical\", \"from\": \"High\"}]}\n"
      + "```\n\n"
      + "Without `from`, nothing is treated as a rename: a current option "
      + "that no entry names, in `name` or `from`, is removed, and tasks set "
      + "to it lose that value. An entry without `color` keeps the current "
      + "color of the option it matches; a new option gets one assigned. A "
      + "`from` that names no current option, two entries matching the same "
      + "option (by `name` or `from`), a duplicate `name`, or a `name` over "
      + "1,280 characters returns `400`.\n\n"
      + ADMIN_ONLY + "\n\n"
      + "Example — hide the field and lock its values: "
      + "`{\"hidden\": true, \"locked\": true}`.\n\n"
      + "To rename a field, use `PUT /project/rename-field/id/{projectId}?name={fieldName}&new-name={newName}`; "
      + "to reorder, `PUT /project/move-field/id/{projectId}?name={fieldName}`.\n\n"
      + "A missing field returns `404`.";

    private static final String REMOVE_FIELD_NOTES =
        "Removes the named custom field from the project, and its value from "
      + "every task (for a `file` field, the files too). This can't be undone.\n\n"
      + ADMIN_ONLY + "\n\n"
      + "Returns `204 No Content`, also when the field doesn't exist.";

    private static final String RENAME_FIELD_NOTES =
        "Renames a custom field and " + FIELD_RESPONSE + " (the new name). "
      + "The definition is kept, and task values move to the new name.\n\n"
      + ADMIN_ONLY + "\n\n"
      + "A missing field returns `404`; a `new-name` already in use, `409`; "
      + "an invalid `new-name`, `400` — it must be non-empty, without `}`, "
      + "`\"` or `\\`, and at most 1,280 characters.";

    private static final String MOVE_FIELD_NOTES =
        "Moves a custom field to just before the `before` field, or to the "
      + "end if `before` is omitted, and " + FIELD_RESPONSE + ".\n\n"
      + ADMIN_ONLY + "\n\n"
      + "A missing field, or a missing `before` field, returns `404`.";

    @POST
    @Path("/add-field/{oid}")
    @ApiOperation(
        value = "Add a custom-field definition to a project.",
        notes = ADD_FIELD_NOTES,
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created custom-field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 402, message = "Payment Required — plan's custom-field limit is reached (`code` `469`)."),
        @ApiResponse(code = 403, message = ADMIN_403),
        @ApiResponse(code = 404, message = "Not Found — project does not exist."),
        @ApiResponse(code = 409, message = "Conflict — a field with this name already exists.")
    })
    public Response addProjectField(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Field definition to add.", required = true)
        AddFieldBody data,

        @ApiParam(
            value = RETURN_FIELD,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @POST
    @Path("/add-field/id/{id}")
    @ApiOperation(
        value = "Add a custom-field definition to a project by ID.",
        notes = ADD_FIELD_NOTES,
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created custom-field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 402, message = "Payment Required — plan's custom-field limit is reached (`code` `469`)."),
        @ApiResponse(code = 403, message = ADMIN_403),
        @ApiResponse(code = 404, message = "Not Found — project does not exist."),
        @ApiResponse(code = 409, message = "Conflict — a field with this name already exists.")
    })
    public Response addProjectFieldById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("id") String id,
        @ApiParam(value = "Field definition to add.", required = true)
        AddFieldBody data,

        @ApiParam(
            value = RETURN_FIELD,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/update-field/{oid}")
    @ApiOperation(
        value = "Update a custom-field definition on a project.",
        notes = UPDATE_FIELD_NOTES,
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated custom-field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = ADMIN_403),
        @ApiResponse(code = 404, message = "Not Found — project or field does not exist.")
    })
    public Response updateProjectField(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Name of the field to update. " + FIELD_NAME_ENCODING, required = true, example = "Stage")
        @QueryParam("name") String name,
        @ApiParam(value = "New field content.", required = true)
        UpdateFieldBody data,

        @ApiParam(
            value = RETURN_FIELD,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/update-field/id/{id}")
    @ApiOperation(
        value = "Update a custom-field definition on a project by ID.",
        notes = UPDATE_FIELD_NOTES,
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated custom-field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = ADMIN_403),
        @ApiResponse(code = 404, message = "Not Found — project or field does not exist.")
    })
    public Response updateProjectFieldById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("id") String id,
        @ApiParam(value = "Name of the field to update. " + FIELD_NAME_ENCODING, required = true, example = "Stage")
        @QueryParam("name") String name,
        @ApiParam(value = "New field content.", required = true)
        UpdateFieldBody data,

        @ApiParam(
            value = RETURN_FIELD,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @DELETE
    @Path("/remove-field/{oid}")
    @ApiOperation(
        value = "Remove a custom-field definition from a project.",
        notes = REMOVE_FIELD_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content — field removed (or already absent)."),
        @ApiResponse(code = 403, message = ADMIN_403)
    })
    public Response removeProjectField(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Name of the field to remove. " + FIELD_NAME_ENCODING, required = true, example = "Stage")
        @QueryParam("name") String name
    ) { return null; }

    @DELETE
    @Path("/remove-field/id/{id}")
    @ApiOperation(
        value = "Remove a custom-field definition from a project by ID.",
        notes = REMOVE_FIELD_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content — field removed (or already absent)."),
        @ApiResponse(code = 403, message = ADMIN_403)
    })
    public Response removeProjectFieldById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("id") String id,
        @ApiParam(value = "Name of the field to remove. " + FIELD_NAME_ENCODING, required = true, example = "Stage")
        @QueryParam("name") String name
    ) { return null; }

    @PUT
    @Path("/rename-field/{oid}")
    @ApiOperation(
        value = "Rename a custom-field definition on a project.",
        notes = RENAME_FIELD_NOTES,
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — renamed custom-field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — `name` missing, or `new-name` missing or invalid."),
        @ApiResponse(code = 403, message = ADMIN_403),
        @ApiResponse(code = 404, message = "Not Found — project or field does not exist."),
        @ApiResponse(code = 409, message = "Conflict — `new-name` is already in use.")
    })
    public Response renameProjectField(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Current field name. " + FIELD_NAME_ENCODING, required = true, example = "Stage")
        @QueryParam("name") String name,
        @ApiParam(value = "New field name. " + FIELD_NAME_ENCODING, required = true, example = "Phase")
        @QueryParam("new-name") String newName,

        @ApiParam(
            value = RETURN_FIELD,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/rename-field/id/{id}")
    @ApiOperation(
        value = "Rename a custom-field definition on a project by ID.",
        notes = RENAME_FIELD_NOTES,
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — renamed custom-field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — `name` missing, or `new-name` missing or invalid."),
        @ApiResponse(code = 403, message = ADMIN_403),
        @ApiResponse(code = 404, message = "Not Found — project or field does not exist."),
        @ApiResponse(code = 409, message = "Conflict — `new-name` is already in use.")
    })
    public Response renameProjectFieldById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("id") String id,
        @ApiParam(value = "Current field name. " + FIELD_NAME_ENCODING, required = true, example = "Stage")
        @QueryParam("name") String name,
        @ApiParam(value = "New field name. " + FIELD_NAME_ENCODING, required = true, example = "Phase")
        @QueryParam("new-name") String newName,

        @ApiParam(
            value = RETURN_FIELD,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/move-field/{oid}")
    @ApiOperation(
        value = "Reorder a custom-field definition on a project.",
        notes = MOVE_FIELD_NOTES,
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — moved custom-field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — `name` missing."),
        @ApiResponse(code = 403, message = ADMIN_403),
        @ApiResponse(code = 404, message = "Not Found — project, field or `before` field does not exist.")
    })
    public Response moveProjectField(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Name of the field to move. " + FIELD_NAME_ENCODING, required = true, example = "Stage")
        @QueryParam("name") String name,
        @ApiParam(
            value = "(Optional) Name of the field to insert before. "
                  + "If omitted, the field is moved to the end. " + FIELD_NAME_ENCODING,
            example = "Estimate",
            required = false
        )
        @QueryParam("before") String before,

        @ApiParam(
            value = RETURN_FIELD,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/move-field/id/{id}")
    @ApiOperation(
        value = "Reorder a custom-field definition on a project by ID.",
        notes = MOVE_FIELD_NOTES,
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — moved custom-field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — `name` missing."),
        @ApiResponse(code = 403, message = ADMIN_403),
        @ApiResponse(code = 404, message = "Not Found — project, field or `before` field does not exist.")
    })
    public Response moveProjectFieldById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("id") String id,
        @ApiParam(value = "Name of the field to move. " + FIELD_NAME_ENCODING, required = true, example = "Stage")
        @QueryParam("name") String name,
        @ApiParam(
            value = "(Optional) Name of the field to insert before. "
                  + "If omitted, the field is moved to the end. " + FIELD_NAME_ENCODING,
            example = "Estimate",
            required = false
        )
        @QueryParam("before") String before,

        @ApiParam(
            value = RETURN_FIELD,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    // -------- Approval categories --------

    // The approval-category endpoints share these.
    private static final String RETURN_APPV_CAT =
        "(Optional) Response shape: `full` (default) for the category, or "
      + "`compact` for just `{\"id\": …}`. "
      + "Any other value returns `400 Bad Request`.";

    private static final String APPV_CAT_USERS =
        "`claimers` and `approvers` are lists of user OIDs; omit them or pass "
      + "`null` for anyone, `[]` for the project's admins only.";

    private static final String ADD_APPV_CAT_NOTES =
        "Adds an [approval category](#definition-AppvCat) to the project and "
      + "returns it. A project always has the default category (`id` `\"\"`); "
      + "add more to give each its own claimers and approvers.\n\n"
      + ADMIN_ONLY + "\n\n"
      + "Example:\n\n"
      + "```json\n"
      + "{\n"
      + "  \"id\": \"legal\",\n"
      + "  \"name\": \"Legal review\",\n"
      + "  \"approvers\": [\"iDsPd.QP_qM.hN.Trymukn8b\"]\n"
      + "}\n"
      + "```\n\n"
      + APPV_CAT_USERS + "\n\n"
      + "An `id` already in use in the project returns `409`; a plan "
      + "without multiple approval categories, `402`.";

    private static final String UPDATE_APPV_CAT_NOTES =
        "Partial update: any of `name`, `claimers`, or `approvers` "
      + "may be supplied; omitted keys preserve their current "
      + "values. At least one key must be present, or it returns `400`. "
      + APPV_CAT_USERS + "\n\n"
      + ADMIN_ONLY + "\n\n"
      + "Response body is the updated category, or an empty "
      + "object if the category no longer exists (e.g. removed "
      + "concurrently). A plan without approvals returns `402`.";

    private static final String REMOVE_APPV_CAT_NOTES =
        "Removes the category. Any task whose approval "
      + "references this category has its approval cleared.\n\n"
      + ADMIN_ONLY + "\n\n"
      + "Returns `204 No Content`, also when the category doesn't exist.";

    @POST
    @Path("/add-appv-cat/{oid}")
    @ApiOperation(
        value = "Add an approval category to a project.",
        notes = ADD_APPV_CAT_NOTES,
        response = AppvCat.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created approval category.", response = AppvCat.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 402, message = "Payment Required — the plan doesn't include multiple approval categories."),
        @ApiResponse(code = 403, message = ADMIN_403),
        @ApiResponse(code = 404, message = "Not Found — project does not exist."),
        @ApiResponse(code = 409, message = "Conflict — a category with this `id` already exists.")
    })
    public Response addProjectAppvCat(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Category to add.", required = true)
        AddAppvCatBody data,

        @ApiParam(
            value = RETURN_APPV_CAT,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @POST
    @Path("/add-appv-cat/id/{id}")
    @ApiOperation(
        value = "Add an approval category to a project by ID.",
        notes = ADD_APPV_CAT_NOTES,
        response = AppvCat.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created approval category.", response = AppvCat.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 402, message = "Payment Required — the plan doesn't include multiple approval categories."),
        @ApiResponse(code = 403, message = ADMIN_403),
        @ApiResponse(code = 404, message = "Not Found — project does not exist."),
        @ApiResponse(code = 409, message = "Conflict — a category with this `id` already exists.")
    })
    public Response addProjectAppvCatById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("id") String id,
        @ApiParam(value = "Category to add.", required = true)
        AddAppvCatBody data,

        @ApiParam(
            value = RETURN_APPV_CAT,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/update-appv-cat/{oid}/{catId}")
    @ApiOperation(
        value = "Update an approval category on a project.",
        notes = UPDATE_APPV_CAT_NOTES,
        response = AppvCat.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated approval category.", response = AppvCat.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 402, message = "Payment Required — the plan doesn't include approvals."),
        @ApiResponse(code = 403, message = ADMIN_403),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response updateProjectAppvCat(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Category id.", required = true, example = "legal")
        @PathParam("catId") String catId,
        @ApiParam(value = "Changes to apply.", required = true)
        UpdateAppvCatBody data,

        @ApiParam(
            value = RETURN_APPV_CAT,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/update-appv-cat/id/{id}/{catId}")
    @ApiOperation(
        value = "Update an approval category on a project by ID.",
        notes = UPDATE_APPV_CAT_NOTES,
        response = AppvCat.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated approval category.", response = AppvCat.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 402, message = "Payment Required — the plan doesn't include approvals."),
        @ApiResponse(code = 403, message = ADMIN_403),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response updateProjectAppvCatById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("id") String id,
        @ApiParam(value = "Category id.", required = true, example = "legal")
        @PathParam("catId") String catId,
        @ApiParam(value = "Changes to apply.", required = true)
        UpdateAppvCatBody data,

        @ApiParam(
            value = RETURN_APPV_CAT,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @DELETE
    @Path("/remove-appv-cat/{oid}/{catId}")
    @ApiOperation(
        value = "Remove an approval category from a project.",
        notes = REMOVE_APPV_CAT_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content — category removed (or already absent)."),
        @ApiResponse(code = 403, message = ADMIN_403)
    })
    public Response removeProjectAppvCat(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Category id.", required = true, example = "legal")
        @PathParam("catId") String catId
    ) { return null; }

    @DELETE
    @Path("/remove-appv-cat/id/{id}/{catId}")
    @ApiOperation(
        value = "Remove an approval category from a project by ID.",
        notes = REMOVE_APPV_CAT_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content — category removed (or already absent)."),
        @ApiResponse(code = 403, message = ADMIN_403)
    })
    public Response removeProjectAppvCatById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("id") String id,
        @ApiParam(value = "Category id.", required = true, example = "legal")
        @PathParam("catId") String catId
    ) { return null; }
}
