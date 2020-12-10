package io.quire.api.resource;

import io.quire.api.model.SimpleIdentity;
import io.quire.api.model.storage.Storage;
import io.quire.api.model.storage.StorageMap;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/storage")
@Api(value = "storage", description =
	"A storage to store application specifica data, or "
	+ "to map a user's name in a third-party application, such as Slack, to "
	+ "a Quire user.")
@Produces({"application/json"})
public class StorageResource {
	@GET
	@Path("/list/{prefix}")
	@ApiOperation(value = "Get all stored values with the given prefix.",
		notes =
		"Returns all stored values that start with the given prefix.\n"
		+ "Note: at most 20 items are returned for each query.",
		response = Storage.class)
	public Response getValues(
		@ApiParam(value = "A prefix (optional).\nExample: \"foo\"."
			+"If not specified, all values are returned.", required = false)
		@PathParam("prefix") String prefix) { return null; }

	@GET
	@Path("/{name}")
	@ApiOperation(value = "Get the value of the given name.",
		notes = "Returns the value of the given name, or null if not found.",
		response = StorageMap.class)
	public Response getValue(
		@ApiParam(value = "The name.\nExample: \"latest\"", required = true)
		@PathParam("name") String name) { return null; }

	@GET
	@Path("/user/{name}")
	@ApiOperation(value = "Get the user of the given name in a third-party application.",
		notes = "Returns the user of the given name in a third-party application, "
		+ "or null if not found.",
		response = SimpleIdentity.class)
	public Response getUserValue(
		@ApiParam(value = "The user's name in a third-party application.\n"
			+"Example: \"John at Slack\"", required = true)
		@PathParam("name") String name) { return null; }

	@PUT
	@Path("/{name}")
	@ApiOperation(value = "Update the value of the given name.",
		notes = "Updates the value of the given name. If it doesn't exist, "
			+"it adds the name to the given value.\n"
			+"If the value is null, it is the same as deletion.\n"
			+"It returns true if the value is updated or added successfully.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json",
				value =	"{'success': true}")}))})
	public Response updateValue(
		@ApiParam(value = "The name.\nExample: \"latest\"", required = true)
		@PathParam("name") String name,
		@ApiParam(value = "The value to store. It can be any object that can "
			+"be stored as a JSON object", required = true)
		StorageMap data) { return null; }

	@PUT
	@Path("/user/{name}")
	@ApiOperation(value = "Maps a name to the current user.",
		notes = "Maps a name to the current user. The name can be anything, "
			+"but it is usually the current user's name in a third-party application.\n"
			+"It is useful if you'd like to retrieve a user from a name in "
			+"a third-party application.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json",
				value =	"{'success': true}")}))})
	public Response updateUserMapping(
		@ApiParam(value = "The name.\nExample: \"John at Slack\"", required = true)
		@PathParam("name") String name) { return null; }

	@DELETE
	@Path("/{name}")
	@ApiOperation(value = "Delete the value of the given name",
		notes = "Delete the value of the given name.\n"
			+"It returns true if the value is deleted successfully, "
			+"or false if the name doesn't exist.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json",
				value =	"{'success': true}")}))})
	public Response deleteValue(
		@ApiParam(value = "The name.\nExample: \"latest\"", required = true)
		@PathParam("name") String name) { return null; }

	@DELETE
	@Path("/user/{name}")
	@ApiOperation(value = "Delete the user mapping of the given name",
		notes = "Delete the user mapping of the given name.\n"
			+"It returns true if the mapping is deleted successfully, "
			+"or false if the mapping doesn't exist.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json",
				value =	"{'success': true}")}))})
	public Response deleteUserMapping(
		@ApiParam(value = "The name.\nExample: \"latest\"", required = true)
		@PathParam("name") String name) { return null; }
}
