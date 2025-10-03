package io.quire.api.resource;

import io.quire.api.model.*;
import io.quire.api.model.comment.*;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/comment")
@Api(
    value = "comment",
    description = "A comment that a user can add to a chat channel or a task."
)
@Produces({"application/json"})
public class CommentResource {

    // -------- Create --------

    @POST
    @Path("/chat/{chatOid}")
    @ApiOperation(
        value = "Add a new comment to a chat channel (by chat OID).",
        notes = "Adds a new comment to the specified chat channel.",
        response = Comment.class
    )
    public Response createCommentToChatByOid(
        @ApiParam(value = "OID of the chat channel.", required = true)
        @PathParam("chatOid") String chatOid,
        @ApiParam(value = "Comment to create.", required = true)
        CreateCommentBody data
    ) { return null; }

    @POST
    @Path("/task/{taskOid}")
    @ApiOperation(
        value = "Add a new comment to a task (by task OID).",
        notes = "Adds a new comment to the specified task.",
        response = Comment.class
    )
    public Response createCommentToTaskByOid(
        @ApiParam(value = "OID of the task.", required = true)
        @PathParam("taskOid") String taskOid,
        @ApiParam(value = "Comment to create.", required = true)
        CreateCommentBody data
    ) { return null; }

    @POST
    @Path("/id/{projectId}/chat/{chatId}")
    @ApiOperation(
        value = "Add a new comment to a chat channel (by project ID and chat ID).",
        notes = "Adds a new comment to the specified chat channel.",
        response = Comment.class
    )
    public Response createCommentToChatById(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Chat channel ID.", required = true)
        @PathParam("chatId") String chatId,
        @ApiParam(value = "Comment to create.", required = true)
        CreateCommentBody data
    ) { return null; }

    @POST
    @Path("/id/{projectId}/task/{taskId}")
    @ApiOperation(
        value = "Add a new comment to a task (by project ID and task ID).",
        notes = "Adds a new comment to the specified task.",
        response = Comment.class
    )
    public Response createCommentToTaskById(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Task ID.", required = true)
        @PathParam("taskId") int taskId,
        @ApiParam(value = "Comment to create.", required = true)
        CreateCommentBody data
    ) { return null; }

    // -------- List --------

    @GET
    @Path("/list/chat/{chatOid}")
    @ApiOperation(
        value = "Get all comments of a chat channel (by chat OID).",
        notes = "Returns all comments of the specified chat channel.",
        response = Comment.class,
        responseContainer = "List"
    )
    public Response getCommentsOfChatByOid(
        @ApiParam(value = "OID of the chat channel.", required = true)
        @PathParam("chatOid") String chatOid
    ) { return null; }

    @GET
    @Path("/list/task/{taskOid}")
    @ApiOperation(
        value = "Get all comments of a task (by task OID).",
        notes = "Returns all comments of the specified task.",
        response = Comment.class,
        responseContainer = "List"
    )
    public Response getCommentsOfTaskByOid(
        @ApiParam(value = "OID of the task.", required = true)
        @PathParam("taskOid") String taskOid
    ) { return null; }

    @GET
    @Path("/list/id/{projectId}/chat/{chatId}")
    @ApiOperation(
        value = "Get all comments of a chat channel (by project ID and chat ID).",
        notes = "Returns all comments of the specified chat channel.",
        response = Comment.class,
        responseContainer = "List"
    )
    public Response getCommentsOfChatById(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Chat channel ID.", required = true)
        @PathParam("chatId") String chatId
    ) { return null; }

    @GET
    @Path("/list/id/{projectId}/task/{taskId}")
    @ApiOperation(
        value = "Get all comments of a task (by project ID and task ID).",
        notes = "Returns all comments of the specified task.",
        response = Comment.class,
        responseContainer = "List"
    )
    public Response getCommentsOfTaskById(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Task ID.", required = true)
        @PathParam("taskId") int taskId
    ) { return null; }

    // -------- Update --------

    @PUT
    @Path("/{commentOid}")
    @ApiOperation(
        value = "Update an existing comment (by comment OID).",
        notes = "Updates an existing comment and returns the updated record.",
        response = Comment.class
    )
    public Response updateCommentByOid(
        @ApiParam(value = "Comment OID.", required = true)
        @PathParam("commentOid") String commentOid,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateCommentBody data
    ) { return null; }

    // -------- Attachments --------

    @POST
    @Path("/attach/{commentOid}/{filename}")
    @ApiOperation(
        value = "Upload an attachment to a comment (by comment OID).",
        notes = "Uploads an attachment to an existing comment.",
        response = SimpleAttachment.class
    )
    public Response attachToCommentByOid(
        @ApiParam(value = "Comment OID.", required = true)
        @PathParam("commentOid") String commentOid,
        @ApiParam(
            value = "Attachment file name, e.g., `readme.txt`.\n\n"
                  + "Provide a meaningful extension so the browser can recognize the MIME type "
                  + "(e.g., `revenue.pdf`, `contacts.json`).\n\n"
                  + "Alternatively, set the MIME type via the `Content-Type` header.",
            required = true
        )
        @PathParam("filename") String filename,
        @ApiParam(
            value = "Attachment content (raw bytes). For images, the request body is the image itself.",
            required = true
        )
        Object data
    ) { return null; }

    // -------- Delete --------

    @DELETE
    @Path("/{commentOid}")
    @ApiOperation(
        value = "Delete an existing comment (by comment OID).",
        notes = "Deletes an existing comment."
    )
    @ApiResponses({
        @ApiResponse(
            code = 200,
            message = "ok",
            examples = @Example({
                @ExampleProperty(mediaType = "application/json", value = "{'success': true}")
            })
        )
    })
    public Response deleteCommentByOid(
        @ApiParam(value = "Comment OID.", required = true)
        @PathParam("commentOid") String commentOid
    ) { return null; }
}
