package io.quire.api.resource;

import io.quire.api.model.task.Task;
import io.swagger.annotations.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/tasks")
@Api(value = "tasks",
    description =
        "The task is the basic object around which many operations in Quire are centered. " +
        "In the Quire application, multiple tasks populate the middle pane according to some view parameters, " +
        "and the set of selected tasks determines the more detailed information presented in the details pane."
    )
@Produces({"application/json"})
public class TaskResource {
    @GET
    @Path("/{id}")
    @ApiOperation(value = "Show a task.",
            notes = "Returns the complete task record for a single task.",
            response = Task.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Task not found")})
    public Response getTask(
            @ApiParam(value = "ID of task that needs to be fetched", required = true)
            @PathParam("id") String id) {
        return null;
    }
}
