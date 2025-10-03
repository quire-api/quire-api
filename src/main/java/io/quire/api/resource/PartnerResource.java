package io.quire.api.resource;

import io.quire.api.model.externalteam.Partner;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/partner")
@Api(
    value = "partner",
    description = "An external team (aka a partner) is a group of users who can access only the tasks assigned to that team."
)
@Produces({"application/json"})
public class PartnerResource {

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get an external team by OID.",
        notes = "Returns the full external team record for the given OID.",
        response = Partner.class
    )
    public Response getPartnerByOid(
        @ApiParam(value = "External team OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @GET
    @Path("/list/{projectOid}")
    @ApiOperation(
        value = "List external teams by project OID.",
        notes = "Returns all external teams in the specified project (by project OID).",
        response = Partner.class,
        responseContainer = "List"
    )
    public Response getPartnersByProjectOid(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("projectOid") String projectOid
    ) { return null; }

    @GET
    @Path("/list/id/{projectId}")
    @ApiOperation(
        value = "List external teams by project ID.",
        notes = "Returns all external teams in the specified project (by project ID).",
        response = Partner.class,
        responseContainer = "List"
    )
    public Response getPartnersByProjectId(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("projectId") String projectId
    ) { return null; }

/* Not supported yet (security concern)
    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete an external team.",
        notes = "Deletes the specified external team."
    )
    @ApiResponses({
        @ApiResponse(
            code = 200,
            message = "ok",
            examples = @Example({
                @ExampleProperty(mediaType = "application/json", value = "{'success': true}")
            })
        )
    })
    public Response deletePartner(
        @ApiParam(value = "External team OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }
*/
}
