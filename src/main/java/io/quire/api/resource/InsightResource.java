package io.quire.api.resource;

import io.quire.api.model.*;
import io.quire.api.model.insight.*;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/insight")
@Api(
    value = "insight",
    description = "Insight views — configurable task views with filters and columns."
)
@Produces({"application/json"})
public class InsightResource {

    // -------- Create --------

    @POST
    @Path("/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "Create an insight view by owner OID.",
        notes = "Adds a new insight view to the specified owner (`project`, `organization`, `folder`, or `smart-folder`).",
        response = Insight.class
    )
    public Response createInsight(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/abc123` is equivalent to `/project/abc123`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner OID.", required = true)
        @PathParam("ownerOid") String ownerOid,
        @ApiParam(value = "Insight view to create.", required = true)
        CreateInsightBody data
    ) { return null; }

    @POST
    @Path("/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "Create an insight view by owner ID.",
        notes = "Adds a new insight view to the specified owner by ID (`project`, `organization`, `folder`, or `smart-folder`).",
        response = Insight.class
    )
    public Response createInsightById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight view to create.", required = true)
        CreateInsightBody data
    ) { return null; }

    // -------- Read --------

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get an insight view by OID.",
        notes = "Returns the full insight record.",
        response = Insight.class
    )
    public Response getInsight(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @GET
    @Path("/id/{ownerType}/{ownerId}/{insightId}")
    @ApiOperation(
        value = "Get an insight view by ID.",
        notes = "Returns the full insight record for the given owner and insight ID.",
        response = Insight.class
    )
    public Response getInsightById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true)
        @PathParam("insightId") String insightId
    ) { return null; }

    @GET
    @Path("/list/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "List insight views by owner OID.",
        notes = "Returns all insight views for the specified owner.",
        response = Insight.class,
        responseContainer = "List"
    )
    public Response getInsightsByOwnerOid(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/list/abc123` is equivalent to `/list/project/abc123`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner OID.", required = true)
        @PathParam("ownerOid") String ownerOid
    ) { return null; }

    @GET
    @Path("/list/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "List insight views by owner ID.",
        notes = "Returns all insight views for the specified owner.",
        response = Insight.class,
        responseContainer = "List"
    )
    public Response getInsightsByOwnerId(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/list/id/foo` is equivalent to `/list/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true)
        @PathParam("ownerId") String ownerId
    ) { return null; }

    // -------- Update --------

    @PUT
    @Path("/{oid}")
    @ApiOperation(
        value = "Update an insight view by OID.",
        notes = "Updates an existing insight view and returns the updated record.",
        response = Insight.class
    )
    public Response updateInsightByOid(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateInsightBody data
    ) { return null; }

    @PUT
    @Path("/id/{ownerType}/{ownerId}/{insightId}")
    @ApiOperation(
        value = "Update an insight view by ID.",
        notes = "Updates an existing insight view and returns the updated record.",
        response = Insight.class
    )
    public Response updateInsightById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true)
        @PathParam("insightId") String insightId,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateInsightBody data
    ) { return null; }

    // -------- Undo remove --------

    @PUT
    @Path("/undo-remove/{oid}")
    @ApiOperation(
        value = "Undo the removal of an insight view by OID.",
        notes = "Restores a previously-removed insight view. "
            + "Idempotent: if the insight is not currently removed, this is "
            + "a no-op and returns the current insight record.\n\n"
            + "Subject to the insight-per-owner quota: may return "
            + "`429 Too Many Requests` if the plan's insight limit is "
            + "already reached.",
        response = Insight.class
    )
    public Response undoRemoveInsightByOid(
        @ApiParam(value = "OID of the insight to restore.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @PUT
    @Path("/undo-remove/id/{ownerType}/{ownerId}/{insightId}")
    @ApiOperation(
        value = "Undo the removal of an insight view by ID.",
        notes = "Restores a previously-removed insight view. "
            + "Idempotent: if the insight is not currently removed, this is "
            + "a no-op and returns the current insight record.\n\n"
            + "Subject to the insight-per-owner quota: may return "
            + "`429 Too Many Requests` if the plan's insight limit is "
            + "already reached.",
        response = Insight.class
    )
    public Response undoRemoveInsightById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/undo-remove/id/foo/bar` "
                  + "is equivalent to `/undo-remove/id/project/foo/bar`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true)
        @PathParam("insightId") String insightId
    ) { return null; }

    // -------- Delete --------

    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete an insight view by OID.",
        notes = "Deletes the specified insight view.\n\n"
            + "> Note: Returns `204 No Content` regardless of whether the insight exists."
    )
    @ApiResponses({
        @ApiResponse(
            code = 204,
            message = "No Content"
        )
    })
    public Response deleteInsightByOid(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @DELETE
    @Path("/id/{ownerType}/{ownerId}/{insightId}")
    @ApiOperation(
        value = "Delete an insight view by ID.",
        notes = "Deletes the specified insight view.\n\n"
            + "> Note: Returns `204 No Content` regardless of whether the insight exists."
    )
    @ApiResponses({
        @ApiResponse(
            code = 204,
            message = "No Content"
        )
    })
    public Response deleteInsightById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true)
        @PathParam("insightId") String insightId
    ) { return null; }
}
