package io.quire.api.resource;

import io.quire.api.model.comment.Comment;
import io.quire.api.model.comment.UpdateCommentBody;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/comments")
@Api(value = "comments", description =
    "The comment belong to a task or project")
@Produces({"application/json"})
public class CommentResource {

    @GET
    @Path("/{oid}")
    @ApiOperation(value = "Get a comment",
        notes = "returns the complete comment record.",
        response = Comment.class)
    public Response getComment(
        @ApiParam(value = "Oid of comment that needs to be fetched", required = true)
        @PathParam("oid") String oid) { return null; }

    @PUT
    @Path("/{oid}")
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
    @Path("/{oid}")
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
