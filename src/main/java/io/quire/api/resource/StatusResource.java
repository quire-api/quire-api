package io.quire.api.resource;

import io.quire.api.model.status.*;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/status")
@Api(
    value = "status",
    description = "Task statuses represent progress values for tasks."
)
@Produces({"application/json"})
public class StatusResource {

    // -------- Create --------

    @POST
    @Path("/{projectOid}")
    @ApiOperation(
        value = "Add a new task status (by project OID).",
        notes = "Creates a new task status in the specified project.",
        response = Status.class
    )
    public Response createStatus(
        @ApiParam(value = "Project OID to add the status to.", required = true)
        @PathParam("projectOid") String projectOid,
        @ApiParam(value = "Status to create.", required = true)
        CreateStatusBody data
    ) { return null; }

    @POST
    @Path("/id/{projectId}")
    @ApiOperation(
        value = "Add a new task status (by project ID).",
        notes = "Creates a new task status in the specified project.",
        response = Status.class
    )
    public Response createStatusToProject(
        @ApiParam(value = "Project ID to add the status to.", required = true)
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Status to create.", required = true)
        CreateStatusBody data
    ) { return null; }

    // -------- Read (single) --------

    @GET
    @Path("/{projectOid}/{value}")
    @ApiOperation(
        value = "Get a task status by value (project OID).",
        notes = "Returns the status record matching the given value in the specified project.",
        response = Status.class
    )
    public Response getStatusByProjectOid(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("projectOid") String projectOid,
        @ApiParam(value = "Status value to fetch.", required = true)
        @PathParam("value") int value
    ) { return null; }

    @GET
    @Path("/id/{projectId}/{value}")
    @ApiOperation(
        value = "Get a task status by value (project ID).",
        notes = "Returns the status record matching the given value in the specified project.",
        response = Status.class
    )
    public Response getStatusByProjectId(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Status value to fetch.", required = true)
        @PathParam("value") int value
    ) { return null; }

    // -------- Read (list) --------

    @GET
    @Path("/list/{projectOid}")
    @ApiOperation(
        value = "List all statuses (by project OID).",
        notes = "Returns all status records in the specified project.",
        response = Status.class,
        responseContainer = "List"
    )
    public Response getStatusesByProjectOid(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("projectOid") String projectOid
    ) { return null; }

    @GET
    @Path("/list/id/{projectId}")
    @ApiOperation(
        value = "List all statuses (by project ID).",
        notes = "Returns all status records in the specified project.",
        response = Status.class,
        responseContainer = "List"
    )
    public Response getStatusesByProjectId(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("projectId") String projectId
    ) { return null; }

    // -------- Update --------

    @PUT
    @Path("/{projectOid}/{value}")
    @ApiOperation(
        value = "Update a task status (by project OID).",
        notes = "Updates an existing status and returns the complete updated record.",
        response = Status.class
    )
    public Response updateStatusByProjectOid(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("projectOid") String projectOid,
        @ApiParam(value = "Status value to update.", required = true)
        @PathParam("value") int value,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateStatusBody data
    ) { return null; }

    @PUT
    @Path("/id/{projectId}/{value}")
    @ApiOperation(
        value = "Update a task status (by project ID).",
        notes = "Updates an existing status and returns the complete updated record.",
        response = Status.class
    )
    public Response updateStatusByProjectId(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Status value to update.", required = true)
        @PathParam("value") int value,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateStatusBody data
    ) { return null; }

    // -------- Delete --------

    @DELETE
    @Path("/{projectOid}/{value}")
    @ApiOperation(
        value = "Delete a task status (by project OID).",
        notes = "Deletes the specified status."
    )
    @ApiResponses({
        @ApiResponse(
            code = 200,
            message = "ok",
            examples = @Example({
                @ExampleProperty(
                    mediaType = "application/json",
                    value = "{'success': true}"
                )
            })
        )
    })
    public Response deleteStatusByProjectOid(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("projectOid") String projectOid,
        @ApiParam(value = "Status value to delete.", required = true)
        @PathParam("value") int value
    ) { return null; }

    @DELETE
    @Path("/id/{projectId}/{value}")
    @ApiOperation(
        value = "Delete a task status (by project ID).",
        notes = "Deletes the specified status."
    )
    @ApiResponses({
        @ApiResponse(
            code = 200,
            message = "ok",
            examples = @Example({
                @ExampleProperty(
                    mediaType = "application/json",
                    value = "{'success': true}"
                )
            })
        )
    })
    public Response deleteStatusByProjectId(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Status value to delete.", required = true)
        @PathParam("value") int value
    ) { return null; }
}
