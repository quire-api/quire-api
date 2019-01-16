package io.quire.api.resource;

import io.quire.api.model.task.CreateTaskBody;
import io.quire.api.model.task.Task;
import io.quire.api.model.task.UpdateTaskBody;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/tasks")
@Api(value = "tasks", description =
    "The task is the basic object around which many operations in Quire are centered. " +
    "In the Quire application, multiple tasks populate the middle pane according to some view parameters, " +
    "and the set of selected tasks determines the more detailed information presented in the details pane.")
@Produces({"application/json"})
public class TaskResource {

    @POST
    @ApiOperation(value = "Add a task",
        notes = "Add a new task in a project.",
        response = Task.class)
    public Response createTask(
        @ApiParam(value = "Task to create", required = true)
        CreateTaskBody data) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(value = "Show a task.",
        notes = "Returns the complete task record for a single task.",
        response = Task.class)
    public Response getTask(
        @ApiParam(value = "Oid of task that needs to be fetched", required = true)
        @PathParam("oid") String oid) { return null; }

    @PUT
    @Path("/{oid}")
    @ApiOperation(value = "Update a task",
        notes = "A specific, existing task can be updated by making a PUT request on the URL for that taks.\n" +
                "Returns the complete updated task record.",
        response = Task.class)
    public Response updateTask(
        @ApiParam(value = "Oid of task that needs to be updated", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Task to update", required = true)
        UpdateTaskBody data) { return null; }

    @DELETE
    @Path("/{oid}")
    @ApiOperation(value = "Delete a task",
        notes = "A specific, existing task can be deleted by making a DELETE request " +
                "on the URL for that task.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok",
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'Success': 'true'}")}))})
    public Response deleteTask(
        @ApiParam(value = "Oid of task that needs to be deleted", required = true)
        @PathParam("oid") String oid) { return null; }
}
