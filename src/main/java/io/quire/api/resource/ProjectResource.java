package io.quire.api.resource;

import io.quire.api.model.project.Project;
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
    @Path("/list/id/{organizationId}")
    @ApiOperation(value = "Get all granted projects of the given organization by its ID.",
        notes = "Returns all project records of the given organization,",
        response = Project.class,
        responseContainer = "List")
    public Response getPrjectsByOrganizationId(
        @ApiParam(value = "ID of the organization", required = true)
        @PathParam("organizationId") String organizationId) { return null; }

    @GET
    @Path("/list/{organizationOid}")
    @ApiOperation(value = "Get all granted projects, or all projects of "
        + "the specified organization.",
        notes = "Returns all granted project records of the given organization."
            + "Note: the \"organizationOid\" is optinal. If omitted, all "
            + "granted project records will be returned.",
        response = Project.class,
        responseContainer = "List")
    public Response getPrjectsByOrganizationOid(
        @ApiParam(value = "(Optional) OID of the organization", required = false)
        @PathParam("organizationOid") String organizationOid) { return null; }

    @GET
    @Path("/id/{id}")
    @ApiOperation(value = "Get a project by its ID.",
        notes = "returns the complete project record.",
        response = Project.class)
    public Response getProjectById(
        @ApiParam(value = "ID of project that needs to be fetched", required = true)
        @PathParam("id") String id) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(value = "Get a project by its OID.",
        notes = "returns the complete project record.",
        response = Project.class)
    public Response getProject(
        @ApiParam(value = "OID of project that needs to be fetched", required = true)
        @PathParam("oid") String oid) { return null; }

/* Not supported yet (security concern)
    @PUT
    @Path("/{oid}")
    @ApiOperation(value = "Update a project",
        notes = "A specific, existing project can be updated by making a PUT request on the URL for that project.\n" +
              "Returns the complete updated project record.",
        response = Project.class)
    public Response updateProject(
        @ApiParam(value = "OID of project that needs to be updated", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Project to update", required = true)
        UpdateProjectBody data) { return null; }

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
