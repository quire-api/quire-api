package io.quire.api.resource;

import io.quire.api.model.board.Board;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/board")
@Api(value = "board", description =
        "A board object.")
@Produces({"application/json"})
public class BoardResource {
    @GET
    @Path("/{oid}")
    @ApiOperation(value = "Get an board",
        notes = "returns the full board record.",
        response = Board.class)
    public Response getBoard(
        @ApiParam(value = "Oid of board that needs to be fetched.", required = true)
        @PathParam("oid") String oid) { return null; }

    @DELETE
    @Path("/{oid}")
    @ApiOperation(value = "Delete a board",
        notes = "A specific, existing board can be deleted by making a DELETE request " +
                "on the URL for that board.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok",
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'Success': 'true'}")}))})
    public Response deleteBoard(
        @ApiParam(value = "Oid of external team that needs to be deleted", required = true)
        @PathParam("oid") String oid) { return null; }
}
