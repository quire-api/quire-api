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

    // The single-user endpoints share these.
    private static final String USER_VISIBILITY =
        "A colleague (someone sharing an organization, project or external "
      + "team with the user) who hasn't authorized this app is returned with "
      + "only `oid`, `id` and the name fields, unless the user granted the "
      + "app access to contacts. Anyone else returns `403`.";

    private static final String USER_403 =
        "Forbidden — the user is neither the current user nor a colleague.";

    // The project-member endpoints share these.
    private static final String PROJECT_MEMBERS_NOTES =
        "Returns the members of the specified project, the current user "
      + "first. External teams' members are left out, except the members of "
      + "the current user's own external teams.\n"
      + "A member who hasn't authorized this app is returned with only "
      + "`oid`, `id` and the name fields, unless the user granted the app "
      + "access to contacts.";

    private static final String PROJECT_403 =
        "Forbidden — the user can't access the project, or didn't authorize it for this app.";

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
        notes = PROJECT_MEMBERS_NOTES,
        response = User.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of project members.",
            response = User.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = PROJECT_403),
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
        notes = PROJECT_MEMBERS_NOTES,
        response = User.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of project members.",
            response = User.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = PROJECT_403),
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
        notes = "Returns the full user record for the given user ID, email address, or \"me\" (the current user). "
              + USER_VISIBILITY,
        response = User.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — user record.", response = User.class),
        @ApiResponse(code = 403, message = USER_403),
        @ApiResponse(code = 404, message = "Not Found — user does not exist.")
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
        notes = "Returns the full user record for the given OID. "
              + USER_VISIBILITY,
        response = User.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — user record.", response = User.class),
        @ApiResponse(code = 403, message = USER_403),
        @ApiResponse(code = 404, message = "Not Found — user does not exist.")
    })
    public Response getUserByOid(
        @ApiParam(value = "User OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }
}
