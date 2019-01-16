package io.quire.api.resource;

import io.quire.api.model.externalteam.ExternalTeam;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/external_teams")
@Api(value = "external teams", description =
    "A external team object.")
@Produces({"application/json"})
public class ExternalTeamResource {
    @GET
    @Path("/{oid}")
    @ApiOperation(value = "Get an external team",
        notes = "returns the full external team record.",
        response = ExternalTeam.class)
    public Response getExternalTeam(
        @ApiParam(value = "Oid of external team that needs to be fetched.", required = true)
        @PathParam("oid") String oid) { return null; }

    @DELETE
    @Path("/{oid}")
    @ApiOperation(value = "Delete an external team",
        notes = "A specific, existing external team can be deleted by making a DELETE request " +
                "on the URL for that external team.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok",
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'Success': 'true'}")}))})
    public Response deleteExternalTeam(
        @ApiParam(value = "Oid of external team that needs to be deleted", required = true)
        @PathParam("oid") String oid) { return null; }
}
