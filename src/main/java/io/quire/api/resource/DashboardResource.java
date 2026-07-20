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
        + "project, organization, or folder. These endpoints manage the dashboard itself "
        + "(create, read, update, archive, remove, and restore); the widgets shown on a "
        + "dashboard are not yet manageable through the public API."
)
@Produces({"application/json"})
public class DashboardResource {

    // -------- Create --------

    @POST
    @Path("/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "Create a dashboard by owner OID.",
        notes = "Adds a new dashboard to the specified owner (`project`, `organization`, `folder`, or `smart-folder`).\n\n"
              + "Creates the dashboard container only (name, description, icon, etc.); "
              + "the widgets shown on the dashboard cannot be set through the API yet.",
        response = Dashboard.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created dashboard record.", response = Dashboard.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist."),
        @ApiResponse(code = 429, message = "Too Many Requests — dashboard quota reached.")
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

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @POST
    @Path("/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "Create a dashboard by owner ID.",
        notes = "Adds a new dashboard to the specified owner by ID (`project`, `organization`, `folder`, or `smart-folder`).\n\n"
              + "Creates the dashboard container only (name, description, icon, etc.); "
              + "the widgets shown on the dashboard cannot be set through the API yet.",
        response = Dashboard.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created dashboard record.", response = Dashboard.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist."),
        @ApiResponse(code = 429, message = "Too Many Requests — dashboard quota reached.")
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

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
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
        @ApiResponse(code = 404, message = "Not Found — dashboard or owner does not exist.")
    })
    public Response getDashboardById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false,
            example = "project"
        )
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
        notes = "Returns all dashboards for the specified owner.",
        response = Dashboard.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of dashboard records (may be empty).", response = Dashboard.class, responseContainer = "List"),
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
        notes = "Returns all dashboards for the specified owner.",
        response = Dashboard.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of dashboard records (may be empty).", response = Dashboard.class, responseContainer = "List"),
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
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — dashboard does not exist.")
    })
    public Response updateDashboardByOid(
        @ApiParam(value = "Dashboard OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateDashboardBody data,

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
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
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — dashboard or owner does not exist.")
    })
    public Response updateDashboardById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Dashboard ID.", required = true, example = "dashboard1")
        @PathParam("dashboardId") String dashboardId,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateDashboardBody data,

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
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
            + "`429 Too Many Requests` if the plan's dashboard limit is "
            + "already reached.",
        response = Dashboard.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — restored dashboard record.", response = Dashboard.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — dashboard does not exist."),
        @ApiResponse(code = 429, message = "Too Many Requests — dashboard quota reached.")
    })
    public Response undoRemoveDashboardByOid(
        @ApiParam(value = "OID of the dashboard to restore.", required = true)
        @PathParam("oid") String oid,

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
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
            + "`429 Too Many Requests` if the plan's dashboard limit is "
            + "already reached.",
        response = Dashboard.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — restored dashboard record.", response = Dashboard.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — dashboard or owner does not exist."),
        @ApiResponse(code = 429, message = "Too Many Requests — dashboard quota reached.")
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

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    // -------- Delete --------

    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete a dashboard by OID.",
        notes = "Deletes the specified dashboard.\n\n"
              + "> Note: Returns `204 No Content` regardless of whether the dashboard exists."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content")
    })
    public Response deleteDashboardByOid(
        @ApiParam(value = "Dashboard OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @DELETE
    @Path("/id/{ownerType}/{ownerId}/{dashboardId}")
    @ApiOperation(
        value = "Delete a dashboard by ID.",
        notes = "Deletes the specified dashboard.\n\n"
              + "> Note: Returns `204 No Content` regardless of whether the dashboard exists."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content")
    })
    public Response deleteDashboardById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Dashboard ID.", required = true)
        @PathParam("dashboardId") String dashboardId
    ) { return null; }
}
