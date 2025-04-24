package io.quire.api.resource;

import io.quire.api.model.chat.Chat;
import io.quire.api.model.chat.CreateChatBody;
import io.quire.api.model.chat.UpdateChatBody;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/chat")
@Api(value = "chat", description = "A chat channel.")
@Produces({"application/json"})
public class ChatResource {
	@POST
	@Path("/{ownerType}/{ownerOid}")
	@ApiOperation(value = "Add a new chat channel by owner's OID.",
		notes = "Add a new chat channel by owner's OID",
		response = Chat.class)
	public Response createChat(
		@ApiParam(value = "The type of the owner that this new chat channel to be added to. "
		+ "Currently, only `project` is supported for chat channels. "
		+ "If omitted, `project` is assumed. For example, `/this_is_oid` is "
		+ "equivalent to `/project/this_is_oid`.",
		required = false)
		@PathParam("ownerType") String ownerType,
		@ApiParam(value = "OID of the owner that this new chat channel to be added to.",
		required = true)
		@PathParam("ownerOid") String ownerOid,
		@ApiParam(value = "Chat channel to create", required = true)
		CreateChatBody data) { return null; }

	@POST
	@Path("/id/{ownerType}/{ownerId}")
	@ApiOperation(value = "Add a new chat channel by owner's ID.",
		notes = "Add a new chat channel by owner's ID.",
		response = Chat.class)
	public Response createChatToProject(
		@ApiParam(value = "The type of the owner that this new chat channel to be added to. "
		+ "Currently, only `project` is supported for chat channels. "
		+ "If omitted, `project` is assumed. For example, `/id/this_is_id` is "
		+ "equivalent to `/id/project/this_is_id`.",
		required = false)
		@PathParam("owenerType") String ownerType,
		@ApiParam(value = "ID of owner that this new chat channel to be added to.",
		required = true)
		@PathParam("ownerId") String ownerId,
		@ApiParam(value = "Chat to create", required = true)
		CreateChatBody data) { return null; }

	@GET
	@Path("/{oid}")
	@ApiOperation(value = "Get an existing chat channel by its OID",
		notes = "Returns the full chat channel record of the given OID.",
		response = Chat.class)
	public Response getChat(
		@ApiParam(value = "OID of chat channel that needs to be fetched.", required = true)
		@PathParam("oid") String oid) { return null; }

	@GET
	@Path("/id/{ownerType}/{ownerId}/{id}")
	@ApiOperation(value = "Get an existing chat channel by its ID.",
		notes = "Returns the full chat channel record of the given ID.",
		response = Chat.class)
	public Response getChatById(
		@ApiParam(value = "The type of the owner that this chat channel belongs to. "
		+ "Currently, only `project` is supported for chat channels. "
		+ "If omitted, `project` is assumed. For example, `/id/this_is_id` is "
		+ "equivalent to `/id/project/this_is_id`.",
		required = false)
		@PathParam("owenerType") String ownerType,
		@ApiParam(value = "ID of owner that this chat channel belongs to.",
		required = true)
		@PathParam("ownerId") String ownerId,
		@ApiParam(value = "ID of the chat channel that needs to be fetched", required = true)
		@PathParam("id") String id) { return null; }

	@GET
	@Path("/list/{ownerType}/{ownerOid}")
	@ApiOperation(value = "Get all chat channels of the given owner by its OID.",
		notes = "Returns all chat channel records of the given owner by its OID.",
		response = Chat.class,
		responseContainer = "List")
	public Response getChatsByProjectOid(
		@ApiParam(value = "The type of the owner. "
		+ "Currently, only `project` is supported for chat channels. "
		+ "If omitted, `project` is assumed. For example, `/list/this_is_oid` is "
		+ "equivalent to `/list/project/this_is_oid`.",
		required = false)
		@PathParam("ownerType") String ownerType,
		@ApiParam(value = "OID of the owner.", required = true)
		@PathParam("ownerOid") String ownerOid) { return null; }

	@GET
	@Path("/list/id/{ownerType}/{ownerId}")
	@ApiOperation(value = "Get all chat channels of the given owner by its ID.",
		notes = "Returns all chat channel records of the given owner by its ID.",
		response = Chat.class,
		responseContainer = "List")
	public Response getChatsByProjectId(
		@ApiParam(value = "The type of the owner. "
		+ "Currently, only `project` is supported for chat channels. "
		+ "If omitted, `project` is assumed. For example, `/list/id/this_is_id` is "
		+ "equivalent to `/list/id/project/this_is_id`.",
		required = false)
		@PathParam("ownerType") String ownerType,
		@ApiParam(value = "ID of the owner.", required = true)
		@PathParam("ownerId") String ownerId) { return null; }

	@PUT
	@Path("/{oid}")
	@ApiOperation(value = "Update a chat channel by its OID.",
		notes = "Updates an existing chat channel, and returns the complete updated record.",
		response = Chat.class)
	public Response updateChat(
		@ApiParam(value = "OID of chat channel that needs to be updated", required = true)
		@PathParam("oid") String oid,
		@ApiParam(value = "Chat to update", required = true)
		UpdateChatBody data) { return null; }

	@PUT
	@Path("/id/{ownerType}/{ownerId}/{id}")
	@ApiOperation(value = "Update a chat channel by its ID.",
		notes = "Updates an existing chat channel, and returns the complete updated record.",
		response = Chat.class)
	public Response updateChat(
		@ApiParam(value = "The type of the owner. "
		+ "Currently, only `project` is supported for chat channels. "
		+ "If omitted, `project` is assumed. For example, `/id/this_is_id` is "
		+ "equivalent to `/id/project/this_is_id`.",
		required = false)
		@PathParam("ownerType") String ownerType,
		@ApiParam(value = "ID of the owner.", required = true)
		@PathParam("ownerId") String ownerId,
		@ApiParam(value = "Chat to update", required = true)
		UpdateChatBody data) { return null; }

	@DELETE
	@Path("/{oid}")
	@ApiOperation(value = "Delete a chat channel by its OID",
		notes = "Delete an existing chat channel of the given OID.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json", value =
				"{'Success': 'true'}")}))})
	public Response deleteChat(
		@ApiParam(value = "OID of chat channel that needs to be deleted", required = true)
		@PathParam("oid") String oid) { return null; }

	@DELETE
	@Path("/id/{ownerType}/{ownerId}/{id}")
	@ApiOperation(value = "Delete a chat channel by its ID",
		notes = "Delete an existing chat channel of the given OID.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json", value =
				"{'Success': 'true'}")}))})
	public Response deleteChat(
		@ApiParam(value = "The type of the owner. "
		+ "Currently, only `project` is supported for chat channels. "
		+ "If omitted, `project` is assumed. For example, `/id/this_is_id` is "
		+ "equivalent to `/id/project/this_is_id`.",
		required = false)
		@PathParam("ownerType") String ownerType,
		@ApiParam(value = "ID of the owner.", required = true)
		@PathParam("ownerId") String ownerId) { return null; }
}
