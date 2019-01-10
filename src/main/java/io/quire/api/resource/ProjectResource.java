package io.quire.api.resource;

import io.quire.api.model.comment.Comment;
import io.quire.api.model.comment.CreateCommentBody;
import io.quire.api.model.project.CreateProjectBody;
import io.quire.api.model.project.Project;
import io.quire.api.model.project.UpdateProjectBody;
import io.quire.api.model.tag.CreateTagBody;
import io.quire.api.model.tag.Tag;
import io.quire.api.model.task.TaskWithChildren;
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
    public Response createProject(
        @ApiParam(value = "Project to create", required = true)
        CreateProjectBody data) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(value = "Get a project",
        notes = "returns the complete project record for a single project.",
        response = Project.class)
    public Response getProject(
        @ApiParam(value = "Oid of project that needs to be fetched", required = true)
        @PathParam("oid") String oid) { return null; }

    @PUT
    @Path("/{oid}")
    @ApiOperation(value = "Update a project",
        notes = "A specific, existing project can be updated by making a PUT request on the URL for that project.\n" +
              "Returns the complete updated project record.",
        response = Project.class)
    public Response updateProject(
        @ApiParam(value = "Oid of project that needs to be updated", required = true)
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
        @ApiParam(value = "Oid of project that needs to be deleted", required = true)
        @PathParam("oid") String oid) { return null; }

    @GET
    @Path("/{oid}/comments")
    @ApiOperation(value = "Get project comments",
        notes = "Returns the complete comment record for a single project.",
        response = Comment.class,
        responseContainer = "List")
    public Response getProjectComments(
        @ApiParam(value = "Oid of project that needs to be deleted", required = true)
        @PathParam("oid") String oid) { return null; }

    @POST
    @Path("/{oid}/comments")
    @ApiOperation(value = "Add a project comment",
        notes = "Add a new comment in a project.",
        response = Comment.class)
    public Response createProjectComment(
        @ApiParam(value = "Oid of project that comment to", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Comment to create", required = true)
        CreateCommentBody data) { return null; }

    @GET
    @Path("/{oid}/tasks")
    @ApiOperation(value = "Get project tasks",
        notes = "Returns the compact task records for all tasks within the given project",
        response = TaskWithChildren.class,
        responseContainer = "List")
    public Response getTasks(
        @ApiParam(value = "Oid of the project in which to search for tasks", required = true)
        @PathParam("oid") String oid) { return null; }

    @POST
    @Path("/{oid}/tags")
    @ApiOperation(value = "Add a project tag",
        notes = "Add a new tag in a project.",
        response = Tag.class)
    public Response createProjectTag(
        @ApiParam(value = "Oid of project", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Tag to create", required = true)
        CreateTagBody data) { return null; }
}
