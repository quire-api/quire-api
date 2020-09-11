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
	@Path("/{projectOid}")
	@ApiOperation(value = "Add a new sublist.",
		notes = "Add a new sublist into a project.",
		response = Sublist.class)
	public Response createSublist(
		@ApiParam(value = "OID of project that this new sublist to be added to.",
		required = true)
		@PathParam("projectOid") String projectOid,
		@ApiParam(value = "Sublist to create", required = true)
		CreateSublistBody data) { return null; }

	@POST
	@Path("/id/{projectId}")
	@ApiOperation(value = "Add a new sublist.",
		notes = "Add a new sublist into a project.",
		response = Sublist.class)
	public Response createSublistToProject(
		@ApiParam(value = "ID of project that this new sublist to be added to.",
		required = true)
		@PathParam("projectId") String projectId,
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
	@Path("/id/{projectId}/{id}")
	@ApiOperation(value = "Get an existing sublist by its ID.",
		notes = "Returns the full sublist record of the given ID.",
		response = Sublist.class)
	public Response getSublistById(
		@ApiParam(value = "ID of the project that the sublist belongs to.", required = true)
		@PathParam("projectId") int projectId,
		@ApiParam(value = "ID of the sublist that needs to be fetched", required = true)
		@PathParam("id") String id) { return null; }

	@GET
	@Path("/list/{projectOid}")
	@ApiOperation(value = "Get all sublists of the given project by its OID.",
		notes = "Returns all sublist records of the given project by its OID.",
		response = Sublist.class,
		responseContainer = "List")
	public Response getSublistsByProjectOid(
		@ApiParam(value = "OID of the project.", required = true)
		@PathParam("projectOid") String projectOid) { return null; }

	@GET
	@Path("/list/id/{projectId}")
	@ApiOperation(value = "Get all sublists of the given project by its ID.",
		notes = "Returns all sublist records of the given project by its ID.",
		response = Sublist.class,
		responseContainer = "List")
	public Response getSublistsByProjectId(
		@ApiParam(value = "ID of project.", required = true)
		@PathParam("projectId") String projectId) { return null; }

	@PUT
	@Path("/{oid}")
	@ApiOperation(value = "Update a sublist.",
		notes = "Updates an existing sublist, and returns the complete updated record.",
		response = Sublist.class)
	public Response updateSublist(
		@ApiParam(value = "OID of sublist that needs to be updated", required = true)
		@PathParam("oid") String oid,
		@ApiParam(value = "Sublist to update", required = true)
		UpdateSublistBody data) { return null; }

	@PUT
	@Path("/id/{projectId}/{id}")
	@ApiOperation(value = "Update a sublist by its ID.",
		notes = "Updates an existing sublist, and returns the complete updated record.",
		response = Sublist.class)
	public Response updateSublist(
		@ApiParam(value = "ID of the project that the sublist belongs to.", required = true)
		@PathParam("projectId") int projectId,
		@ApiParam(value = "ID of the sublist that needs to be fetched", required = true)
		@PathParam("id") String id,
		@ApiParam(value = "Sublist to update", required = true)
		UpdateSublistBody data) { return null; }

	@DELETE
	@Path("/{oid}")
	@ApiOperation(value = "Delete a sublist",
		notes = "Delete an existing sublist of the given OID.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json", value =
				"{'Success': 'true'}")}))})
	public Response deleteSublist(
		@ApiParam(value = "OID of sublist that needs to be deleted", required = true)
		@PathParam("oid") String oid) { return null; }

	@DELETE
	@Path("/id/{projectId}/{id}")
	@ApiOperation(value = "Delete a sublist by its ID",
		notes = "Delete an existing sublist of the given OID.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json", value =
				"{'Success': 'true'}")}))})
	public Response deleteSublist(
		@ApiParam(value = "ID of the project that the sublist belongs to.", required = true)
		@PathParam("projectId") int projectId,
		@ApiParam(value = "ID of the sublist that needs to be fetched", required = true)
		@PathParam("id") String id) { return null; }
}
