package io.quire.api.resource;

import io.quire.api.model.comment.Comment;
import io.quire.api.model.comment.CreateCommentBody;
import io.quire.api.model.comment.UpdateCommentBody;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Api(value = "comments", description =
    "The comment belong to a task or project")
@Produces({"application/json"})
public class CommentResource {

    @GET
    @Path("/projects/{oid}/comments")
    @ApiOperation(value = "Get project comments",
        notes = "Returns the complete comment record for a single project.",
        response = Comment.class,
        responseContainer = "List")
    public Response getProjectComments(
        @ApiParam(value = "Oid of project that needs to be deleted", required = true)
        @PathParam("oid") String oid) { return null; }

    @POST
    @Path("/projects/{oid}/comments")
    @ApiOperation(value = "Add a project comment",
        notes = "Add a new comment in a project.",
        response = Comment.class)
    public Response createProjectComment(
        @ApiParam(value = "Oid of project that comment to", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Comment to create", required = true)
            CreateCommentBody data) { return null; }

    @GET
    @Path("/tasks/{oid}/comments")
    @ApiOperation(value = "Get task comments",
        notes = "Returns the complete comment record for a single task.",
        response = Comment.class,
        responseContainer = "List")
    public Response getTaskComments(
        @ApiParam(value = "Oid of task that needs to be fetched", required = true)
        @PathParam("oid") String oid) { return null; }

    @POST
    @Path("/tasks/{oid}/comments")
    @ApiOperation(value = "Add a task comment",
        notes = "Add a new comment in a task.",
        response = Comment.class)
    public Response createTaskComment(
        @ApiParam(value = "Oid of task that comment to", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Comment to create", required = true)
            CreateCommentBody data) { return null; }

    @GET
    @Path("/comments/{oid}")
    @ApiOperation(value = "Get a comment",
        notes = "returns the complete comment record.",
        response = Comment.class)
    public Response getComment(
        @ApiParam(value = "Oid of comment that needs to be fetched", required = true)
        @PathParam("oid") String oid) { return null; }

    @PUT
    @Path("/comments/{oid}")
    @ApiOperation(value = "Update a comment",
        notes = "A specific, existing comment can be updated by making a PUT request on the URL for that comment.\n" +
                "Returns the complete updated comment record.",
        response = Comment.class)
    public Response updateComment(
        @ApiParam(value = "OID of comment that needs to be updated", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Comment to update", required = true)
        UpdateCommentBody data) { return null; }

    @DELETE
    @Path("/comments/{oid}")
    @ApiOperation(value = "Delete a comment",
        notes = "A specific, existing comment can be deleted by making a DELETE request " +
                "on the URL for that comment.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok",
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'Success': 'true'}")}))})
    public Response deleteComment(
        @ApiParam(value = "Oid of comment that needs to be deleted", required = true)
        @PathParam("oid") String oid) { return null; }
}
