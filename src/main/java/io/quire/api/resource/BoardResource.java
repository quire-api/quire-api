package io.quire.api.resource;

import io.quire.api.model.board.Board;
import io.quire.api.model.board.CreateBoardBody;
import io.quire.api.model.board.UpdateBoardBody;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/board")
@Api(value = "board", description =
		"A board is a group of columns that an user can visualize "
		+ "the progress of tasks.")
@Produces({"application/json"})
public class BoardResource {
	@POST
	@Path("/{projectOid}")
	@ApiOperation(value = "Add a new board.",
		notes = "Add a new board into a project.",
		response = Board.class)
	public Response createBoard(
		@ApiParam(value = "OID of project that this new board to be added to.",
		required = true)
		@PathParam("projectOid") String projectOid,
		@ApiParam(value = "Board to create", required = true)
		CreateBoardBody data) { return null; }

	@GET
	@Path("/{oid}")
	@ApiOperation(value = "Get an existing board by its OID",
		notes = "Returns the full board record of the given OID.",
		response = Board.class)
	public Response getBoard(
		@ApiParam(value = "OID of board that needs to be fetched.", required = true)
		@PathParam("oid") String oid) { return null; }

	@GET
	@Path("/id/{projectId}/{id}")
	@ApiOperation(value = "Get an existing board by its ID.",
		notes = "Returns the full board record of the given ID.",
		response = Board.class)
	public Response getBoardById(
		@ApiParam(value = "ID of the project that the board belongs to.", required = true)
		@PathParam("projectId") int projectId,
		@ApiParam(value = "ID of the board that needs to be fetched", required = true)
		@PathParam("id") String id) { return null; }

	@GET
	@Path("/list/{projectOid}")
	@ApiOperation(value = "Get all boards of the given project by its OID.",
		notes = "Returns all board records of the given project by its OID.",
		response = Board.class,
		responseContainer = "List")
	public Response getBoardsByProjectOid(
		@ApiParam(value = "OID of the project.", required = true)
		@PathParam("projectOid") String projectOid) { return null; }

	@GET
	@Path("/list/id/{projectId}")
	@ApiOperation(value = "Get all boards of the given project by its ID.",
		notes = "Returns all board records of the given project by its ID.",
		response = Board.class,
		responseContainer = "List")
	public Response getBoardsByProjectId(
		@ApiParam(value = "ID of project.", required = true)
		@PathParam("projectId") String projectId) { return null; }

	@PUT
	@Path("/{oid}")
	@ApiOperation(value = "Update a board.",
		notes = "Updates an existing board, and returns the complete updated record.",
		response = Board.class)
	public Response updateBoard(
		@ApiParam(value = "OID of board that needs to be updated", required = true)
		@PathParam("oid") String oid,
		@ApiParam(value = "Board to update", required = true)
		UpdateBoardBody data) { return null; }

	@DELETE
	@Path("/{oid}")
	@ApiOperation(value = "Delete a board",
		notes = "Delete an existing board of the given OID.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json", value =
				"{'Success': 'true'}")}))})
	public Response deleteBoard(
		@ApiParam(value = "OID of board that needs to be deleted", required = true)
		@PathParam("oid") String oid) { return null; }
}
