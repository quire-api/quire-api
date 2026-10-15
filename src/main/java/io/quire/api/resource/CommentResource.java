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
        notes = "Uploads an attachment to an existing comment.\n\n"
              + "Executable files (`.exe`, `.com`, `.bat`) are rejected unless the "
              + "project has enabled uploading executable files.",
        response = SimpleAttachment.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — attachment record.", response = SimpleAttachment.class),
        @ApiResponse(code = 400, message = "Bad Request — bad payload or filename, or an executable file is not allowed for this project."),
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

    // -------- Emojis --------
    //
    // The names are listed inline, not only in the Emojis section, so each
    // endpoint stays self-contained for MCP / agent consumers, which may
    // see only one endpoint at a time.

    private static final String EMOJI_NAMES =
        "😃 `smiley`, 😞 `disappointed`, 😐 `neutral_face`, 😂 `joy`, "
      + "😍 `heart_eyes`, 😜 `stuck_out_tongue_winking_eye`, 😏 `smirk`, "
      + "😱 `scream`, 😭 `sob`, 😡 `rage` (or `pout`), 👍 `+1` (or `thumbsup`), "
      + "👎 `-1` (or `thumbsdown`), 🙌 `raised_hands`, 👊 `punch` (or `facepunch`, "
      + "`fist_oncoming`), 💪 `muscle`, ❤️ `heart`, 🎉 `tada`, 🚀 `rocket`, "
      + "💯 `100`, ⚠️ `warning`";

    private static final String EMOJIS_PARAM =
        "One or more emojis separated by commas, each as the emoji itself or "
      + "its name (listed in the endpoint's description), percent-encoded. "
      + "Names need no encoding, so prefer them, e.g. `+1,tada`. "
      + "At most 20 per request. Duplicates and empty items are ignored.";

    @POST
    @Path("/add-emoji/{commentOid}/{emojis}")
    @ApiOperation(
        value = "Add emoji reactions to a comment (by comment OID).",
        notes = "Adds the caller's reactions with the given emojis. The request "
              + "has no body; the emojis are in the URL path.\n\n"
              + "Only the emojis that Quire's reaction picker offers are accepted. "
              + "Each can be given as the emoji itself or, more simply, by its "
              + "name — the shortcode GitHub and Slack use — with or without "
              + "colons and in any case: " + EMOJI_NAMES + ". "
              + "❤ and ⚠ without the variation selector (U+FE0F) are accepted as "
              + "❤️ and ⚠️. See also [Emojis](#emojis).\n\n"
              + "All or nothing: if any emoji is invalid, none is added and "
              + "`400` is returned. Adding a reaction the caller already has "
              + "is a no-op.\n\n"
              + "Anyone who can view the comment can react. A reaction always "
              + "belongs to the authorizing user, never to the app. The "
              + "comment's author is notified of each reaction added.\n\n"
              + "Returns `204 No Content`. To see the reactions, read the "
              + "comment with `GET /comment/{commentOid}`: its `emojis` lists "
              + "each emoji with the users who reacted.\n\n"
              + "Example: `POST /comment/add-emoji/iDsPd.QP_qM.hN.Trymukn8b/+1,tada` "
              + "adds 👍 and 🎉, and so does "
              + "`POST /comment/add-emoji/iDsPd.QP_qM.hN.Trymukn8b/%F0%9F%91%8D,%F0%9F%8E%89`."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content — reactions added (or already there)."),
        @ApiResponse(code = 400, message = "Bad Request — an emoji or name is not accepted, none is given, or more than 20 are given."),
        @ApiResponse(code = 403, message = "Forbidden — caller can't view this comment."),
        @ApiResponse(code = 404, message = "Not Found — comment does not exist.")
    })
    public Response addCommentEmojiByOid(
        @ApiParam(value = "Comment OID.", required = true)
        @PathParam("commentOid") String commentOid,
        @ApiParam(value = EMOJIS_PARAM, required = true, example = "+1,tada")
        @PathParam("emojis") String emojis
    ) { return null; }

    @DELETE
    @Path("/remove-emoji/{commentOid}/{emojis}")
    @ApiOperation(
        value = "Remove emoji reactions from a comment (by comment OID).",
        notes = "Removes the caller's reactions with the given emojis. The "
              + "request has no body; the emojis are in the URL path.\n\n"
              + "Each can be given as the emoji itself or by its name, with or "
              + "without colons and in any case: " + EMOJI_NAMES + ". "
              + "❤ and ⚠ without the variation selector (U+FE0F) are taken as "
              + "❤️ and ⚠️. Any other non-empty value is accepted too, so a "
              + "reaction with an emoji the picker no longer offers can still "
              + "be removed. See also [Emojis](#emojis).\n\n"
              + "Removing a reaction the caller doesn't have is a no-op.\n\n"
              + "Returns `204 No Content`. To see the remaining reactions, read "
              + "the comment with `GET /comment/{commentOid}`.\n\n"
              + "> Note: Like deleting a comment, returns `204 No Content` regardless "
              + "of whether the comment exists. If it doesn't, or it has been "
              + "deleted, nothing is changed.\n\n"
              + "Example: `DELETE /comment/remove-emoji/iDsPd.QP_qM.hN.Trymukn8b/+1` "
              + "removes the caller's 👍."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content — reactions removed (or already absent, or no such comment)."),
        @ApiResponse(code = 400, message = "Bad Request — no emoji is given, or more than 20 are given."),
        @ApiResponse(code = 403, message = "Forbidden — caller can't view this comment.")
    })
    public Response removeCommentEmojiByOid(
        @ApiParam(value = "Comment OID.", required = true)
        @PathParam("commentOid") String commentOid,
        @ApiParam(value = EMOJIS_PARAM, required = true, example = "+1,tada")
        @PathParam("emojis") String emojis
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
