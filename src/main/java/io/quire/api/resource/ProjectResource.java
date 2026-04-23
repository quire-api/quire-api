package io.quire.api.resource;

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
    public Response getProjectsByOrganizationId(
        @ApiParam(value = "Organization ID.", required = true)
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
    public Response getProjectById(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("id") String id
    ) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get a project by OID.",
        notes = "Returns the complete project record for the given OID.",
        response = ProjectWithPlan.class
    )
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
              + "Note: The number of allowed invocations may be more restricted.",
        response = String.class
    )
    public Response getProjectCsvById(
        @ApiParam(value = "Project ID.", required = true)
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
              + "Note: The number of allowed invocations may be more restricted.",
        response = String.class
    )
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
              + "Note: The number of allowed invocations may be more restricted.",
        response = ProjectJsonMap.class
    )
    public Response getProjectJsonById(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("id") String id
    ) { return null; }

    @GET
    @Path("/export-json/{oid}")
    @ApiOperation(
        value = "Export a project to JSON by OID.",
        notes = "Returns a JSON map containing the project, all tasks, and related data.\n"
              + "Available on the Professional plan and above.\n"
              + "Note: The number of allowed invocations may be more restricted.",
        response = ProjectJsonMap.class
    )
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
    public Response updateProjectById(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("id") String id,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateProjectBody data
    ) { return null; }

    @PUT
    @Path("/{oid}")
    @ApiOperation(
        value = "Update a project by OID.",
        notes = "Updates an existing project and returns the complete updated record.",
        response = Project.class
    )
    public Response updateProjectByOid(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateProjectBody data
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
              + "Requires the `Admin` scope to invoke.\n\n"
              + "Returns `400 Bad Request` if the body is invalid; "
              + "`403 Forbidden` if the caller lacks permission; "
              + "`429 Too Many Requests` if the plan's custom-field limit is reached.",
        response = FieldDefinitionWithName.class
    )
    public Response addProjectField(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Field definition to add.", required = true)
        AddFieldBody data
    ) { return null; }

    @POST
    @Path("/add-field/id/{id}")
    @ApiOperation(
        value = "Add a custom-field definition to a project by ID.",
        notes = "Same as `/project/add-field/{oid}`, but identifies the project by ID.",
        response = FieldDefinitionWithName.class
    )
    public Response addProjectFieldById(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("id") String id,
        @ApiParam(value = "Field definition to add.", required = true)
        AddFieldBody data
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
              + "To rename a field, use `/rename-field/{oid}/{name}/{newName}`; "
              + "to reorder, use `/move-field/{oid}/{name}`.\n\n"
              + "Response body is a `FieldDefinition` with an extra `name` key "
              + "(equal to the field's name), or an empty object if the field does not exist.",
        response = FieldDefinitionWithName.class
    )
    public Response updateProjectField(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Name of the field to update.", required = true)
        @PathParam("fieldName") String fieldName,
        @ApiParam(value = "New field content.", required = true)
        UpdateFieldBody data
    ) { return null; }

    @PUT
    @Path("/update-field/id/{id}/{fieldName}")
    @ApiOperation(
        value = "Update a custom-field definition on a project by ID.",
        notes = "Same as `/project/update-field/{oid}/{fieldName}`, but identifies the project by ID.",
        response = FieldDefinitionWithName.class
    )
    public Response updateProjectFieldById(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("id") String id,
        @ApiParam(value = "Name of the field to update.", required = true)
        @PathParam("fieldName") String fieldName,
        @ApiParam(value = "New field content.", required = true)
        UpdateFieldBody data
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
        @ApiResponse(code = 204, message = "No Content")
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
        notes = "Same as `/project/remove-field/{oid}/{fieldName}`, but identifies the project by ID."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content")
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
    public Response renameProjectField(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Current field name.", required = true)
        @PathParam("fieldName") String fieldName,
        @ApiParam(value = "New field name.", required = true)
        @PathParam("newName") String newName
    ) { return null; }

    @PUT
    @Path("/rename-field/id/{id}/{fieldName}/{newName}")
    @ApiOperation(
        value = "Rename a custom-field definition on a project by ID.",
        notes = "Same as `/project/rename-field/{oid}/{fieldName}/{newName}`, "
              + "but identifies the project by ID.",
        response = FieldDefinitionWithName.class
    )
    public Response renameProjectFieldById(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("id") String id,
        @ApiParam(value = "Current field name.", required = true)
        @PathParam("fieldName") String fieldName,
        @ApiParam(value = "New field name.", required = true)
        @PathParam("newName") String newName
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
    public Response moveProjectField(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Name of the field to move.", required = true)
        @PathParam("fieldName") String fieldName,
        @ApiParam(
            value = "(Optional) Name of the field to insert before. "
                  + "If omitted, the field is moved to the end.",
            example = "before=Priority",
            required = false
        )
        @QueryParam("before") String before
    ) { return null; }

    @PUT
    @Path("/move-field/id/{id}/{fieldName}")
    @ApiOperation(
        value = "Reorder a custom-field definition on a project by ID.",
        notes = "Same as `/project/move-field/{oid}/{fieldName}`, but identifies the project by ID.",
        response = FieldDefinitionWithName.class
    )
    public Response moveProjectFieldById(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("id") String id,
        @ApiParam(value = "Name of the field to move.", required = true)
        @PathParam("fieldName") String fieldName,
        @ApiParam(
            value = "(Optional) Name of the field to insert before. "
                  + "If omitted, the field is moved to the end.",
            example = "before=Priority",
            required = false
        )
        @QueryParam("before") String before
    ) { return null; }
}
