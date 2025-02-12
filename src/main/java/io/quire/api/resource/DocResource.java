package io.quire.api.resource;

import io.quire.api.model.doc.Doc;
import io.quire.api.model.doc.CreateDocBody;
import io.quire.api.model.doc.UpdateDocBody;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/doc")
@Api(value = "doc", description =
		"A document.")
@Produces({"application/json"})
public class DocResource {
	@POST
	@Path("/{ownerType}/{ownerOid}")
	@ApiOperation(value = "Add a new doc by owner's OID.",
		notes = "Add a new doc by owner's OID",
		response = Doc.class)
	public Response createDoc(
		@ApiParam(value = "The type of the owner that this new doc to be added to. "
		+ "It can be `project`, `organization`, `folder` or `smart-folder`. "
		+ "If omitted, `project` is assumed. For example, `/this_is_oid` is "
		+ "equivalent to `/project/this_is_oid`.",
		required = false)
		@PathParam("ownerType") String ownerType,
		@ApiParam(value = "OID of the owner that this new doc to be added to.",
		required = true)
		@PathParam("ownerOid") String ownerOid,
		@ApiParam(value = "Doc to create", required = true)
		CreateDocBody data) { return null; }

	@POST
	@Path("/id/{ownerType}/{ownerId}")
	@ApiOperation(value = "Add a new doc by owner's ID.",
		notes = "Add a new doc by owner's ID.",
		response = Doc.class)
	public Response createDocToProject(
		@ApiParam(value = "The type of the owner that this new doc to be added to. "
		+ "It can be `project`, `organization`, `folder` or `smart-folder`. "
		+ "If omitted, `project` is assumed. For example, `/id/this_is_id` is "
		+ "equivalent to `/id/project/this_is_id`.",
		required = false)
		@PathParam("owenerType") String ownerType,
		@ApiParam(value = "ID of owner that this new doc to be added to.",
		required = true)
		@PathParam("ownerId") String ownerId,
		@ApiParam(value = "Doc to create", required = true)
		CreateDocBody data) { return null; }

	@GET
	@Path("/{oid}")
	@ApiOperation(value = "Get an existing doc by its OID",
		notes = "Returns the full doc record of the given OID.",
		response = Doc.class)
	public Response getDoc(
		@ApiParam(value = "OID of doc that needs to be fetched.", required = true)
		@PathParam("oid") String oid) { return null; }

	@GET
	@Path("/id/{ownerType}/{ownerId}/{id}")
	@ApiOperation(value = "Get an existing doc by its ID.",
		notes = "Returns the full doc record of the given ID.",
		response = Doc.class)
	public Response getDocById(
		@ApiParam(value = "The type of the owner that this doc belongs to. "
		+ "It can be `project`, `organization`, `folder` or `smart-folder`. "
		+ "If omitted, `project` is assumed. For example, `/id/this_is_id` is "
		+ "equivalent to `/id/project/this_is_id`.",
		required = false)
		@PathParam("owenerType") String ownerType,
		@ApiParam(value = "ID of owner that this doc belongs to.",
		required = true)
		@PathParam("ownerId") String ownerId,
		@ApiParam(value = "ID of the doc that needs to be fetched", required = true)
		@PathParam("id") String id) { return null; }

	@GET
	@Path("/list/{ownerType}/{ownerOid}")
	@ApiOperation(value = "Get all docs of the given owner by its OID.",
		notes = "Returns all doc records of the given owner by its OID.",
		response = Doc.class,
		responseContainer = "List")
	public Response getDocsByProjectOid(
		@ApiParam(value = "The type of the owner. "
		+ "It can be `project`, `organization`, `folder` or `smart-folder`. "
		+ "If omitted, `project` is assumed. For example, `/list/this_is_oid` is "
		+ "equivalent to `/list/project/this_is_oid`.",
		required = false)
		@PathParam("ownerType") String ownerType,
		@ApiParam(value = "OID of the owner.", required = true)
		@PathParam("ownerOid") String ownerOid) { return null; }

	@GET
	@Path("/list/id/{ownerType}/{ownerId}")
	@ApiOperation(value = "Get all docs of the given owner by its ID.",
		notes = "Returns all doc records of the given owner by its ID.",
		response = Doc.class,
		responseContainer = "List")
	public Response getDocsByProjectId(
		@ApiParam(value = "The type of the owner. "
		+ "It can be `project`, `organization`, `folder` or `smart-folder`. "
		+ "If omitted, `project` is assumed. For example, `/list/id/this_is_id` is "
		+ "equivalent to `/list/id/project/this_is_id`.",
		required = false)
		@PathParam("ownerType") String ownerType,
		@ApiParam(value = "ID of the owner.", required = true)
		@PathParam("ownerId") String ownerId) { return null; }

	@PUT
	@Path("/{oid}")
	@ApiOperation(value = "Update a doc by its OID.",
		notes = "Updates an existing doc, and returns the complete updated record.",
		response = Doc.class)
	public Response updateDoc(
		@ApiParam(value = "OID of doc that needs to be updated", required = true)
		@PathParam("oid") String oid,
		@ApiParam(value = "Doc to update", required = true)
		UpdateDocBody data) { return null; }

	@PUT
	@Path("/id/{ownerType}/{ownerId}/{id}")
	@ApiOperation(value = "Update a doc by its ID.",
		notes = "Updates an existing doc, and returns the complete updated record.",
		response = Doc.class)
	public Response updateDoc(
		@ApiParam(value = "The type of the owner. "
		+ "It can be `project`, `organization`, `folder` or `smart-folder`. "
		+ "If omitted, `project` is assumed. For example, `/id/this_is_id` is "
		+ "equivalent to `/id/project/this_is_id`.",
		required = false)
		@PathParam("ownerType") String ownerType,
		@ApiParam(value = "ID of the owner.", required = true)
		@PathParam("ownerId") String ownerId,
		@ApiParam(value = "Doc to update", required = true)
		UpdateDocBody data) { return null; }

	@DELETE
	@Path("/{oid}")
	@ApiOperation(value = "Delete a doc by its OID",
		notes = "Delete an existing doc of the given OID.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json", value =
				"{'Success': 'true'}")}))})
	public Response deleteDoc(
		@ApiParam(value = "OID of doc that needs to be deleted", required = true)
		@PathParam("oid") String oid) { return null; }

	@DELETE
	@Path("/id/{ownerType}/{ownerId}/{id}")
	@ApiOperation(value = "Delete a doc by its ID",
		notes = "Delete an existing doc of the given OID.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json", value =
				"{'Success': 'true'}")}))})
	public Response deleteDoc(
		@ApiParam(value = "The type of the owner. "
		+ "It can be `project`, `organization`, `folder` or `smart-folder`. "
		+ "If omitted, `project` is assumed. For example, `/id/this_is_id` is "
		+ "equivalent to `/id/project/this_is_id`.",
		required = false)
		@PathParam("ownerType") String ownerType,
		@ApiParam(value = "ID of the owner.", required = true)
		@PathParam("ownerId") String ownerId) { return null; }
}
