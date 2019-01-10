package io.quire.api.resource;

import io.quire.api.model.user.UpdateUserBody;
import io.quire.api.model.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/users")
@Api(value = "users", description =
    "A user object represents an account in Quire that can be given access to various organizations, projects, and tasks.")
@Produces({"application/json"})
public class UserResource {
    @GET
    @Path("/{oid}")
    @ApiOperation(value = "Get a user",
        notes = "returns the full user record.",
        response = User.class)
    public Response getUser(
        @ApiParam(value = "Oid of user that needs to be fetched, can be one of an email address.\n" +
                "Example: \"john@gmail.com\",\"me\"", required = true)
        @PathParam("oid") String oid) { return null; }

    @PUT
    @Path("/{oid}")
    @ApiOperation(value = "Update a user",
        notes = "A specific, existing user can be updated by making a PUT request on the URL for that user.\n" +
                "Returns the complete updated user record.",
        response = User.class)
    public Response updateUser(
        @ApiParam(value = "Oid of user that needs to be updated", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "User to update", required = true)
        UpdateUserBody data) { return null; }
}
