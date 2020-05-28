package io.quire.api.resource;

import io.quire.api.model.project.Project;
import io.quire.api.model.project.UpdateProjectBody;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/project")
@Api(value = "project", description =
    "A project represents a prioritized list of tasks in Quire. " +
    "It exists in a single organization and is accessible to a subset of " +
    "users in that organization, depending on its permissions.")
@Produces({"application/json"})
public class ProjectResource {
/* Not supported yet (security concern)
    @POST
    @ApiOperation(value = "Create a project.",
        notes = "Creates a new project in an organization.",
        response = Project.class)
    public Response createProject(
        @ApiParam(value = "Project to create", required = true)
        CreateProjectBody data) { return null; }
*/
    @GET
    @Path("/list")
    @ApiOperation(value = "Get all granted projects.",
        notes = "Returns the project records for all projects "
        + "that the current user can grant to this application.",
        response = Project.class,
        responseContainer = "List")
    public Response getProjects(
        @ApiParam(value = "Whether to return archived projects.\n"
        + "By default, archived projects won't be returned.\n"
        + "If the parameter is specified without value, `true` is assumed.",
        example = "archived=true", required = false)
        @QueryParam(value = "archived") boolean archived,

        @ApiParam(value = "Whether to return only projects that you can "
        + "add tasks to. Default: `false`.\n"
        + "If the parameter is specified without value, `true` is assumed.",
        example = "add-task=true", required = false)
        @QueryParam(value = "add-task") boolean addTask) { return null; }

    @GET
    @Path("/list/{organizationOid}")
    @ApiOperation(value = "Get all granted projects of the organization by its OID.",
        notes = "Returns all project records of the given organization. "
        + "Only granted projects will be returned.",
        response = Project.class,
        responseContainer = "List")
    public Response getProjectsByOrganizationOid(
        @ApiParam(value = "OID of the organization.", required = true)
        @PathParam("organizationOid") String organizationOid,

        @ApiParam(value = "Whether to return archived projects.\n"
        + "By default, archived projects won't be returned.\n"
        + "If the parameter is specified without value, `true` is assumed.",
        example = "archived=true", required = false)
        @QueryParam(value = "archived") boolean archived,

        @ApiParam(value = "Whether to return only projects that you can "
        + "add tasks to. Default: `false`.\n"
        + "If the parameter is specified without value, `true` is assumed.",
        example = "add-task=true", required = false)
        @QueryParam(value = "add-task") boolean addTask) { return null; }

    @GET
    @Path("/list/id/{organizationId}")
    @ApiOperation(value = "Get all granted projects of the organization by its ID.",
        notes = "Returns all project records of the given organization. "
        + "Only granted projects will be returned.",
        response = Project.class,
        responseContainer = "List")
    public Response getProjectsByOrganizationId(
        @ApiParam(value = "ID of the organization", required = true)
        @PathParam("organizationId") String organizationId,

        @ApiParam(value = "Whether to return archived projects.\n"
        + "By default, archived projects won't be returned.\n"
        + "If the parameter is specified without value, `true` is assumed.",
        example = "archived=true", required = false)
        @QueryParam(value = "archived") boolean archived,

        @ApiParam(value = "Whether to return only projects that you can "
        + "add tasks to. Default: `false`.\n"
        + "If the parameter is specified without value, `true` is assumed.",
        example = "add-task=true", required = false)
        @QueryParam(value = "add-task") boolean addTask) { return null; }

    @GET
    @Path("/id/{id}")
    @ApiOperation(value = "Get a project by its ID.",
        notes = "Returns the complete project record of the given ID.",
        response = Project.class)
    public Response getProjectById(
        @ApiParam(value = "ID of project that needs to be fetched",
            required = true)
        @PathParam("id") String id) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(value = "Get a project by its OID.",
        notes = "Returns the complete project record of the given OID.",
        response = Project.class)
    public Response getProject(
        @ApiParam(value = "OID of project that needs to be fetched",
            required = true)
        @PathParam("oid") String oid) { return null; }

    @PUT
    @Path("/{oid}")
    @ApiOperation(value = "Update a project.",
        notes = "Updates an existing project, and returns the complete updated project record.",
        response = Project.class)
    public Response updateProject(
        @ApiParam(value = "OID of project that needs to be updated", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Project to update", required = true)
        UpdateProjectBody data) { return null; }

/* Not supported yet (security concern)
    @DELETE
    @Path("/{oid}")
    @ApiOperation(value = "Delete a project",
        notes = "A specific, existing project can be deleted by making a DELETE request " +
                "on the URL for that project.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok",
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'Success': 'true'}")}))})
    public Response deleteProject(
        @ApiParam(value = "OID of project that needs to be deleted", required = true)
        @PathParam("oid") String oid) { return null; }
*/
}
