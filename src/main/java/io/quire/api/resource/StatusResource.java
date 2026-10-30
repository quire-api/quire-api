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

    // The status write endpoints share these.
    private static final String STATUS_RIGHT =
        "Requires a user who can manage the project's statuses, and an app "
      + "with the *Manage statuses* permission; otherwise `403`. An external "
      + "team's members can't.";

    private static final String STATUS_403 =
        "Forbidden — the user can't manage the project's statuses, or the app lacks the *Manage statuses* permission.";

    // The status read endpoints share this.
    private static final String READ_403 =
        "Forbidden — the user can't access it, or didn't authorize it for this app.";

    private static final String RETURN_STATUS =
        "(Optional) Response shape: `full` (default) for the full record, "
      + "or `compact` for just `{\"value\": …}`. "
      + "Any other value returns `400 Bad Request`.";

    private static final String DUP_NAME =
        "A `name` already in use, ignoring case, gets a numbered suffix, "
      + "e.g. `Doing (1)`.";

    private static final String CREATE_STATUS_NOTES =
        "Creates a new task status in the specified project and returns it. "
      + "A `value` already in use in the project returns `409`. " + DUP_NAME + "\n\n"
      + STATUS_RIGHT + "\n\n"
      + "Example: `{\"name\": \"Review\", \"value\": 70, \"color\": \"35\"}`.\n\n"
      + "A status over the plan's status limit returns `402`.";

    private static final String UPDATE_STATUS_NOTES =
        "Updates an existing status and returns the complete updated record. "
      + "Omitted body fields are left unchanged. " + DUP_NAME + " "
      + "The value of `0` (the initial status) and `100` (completed) can't "
      + "change (`403`); changing `value` to one already in use returns "
      + "`409`. With `?return=compact`, the response holds the value after "
      + "the update.\n\n"
      + STATUS_RIGHT;

    private static final String DELETE_STATUS_NOTES =
        "Deletes the specified status; its tasks move to the next lower "
      + "status. `0` (the initial status) and `100` (completed) can't be "
      + "deleted (`403`).\n\n"
      + STATUS_RIGHT + "\n\n"
      + "Returns `204 No Content`, also when the status doesn't exist.";

    // -------- Create --------

    @POST
    @Path("/{projectOid}")
    @ApiOperation(
        value = "Add a new task status (by project OID).",
        notes = CREATE_STATUS_NOTES,
        response = Status.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — newly created status.", response = Status.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 402, message = "Payment Required — plan's status limit is reached (`code` `469`)."),
        @ApiResponse(code = 403, message = STATUS_403),
        @ApiResponse(code = 404, message = "Not Found — project does not exist."),
        @ApiResponse(code = 409, message = "Conflict — a status with this `value` already exists.")
    })
    public Response createStatus(
        @ApiParam(value = "Project OID to add the status to.", required = true)
        @PathParam("projectOid") String projectOid,
        @ApiParam(value = "Status to create.", required = true)
        CreateStatusBody data,

        @ApiParam(
            value = RETURN_STATUS,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @POST
    @Path("/id/{projectId}")
    @ApiOperation(
        value = "Add a new task status (by project ID).",
        notes = CREATE_STATUS_NOTES,
        response = Status.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — newly created status.", response = Status.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 402, message = "Payment Required — plan's status limit is reached (`code` `469`)."),
        @ApiResponse(code = 403, message = STATUS_403),
        @ApiResponse(code = 404, message = "Not Found — project does not exist."),
        @ApiResponse(code = 409, message = "Conflict — a status with this `value` already exists.")
    })
    public Response createStatusToProject(
        @ApiParam(value = "Project ID to add the status to.", example = "my_project", required = true)
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Status to create.", required = true)
        CreateStatusBody data,

        @ApiParam(
            value = RETURN_STATUS,
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
        @ApiResponse(code = 403, message = READ_403),
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
        @ApiResponse(code = 403, message = READ_403),
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
        @ApiResponse(code = 403, message = READ_403),
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
        @ApiResponse(code = 403, message = READ_403),
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
        notes = UPDATE_STATUS_NOTES,
        response = Status.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated status record.", response = Status.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = STATUS_403),
        @ApiResponse(code = 404, message = "Not Found — project or status does not exist."),
        @ApiResponse(code = 409, message = "Conflict — another status already has the new `value`.")
    })
    public Response updateStatusByProjectOid(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("projectOid") String projectOid,
        @ApiParam(value = "Status value to update.", example = "100", required = true)
        @PathParam("value") int value,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateStatusBody data,

        @ApiParam(
            value = RETURN_STATUS,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/id/{projectId}/{value}")
    @ApiOperation(
        value = "Update a task status (by project ID).",
        notes = UPDATE_STATUS_NOTES,
        response = Status.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated status record.", response = Status.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = STATUS_403),
        @ApiResponse(code = 404, message = "Not Found — project or status does not exist."),
        @ApiResponse(code = 409, message = "Conflict — another status already has the new `value`.")
    })
    public Response updateStatusByProjectId(
        @ApiParam(value = "Project ID.", example = "my_project", required = true)
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Status value to update.", example = "100", required = true)
        @PathParam("value") int value,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateStatusBody data,

        @ApiParam(
            value = RETURN_STATUS,
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
        notes = DELETE_STATUS_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content — status deleted (or already absent)."),
        @ApiResponse(code = 403, message = STATUS_403)
    })
    public Response deleteStatusByProjectOid(
        @ApiParam(value = "Project OID.", required = true)
        @PathParam("projectOid") String projectOid,
        @ApiParam(value = "Status value to delete.", example = "50", required = true)
        @PathParam("value") int value
    ) { return null; }

    @DELETE
    @Path("/id/{projectId}/{value}")
    @ApiOperation(
        value = "Delete a task status (by project ID).",
        notes = DELETE_STATUS_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content — status deleted (or already absent)."),
        @ApiResponse(code = 403, message = STATUS_403)
    })
    public Response deleteStatusByProjectId(
        @ApiParam(value = "Project ID.", example = "my_project", required = true)
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Status value to delete.", example = "50", required = true)
        @PathParam("value") int value
    ) { return null; }
}
