package io.quire.api.resource;

import io.quire.api.model.ErrorResponse;
import io.quire.api.model.task.CreateTaskBody;
import io.quire.api.model.task.Task;
import io.quire.api.model.task.TaskWithChildren;
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
    @ApiOperation(value = "Add a task.",
        notes = "Add a new task in a project.",
        response = Task.class)
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad parameter", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allow", response = ErrorResponse.class)})
    public Response createTask(
        @ApiParam(value = "Task to create", required = true)
        CreateTaskBody data) { return null; }

    @GET
    @Path("/{projectId}/{id}")
    @ApiOperation(value = "Show a task.",
        notes = "Returns the complete task record for a single task.",
        response = Task.class)
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad parameter", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Task not found", response = ErrorResponse.class,
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'message': 'Task not found: 12'}")}))})
    public Response getTask(
        @ApiParam(value = "ID of project", required = true)
        @PathParam("projectId") String projectId,
        @ApiParam(value = "ID of task that needs to be fetched", required = true)
        @PathParam("id") Integer id) { return null; }

    @PUT
    @Path("/{projectId}/{id}")
    @ApiOperation(value = "Update a task",
        notes = "A specific, existing task can be updated by making a PUT request on the URL for that taks.\n" +
                "Returns the complete updated task record.",
        response = Task.class)
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad parameter", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allow", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Task not found", response = ErrorResponse.class,
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'message': 'Task not found: 12'}")}))})
    public Response updateTask(
        @ApiParam(value = "ID of project", required = true)
        @PathParam("projectId") String projectId,
        @ApiParam(value = "ID of project that needs to be updated", required = true)
        @PathParam("id") String id,
        @ApiParam(value = "Task to update", required = true)
        UpdateTaskBody data) { return null; }

    @DELETE
    @Path("/{projectId}/{id}")
    @ApiOperation(value = "Delete a task",
        notes = "A specific, existing task can be deleted by making a DELETE request " +
                "on the URL for that task.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok",
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'Success': 'true'}")})),
        @ApiResponse(code = 400, message = "Bad parameter", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allow", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Task not found", response = ErrorResponse.class,
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'message': 'Task not found: 12'}")}))})
    public Response deleteTask(
        @ApiParam(value = "ID of project", required = true)
        @PathParam("projectId") String projectId,
        @ApiParam(value = "ID of task that needs to be deleted", required = true)
        @PathParam("id") String id) { return null; }

    @GET
    @Path("/{projectId}")
    @ApiOperation(value = "Get project tasks",
        notes = "Returns the compact task records for all tasks within the given project",
        response = TaskWithChildren.class,
        responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad parameter", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Project not found", response = ErrorResponse.class,
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'message': 'Project not found: Marketing_Project'}")}))})
    public Response getTasks(
        @ApiParam(value = "ID of the project in which to search for tasks", required = true)
        @PathParam("projectId") String projectId) { return null; }

}
