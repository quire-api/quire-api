package io.quire.api.resource;

import io.quire.api.model.storage.StorageList;
import io.quire.api.model.storage.StorageMap;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/storage")
@Api(value = "storage", description =
	"A storage to store application specific data.\n"
	+ "The application specific data is stored per access token. "
	+ "They will be deleted if the token is revoked or expired.")
//TODO: add the following description:
//"**Special Values**\n"
//"If the name is \"channel\" or \"channel-<i>host</i>\", the value will be shown "
//"on [My Apps](https://quire.io/r/setting?tab=app). It is used to know "
//"the status of your app.\n"
//"Note: `host` in \"channel-<i>host</i>\" must be the OID of the project "
//"or the organization that the user granted the access to.\n"
//"The value must be a map. For example,\n"
//"```\n"
//"PUT /storage/channel-KocoR0Nr8yCFxGejEdsLZdzG\n"
//"{\n"
//"  "id": "app_specific_id",\n"
//"  "name": "a human readable name or message", //optional. Omitted, `id` will be displayed instead.\n"
//"  "url": "https://..." //optional. A hyperlink to your website for details.\n"
//"}\n"
@Produces({"application/json"})
public class StorageResource {
	@GET
	@Path("/list/{prefix}")
	@ApiOperation(value = "Get all stored application specific values with the given prefix.",
		notes =
		"Returns all stored application specific values that start with the given prefix.\n"
		+ "Note: at most 20 items are returned for each query.",
		response = StorageList.class)
	public Response getValues(
		@ApiParam(value = "A prefix (optional).\nExample: \"foo\"."
			+"If not specified, all values are returned.", required = false)
		@PathParam("prefix") String prefix) { return null; }

	@GET
	@Path("/{name}")
	@ApiOperation(value = "Get the application specific data of the given name.",
		notes = "Returns the application specific data of the given name, or null if not found.\n"
		+ "Note: application specific data are stored per access token.",
		response = StorageMap.class)
	public Response getValue(
		@ApiParam(value = "The name.\nExample: \"latest\"", required = true)
		@PathParam("name") String name) { return null; }

	@PUT
	@Path("/{name}")
	@ApiOperation(value = "Update the application specific data of the given name.",
		notes = "Updates the application specific data of the given name. "
		    +"If it doesn't exist, it adds the name to the given value.\n"
			+"If the value is null, it is the same as deletion.\n"
			+"It returns true if the value is updated or added successfully.\n"
			+"Note: application specific data are stored per access token.")
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

	@DELETE
	@Path("/{name}")
	@ApiOperation(value = "Delete the application specific data of the given name",
		notes = "Delete the application specific data of the given name.\n"
			+"It returns true if the value is deleted successfully, "
			+"or false if the name doesn't exist.\n"
			+"Note: application specific data are stored per access token.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json",
				value =	"{'success': true}")}))})
	public Response deleteValue(
		@ApiParam(value = "The name.\nExample: \"latest\"", required = true)
		@PathParam("name") String name) { return null; }
}
