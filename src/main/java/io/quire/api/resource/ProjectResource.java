package io.quire.api.resource;

import io.quire.api.model.ErrorResponse;
import io.quire.api.model.comment.Comment;
import io.quire.api.model.comment.CreateCommentBody;
import io.quire.api.model.project.CreateProjectBody;
import io.quire.api.model.project.Project;
import io.quire.api.model.project.UpdateProjectBody;
import io.quire.api.model.task.CreateTaskBody;
import io.quire.api.model.task.Task;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/projects")
@Api(value = "projects", description =
    "A project represents a prioritized list of tasks in Quire. " +
    "It exists in a single organization and is accessible to a subset of " +
    "users in that organization, depending on its permissions.")
@Produces({"application/json"})
public class ProjectResource {

    @POST
    @ApiOperation(value = "Create a project",
        notes = "Creates a new project in an organization.",
        response = Project.class)
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad parameter", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allow", response = ErrorResponse.class)})
    public Response createProject(
        @ApiParam(value = "Project to create", required = true)
        CreateProjectBody data) { return null; }

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Get a project",
        notes = "returns the complete project record for a single project.",
        response = Project.class)
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad parameter", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Project not found", response = ErrorResponse.class,
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'message': 'Project not found: Marketing_Project'}")}))})
    public Response getProject(
        @ApiParam(value = "ID of project that needs to be fetched", required = true)
        @PathParam("id") String id) { return null; }

    @PUT
    @Path("/{id}")
    @ApiOperation(value = "Update a project",
        notes = "A specific, existing project can be updated by making a PUT request on the URL for that project.\n" +
              "Returns the complete updated project record.",
        response = Project.class)
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad parameter", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allow", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Project not found", response = ErrorResponse.class,
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'message': 'Project not found: Marketing_Project'}")}))})
    public Response updateProject(
        @ApiParam(value = "ID of project that needs to be updated", required = true)
        @PathParam("id") String id,
        @ApiParam(value = "Project to update", required = true)
        UpdateProjectBody data) { return null; }

    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "Delete a project",
        notes = "A specific, existing project can be deleted by making a DELETE request " +
                "on the URL for that project.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok",
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'Success': 'true'}")})),
        @ApiResponse(code = 400, message = "Bad parameter", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allow", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Project not found", response = ErrorResponse.class,
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'message': 'Project not found: Marketing_Project'}")}))})
    public Response deleteProject(
        @ApiParam(value = "ID of project that needs to be deleted", required = true)
        @PathParam("id") String id) { return null; }

    @GET
    @Path("/{id}/comments")
    @ApiOperation(value = "Get project comments",
        notes = "Returns the complete comment record for a single project.",
        response = Comment.class,
        responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad parameter", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Task not found", response = ErrorResponse.class,
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'message': 'Project not found: Marketing_Project'}")}))})
    public Response getProjectComments(
        @ApiParam(value = "ID of project that needs to be deleted", required = true)
        @PathParam("id") String id) { return null; }

    @POST
    @Path("/{id}/comments")
    @ApiOperation(value = "Add a project comment",
        notes = "Add a new comment in a project.",
        response = Comment.class)
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad parameter", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allow", response = ErrorResponse.class)})
    public Response createProjectComment(
        @ApiParam(value = "Comment to create", required = true)
        CreateCommentBody data) { return null; }

    @GET
    @Path("/search")
    @ApiOperation(value = "Search projects",
        notes = "Returns the compact project records for query projects",
        response = Project.class,
        responseContainer = "List")
    public Response searchProjects(
        @ApiParam(value = "Query text for search project",
            example = "query=my project", required = true)
        @QueryParam(value = "query") String query,
        @ApiParam(value = "State of project",
            defaultValue = "active",
            allowableValues = "all,active,archived",
            example = "state=all")
        @QueryParam(value = "state") String state){ return null; }
}
