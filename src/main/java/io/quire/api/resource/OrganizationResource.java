package io.quire.api.resource;

import io.quire.api.model.ErrorResponse;
import io.quire.api.model.organization.*;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/organization")
@Api(value = "organizations", description =
    "An organization is a group of projects where members collaborate at once.")
@Produces({"application/json"})
public class OrganizationResource {
/* Not supported yet
    @POST
    @ApiOperation(value = "Create an organization.",
        notes = "Creates a new organization, and returns the complete record of "
            + "new crated organization".,
        response = Organization.class)
    public Response createOrganization(
        @ApiParam(value = "Organization to create", required = true)
        CreateOrganizationBody data) { return null; }
*/
    @GET
    @Path("/list")
    @ApiOperation(value = "Get all organizations.",
        notes = "Returns the organization records for all organizations "
            + "that the user can access",
        response = List<Organization>.class,
        responseContainer = "List")
    public Response getOrganizations(){ return null; }

    @GET
    @Path("/id/{id}")
    @ApiOperation(value = "Get an organization by its ID.",
        notes = "Returns the complete organization record.",
        response = Organization.class)
    public Response getOrganizationById(
        @ApiParam(value = "ID of organization that needs to be fetched", required = true)
        @PathParam("id") String id) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(value = "Get an organization by its OID.",
        notes = "returns the complete organization record.",
        response = Organization.class)
    public Response getOrganization(
        @ApiParam(value = "OID of organization that needs to be fetched", required = true)
        @PathParam("oid") String oid) { return null; }

/* Not supported yet
    @PUT
    @Path("/{oid}")
    @ApiOperation(value = "Update an organization",
        notes = "A specific, existing organization can be updated by making a PUT request on the URL for that organization.\n" +
                "Returns the complete updated organization record.",
        response = Organization.class)
    public Response updateOrganization(
        @ApiParam(value = "OID of organization that needs to be updated", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Organization to update", required = true)
        UpdateOrganizationBody data) { return null; }

    @DELETE
    @Path("/{oid}")
    @ApiOperation(value = "Delete an organization",
        notes = "A specific, existing organization can be deleted by making a DELETE request " +
                "on the URL for that organization.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok",
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'Success': 'true'}")}))})
    public Response deleteOrganization(
        @ApiParam(value = "OID of organization that needs to be deleted", required = true)
        @PathParam("oid") String oid) { return null; }
*/
}
