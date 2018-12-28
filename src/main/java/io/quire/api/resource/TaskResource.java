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

    @GET
    @Path("/{projectId}/search")
    @ApiOperation(value = "Search tasks",
        notes = "Returns the compact task records for query tasks",
        response = Task.class,
        responseContainer = "List")
    public Response searchTasks(
        @ApiParam(value = "ID of the project in which to search for tasks", required = true)
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Query text for search tasks",
            example = "query=my task")
        @QueryParam(value = "query") String query,
        @ApiParam(value = "State of task",
            defaultValue = "active",
            allowableValues = "all,active,completed",
            example = "state=completed")
        @QueryParam(value = "state") String state,

        @ApiParam(value = "Tasks include all assignee",
            allowMultiple = true,
            example = "assignees.all=mary@gmail.com,john")
        @QueryParam(value = "assignees.all") String assigneesAll,
        @ApiParam(value = "Tasks contains any of assignees",
            allowMultiple = true,
            example = "assignees.any=john")
        @QueryParam(value = "assignees.any") String assigneesAny,
        @ApiParam(value = "Tasks not contains any of assignees",
            allowMultiple = true,
            example = "assignees.not=mary@gmail.com")
        @QueryParam(value = "assignees.not") String assigneesNot,

        @ApiParam(value = "Tasks include all tags",
            allowMultiple = true,
            example = "tags.all=yellow,red")
        @QueryParam(value = "tags.all") String tagsAll,
        @ApiParam(value = "Tasks contains any of tags",
            allowMultiple = true,
            example = "tags.any=yellow,red")
        @QueryParam(value = "tags.any") String tagsAny,
        @ApiParam(value = "Tasks not contains any of tags",
            allowMultiple = true,
            example = "tags.not=black,grey")
        @QueryParam(value = "tags.not") String tagsNot,

        @ApiParam(value = "Tasks on due",
            example = "due=2018-12-05")
        @QueryParam(value = "due") String due,
        @ApiParam(value = "Tasks before due",
            example = "due.before=2018-11-05")
        @QueryParam(value = "due.before") String dueBefore,
        @ApiParam(value = "Tasks after due",
            example = "due.after=2018-12-15")
        @QueryParam(value = "due.after") String dueAfter,

        @ApiParam(value = "Tasks on start",
                example = "start=2018-12-05")
        @QueryParam(value = "start") String start,
        @ApiParam(value = "Tasks before start",
                example = "start.before=2018-11-05")
        @QueryParam(value = "start.before") String startBefore,
        @ApiParam(value = "Tasks after start",
                example = "start.after=2018-12-15")
        @QueryParam(value = "start.after") String startAfter) { return null; }

}
