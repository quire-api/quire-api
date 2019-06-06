package io.quire.api.resource;

import io.quire.api.model.task.CreateTaskBody;
import io.quire.api.model.task.Task;
import io.quire.api.model.task.UpdateTaskBody;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/task")
@Api(value = "tasks", description =
	"The task is a piece of work to be done or undertaken. " +
	"It is the basic object that you and your team can collaborate on.")
@Produces({"application/json"})
public class TaskResource {

	@POST
	@Path("/{oid}")
	@ApiOperation(value = "Add a new task.",
		notes = "Add a new task into a project.",
		response = Task.class)
	public Response createTask(
		@ApiParam(value = "OID of project or task that this new task to be added to. "
		+ "If the given OID is a project, the new task will be added as a root task. "
		+ "If the given OID is a task, the new task will become its subtask.",
		required = true)
		@PathParam("oid") String oid,
		@ApiParam(value = "Task to create", required = true)
		CreateTaskBody data) { return null; }

	@POST
	@Path("/before/{oid}")
	@ApiOperation(value = "Add a new task before the given task.",
		notes = "Add a new task before the given task.",
		response = Task.class)
	public Response createTaskBefore(
		@ApiParam(value = "OID of the task that this new task to be added after. ",
			required = true)
		@PathParam("oid") String oid,
		@ApiParam(value = "Task to create", required = true)
		CreateTaskBody data) { return null; }

	@POST
	@Path("/after/{oid}")
	@ApiOperation(value = "Add a new task after the given task.",
		notes = "Add a new task after the given task.",
		response = Task.class)
	public Response createTaskAfter(
		@ApiParam(value = "OID of the task that this new task to be added before. ",
			required = true)
		@PathParam("oid") String oid,
		@ApiParam(value = "Task to create", required = true)
		CreateTaskBody data) { return null; }

	@GET
	@Path("/list/{oid}")
	@ApiOperation(value = "Get all root tasks or subtask of the given "
		+ "project or task by its OID. "
		+ "If the given OID is a project, the root tasks are returned. "
		+ "If the given OID is a task, the subtasks are returned. "
		+ "Note: tasks in the same level are return. That is, it won't "
		+ "returns subtasks of subtasks. You have to retrieve them recursively.",
		notes = "Returns all task records of the given project or task.",
		response = Task.class,
		responseContainer = "List")
	public Response getSubTasks(
		@ApiParam(value = "OID of project or parent task to look for", required = true)
		@PathParam("oid") String oid) { return null; }

	@GET
	@Path("/list/id/{projectId}")
	@ApiOperation(value = "Get all root tasks of the given project by its ID.",
		notes = "Returns all task records of the given project.",
		response = Task.class,
		responseContainer = "List")
	public Response getTasks(
		@ApiParam(value = "ID of project to look for", required = true)
		@PathParam("projectId") String projectId) { return null; }

	@GET
	@Path("/list/id/{projectId}/${id}")
	@ApiOperation(value = "Get all subtasks of the given task by its ID. "
		+ "Note: tasks in the same level are return. That is, it won't "
		+ "returns subtasks of subtasks. You have to retrieve them recursively.",
		notes = "Returns all subtask records of the given task.",
		response = Task.class,
		responseContainer = "List")
	public Response getTasks(
		@ApiParam(value = "ID of project.", required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "ID of the parent task", required = true)
		@PathParam("id") int id) { return null; }

	@GET
	@Path("/{oid}")
	@ApiOperation(value = "Get an existing task by its OID.",
		notes = "Returns the complete task record for a single task.",
		response = Task.class)
	public Response getTask(
		@ApiParam(value = "OID of the task that needs to be fetched", required = true)
		@PathParam("oid") String oid) { return null; }

	@GET
	@Path("/id/{projectId}/{id}")
	@ApiOperation(value = "Get an existing task by its ID.",
		notes = "Returns the complete task record for a single task.",
		response = Task.class)
	public Response getTaskById(
		@ApiParam(value = "ID of the project that the task belongs to.", required = true)
		@PathParam("projectId") int projectId,
		@ApiParam(value = "ID of the task that needs to be fetched", required = true)
		@PathParam("id") int id) { return null; }

	@PUT
	@Path("/{oid}")
	@ApiOperation(value = "Update an existing task.",
		notes = "Returns the complete updated task record.",
		response = Task.class)
	public Response updateTask(
		@ApiParam(value = "OID of task that needs to be updated.", required = true)
		@PathParam("oid") String oid,
		@ApiParam(value = "The new content of the task to update to.", required = true)
		UpdateTaskBody data) { return null; }

	@DELETE
	@Path("/{oid}")
	@ApiOperation(value = "Delete an existing task and all of its subtasks.",
		notes = "Delete an existing task.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json",
			value = "{'success': true}")}))})
	public Response deleteTask(
		@ApiParam(value = "OID of task that needs to be deleted", required = true)
		@PathParam("oid") String oid) { return null; }
}
