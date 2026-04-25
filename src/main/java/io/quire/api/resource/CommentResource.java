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
    @Path("/{taskOid}")
    @ApiOperation(
        value = "Add a new comment to a task (by task OID).",
        notes = "Adds a new comment to the specified task.",
        response = Comment.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created comment record.", response = Comment.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to add a comment to this task."),
        @ApiResponse(code = 404, message = "Not Found — task does not exist.")
    })
    public Response createCommentToTaskByOid(
        @ApiParam(value = "OID of the task.", required = true)
        @PathParam("taskOid") String taskOid,
        @ApiParam(value = "Comment to create.", required = true)
        CreateCommentBody data,

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @POST
    @Path("/chat/{chatOid}")
    @ApiOperation(
        value = "Add a new comment to a chat channel (by chat OID).",
        notes = "Adds a new comment to the specified chat channel.",
        response = Comment.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created comment record.", response = Comment.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to add a comment to this chat channel."),
        @ApiResponse(code = 404, message = "Not Found — chat channel does not exist.")
    })
    public Response createCommentToChatByOid(
        @ApiParam(value = "OID of the chat channel.", required = true)
        @PathParam("chatOid") String chatOid,
        @ApiParam(value = "Comment to create.", required = true)
        CreateCommentBody data,

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @POST
    @Path("/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Add a new comment to a task (by project ID and task ID).",
        notes = "Adds a new comment to the specified task.",
        response = Comment.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created comment record.", response = Comment.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to add a comment to this task."),
        @ApiResponse(code = 404, message = "Not Found — task does not exist.")
    })
    public Response createCommentToTaskById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Task ID.", required = true, example = "42")
        @PathParam("taskId") int taskId,
        @ApiParam(value = "Comment to create.", required = true)
        CreateCommentBody data,

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @POST
    @Path("/chat/id/{projectId}/{chatId}")
    @ApiOperation(
        value = "Add a new comment to a chat channel (by project ID and chat ID).",
        notes = "Adds a new comment to the specified chat channel.",
        response = Comment.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created comment record.", response = Comment.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to add a comment to this chat channel."),
        @ApiResponse(code = 404, message = "Not Found — chat channel does not exist.")
    })
    public Response createCommentToChatById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Chat channel ID.", required = true, example = "1234")
        @PathParam("chatId") String chatId,
        @ApiParam(value = "Comment to create.", required = true)
        CreateCommentBody data,

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
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
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of comments (may be empty).", response = Comment.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Not Found — chat channel does not exist.")
    })
    public Response getCommentsOfChatByOid(
        @ApiParam(value = "OID of the chat channel.", required = true)
        @PathParam("chatOid") String chatOid
    ) { return null; }

    @GET
    @Path("/list/{taskOid}")
    @ApiOperation(
        value = "Get all comments of a task (by task OID).",
        notes = "Returns all comments of the specified task.",
        response = Comment.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of comments (may be empty).", response = Comment.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Not Found — task does not exist.")
    })
    public Response getCommentsOfTaskByOid(
        @ApiParam(value = "OID of the task.", required = true)
        @PathParam("taskOid") String taskOid
    ) { return null; }

    @GET
    @Path("/list/chat/id/{projectId}/{chatId}")
    @ApiOperation(
        value = "Get all comments of a chat channel (by project ID and chat ID).",
        notes = "Returns all comments of the specified chat channel.",
        response = Comment.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of comments (may be empty).", response = Comment.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Not Found — chat channel does not exist.")
    })
    public Response getCommentsOfChatById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Chat channel ID.", required = true, example = "1234")
        @PathParam("chatId") String chatId
    ) { return null; }

    @GET
    @Path("/list/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Get all comments of a task (by project ID and task ID).",
        notes = "Returns all comments of the specified task.",
        response = Comment.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of comments (may be empty).", response = Comment.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Not Found — task does not exist.")
    })
    public Response getCommentsOfTaskById(
        @ApiParam(value = "Project ID.", required = true, example = "my_project")
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Task ID.", required = true, example = "42")
        @PathParam("taskId") int taskId
    ) { return null; }

    // -------- Get --------

    @GET
    @Path("/{commentOid}")
    @ApiOperation(
        value = "Get an existing comment (by comment OID).",
        notes = "Returns the full comment record.",
        response = Comment.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — comment record.", response = Comment.class),
        @ApiResponse(code = 404, message = "Not Found — comment does not exist.")
    })
    public Response getCommentByOid(
        @ApiParam(value = "Comment OID.", required = true)
        @PathParam("commentOid") String commentOid
    ) { return null; }

    // -------- Update --------

    @PUT
    @Path("/{commentOid}")
    @ApiOperation(
        value = "Update an existing comment (by comment OID).",
        notes = "Updates an existing comment and returns the updated record.",
        response = Comment.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated comment record.", response = Comment.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to modify this comment."),
        @ApiResponse(code = 404, message = "Not Found — comment does not exist.")
    })
    public Response updateCommentByOid(
        @ApiParam(value = "Comment OID.", required = true)
        @PathParam("commentOid") String commentOid,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateCommentBody data,

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    // -------- Attachments --------

    @POST
    @Path("/attach/{commentOid}/{filename}")
    @ApiOperation(
        value = "Upload an attachment to a comment (by comment OID).",
        notes = "Uploads an attachment to an existing comment.",
        response = SimpleAttachment.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — attachment record.", response = SimpleAttachment.class),
        @ApiResponse(code = 400, message = "Bad Request — bad payload or filename."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to attach to this comment."),
        @ApiResponse(code = 404, message = "Not Found — comment does not exist.")
    })
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

    // -------- Undo remove --------

    @PUT
    @Path("/undo-remove/{commentOid}")
    @ApiOperation(
        value = "Undo the removal of a comment (by comment OID).",
        notes = "Restores a previously-removed comment. "
            + "Idempotent: if the comment is not currently removed, this is "
            + "a no-op and returns the current comment record.",
        response = Comment.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — restored comment record.", response = Comment.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to restore this comment."),
        @ApiResponse(code = 404, message = "Not Found — comment does not exist.")
    })
    public Response undoRemoveCommentByOid(
        @ApiParam(value = "Comment OID.", required = true)
        @PathParam("commentOid") String commentOid,

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    // -------- Delete --------

    @DELETE
    @Path("/{commentOid}")
    @ApiOperation(
        value = "Delete an existing comment (by comment OID).",
        notes = "Deletes an existing comment.\n\n"
              + "> Note: Returns `204 No Content` regardless of whether the comment exists."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content")
    })
    public Response deleteCommentByOid(
        @ApiParam(value = "Comment OID.", required = true)
        @PathParam("commentOid") String commentOid
    ) { return null; }
}
