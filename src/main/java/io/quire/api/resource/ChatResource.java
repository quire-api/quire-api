package io.quire.api.resource;

import io.quire.api.model.chat.Chat;
import io.quire.api.model.chat.CreateChatBody;
import io.quire.api.model.chat.UpdateChatBody;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/chat")
@Api(value = "chat", description = "Chat channels.")
@Produces({"application/json"})
public class ChatResource {

    // The read endpoints share this.
    private static final String READ_403 =
        "Forbidden — the user can't access it, or didn't authorize it for this app.";

    // The create endpoints share this.
    private static final String CREATE_NOTES =
        "Adds a new chat channel to the specified owner (currently only `project`) "
      + "and returns it.\n\n"
      + "Example — `POST /chat/id/project/my_project`:\n\n"
      + "```json\n"
      + "{\n"
      + "  \"name\": \"Design\",\n"
      + "  \"followers\": [\"me\"]\n"
      + "}\n"
      + "```\n\n"
      + "Returns `402` if the plan's chat-channel limit is reached.";

    // The list endpoints share this.
    private static final String LIST_NOTES =
        "Returns the project's chat channels that the caller can see; one shown "
      + "to selected members only is left out for the others. Archived channels "
      + "are included (they carry `archivedAt`); removed ones are not.";

    // The delete endpoints share this.
    private static final String DELETE_NOTES =
        "Removes the chat channel. Restore it with "
      + "`PUT /chat/undo-remove/id/{ownerType}/{ownerId}/{chatId}` "
      + "(or `/chat/undo-remove/{oid}`).\n\n"
      + "Returns `204 No Content`, also when the channel does not exist.";

    // The create, list, get, update and delete endpoints share this.
    private static final String OWNER_TYPE =
        "Owner type. Currently only `project` is supported; other types return `400`. ";

    // The by-ID get, update and delete endpoints share this.
    private static final String OWNER_TYPE_BY_ID = OWNER_TYPE
      + "If omitted, `project` is assumed; e.g., `/id/my_project/1234` "
      + "is equivalent to `/id/project/my_project/1234`.";

    // The create, update and undo-remove endpoints share this.
    private static final String RETURN_MODE =
        "(Optional) Response shape: `full` (default) returns the full chat "
      + "channel record; `compact` returns only `{\"oid\": \"…\", \"id\": \"…\"}`, "
      + "which is cheaper — use it when you don't need the record back. "
      + "Any other value returns `400 Bad Request`.";

    @POST
    @Path("/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "Create a chat channel by owner OID.",
        notes = CREATE_NOTES,
        response = Chat.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created chat channel.", response = Chat.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed, for example a `partner` that isn't an external team of the project, or none from an external team member sharing with every member; or the owner is not a project."),
        @ApiResponse(code = 402, message = "Payment Required — chat-channel quota reached."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to create a chat in this owner."),
        @ApiResponse(code = 404, message = "Not Found — parent owner does not exist.")
    })
    public Response createChat(
        @ApiParam(
            value = OWNER_TYPE
                  + "If omitted, `project` is assumed; e.g., `/abc123` is equivalent to `/project/abc123`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner OID.", required = true)
        @PathParam("ownerOid") String ownerOid,
        @ApiParam(value = "Chat channel to create.", required = true)
        CreateChatBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @POST
    @Path("/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "Create a chat channel by owner ID.",
        notes = CREATE_NOTES,
        response = Chat.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created chat channel.", response = Chat.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed, for example a `partner` that isn't an external team of the project, or none from an external team member sharing with every member; or the owner is not a project."),
        @ApiResponse(code = 402, message = "Payment Required — chat-channel quota reached."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to create a chat in this owner."),
        @ApiResponse(code = 404, message = "Not Found — parent owner does not exist.")
    })
    public Response createChatById(
        @ApiParam(
            value = OWNER_TYPE
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Chat channel to create.", required = true)
        CreateChatBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get a chat channel by OID.",
        notes = "Returns the full chat channel record.",
        response = Chat.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — chat channel record.", response = Chat.class),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — chat does not exist.")
    })
    public Response getChat(
        @ApiParam(value = "Chat channel OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @GET
    @Path("/id/{ownerType}/{ownerId}/{chatId}")
    @ApiOperation(
        value = "Get a chat channel by ID.",
        notes = "Returns the full chat channel record for the given owner and channel ID.",
        response = Chat.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — chat channel record.", response = Chat.class),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — chat does not exist.")
    })
    public Response getChatById(
        @ApiParam(value = OWNER_TYPE_BY_ID, required = false, example = "project")
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Chat channel ID.", required = true, example = "1234")
        @PathParam("chatId") String chatId
    ) { return null; }

    @GET
    @Path("/list/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "List chat channels by owner OID.",
        notes = LIST_NOTES,
        response = Chat.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of chat channels (may be empty).", response = Chat.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — parent owner does not exist.")
    })
    public Response getChatsByOwnerOid(
        @ApiParam(
            value = OWNER_TYPE
                  + "If omitted, `project` is assumed; e.g., `/list/abc123` is equivalent to `/list/project/abc123`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner OID.", required = true)
        @PathParam("ownerOid") String ownerOid
    ) { return null; }

    @GET
    @Path("/list/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "List chat channels by owner ID.",
        notes = LIST_NOTES,
        response = Chat.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of chat channels (may be empty).", response = Chat.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — parent owner does not exist.")
    })
    public Response getChatsByOwnerId(
        @ApiParam(
            value = OWNER_TYPE
                  + "If omitted, `project` is assumed; e.g., `/list/id/foo` is equivalent to `/list/id/project/foo`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId
    ) { return null; }

    @PUT
    @Path("/{oid}")
    @ApiOperation(
        value = "Update a chat channel by OID.",
        notes = "Updates an existing chat channel and returns the updated record.\n\n"
              + "To archive or unarchive, set `archived: true` or `archived: false` in the body.",
        response = Chat.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated chat channel.", response = Chat.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to modify this chat."),
        @ApiResponse(code = 404, message = "Not Found — chat does not exist.")
    })
    public Response updateChatByOid(
        @ApiParam(value = "Chat channel OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateChatBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/id/{ownerType}/{ownerId}/{chatId}")
    @ApiOperation(
        value = "Update a chat channel by ID.",
        notes = "Updates an existing chat channel and returns the updated record.\n\n"
              + "To archive or unarchive, set `archived: true` or `archived: false` in the body.",
        response = Chat.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated chat channel.", response = Chat.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to modify this chat."),
        @ApiResponse(code = 404, message = "Not Found — chat does not exist.")
    })
    public Response updateChatById(
        @ApiParam(value = OWNER_TYPE_BY_ID, required = false, example = "project")
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Chat channel ID.", required = true, example = "1234")
        @PathParam("chatId") String chatId,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateChatBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/undo-remove/{oid}")
    @ApiOperation(
        value = "Undo the removal of a chat channel by OID.",
        notes = "Restores a previously-removed chat channel. "
            + "Idempotent: if the channel is not currently removed, this is "
            + "a no-op and returns the current channel record.\n\n"
            + "Subject to the chat-channel-per-project quota: may return "
            + "`402 Payment Required` if the plan's chat-channel limit is "
            + "already reached.",
        response = Chat.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — restored chat channel.", response = Chat.class),
        @ApiResponse(code = 402, message = "Payment Required — chat-channel quota reached."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to modify this chat."),
        @ApiResponse(code = 404, message = "Not Found — chat does not exist.")
    })
    public Response undoRemoveChatByOid(
        @ApiParam(value = "Chat channel OID.", required = true)
        @PathParam("oid") String oid,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/undo-remove/id/{ownerType}/{ownerId}/{chatId}")
    @ApiOperation(
        value = "Undo the removal of a chat channel by ID.",
        notes = "Restores a previously-removed chat channel. "
            + "Idempotent: if the channel is not currently removed, this is "
            + "a no-op and returns the current channel record.\n\n"
            + "Subject to the chat-channel-per-project quota: may return "
            + "`402 Payment Required` if the plan's chat-channel limit is "
            + "already reached.",
        response = Chat.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — restored chat channel.", response = Chat.class),
        @ApiResponse(code = 402, message = "Payment Required — chat-channel quota reached."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to modify this chat."),
        @ApiResponse(code = 404, message = "Not Found — chat does not exist.")
    })
    public Response undoRemoveChatById(
        @ApiParam(
            value = "Owner type. Currently only `project` is supported. "
                  + "If omitted, `project` is assumed; e.g., `/undo-remove/id/foo/bar` "
                  + "is equivalent to `/undo-remove/id/project/foo/bar`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Chat channel ID.", required = true, example = "1234")
        @PathParam("chatId") String chatId,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete a chat channel by OID.",
        notes = DELETE_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to remove this chat.")
    })
    public Response deleteChatByOid(
        @ApiParam(value = "Chat channel OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @DELETE
    @Path("/id/{ownerType}/{ownerId}/{chatId}")
    @ApiOperation(
        value = "Delete a chat channel by ID.",
        notes = DELETE_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to remove this chat.")
    })
    public Response deleteChatById(
        @ApiParam(value = OWNER_TYPE_BY_ID, required = false, example = "project")
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Chat channel ID.", required = true, example = "1234")
        @PathParam("chatId") String chatId
    ) { return null; }
}
