package io.quire.api.resource;

import io.quire.api.model.project.CreateProjectBody;
import io.quire.api.model.project.Project;
import io.quire.api.model.task.Task;
import io.quire.api.model.project.UpdateProjectBody;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/projects")
@Api(value = "projects", description =
    "A project represents a prioritized list of tasks in Quire. " +
    "It exists in a single organization and is accessible to a subset of " +
    "users in that organization, depending on its permissions."
)
@Produces({"application/json"})
public class ProjectResource {

    @POST
    @ApiOperation(value = "Create a project.",
      notes = "Creates a new project in an organization.",
      response = Project.class)
    @ApiResponses(value = {
      @ApiResponse(code = 400, message = "Invalid ID supplied"),
      @ApiResponse(code = 403, message = "Not allow")})
    public Response createProject(
        @ApiParam(value = "Project to create", required = true)
        CreateProjectBody data) {
        return null;
    }

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Get a project",
      notes = "returns the complete project record for a single project.",
      response = Project.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied"),
        @ApiResponse(code = 404, message = "Project not found")})
    public Response getProject(
        @ApiParam(value = "ID of project that needs to be fetched", required = true)
        @PathParam("id") String id) {
        return null;
    }

    @PUT
    @Path("/{id}")
    @ApiOperation(value = "Update a project",
      notes = "A specific, existing project can be updated by making a PUT request on the URL for that project.\n" +
              "Returns the complete updated project record.",
      response = Project.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied"),
        @ApiResponse(code = 403, message = "Not allow"),
        @ApiResponse(code = 404, message = "Project not found")})
    public Response updateProject(
        @ApiParam(value = "ID of project that needs to be updated", required = true)
        @PathParam("id") String id,
        @ApiParam(value = "Project to update", required = true)
        UpdateProjectBody data) {
        return null;
    }

    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "Delete a project",
        notes = "A specific, existing project can be deleted by making a DELETE request " +
                "on the URL for that project."
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok"),
        @ApiResponse(code = 400, message = "Invalid ID supplied"),
        @ApiResponse(code = 403, message = "Not allow"),
        @ApiResponse(code = 404, message = "Project not found")})
    public Response deleteProject(
        @ApiParam(value = "ID of project that needs to be deleted", required = true)
        @PathParam("id") String id) {
        return null;
    }

    @GET
    @Path("/{id}/tasks")
    @ApiOperation(value = "Get project tasks",
        notes = "Returns the compact task records for all tasks within the given project",
        response = Task.class,
        responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied"),
        @ApiResponse(code = 404, message = "Project not found")})
    public Response getTasks(
        @ApiParam(value = "ID of the project in which to search for tasks", required = true)
        @PathParam("id") String id){
        return null;
    }
}
