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

    @POST
    @Path("/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "Create a chat channel by owner OID.",
        notes = "Adds a new chat channel to the specified owner (currently only `project`).",
        response = Chat.class
    )
    public Response createChat(
        @ApiParam(
            value = "Owner type. Currently only `project` is supported. "
                  + "If omitted, `project` is assumed; e.g., `/abc123` is equivalent to `/project/abc123`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner OID.", required = true)
        @PathParam("ownerOid") String ownerOid,
        @ApiParam(value = "Chat channel to create.", required = true)
        CreateChatBody data
    ) { return null; }

    @POST
    @Path("/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "Create a chat channel by owner ID.",
        notes = "Adds a new chat channel to the specified owner by ID (currently only `project`).",
        response = Chat.class
    )
    public Response createChatById(
        @ApiParam(
            value = "Owner type. Currently only `project` is supported. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Chat channel to create.", required = true)
        CreateChatBody data
    ) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get a chat channel by OID.",
        notes = "Returns the full chat channel record.",
        response = Chat.class
    )
    public Response getChat(
        @ApiParam(value = "Chat channel OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @GET
    @Path("/id/{ownerType}/{ownerId}/{id}")
    @ApiOperation(
        value = "Get a chat channel by ID.",
        notes = "Returns the full chat channel record for the given owner and channel ID.",
        response = Chat.class
    )
    public Response getChatById(
        @ApiParam(
            value = "Owner type. Currently only `project` is supported. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Chat channel ID.", required = true)
        @PathParam("id") String id
    ) { return null; }

    @GET
    @Path("/list/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "List chat channels by owner OID.",
        notes = "Returns all chat channel records for the given owner.",
        response = Chat.class,
        responseContainer = "List"
    )
    public Response getChatsByOwnerOid(
        @ApiParam(
            value = "Owner type. Currently only `project` is supported. "
                  + "If omitted, `project` is assumed; e.g., `/list/abc123` is equivalent to `/list/project/abc123`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner OID.", required = true)
        @PathParam("ownerOid") String ownerOid
    ) { return null; }

    @GET
    @Path("/list/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "List chat channels by owner ID.",
        notes = "Returns all chat channel records for the given owner.",
        response = Chat.class,
        responseContainer = "List"
    )
    public Response getChatsByOwnerId(
        @ApiParam(
            value = "Owner type. Currently only `project` is supported. "
                  + "If omitted, `project` is assumed; e.g., `/list/id/foo` is equivalent to `/list/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true)
        @PathParam("ownerId") String ownerId
    ) { return null; }

    @PUT
    @Path("/{oid}")
    @ApiOperation(
        value = "Update a chat channel by OID.",
        notes = "Updates an existing chat channel and returns the updated record.",
        response = Chat.class
    )
    public Response updateChatByOid(
        @ApiParam(value = "Chat channel OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateChatBody data
    ) { return null; }

    @PUT
    @Path("/id/{ownerType}/{ownerId}/{id}")
    @ApiOperation(
        value = "Update a chat channel by ID.",
        notes = "Updates an existing chat channel and returns the updated record.",
        response = Chat.class
    )
    public Response updateChatById(
        @ApiParam(
            value = "Owner type. Currently only `project` is supported. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Chat channel ID.", required = true)
        @PathParam("id") String id,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateChatBody data
    ) { return null; }

    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete a chat channel by OID.",
        notes = "Deletes the specified chat channel."
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ok",
            examples = @Example({
                @ExampleProperty(mediaType = "application/json", value = "{'success': true}")
            }))
    })
    public Response deleteChatByOid(
        @ApiParam(value = "Chat channel OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @DELETE
    @Path("/id/{ownerType}/{ownerId}/{id}")
    @ApiOperation(
        value = "Delete a chat channel by ID.",
        notes = "Deletes the specified chat channel."
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ok",
            examples = @Example({
                @ExampleProperty(mediaType = "application/json", value = "{'success': true}")
            }))
    })
    public Response deleteChatById(
        @ApiParam(
            value = "Owner type. Currently only `project` is supported. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Chat channel ID.", required = true)
        @PathParam("id") String id
    ) { return null; }
}
