package io.quire.api.resource;

import io.quire.api.model.state.UpdateStateBody;
import io.quire.api.model.state.CreateStateBody;
import io.quire.api.model.state.State;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/state")
@Api(value = "state", description =
	"A state is a value to indicate the progress of a task.")
@Produces({"application/json"})
public class StateResource {
	@POST
	@Path("/{projectOid}")
	@ApiOperation(value = "Add a new state.",
		notes = "Add a new state into a project.",
		response = State.class)
	public Response createState(
		@ApiParam(value = "OID of project that this new state to be added to.",
		required = true)
		@PathParam("projectOid") String projectOid,
		@ApiParam(value = "State to create", required = true)
		CreateStateBody data) { return null; }

	@POST
	@Path("/id/{projectId}")
	@ApiOperation(value = "Add a new state.",
		notes = "Add a new state into a project.",
		response = State.class)
	public Response createStateToProject(
		@ApiParam(value = "ID of project that this new state to be added to.",
		required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "State to create", required = true)
		CreateStateBody data) { return null; }

	@GET
	@Path("/{projectOid}/{value}")
	@ApiOperation(value = "Get the state of the given value.",
		notes = "Returns the full state record of the given value.",
		response = State.class)
	public Response getState(
		@ApiParam(value = "OID of project that the state belongs to.",
		required = true)
		@PathParam("projectOid") String projectOid,
		@ApiParam(value = "The state's value that needs to be fetched.",
			required = true)
		@PathParam("value") int value) { return null; }

	@GET
	@Path("/id/{projectId}/{value}")
	@ApiOperation(value = "Get the state of the given value.",
		notes = "Returns the full state record of the given value.",
		response = State.class)
	public Response getState(
		@ApiParam(value = "ID of project that the state belongs to.",
			required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "The state's value that needs to be fetched.",
			required = true)
		@PathParam("value") int value) { return null; }

	@GET
	@Path("/list/{projectOid}")
	@ApiOperation(value = "Get all states of the given project by its OID.",
		notes = "Returns all state records of the given project by its OID.",
		response = State.class,
        responseContainer = "List")
	public Response getStatesByProjectOid(
		@ApiParam(value = "OID of the project.", required = true)
		@PathParam("projectOid") String projectOid) { return null; }

	@GET
	@Path("/list/id/{projectId}")
	@ApiOperation(value = "Get all states of the given project by its ID.",
		notes = "Returns all state records of the given project by its ID.",
		response = State.class,
        responseContainer = "List")
	public Response getStatesByProjectId(
		@ApiParam(value = "ID of project.", required = true)
		@PathParam("projectId") String projectId) { return null; }

	@PUT
	@Path("/{projectOid}/{value}")
	@ApiOperation(value = "Update a state.",
		notes = "Updates an existing state, and returns the complete updated record.",
		response = State.class)
	public Response updateState(
		@ApiParam(value = "OID of project that the state belongs to.",
		required = true)
		@PathParam("projectOid") String projectOid,
		@ApiParam(value = "The state's value that needs to be updated.",
			required = true)
		@PathParam("value") int value,
		@ApiParam(value = "State to update", required = true)
		UpdateStateBody data) { return null; }

	@PUT
	@Path("/id/{projectId}/{value}")
	@ApiOperation(value = "Update a state.",
		notes = "Updates an existing state, and returns the complete updated record.",
		response = State.class)
	public Response updateState(
		@ApiParam(value = "ID of project that the state belongs to.",
			required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "The state's value that needs to be updated.",
			required = true)
		@PathParam("value") int value,
		@ApiParam(value = "State to update", required = true)
		UpdateStateBody data) { return null; }

	@DELETE
	@Path("/{projectOid}/{value}")
	@ApiOperation(value = "Delete a state",
		notes = "Delete an existing state.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json",
				value =	"{'Success': 'true'}")}))})
	public Response deleteState(
		@ApiParam(value = "OID of project that the state belongs to.",
		required = true)
		@PathParam("projectOid") String projectOid,
		@ApiParam(value = "The state's value that needs to be deleted.",
			required = true)
		@PathParam("value") int value) { return null; }

	@DELETE
	@Path("/id/{projectId}/{value}")
	@ApiOperation(value = "Delete a state",
		notes = "Delete an existing state.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json",
				value =	"{'Success': 'true'}")}))})
	public Response deleteState(
		@ApiParam(value = "ID of project that the state belongs to.",
			required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "The state's value that needs to be deleted.",
			required = true)
		@PathParam("value") int value) { return null; }
}
