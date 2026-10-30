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

    // The organization read endpoints share this.
    private static final String ORG_403 =
        "Forbidden — the user can't access the organization, or didn't authorize it for this app.";

    // The organization update endpoints share these.
    private static final String ORG_UPDATE_RIGHT =
        "Who may change what: `name` and `description` need an admin of the "
      + "organization, and an app with the *Manage profiles* permission. "
      + "`followers`, `addFollowers` and `removeFollowers` need edit access "
      + "to the organization itself (being a member of one of its projects "
      + "isn't enough), and an app with the *Manage tasks* permission — "
      + "even for only `\"me\"`: unlike a project, an organization can't "
      + "be followed with read access. A body mixing them needs the rights "
      + "of each; otherwise `403`.";

    private static final String ORG_UPDATE_403 =
        "Forbidden — the user lacks the role, or the app the permission, a "
      + "given field needs: an admin and *Manage profiles* for `name` and "
      + "`description`; edit access and *Manage tasks* for the followers.";

    private static final String RETURN_ORG =
        "(Optional) Response shape: `full` (default) for the full "
      + "organization record, or `compact` for just "
      + "`{\"oid\": …, \"id\": …}`. "
      + "Any other value returns `400 Bad Request`.";

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
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of organizations (may be empty).",
            response = Organization.class, responseContainer = "List")
    })
    public Response getOrganizations() { return null; }

    @GET
    @Path("/id/{id}")
    @ApiOperation(
        value = "Get an organization by ID.",
        notes = "Returns the complete organization record for the given ID.",
        response = OrganizationWithPlan.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — organization record with plan info.",
            response = OrganizationWithPlan.class),
        @ApiResponse(code = 403, message = ORG_403),
        @ApiResponse(code = 404, message = "Not Found — organization does not exist.")
    })
    public Response getOrganizationById(
        @ApiParam(value = "Organization ID.", example = "my_org", required = true)
        @PathParam("id") String id
    ) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get an organization by OID.",
        notes = "Returns the complete organization record for the given OID.",
        response = OrganizationWithPlan.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — organization record with plan info.",
            response = OrganizationWithPlan.class),
        @ApiResponse(code = 403, message = ORG_403),
        @ApiResponse(code = 404, message = "Not Found — organization does not exist.")
    })
    public Response getOrganizationByOid(
        @ApiParam(value = "Organization OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @PUT
    @Path("/id/{id}")
    @ApiOperation(
        value = "Update an organization by ID.",
        notes = "Updates an existing organization and returns the complete updated record. "
              + "Omitted body fields are left unchanged.\n\n"
              + ORG_UPDATE_RIGHT,
        response = Organization.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated organization record.",
            response = Organization.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = ORG_UPDATE_403),
        @ApiResponse(code = 404, message = "Not Found — organization does not exist.")
    })
    public Response updateOrganizationById(
        @ApiParam(value = "Organization ID.", example = "my_org", required = true)
        @PathParam("id") String id,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateOrganizationBody data,

        @ApiParam(
            value = RETURN_ORG,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/{oid}")
    @ApiOperation(
        value = "Update an organization by OID.",
        notes = "Updates an existing organization and returns the complete updated record. "
              + "Omitted body fields are left unchanged.\n\n"
              + ORG_UPDATE_RIGHT,
        response = Organization.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated organization record.",
            response = Organization.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = ORG_UPDATE_403),
        @ApiResponse(code = 404, message = "Not Found — organization does not exist.")
    })
    public Response updateOrganizationByOid(
        @ApiParam(value = "Organization OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateOrganizationBody data,

        @ApiParam(
            value = RETURN_ORG,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

/* Not supported yet (security concern)
    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete an organization.",
        notes = "Deletes the specified organization.\n\n"
            + "> Note: Returns `204 No Content` regardless of whether the organization exists."
    )
    @ApiResponses({
        @ApiResponse(
            code = 204,
            message = "No Content"
        )
    })
    public Response deleteOrganization(
        @ApiParam(value = "Organization OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }
*/
}
