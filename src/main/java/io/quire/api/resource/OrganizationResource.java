package io.quire.api.resource;

import io.quire.api.model.ErrorResponse;
import io.quire.api.model.organization.*;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/organizations")
@Api(value = "organizations", description =
    "An organization represents a prioritized list of projects in Quire.")
@Produces({"application/json"})
public class OrganizationResource {
    @POST
    @ApiOperation(value = "Create an organization",
        notes = "Creates a new organization.",
        response = Organization.class)
    public Response createOrganization(
        @ApiParam(value = "Organization to create", required = true)
        CreateOrganizationBody data) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(value = "Get an organization",
        notes = "returns the complete organization record.",
        response = Organization.class)
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad parameter", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Organization not found", response = ErrorResponse.class,
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'message': 'Organization not found: Develop_group'}")}))})
    public Response getOrganization(
        @ApiParam(value = "Oid of organization that needs to be fetched", required = true)
        @PathParam("oid") String oid) { return null; }

    @PUT
    @Path("/{oid}")
    @ApiOperation(value = "Update an organization",
        notes = "A specific, existing organization can be updated by making a PUT request on the URL for that organization.\n" +
                "Returns the complete updated organization record.",
        response = Organization.class)
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad parameter", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allow", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Organization not found", response = ErrorResponse.class,
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'message': 'Organization not found: Develop_group'}")}))})
    public Response updateOrganization(
        @ApiParam(value = "Oid of organization that needs to be updated", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Organization to update", required = true)
        UpdateOrganizationBody data) {
        return null;
    }

    @DELETE
    @Path("/{oid}")
    @ApiOperation(value = "Delete an organization",
        notes = "A specific, existing organization can be deleted by making a DELETE request " +
                "on the URL for that organization.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok",
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'Success': 'true'}")})),
        @ApiResponse(code = 400, message = "Bad parameter", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allow", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Organization not found", response = ErrorResponse.class,
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'message': 'Organization not found: Develop_group'}")}))})
    public Response deleteOrganization(
        @ApiParam(value = "Oid of organization that needs to be deleted", required = true)
        @PathParam("oid") String oid) { return null; }

    @GET
    @ApiOperation(value = "Get all organizations",
        notes = "Returns the compact organization records for all organizations",
        response = OrganizationWithProjects.class,
        responseContainer = "List")
    public Response getOrganizations(){ return null; }
}
