package io.quire.api.resource;

import io.quire.api.model.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/user")
@Api(
    value = "user",
    description = "Represents a Quire account that can access organizations, projects, and tasks."
)
@Produces({"application/json"})
public class UserResource {

    @GET
    @Path("/list")
    @ApiOperation(
        value = "Get all user records.",
        notes =
            "Returns all colleagues of the current user if the user granted the app access to contacts. "
          + "Otherwise, returns only colleagues who also authorized the same app.\n"
          + "If the user did not grant contact access and none of the user’s colleagues authorized this app, "
          + "only the current user is returned.\n"
          + "The first record is always the current user.",
        response = User.class,
        responseContainer = "List"
    )
    public Response getUsers() { return null; }

    @GET
    @Path("/list/project/id/{projectId}")
    @ApiOperation(
        value = "Get all user records of the given project (by project ID).",
        notes =
            "Returns all members of the specified project by project ID.\n"
          + "If the current user did not grant the app access to contacts, only basic information is returned.\n"
          + "The first record is always the current user.",
        response = User.class,
        responseContainer = "List"
    )
    public Response getUsersOfProjectById(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("projectId") String projectId
    ) { return null; }

    @GET
    @Path("/list/project/{oid}")
    @ApiOperation(
        value = "Get all user records of the given project (by project OID).",
        notes =
            "Returns all members of the specified project by project OID.\n"
          + "If the current user did not grant the app access to contacts, only basic information is returned.\n"
          + "The first record is always the current user.",
        response = User.class,
        responseContainer = "List"
    )
    public Response getUsersOfProjectByOid(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("oid") String projectOid
    ) { return null; }

    @GET
    @Path("/id/{id}")
    @ApiOperation(
        value = "Get a user by ID or email address.",
        notes = "Returns the full user record for the given user ID, email address, or \"me\" (the current user).",
        response = User.class
    )
    public Response getUserById(
        @ApiParam(
            value = "User ID, email address, or \"me\".\nExample: \"john@gmail.com\", \"me\"",
            required = true
        )
        @PathParam("id") String id
    ) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get a user by OID.",
        notes = "Returns the full user record for the given OID.",
        response = User.class
    )
    public Response getUserByOid(
        @ApiParam(value = "User OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }
}
