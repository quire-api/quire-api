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
        notes = "Returns projects that the current user has authorized for this application.",
        response = Project.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of authorized projects (may be empty).", response = Project.class, responseContainer = "List")
    })
    public Response getProjects(
        @ApiParam(
            value = "Whether to include archived projects.\n"
                  + "By default, archived projects are excluded.\n"
                  + "If the parameter is present without a value, `true` is assumed.",
            example = "archived=true",
            required = false
        )
        @QueryParam("archived") boolean archived,

        @ApiParam(
            value = "Whether to return only projects to which you can add tasks. Default: `false`.\n"
                  + "If the parameter is present without a value, `true` is assumed.",
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
        @ApiResponse(code = 404, message = "Not Found — organization does not exist.")
    })
    public Response getProjectsByOrganizationOid(
        @ApiParam(value = "Organization OID.", required = true)
        @PathParam("organizationOid") String organizationOid,

        @ApiParam(
            value = "Whether to include archived projects.\n"
                  + "By default, archived projects are excluded.\n"
                  + "If the parameter is present without a value, `true` is assumed.",
            example = "archived=true",
            required = false
        )
        @QueryParam("archived") boolean archived,

        @ApiParam(
            value = "Whether to return only projects to which you can add tasks. Default: `false`.\n"
                  + "If the parameter is present without a value, `true` is assumed.",
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
        @ApiResponse(code = 404, message = "Not Found — organization does not exist.")
    })
    public Response getProjectsByOrganizationId(
        @ApiParam(value = "Organization ID.", required = true, example = "my_org")
        @PathParam("organizationId") String organizationId,

        @ApiParam(
            value = "Whether to include archived projects.\n"
                  + "By default, archived projects are excluded.\n"
                  + "If the parameter is present without a value, `true` is assumed.",
            example = "archived=true",
            required = false
        )
        @QueryParam("archived") boolean archived,

        @ApiParam(
            value = "Whether to return only projects to which you can add tasks. Default: `false`.\n"
                  + "If the parameter is present without a value, `true` is assumed.",
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
        notes = "Returns the complete project record for the given ID.",
        response = ProjectWithPlan.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — project record.", response = ProjectWithPlan.class),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response getProjectById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("id") String id
    ) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get a project by OID.",
        notes = "Returns the complete project record for the given OID.",
        response = ProjectWithPlan.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — project record.", response = ProjectWithPlan.class),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response getProjectByOid(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    // -------- Export (CSV) --------

    @GET
    @Path("/export-csv/id/{id}")
    @ApiOperation(
        value = "Export a project to CSV by ID.",
        notes = "Returns a CSV string containing the project, tasks, and related data.\n"
              + "Available on the Professional plan and above.\n"
              + "**Rate-limit cost** (#24558): proportional to the project's task count — "
              + "`max(1, ceil(tasks / 100))` units. So a 50-task project costs 1, "
              + "a 1,000-task project costs 10. (Plus a small fixed export overhead.)",
        response = String.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — CSV export of project.", response = String.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission or plan does not include CSV export."),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response getProjectCsvById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("id") String id,

        @ApiParam(
            value = "Task status filter.\n"
                  + "Use `active` for active tasks, `completed` for completed tasks, or `all` for all tasks.\n"
                  + "Default: `all`.",
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
        notes = "Returns a CSV string containing the project, tasks, and related data.\n"
              + "Available on the Professional plan and above.\n"
              + "**Rate-limit cost** (#24558): proportional to the project's task count — "
              + "`max(1, ceil(tasks / 100))` units. So a 50-task project costs 1, "
              + "a 1,000-task project costs 10. (Plus a small fixed export overhead.)",
        response = String.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — CSV export of project.", response = String.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission or plan does not include CSV export."),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response getProjectCsvByOid(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid,

        @ApiParam(
            value = "Task status filter.\n"
                  + "Use `active` for active tasks, `completed` for completed tasks, or `all` for all tasks.\n"
                  + "Default: `all`.",
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
        notes = "Returns a JSON map containing the project, all tasks, and related data.\n"
              + "Available on the Professional plan and above.\n"
              + "**Rate-limit cost** (#24558): proportional to the project's task count — "
              + "`max(1, ceil(tasks / 100))` units. So a 50-task project costs 1, "
              + "a 1,000-task project costs 10. (Plus a small fixed export overhead.)",
        response = ProjectJsonMap.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — JSON export of project.", response = ProjectJsonMap.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission or plan does not include JSON export."),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response getProjectJsonById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("id") String id
    ) { return null; }

    @GET
    @Path("/export-json/{oid}")
    @ApiOperation(
        value = "Export a project to JSON by OID.",
        notes = "Returns a JSON map containing the project, all tasks, and related data.\n"
              + "Available on the Professional plan and above.\n"
              + "**Rate-limit cost** (#24558): proportional to the project's task count — "
              + "`max(1, ceil(tasks / 100))` units. So a 50-task project costs 1, "
              + "a 1,000-task project costs 10. (Plus a small fixed export overhead.)",
        response = ProjectJsonMap.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — JSON export of project.", response = ProjectJsonMap.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission or plan does not include JSON export."),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response getProjectJsonByOid(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    // -------- Update --------

    @PUT
    @Path("/id/{id}")
    @ApiOperation(
        value = "Update a project by ID.",
        notes = "Updates an existing project and returns the complete updated record.",
        response = Project.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated project record.", response = Project.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (Admin scope may be required)."),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response updateProjectById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("id") String id,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateProjectBody data,

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
    @Path("/{oid}")
    @ApiOperation(
        value = "Update a project by OID.",
        notes = "Updates an existing project and returns the complete updated record.",
        response = Project.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated project record.", response = Project.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (Admin scope may be required)."),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response updateProjectByOid(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateProjectBody data,

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
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

    @POST
    @Path("/add-field/{oid}")
    @ApiOperation(
        value = "Add a custom-field definition to a project.",
        notes = "Adds a new [custom-field definition](#definition-FieldDefinition) "
              + "to the project (by OID). The response is the created field "
              + "in public form (same shape as entries in `Project.fields`, "
              + "with an extra `name` key).\n\n"
              + "Requires the `Admin` scope to invoke.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created custom-field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (Admin scope may be required)."),
        @ApiResponse(code = 404, message = "Not Found — project does not exist."),
        @ApiResponse(code = 429, message = "Too Many Requests — plan's custom-field limit is reached.")
    })
    public Response addProjectField(
        @ApiParam(value = "Project OID.", required = true)
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

    @POST
    @Path("/add-field/id/{id}")
    @ApiOperation(
        value = "Add a custom-field definition to a project by ID.",
        notes = "Adds a custom-field definition to the project, identifying "
              + "the project by its ID. See also `/project/add-field/{oid}` for the OID form.\n\n"
              + "Example — a single-select \"Priority\" field with three options:\n\n"
              + "```json\n"
              + "{\n"
              + "  \"name\": \"Priority\",\n"
              + "  \"type\": \"select\",\n"
              + "  \"options\": [\"Low\", \"Medium\", \"High\"]\n"
              + "}\n"
              + "```\n\n"
              + "> See [FieldDefinition](#definition-FieldDefinition) for the full "
              + "list of supported `type` values (`text`, `number`, `money`, "
              + "`select`, `checkbox`, `date`, `duration`, `email`, `hyperlink`, "
              + "`user`, `task`, `formula`, `lookup`) and the type-specific keys "
              + "each one accepts.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created custom-field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (Admin scope may be required)."),
        @ApiResponse(code = 404, message = "Not Found — project does not exist."),
        @ApiResponse(code = 429, message = "Too Many Requests — plan's custom-field limit is reached.")
    })
    public Response addProjectFieldById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("id") String id,
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
        value = "Update a custom-field definition on a project.",
        notes = "Updates the content of an existing custom field. "
              + "`type` is immutable — if supplied, it must match the existing "
              + "type. Keys that are omitted leave their current values intact "
              + "(including individual flag bits — flags are merged, not replaced).\n\n"
              + "Requires the `Admin` scope to invoke.\n\n"
              + "To rename a field, use `/project/rename-field/id/{projectId}/{fieldName}/{newName}`; "
              + "to reorder, use `/project/move-field/id/{projectId}/{fieldName}`. "
              + "(The `/{oid}/...` URL form is also accepted.)\n\n"
              + "Response body is a `FieldDefinition` with an extra `name` key "
              + "(equal to the field's name), or an empty object if the field does not exist.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated custom-field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (Admin scope may be required)."),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response updateProjectField(
        @ApiParam(value = "Project OID.", required = true)
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

    @PUT
    @Path("/update-field/id/{id}/{fieldName}")
    @ApiOperation(
        value = "Update a custom-field definition on a project by ID.",
        notes = "Updates an existing custom-field definition on the project, "
              + "identifying the project by its ID. `type` is immutable; omitted "
              + "keys preserve their current values (flags are merged). See also "
              + "`/project/update-field/{oid}/{fieldName}` for the OID form.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated custom-field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (Admin scope may be required)."),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response updateProjectFieldById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("id") String id,
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
        value = "Remove a custom-field definition from a project.",
        notes = "Removes the named custom field from the project.\n\n"
              + "Requires the `Admin` scope to invoke.\n\n"
              + "> Note: Returns `204 No Content` regardless of whether the field exists."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content — field removed (or already absent).")
    })
    public Response removeProjectField(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Name of the field to remove.", required = true)
        @PathParam("fieldName") String fieldName
    ) { return null; }

    @DELETE
    @Path("/remove-field/id/{id}/{fieldName}")
    @ApiOperation(
        value = "Remove a custom-field definition from a project by ID.",
        notes = "Removes the named custom field from the project, identifying "
              + "the project by its ID. Returns `204 No Content` regardless of "
              + "whether the field exists. See also `/project/remove-field/{oid}/{fieldName}` "
              + "for the OID form."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content — field removed (or already absent).")
    })
    public Response removeProjectFieldById(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("id") String id,
        @ApiParam(value = "Name of the field to remove.", required = true)
        @PathParam("fieldName") String fieldName
    ) { return null; }

    @PUT
    @Path("/rename-field/{oid}/{fieldName}/{newName}")
    @ApiOperation(
        value = "Rename a custom-field definition on a project.",
        notes = "Renames the field in place and returns the renamed field. "
              + "The field's content is preserved; any task values under the "
              + "old name are migrated to the new name.\n\n"
              + "Requires the `Admin` scope to invoke.\n\n"
              + "Response body is a `FieldDefinition` with an extra `name` key "
              + "(equal to the new name), or an empty object if the source "
              + "field is missing or the target name is already in use.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — renamed custom-field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (Admin scope may be required)."),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response renameProjectField(
        @ApiParam(value = "Project OID.", required = true)
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
    @Path("/rename-field/id/{id}/{fieldName}/{newName}")
    @ApiOperation(
        value = "Rename a custom-field definition on a project by ID.",
        notes = "Renames a custom-field definition on the project, identifying "
              + "the project by its ID. The field's content is preserved; task "
              + "values under the old name are migrated to the new name. See also "
              + "`/project/rename-field/{oid}/{fieldName}/{newName}` for the OID form.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — renamed custom-field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (Admin scope may be required)."),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response renameProjectFieldById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("id") String id,
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
        value = "Reorder a custom-field definition on a project.",
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
        @ApiResponse(code = 200, message = "OK — moved custom-field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (Admin scope may be required)."),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response moveProjectField(
        @ApiParam(value = "Project OID.", required = true)
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

    @PUT
    @Path("/move-field/id/{id}/{fieldName}")
    @ApiOperation(
        value = "Reorder a custom-field definition on a project by ID.",
        notes = "Reorders a custom-field definition on the project, identifying "
              + "the project by its ID. Pass `?before={otherName}` to place the "
              + "field before another; omit to move it to the end. See also "
              + "`/project/move-field/{oid}/{fieldName}` for the OID form.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — moved custom-field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (Admin scope may be required)."),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response moveProjectFieldById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("id") String id,
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

    // -------- Approval categories --------

    @POST
    @Path("/add-appv-cat/{oid}")
    @ApiOperation(
        value = "Add an approval category to a project.",
        notes = "Adds a new [approval category](#definition-AppvCat) to "
              + "the project. Projects carry an implicit default category "
              + "(id `\"\"`) that is always available; use this endpoint "
              + "to define additional categories with their own claimer / "
              + "approver rosters.\n\n"
              + "Requires the `Admin` scope to invoke.",
        response = AppvCat.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created approval category.", response = AppvCat.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (Admin scope may be required)."),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response addProjectAppvCat(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Category to add.", required = true)
        AddAppvCatBody data,

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
    @Path("/add-appv-cat/id/{id}")
    @ApiOperation(
        value = "Add an approval category to a project by ID.",
        notes = "Adds an approval category to the project, identifying the "
              + "project by its ID. See also `/project/add-appv-cat/{oid}` for the OID form.\n\n"
              + "Example:\n\n"
              + "```json\n"
              + "{\n"
              + "  \"name\": \"Legal review\",\n"
              + "  \"claimers\": [\"alice@example.com\"],\n"
              + "  \"approvers\": [\"bob@example.com\"]\n"
              + "}\n"
              + "```\n\n"
              + "> Each entry in `claimers` / `approvers` is a user OID, ID, or "
              + "email — same dispatch as the User custom-field type.",
        response = AppvCat.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created approval category.", response = AppvCat.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (Admin scope may be required)."),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response addProjectAppvCatById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("id") String id,
        @ApiParam(value = "Category to add.", required = true)
        AddAppvCatBody data,

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
    @Path("/update-appv-cat/{oid}/{catId}")
    @ApiOperation(
        value = "Update an approval category on a project.",
        notes = "Partial update: any of `name`, `claimers`, or `approvers` "
              + "may be supplied; omitted keys preserve their current "
              + "values. At least one key must be present.\n\n"
              + "Requires the `Admin` scope to invoke.\n\n"
              + "Response body is the updated category, or an empty "
              + "object if the category no longer exists (e.g. removed "
              + "concurrently).",
        response = AppvCat.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated approval category.", response = AppvCat.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (Admin scope may be required)."),
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
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/update-appv-cat/id/{id}/{catId}")
    @ApiOperation(
        value = "Update an approval category on a project by ID.",
        notes = "Updates an approval category on the project, identifying the "
              + "project by its ID. Partial update — omitted keys preserve their "
              + "current values; at least one of `name`, `claimers`, or `approvers` "
              + "must be present. See also `/project/update-appv-cat/{oid}/{catId}` "
              + "for the OID form.",
        response = AppvCat.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated approval category.", response = AppvCat.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (Admin scope may be required)."),
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
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @DELETE
    @Path("/remove-appv-cat/{oid}/{catId}")
    @ApiOperation(
        value = "Remove an approval category from a project.",
        notes = "Removes the named category. Any task whose approval "
              + "references this category has its approval cleared.\n\n"
              + "Requires the `Admin` scope to invoke.\n\n"
              + "> Note: Returns `204 No Content` regardless of whether "
              + "the category exists."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content — category removed (or already absent).")
    })
    public Response removeProjectAppvCat(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Category id.", required = true)
        @PathParam("catId") String catId
    ) { return null; }

    @DELETE
    @Path("/remove-appv-cat/id/{id}/{catId}")
    @ApiOperation(
        value = "Remove an approval category from a project by ID.",
        notes = "Removes an approval category from the project, identifying the "
              + "project by its ID. Any task whose approval references this "
              + "category has its approval cleared. Returns `204 No Content` "
              + "regardless of whether the category exists. See also "
              + "`/project/remove-appv-cat/{oid}/{catId}` for the OID form."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content — category removed (or already absent).")
    })
    public Response removeProjectAppvCatById(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("id") String id,
        @ApiParam(value = "Category id.", required = true)
        @PathParam("catId") String catId
    ) { return null; }
}
