package io.quire.api.resource;

import io.quire.api.model.tag.UpdateTagBody;
import io.quire.api.model.tag.CreateTagBody;
import io.quire.api.model.tag.Tag;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/tag")
@Api(value = "tags", description =
	"A tag is a label that can be attached to any task in Quire.")
@Produces({"application/json"})
public class TagResource {
	@POST
	@Path("/{projectOid}")
	@ApiOperation(value = "Add a new tag.",
		notes = "Add a new tag into a project.",
		response = Tag.class)
	public Response createTag(
		@ApiParam(value = "OID of project that this new tag to be added to.",
		required = true)
		@PathParam("projectOid") String projectOid,
		@ApiParam(value = "Tag to create", required = true)
		CreateTagBody data) { return null; }

	@GET
	@Path("/{oid}")
	@ApiOperation(value = "Get a tag",
		notes = "Returns the full tag record.",
		response = Tag.class)
	public Response getTag(
		@ApiParam(value = "Oid of tag that needs to be fetched.", required = true)
		@PathParam("oid") String oid) { return null; }

	@GET
	@Path("/list/{projectOid}")
	@ApiOperation(value = "Get all tags of the given project by its OID.",
		notes = "Returns all tag records of the given project.",
		response = Tag.class)
	public Response getTags(
		@ApiParam(value = "OID of the project to look for", required = true)
		@PathParam("projectOid") String projectOid) { return null; }

	@GET
	@Path("/list/id/{projectId}")
	@ApiOperation(value = "Get all tags of the given project by its ID.",
		notes = "Returns all tag records of the given project.",
		response = Tag.class)
	public Response getTags(
		@ApiParam(value = "ID of project to look for", required = true)
		@PathParam("projectId") String projectId) { return null; }

	@PUT
	@Path("/{oid}")
	@ApiOperation(value = "Update a tag",
		notes = "Returns the complete updated tag record.",
		response = Tag.class)
	public Response updateTag(
		@ApiParam(value = "Oid of tag that needs to be updated", required = true)
		@PathParam("oid") String oid,
		@ApiParam(value = "Tag to update", required = true)
		UpdateTagBody data) { return null; }

	@DELETE
	@Path("/{oid}")
	@ApiOperation(value = "Delete an existing tag",
		notes = "Delete an existing tag.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json", value =
				"{'Success': 'true'}")}))})
	public Response deleteTag(
		@ApiParam(value = "Oid of tag that needs to be deleted", required = true)
		@PathParam("oid") String oid) { return null; }

}
