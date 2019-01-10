package io.quire.api.resource;

import io.quire.api.model.ErrorResponse;
import io.quire.api.model.comment.Comment;
import io.quire.api.model.comment.CreateCommentBody;
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
    @ApiOperation(value = "Add a task",
        notes = "Add a new task in a project.",
        response = Task.class)
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad parameter", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allow", response = ErrorResponse.class)})
    public Response createTask(
        @ApiParam(value = "Task to create", required = true)
        CreateTaskBody data) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(value = "Show a task.",
        notes = "Returns the complete task record for a single task.",
        response = Task.class)
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad parameter", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Task not found", response = ErrorResponse.class,
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'message': 'Task not found: 12'}")}))})
    public Response getTask(
        @ApiParam(value = "Oid of task that needs to be fetched", required = true)
        @PathParam("oid") String oid) { return null; }

    @PUT
    @Path("/{oid}")
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
                "{'Success': 'true'}")})),
        @ApiResponse(code = 400, message = "Bad parameter", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allow", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Task not found", response = ErrorResponse.class,
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'message': 'Task not found: 12'}")}))})
    public Response deleteTask(
        @ApiParam(value = "Oid of task that needs to be deleted", required = true)
        @PathParam("oid") String oid) { return null; }

    @GET
    @Path("/{oid}/comments")
    @ApiOperation(value = "Get task comments.",
        notes = "Returns the complete comment record for a single task.",
        response = Comment.class,
        responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad parameter", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Task not found", response = ErrorResponse.class,
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'message': 'Task not found: 12'}")}))})
    public Response getTaskComments(
        @ApiParam(value = "Oid of task that needs to be fetched", required = true)
        @PathParam("oid") String oid) { return null; }

    @POST
    @Path("/{oid}/comments")
    @ApiOperation(value = "Add a task comment",
        notes = "Add a new comment in a task.",
        response = Comment.class)
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad parameter", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allow", response = ErrorResponse.class)})
    public Response createTaskComment(
        @ApiParam(value = "Oid of task that comment to", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Comment to create", required = true)
        CreateCommentBody data) { return null; }
}
