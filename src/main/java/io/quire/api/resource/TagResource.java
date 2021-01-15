package io.quire.api.resource;

import io.quire.api.model.tag.UpdateTagBody;
import io.quire.api.model.tag.CreateTagBody;
import io.quire.api.model.tag.Tag;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/tag")
@Api(value = "tag", description =
	"A tag is a label that can be attached to any task in Quire.")
@Produces({"application/json"})
public class TagResource {
	@POST
	@Path("/{projectOid}")
	@ApiOperation(value = "Add a new tag.",
		notes = "Add a new tag into a project.",
		response = Tag.class)
	public Response createTag(
		@ApiParam(value = "OID of project that this new tag to be added to."
			+ "Specify \"-\" if you'd like to add it to My Tasks.",
		required = true)
		@PathParam("projectOid") String projectOid,
		@ApiParam(value = "Tag to create", required = true)
		CreateTagBody data) { return null; }

	@POST
	@Path("/id/{projectId}")
	@ApiOperation(value = "Add a new tag.",
		notes = "Add a new tag into a project.",
		response = Tag.class)
	public Response createTagToProject(
		@ApiParam(value = "ID of project that this new tag to be added to. "
		+ "Specify \"-\" if you'd like to add it to My Tasks.",
		required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "Tag to create", required = true)
		CreateTagBody data) { return null; }

	@GET
	@Path("/{oid}")
	@ApiOperation(value = "Get a tag.",
		notes = "Returns the full tag record of the given OID.",
		response = Tag.class)
	public Response getTag(
		@ApiParam(value = "OID of tag that needs to be fetched.", required = true)
		@PathParam("oid") String oid) { return null; }

	@GET
	@Path("/list/{projectOid}")
	@ApiOperation(value = "Get all tags of the given project by its OID.",
		notes = "Returns all tag records of the given project by its OID.",
		response = Tag.class,
        responseContainer = "List")
	public Response getTagsByProjectOid(
		@ApiParam(value = "OID of the project. "
		+ "Specify \"-\" if you'd like to retrieve tags from My Tasks.",
		required = true)
		@PathParam("projectOid") String projectOid) { return null; }

	@GET
	@Path("/list/id/{projectId}")
	@ApiOperation(value = "Get all tags of the given project by its ID.",
		notes = "Returns all tag records of the given project by its ID.",
		response = Tag.class,
        responseContainer = "List")
	public Response getTagsByProjectId(
		@ApiParam(value = "ID of project. "
		+ "Specify \"-\" if you'd like to retrieve tags from My Tasks.",
		required = true)
		@PathParam("projectId") String projectId) { return null; }

	@PUT
	@Path("/{oid}")
	@ApiOperation(value = "Update a tag.",
		notes = "Updates an existing tag, and returns the complete updated record.",
		response = Tag.class)
	public Response updateTag(
		@ApiParam(value = "OID of tag that needs to be updated", required = true)
		@PathParam("oid") String oid,
		@ApiParam(value = "Tag to update", required = true)
		UpdateTagBody data) { return null; }

	@DELETE
	@Path("/{oid}")
	@ApiOperation(value = "Delete a tag",
		notes = "Delete an existing tag of the given OID.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json",
				value =	"{'success': true}")}))})
	public Response deleteTag(
		@ApiParam(value = "OID of tag that needs to be deleted", required = true)
		@PathParam("oid") String oid) { return null; }
}
