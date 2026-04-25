package io.quire.api.resource;

import io.quire.api.model.user.User;
import io.swagger.annotations.*;

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
    @ApiResponses({
        @ApiResponse(code = 200,
            message = "OK — list of user records; first entry is always the current user.",
            response = User.class, responseContainer = "List")
    })
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
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of project members.",
            response = User.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response getUsersOfProjectById(
        @ApiParam(value = "Project ID.", example = "my_project", required = true)
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
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of project members.",
            response = User.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
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
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — user record.", response = User.class),
        @ApiResponse(code = 404, message = "Not Found — user does not exist, or is not visible to the caller.")
    })
    public Response getUserById(
        @ApiParam(
            value = "User ID, email address, or \"me\".",
            example = "john@example.com",
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
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — user record.", response = User.class),
        @ApiResponse(code = 404, message = "Not Found — user does not exist, or is not visible to the caller.")
    })
    public Response getUserByOid(
        @ApiParam(value = "User OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }
}
