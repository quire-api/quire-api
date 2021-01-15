package io.quire.api.resource;

import io.quire.api.model.status.*;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/status")
@Api(value = "status", description =
	"A status is a value to indicate the progress of a task.")
@Produces({"application/json"})
public class StatusResource {
	@POST
	@Path("/{projectOid}")
	@ApiOperation(value = "Add a new status.",
		notes = "Add a new status into a project.",
		response = Status.class)
	public Response createStatus(
		@ApiParam(value = "OID of project that this new status to be added to.",
		required = true)
		@PathParam("projectOid") String projectOid,
		@ApiParam(value = "Status to create", required = true)
		CreateStatusBody data) { return null; }

	@POST
	@Path("/id/{projectId}")
	@ApiOperation(value = "Add a new status.",
		notes = "Add a new status into a project.",
		response = Status.class)
	public Response createStatusToProject(
		@ApiParam(value = "ID of project that this new status to be added to.",
		required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "Status to create", required = true)
		CreateStatusBody data) { return null; }

	@GET
	@Path("/{projectOid}/{value}")
	@ApiOperation(value = "Get the status of the given value.",
		notes = "Returns the full status record of the given value.",
		response = Status.class)
	public Response getStatusByProjectOid(
		@ApiParam(value = "OID of project that the status belongs to.",
		required = true)
		@PathParam("projectOid") String projectOid,
		@ApiParam(value = "The status's value that needs to be fetched.",
			required = true)
		@PathParam("value") int value) { return null; }

	@GET
	@Path("/id/{projectId}/{value}")
	@ApiOperation(value = "Get the status of the given value.",
		notes = "Returns the full status record of the given value.",
		response = Status.class)
	public Response getStatusByProjectId(
		@ApiParam(value = "ID of project that the status belongs to.",
			required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "The status's value that needs to be fetched.",
			required = true)
		@PathParam("value") int value) { return null; }

	@GET
	@Path("/list/{projectOid}")
	@ApiOperation(value = "Get all statuses of the given project by its OID.",
		notes = "Returns all status records of the given project by its OID.",
		response = Status.class,
        responseContainer = "List")
	public Response getStatusesByProjectOid(
		@ApiParam(value = "OID of the project.", required = true)
		@PathParam("projectOid") String projectOid) { return null; }

	@GET
	@Path("/list/id/{projectId}")
	@ApiOperation(value = "Get all statuses of the given project by its ID.",
		notes = "Returns all status records of the given project by its ID.",
		response = Status.class,
        responseContainer = "List")
	public Response getStatusesByProjectId(
		@ApiParam(value = "ID of project.", required = true)
		@PathParam("projectId") String projectId) { return null; }

	@PUT
	@Path("/{projectOid}/{value}")
	@ApiOperation(value = "Update a status.",
		notes = "Updates an existing status, and returns the complete updated record.",
		response = Status.class)
	public Response updateStatusByProjectOid(
		@ApiParam(value = "OID of project that the status belongs to.",
		required = true)
		@PathParam("projectOid") String projectOid,
		@ApiParam(value = "The status's value that needs to be updated.",
			required = true)
		@PathParam("value") int value,
		@ApiParam(value = "Status to update", required = true)
		UpdateStatusBody data) { return null; }

	@PUT
	@Path("/id/{projectId}/{value}")
	@ApiOperation(value = "Update a status.",
		notes = "Updates an existing status, and returns the complete updated record.",
		response = Status.class)
	public Response updateStatusByProjectId(
		@ApiParam(value = "ID of project that the status belongs to.",
			required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "The status's value that needs to be updated.",
			required = true)
		@PathParam("value") int value,
		@ApiParam(value = "Status to update", required = true)
		UpdateStatusBody data) { return null; }

	@DELETE
	@Path("/{projectOid}/{value}")
	@ApiOperation(value = "Delete a status",
		notes = "Delete an existing status.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json",
				value =	"{'Success': 'true'}")}))})
	public Response deleteStatusByProjectOid(
		@ApiParam(value = "OID of project that the status belongs to.",
		required = true)
		@PathParam("projectOid") String projectOid,
		@ApiParam(value = "The status's value that needs to be deleted.",
			required = true)
		@PathParam("value") int value) { return null; }

	@DELETE
	@Path("/id/{projectId}/{value}")
	@ApiOperation(value = "Delete a status",
		notes = "Delete an existing status.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json",
				value =	"{'Success': 'true'}")}))})
	public Response deleteStatusByProjectId(
		@ApiParam(value = "ID of project that the status belongs to.",
			required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "The status's value that needs to be deleted.",
			required = true)
		@PathParam("value") int value) { return null; }
}
