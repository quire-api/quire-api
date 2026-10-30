package io.quire.api.resource;

import io.quire.api.model.*;
import io.quire.api.model.dashboard.*;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/dashboard")
@Api(
    value = "dashboard",
    description = "Dashboards — a dashboard groups widgets that visualize the data of a "
        + "project, organization, folder, or smart folder. These endpoints manage the dashboard itself "
        + "(create, read, update, archive, remove, and restore); the widgets shown on a "
        + "dashboard are not yet manageable through the public API."
)
@Produces({"application/json"})
public class DashboardResource {

    // The read endpoints share this.
    private static final String READ_403 =
        "Forbidden — the user can't access it, or didn't authorize it for this app.";

    // The create endpoints share this.
    private static final String CREATE_NOTES =
        "Adds a new dashboard to the specified owner (`project`, `organization`, `folder`, or `smart-folder`) "
      + "and returns it.\n\n"
      + "Creates the dashboard container only (name, description, icon, etc.); "
      + "the widgets shown on the dashboard cannot be set through the API yet.\n\n"
      + "Example — `POST /dashboard/id/project/my_project`:\n\n"
      + "```json\n"
      + "{\n"
      + "  \"name\": \"Team overview\",\n"
      + "  \"image\": \"icon-view-dashboard\"\n"
      + "}\n"
      + "```\n\n"
      + "Returns `402` if the plan's dashboard limit is reached.";

    // The list endpoints share this.
    private static final String LIST_NOTES =
        "Returns the owner's dashboards that the caller can see; one shown to "
      + "selected members only is left out for the others. Archived dashboards "
      + "are included (they carry `archivedAt`); removed ones are not.";

    // The delete endpoints share this.
    private static final String DELETE_NOTES =
        "Removes the dashboard. Restore it with "
      + "`PUT /dashboard/undo-remove/id/{ownerType}/{ownerId}/{dashboardId}` "
      + "(or `/dashboard/undo-remove/{oid}`).\n\n"
      + "Returns `204 No Content`, also when the dashboard does not exist.";

    // The by-ID get, update and delete endpoints share this.
    private static final String OWNER_TYPE_BY_ID =
        "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
      + "If omitted, `project` is assumed; e.g., `/id/my_project/dashboard1` "
      + "is equivalent to `/id/project/my_project/dashboard1`.";

    // The create, update and undo-remove endpoints share this.
    private static final String RETURN_MODE =
        "(Optional) Response shape: `full` (default) returns the full dashboard "
      + "record; `compact` returns only `{\"oid\": \"…\", \"id\": \"…\"}`, "
      + "which is cheaper — use it when you don't need the record back. "
      + "Any other value returns `400 Bad Request`.";

    // -------- Create --------

    @POST
    @Path("/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "Create a dashboard by owner OID.",
        notes = CREATE_NOTES,
        response = Dashboard.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created dashboard record.", response = Dashboard.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed, for example a `partner` that isn't an external team of the project (or any, when the owner isn't a project), or none from an external team member sharing with every member."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to create a dashboard under this owner."),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist."),
        @ApiResponse(code = 402, message = "Payment Required — dashboard quota reached.")
    })
    public Response createDashboard(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/abc123` is equivalent to `/project/abc123`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner OID.", required = true)
        @PathParam("ownerOid") String ownerOid,
        @ApiParam(value = "Dashboard to create.", required = true)
        CreateDashboardBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @POST
    @Path("/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "Create a dashboard by owner ID.",
        notes = CREATE_NOTES,
        response = Dashboard.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created dashboard record.", response = Dashboard.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed, for example a `partner` that isn't an external team of the project (or any, when the owner isn't a project), or none from an external team member sharing with every member."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to create a dashboard under this owner."),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist."),
        @ApiResponse(code = 402, message = "Payment Required — dashboard quota reached.")
    })
    public Response createDashboardById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Dashboard to create.", required = true)
        CreateDashboardBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    // -------- Read --------

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get a dashboard by OID.",
        notes = "Returns the full dashboard record.",
        response = Dashboard.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — dashboard record.", response = Dashboard.class),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — dashboard does not exist.")
    })
    public Response getDashboard(
        @ApiParam(value = "Dashboard OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @GET
    @Path("/id/{ownerType}/{ownerId}/{dashboardId}")
    @ApiOperation(
        value = "Get a dashboard by ID.",
        notes = "Returns the full dashboard record for the given owner and dashboard ID.",
        response = Dashboard.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — dashboard record.", response = Dashboard.class),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — dashboard or owner does not exist.")
    })
    public Response getDashboardById(
        @ApiParam(value = OWNER_TYPE_BY_ID, required = false, example = "project")
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Dashboard ID.", required = true, example = "dashboard1")
        @PathParam("dashboardId") String dashboardId
    ) { return null; }

    @GET
    @Path("/list/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "List dashboards by owner OID.",
        notes = LIST_NOTES,
        response = Dashboard.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of dashboard records (may be empty).", response = Dashboard.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist.")
    })
    public Response getDashboardsByOwnerOid(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/list/abc123` is equivalent to `/list/project/abc123`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner OID.", required = true)
        @PathParam("ownerOid") String ownerOid
    ) { return null; }

    @GET
    @Path("/list/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "List dashboards by owner ID.",
        notes = LIST_NOTES,
        response = Dashboard.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of dashboard records (may be empty).", response = Dashboard.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist.")
    })
    public Response getDashboardsByOwnerId(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/list/id/foo` is equivalent to `/list/id/project/foo`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId
    ) { return null; }

    // -------- Update --------

    @PUT
    @Path("/{oid}")
    @ApiOperation(
        value = "Update a dashboard by OID.",
        notes = "Updates an existing dashboard and returns the updated record.\n\n"
              + "To archive or unarchive, set `archived: true` or `archived: false` in the body.\n\n"
              + "Updates the dashboard container only; the widgets shown on the dashboard "
              + "cannot be changed through the API yet.",
        response = Dashboard.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated dashboard record.", response = Dashboard.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to modify this dashboard."),
        @ApiResponse(code = 404, message = "Not Found — dashboard does not exist.")
    })
    public Response updateDashboardByOid(
        @ApiParam(value = "Dashboard OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateDashboardBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/id/{ownerType}/{ownerId}/{dashboardId}")
    @ApiOperation(
        value = "Update a dashboard by ID.",
        notes = "Updates an existing dashboard and returns the updated record.\n\n"
              + "To archive or unarchive, set `archived: true` or `archived: false` in the body.\n\n"
              + "Updates the dashboard container only; the widgets shown on the dashboard "
              + "cannot be changed through the API yet.",
        response = Dashboard.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated dashboard record.", response = Dashboard.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to modify this dashboard."),
        @ApiResponse(code = 404, message = "Not Found — dashboard or owner does not exist.")
    })
    public Response updateDashboardById(
        @ApiParam(value = OWNER_TYPE_BY_ID, required = false, example = "project")
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Dashboard ID.", required = true, example = "dashboard1")
        @PathParam("dashboardId") String dashboardId,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateDashboardBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    // -------- Undo remove --------

    @PUT
    @Path("/undo-remove/{oid}")
    @ApiOperation(
        value = "Undo the removal of a dashboard by OID.",
        notes = "Restores a previously-removed dashboard. "
            + "Idempotent: if the dashboard is not currently removed, this is "
            + "a no-op and returns the current dashboard record.\n\n"
            + "Subject to the dashboard-per-owner quota: may return "
            + "`402 Payment Required` if the plan's dashboard limit is "
            + "already reached.",
        response = Dashboard.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — restored dashboard record.", response = Dashboard.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to restore this dashboard."),
        @ApiResponse(code = 404, message = "Not Found — dashboard does not exist."),
        @ApiResponse(code = 402, message = "Payment Required — dashboard quota reached.")
    })
    public Response undoRemoveDashboardByOid(
        @ApiParam(value = "OID of the dashboard to restore.", required = true)
        @PathParam("oid") String oid,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/undo-remove/id/{ownerType}/{ownerId}/{dashboardId}")
    @ApiOperation(
        value = "Undo the removal of a dashboard by ID.",
        notes = "Restores a previously-removed dashboard. "
            + "Idempotent: if the dashboard is not currently removed, this is "
            + "a no-op and returns the current dashboard record.\n\n"
            + "Subject to the dashboard-per-owner quota: may return "
            + "`402 Payment Required` if the plan's dashboard limit is "
            + "already reached.",
        response = Dashboard.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — restored dashboard record.", response = Dashboard.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to restore this dashboard."),
        @ApiResponse(code = 404, message = "Not Found — dashboard or owner does not exist."),
        @ApiResponse(code = 402, message = "Payment Required — dashboard quota reached.")
    })
    public Response undoRemoveDashboardById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/undo-remove/id/foo/bar` "
                  + "is equivalent to `/undo-remove/id/project/foo/bar`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Dashboard ID.", required = true, example = "dashboard1")
        @PathParam("dashboardId") String dashboardId,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    // -------- Delete --------

    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete a dashboard by OID.",
        notes = DELETE_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to remove this dashboard.")
    })
    public Response deleteDashboardByOid(
        @ApiParam(value = "Dashboard OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @DELETE
    @Path("/id/{ownerType}/{ownerId}/{dashboardId}")
    @ApiOperation(
        value = "Delete a dashboard by ID.",
        notes = DELETE_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to remove this dashboard.")
    })
    public Response deleteDashboardById(
        @ApiParam(value = OWNER_TYPE_BY_ID, required = false, example = "project")
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Dashboard ID.", required = true, example = "dashboard1")
        @PathParam("dashboardId") String dashboardId
    ) { return null; }
}
