package io.quire.api.resource;

import io.quire.api.model.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/user")
@Api(value = "user", description =
    "A user object represents an account in Quire that can be given access to various organizations, projects, and tasks.")
@Produces({"application/json"})
public class UserResource {

    @GET
    @Path("/list")
    @ApiOperation(value = "Get all colleagues of the current user if "
        + "he granted the app to access his contacts. "
        + "Otherwise, it returns only colleagues who also authorized "
        + "the same app. "
        + "If the current user didn't grant the access of his contacts "
        + "and none of his collegues authorized this app, only the current "
        + "user's record will be returned.",
        notes = "Returns all required user records. "
        + "The first record must be the current user.",
        response = List<User>.class,
        responseContainer = "List")
    public Response getUsers(){ return null; }

    @GET
    @Path("/id/{id}")
    @ApiOperation(value = "Get a user by its ID.",
        notes = "returns the full user record.",
        response = User.class)
    public Response getUserById(
        @ApiParam(value = "ID, email address or \"me\" of user that needs to be fetched.\n" +
                "Example: \"john@gmail.com\",\"me\"", required = true)
        @PathParam("id") String id) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(value = "Get a user by its OID.",
        notes = "returns the full user record.",
        response = User.class)
    public Response getUser(
        @ApiParam(value = "OID of user that needs to be fetched", required = true)
        @PathParam("oid") String oid) { return null; }
}
