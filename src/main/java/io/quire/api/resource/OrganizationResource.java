package io.quire.api.resource;

import io.quire.api.model.organization.*;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/organization")
@Api(
    value = "organization",
    description = "An organization is a group of projects where members collaborate."
)
@Produces({"application/json"})
public class OrganizationResource {

/* Not supported yet (security concern)
    @POST
    @ApiOperation(
        value = "Create an organization.",
        notes = "Creates a new organization and returns the complete record of the newly created organization.",
        response = Organization.class
    )
    public Response createOrganization(
        @ApiParam(value = "Organization to create.", required = true)
        CreateOrganizationBody data
    ) { return null; }
*/

    @GET
    @Path("/list")
    @ApiOperation(
        value = "Get all organizations.",
        notes =
            "Returns organization records that the current user can authorize for this application, "
          + "or already has authorized.",
        response = Organization.class,
        responseContainer = "List"
    )
    public Response getOrganizations() { return null; }

    @GET
    @Path("/id/{id}")
    @ApiOperation(
        value = "Get an organization by ID.",
        notes = "Returns the complete organization record for the given ID.",
        response = OrganizationWithPlan.class
    )
    public Response getOrganizationById(
        @ApiParam(value = "Organization ID.", required = true)
        @PathParam("id") String id
    ) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get an organization by OID.",
        notes = "Returns the complete organization record for the given OID.",
        response = OrganizationWithPlan.class
    )
    public Response getOrganizationByOid(
        @ApiParam(value = "Organization OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @PUT
    @Path("/id/{id}")
    @ApiOperation(
        value = "Update an organization by ID.",
        notes = "Updates an existing organization and returns the complete updated record.",
        response = Organization.class
    )
    public Response updateOrganizationById(
        @ApiParam(value = "Organization ID.", required = true)
        @PathParam("id") String id,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateOrganizationBody data
    ) { return null; }

    @PUT
    @Path("/{oid}")
    @ApiOperation(
        value = "Update an organization by OID.",
        notes = "Updates an existing organization and returns the complete updated record.",
        response = Organization.class
    )
    public Response updateOrganizationByOid(
        @ApiParam(value = "Organization OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateOrganizationBody data
    ) { return null; }

/* Not supported yet (security concern)
    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete an organization.",
        notes = "Deletes the specified organization."
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ok",
            examples = @Example({
                @ExampleProperty(mediaType = "application/json", value = "{'success': true}")
            }))
    })
    public Response deleteOrganization(
        @ApiParam(value = "Organization OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }
*/
}
