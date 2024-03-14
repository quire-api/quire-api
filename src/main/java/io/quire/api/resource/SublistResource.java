package io.quire.api.resource;

import io.quire.api.model.sublist.Sublist;
import io.quire.api.model.sublist.CreateSublistBody;
import io.quire.api.model.sublist.UpdateSublistBody;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/sublist")
@Api(value = "sublist", description =
		"A sublist is a collections of tasks. It represents a subset of all tasks.")
@Produces({"application/json"})
public class SublistResource {
	@POST
	@Path("/{ownerType}/{ownerOid}")
	@ApiOperation(value = "Add a new sublist by owner's OID.",
		notes = "Add a new sublist by owner's OID",
		response = Sublist.class)
	public Response createSublist(
		@ApiParam(value = "The type of the owner that this new sublist to be added to. "
		+ "It can be `project`, `organization`, `folder` or `smart-folder`. "
		+ "If omitted, `project` is assumed. For example, `/this_is_oid` is "
		+ "equivalent to `/project/this_is_oid`.",
		required = false)
		@PathParam("ownerType") String ownerType,
		@ApiParam(value = "OID of the owner that this new sublist to be added to.",
		required = true)
		@PathParam("ownerOid") String ownerOid,
		@ApiParam(value = "Sublist to create", required = true)
		CreateSublistBody data) { return null; }

	@POST
	@Path("/id/{ownerType}/{ownerId}")
	@ApiOperation(value = "Add a new sublist by owner's ID.",
		notes = "Add a new sublist by owner's ID.",
		response = Sublist.class)
	public Response createSublistToProject(
		@ApiParam(value = "The type of the owner that this new sublist to be added to. "
		+ "It can be `project`, `organization`, `folder` or `smart-folder`. "
		+ "If omitted, `project` is assumed. For example, `/id/this_is_id` is "
		+ "equivalent to `/id/project/this_is_id`.",
		required = false)
		@PathParam("owenerType") String ownerType,
		@ApiParam(value = "ID of owner that this new sublist to be added to.",
		required = true)
		@PathParam("ownerId") String ownerId,
		@ApiParam(value = "Sublist to create", required = true)
		CreateSublistBody data) { return null; }

	@GET
	@Path("/{oid}")
	@ApiOperation(value = "Get an existing sublist by its OID",
		notes = "Returns the full sublist record of the given OID.",
		response = Sublist.class)
	public Response getSublist(
		@ApiParam(value = "OID of sublist that needs to be fetched.", required = true)
		@PathParam("oid") String oid) { return null; }

	@GET
	@Path("/id/{ownerType}/{ownerId}/{id}")
	@ApiOperation(value = "Get an existing sublist by its ID.",
		notes = "Returns the full sublist record of the given ID.",
		response = Sublist.class)
	public Response getSublistById(
		@ApiParam(value = "The type of the owner that this sublist belongs to. "
		+ "It can be `project`, `organization`, `folder` or `smart-folder`. "
		+ "If omitted, `project` is assumed. For example, `/id/this_is_id` is "
		+ "equivalent to `/id/project/this_is_id`.",
		required = false)
		@PathParam("owenerType") String ownerType,
		@ApiParam(value = "ID of owner that this sublist belongs to.",
		required = true)
		@PathParam("ownerId") String ownerId,
		@ApiParam(value = "ID of the sublist that needs to be fetched", required = true)
		@PathParam("id") String id) { return null; }

	@GET
	@Path("/list/{ownerType}/{ownerOid}")
	@ApiOperation(value = "Get all sublists of the given owner by its OID.",
		notes = "Returns all sublist records of the given owner by its OID.",
		response = Sublist.class,
		responseContainer = "List")
	public Response getSublistsByProjectOid(
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
	@ApiOperation(value = "Get all sublists of the given owner by its ID.",
		notes = "Returns all sublist records of the given owner by its ID.",
		response = Sublist.class,
		responseContainer = "List")
	public Response getSublistsByProjectId(
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
	@ApiOperation(value = "Update a sublist by its OID.",
		notes = "Updates an existing sublist, and returns the complete updated record.",
		response = Sublist.class)
	public Response updateSublist(
		@ApiParam(value = "OID of sublist that needs to be updated", required = true)
		@PathParam("oid") String oid,
		@ApiParam(value = "Sublist to update", required = true)
		UpdateSublistBody data) { return null; }

	@PUT
	@Path("/id/{ownerType}/{ownerId}/{id}")
	@ApiOperation(value = "Update a sublist by its ID.",
		notes = "Updates an existing sublist, and returns the complete updated record.",
		response = Sublist.class)
	public Response updateSublist(
		@ApiParam(value = "The type of the owner. "
		+ "It can be `project`, `organization`, `folder` or `smart-folder`. "
		+ "If omitted, `project` is assumed. For example, `/id/this_is_id` is "
		+ "equivalent to `/id/project/this_is_id`.",
		required = false)
		@PathParam("ownerType") String ownerType,
		@ApiParam(value = "ID of the owner.", required = true)
		@PathParam("ownerId") String ownerId,
		@ApiParam(value = "Sublist to update", required = true)
		UpdateSublistBody data) { return null; }

	@DELETE
	@Path("/{oid}")
	@ApiOperation(value = "Delete a sublist by its OID",
		notes = "Delete an existing sublist of the given OID.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json", value =
				"{'Success': 'true'}")}))})
	public Response deleteSublist(
		@ApiParam(value = "OID of sublist that needs to be deleted", required = true)
		@PathParam("oid") String oid) { return null; }

	@DELETE
	@Path("/id/{ownerType}/{ownerId}/{id}")
	@ApiOperation(value = "Delete a sublist by its ID",
		notes = "Delete an existing sublist of the given OID.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json", value =
				"{'Success': 'true'}")}))})
	public Response deleteSublist(
		@ApiParam(value = "The type of the owner. "
		+ "It can be `project`, `organization`, `folder` or `smart-folder`. "
		+ "If omitted, `project` is assumed. For example, `/id/this_is_id` is "
		+ "equivalent to `/id/project/this_is_id`.",
		required = false)
		@PathParam("ownerType") String ownerType,
		@ApiParam(value = "ID of the owner.", required = true)
		@PathParam("ownerId") String ownerId) { return null; }
}
