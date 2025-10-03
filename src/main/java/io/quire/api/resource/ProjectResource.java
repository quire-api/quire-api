package io.quire.api.resource;

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
        notes = "Deletes the specified project."
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ok",
            examples = @Example({
                @ExampleProperty(mediaType = "application/json", value = "{'success': true}")
            }))
    })
    public Response deleteProject(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }
*/
}
