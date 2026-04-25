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
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — newly created status.", response = Status.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to modify the project."),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response createStatus(
        @ApiParam(value = "Project OID to add the status to.", required = true)
        @PathParam("projectOid") String projectOid,
        @ApiParam(value = "Status to create.", required = true)
        CreateStatusBody data,

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
    @Path("/id/{projectId}")
    @ApiOperation(
        value = "Add a new task status (by project ID).",
        notes = "Creates a new task status in the specified project.",
        response = Status.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — newly created status.", response = Status.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to modify the project."),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response createStatusToProject(
        @ApiParam(value = "Project ID to add the status to.", example = "my_project", required = true)
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Status to create.", required = true)
        CreateStatusBody data,

        @ApiParam(
            value = "(Optional) Response shape: `full` (default) for the "
                + "full record, or `compact` for identifiers only. "
                + "See API description for `?return=` semantics.",
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    // -------- Read (single) --------

    @GET
    @Path("/{projectOid}/{value}")
    @ApiOperation(
        value = "Get a task status by value (project OID).",
        notes = "Returns the status record matching the given value in the specified project.",
        response = Status.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — status record.", response = Status.class),
        @ApiResponse(code = 404, message = "Not Found — project or status does not exist.")
    })
    public Response getStatusByProjectOid(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("projectOid") String projectOid,
        @ApiParam(value = "Status value to fetch.", example = "100", required = true)
        @PathParam("value") int value
    ) { return null; }

    @GET
    @Path("/id/{projectId}/{value}")
    @ApiOperation(
        value = "Get a task status by value (project ID).",
        notes = "Returns the status record matching the given value in the specified project.",
        response = Status.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — status record.", response = Status.class),
        @ApiResponse(code = 404, message = "Not Found — project or status does not exist.")
    })
    public Response getStatusByProjectId(
        @ApiParam(value = "Project ID.", example = "my_project", required = true)
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Status value to fetch.", example = "100", required = true)
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
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of statuses (may be empty).",
            response = Status.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
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
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of statuses (may be empty).",
            response = Status.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response getStatusesByProjectId(
        @ApiParam(value = "Project ID.", example = "my_project", required = true)
        @PathParam("projectId") String projectId
    ) { return null; }

    // -------- Update --------

    @PUT
    @Path("/{projectOid}/{value}")
    @ApiOperation(
        value = "Update a task status (by project OID).",
        notes = "Updates an existing status and returns the complete updated record. "
              + "Omitted body fields are left unchanged.",
        response = Status.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated status record.", response = Status.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to modify the project."),
        @ApiResponse(code = 404, message = "Not Found — project or status does not exist.")
    })
    public Response updateStatusByProjectOid(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("projectOid") String projectOid,
        @ApiParam(value = "Status value to update.", example = "100", required = true)
        @PathParam("value") int value,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateStatusBody data,

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
    @Path("/id/{projectId}/{value}")
    @ApiOperation(
        value = "Update a task status (by project ID).",
        notes = "Updates an existing status and returns the complete updated record. "
              + "Omitted body fields are left unchanged.",
        response = Status.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated status record.", response = Status.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to modify the project."),
        @ApiResponse(code = 404, message = "Not Found — project or status does not exist.")
    })
    public Response updateStatusByProjectId(
        @ApiParam(value = "Project ID.", example = "my_project", required = true)
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Status value to update.", example = "100", required = true)
        @PathParam("value") int value,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateStatusBody data,

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
    @Path("/{projectOid}/{value}")
    @ApiOperation(
        value = "Delete a task status (by project OID).",
        notes = "Deletes the specified status.\n\n"
              + "> Note: Returns `204 No Content` regardless of whether the status exists."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content")
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
        notes = "Deletes the specified status.\n\n"
              + "> Note: Returns `204 No Content` regardless of whether the status exists."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content")
    })
    public Response deleteStatusByProjectId(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Status value to delete.", required = true)
        @PathParam("value") int value
    ) { return null; }
}
