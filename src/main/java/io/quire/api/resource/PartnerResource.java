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
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — external team record.",
            response = Partner.class),
        @ApiResponse(code = 404, message = "Not Found — external team does not exist.")
    })
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
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of external teams (may be empty).",
            response = Partner.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
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
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of external teams (may be empty).",
            response = Partner.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response getPartnersByProjectId(
        @ApiParam(value = "Project ID.", example = "my_project", required = true)
        @PathParam("projectId") String projectId
    ) { return null; }

/* Not supported yet (security concern)
    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete an external team.",
        notes = "Deletes the specified external team.\n\n"
            + "> Note: Returns `204 No Content` regardless of whether the external team exists."
    )
    @ApiResponses({
        @ApiResponse(
            code = 204,
            message = "No Content"
        )
    })
    public Response deletePartner(
        @ApiParam(value = "External team OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }
*/
}
