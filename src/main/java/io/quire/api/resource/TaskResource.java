package io.quire.api.resource;

import io.quire.api.model.task.CreateTaskBody;
import io.quire.api.model.task.SimpleTask;
import io.quire.api.model.task.Task;
import io.quire.api.model.task.UpdateTaskBody;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/task")
@Api(value = "task", description =
	"The task is a piece of work to be done or undertaken. " +
	"It is the basic object that you and your team can collaborate on.")
@Produces({"application/json"})
public class TaskResource {

	@POST
	@Path("/{oid}")
	@ApiOperation(value = "Add a new task.",
		notes = "Add a new task into a project or a task.",
		response = Task.class)
	public Response createTask(
		@ApiParam(value = "OID of project or task that this new task to be added to. "
		+ "If the given OID is a project, the new task will be added as a root task. "
		+ "If the given OID is a task, the new task will become its subtask. "
		+ "Specify \"-\" if you'd like to add it to My Tasks.",
		required = true)
		@PathParam("oid") String oid,
		@ApiParam(value = "Task to create", required = true)
		CreateTaskBody data) { return null; }

	@POST
	@Path("/id/{projectId}")
	@ApiOperation(value = "Add a new task.",
		notes = "Add a new task into a project.",
		response = Task.class)
	public Response createTaskByProject(
		@ApiParam(value = "ID of project that this new task to be added to. "
		+ "The new task will be added as a root task. "
		+ "Specify \"-\" if you'd like to add it to My Tasks.",
		required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "Task to create", required = true)
		CreateTaskBody data) { return null; }

	@POST
	@Path("/before/{oid}")
	@ApiOperation(value = "Add a new task before the given task.",
		notes = "Add a new task before the given task.",
		response = Task.class)
	public Response createTaskBefore(
		@ApiParam(value = "OID of the task that this new task to be added before.",
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
		@ApiParam(value = "OID of the task that this new task to be added after. ",
			required = true)
		@PathParam("oid") String oid,
		@ApiParam(value = "Task to create", required = true)
		CreateTaskBody data) { return null; }

	@GET
	@Path("/list/{oid}")
	@ApiOperation(value = "Get all root tasks of the given project or "
		+ "all subtasks of the given task.",
		notes = "Returns all root task records of the given project or "
		+ "all subtasks of the given task by OID. "
		+ "If the given OID is a project, the root tasks are returned. "
		+ "If the given OID is a task, its subtasks are returned.\n"
		+ "Note: tasks in the same level are return. That is, it won't "
		+ "returns subtasks of subtasks. You have to retrieve them recursively.",
		response = Task.class,
		responseContainer = "List")
	public Response getTasksByOid(
		@ApiParam(value = "OID of project or parent task to look for.\n" +
			"Specify \"-\" if you'd like to retrieve My Tasks.",
		required = true)
		@PathParam("oid") String oid) { return null; }

	@GET
	@Path("/list/id/{projectId}")
	@ApiOperation(value = "Get all root tasks of the given project.",
		notes = "Returns all root task records of the given project.",
		response = Task.class,
		responseContainer = "List")
	public Response getRootTasks(
		@ApiParam(value = "ID of project.\n" +
			"Specify \"-\" if you'd like to retrieve My Tasks.",
		required = true)
		@PathParam("projectId") String projectId) { return null; }

	@GET
	@Path("/list/id/{projectId}/{taskId}")
	@ApiOperation(value = "Get all subtasks of the given task.",
		notes = "Returns all subtask records of the given task.\n"
		+ "Note: tasks in the same level are return. That is, it won't "
		+ "returns subtasks of subtasks. You have to retrieve them recursively.",
		response = Task.class,
		responseContainer = "List")
	public Response getSubtasks(
		@ApiParam(value = "ID of the project.\n" +
			"Specify \"-\" if you'd like to retrieve My Tasks.",
		required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "ID of the parent task.", required = true)
		@PathParam("taskId") int taskId) { return null; }

	@GET
	@Path("/search/{projectOid}")
	@ApiOperation(value = "Searches tasks in the given project.",
		notes = "Returns task records that match the specified criteria in "
		+ "the given project.\n\n"
		+ "Note: it returns at most 50 records, and recent edited first.",
		response = SimpleTask.class,
		responseContainer = "List")
	public Response searchTasksByOid(
		@ApiParam(value = "OID of the project to search for the tasks.\n" +
			"Specify \"-\" if you'd like to search My Tasks.",
		required = true)
		@PathParam("projectOid") String projectOid,

		@ApiParam(value = "Text to do a full-text search against the name, "
			+ "description, and attachments.\n"
			+ "Note: it doesn't include the content and attachment of comments.",
			example = "text=important major", required = false)
		@QueryParam(value = "text") String text,

		@ApiParam(value = "Task name to match with.\n"
			+ "To specify a regular expression, you can precede it with `~`.\n"
			+ "To specify a case-insensitive regular expression, you can precede it with `~*`.\n"
			+ "To do a full-text search, please use `text` instead.",
			example = "name=My first task", required = false)
		@QueryParam(value = "name") String name,

		@ApiParam(value = "Task's description to match with.\n"
			+ "To specify a regular expression, you can precede it with `~`.\n"
			+ "To specify a case-insensitive regular expression, you can precede it with `~*`.",
			example = "description=~john@gooodjob.com", required = false)
		@QueryParam(value = "description") String description,

		@ApiParam(value = "ID or OID of task's board to match with.\n"
			+ "To search tasks without board, you can specify `board=` or `board=none`.\n"
			+ "To search tasks with any board, you can specify `board=any`.",
			example = "board=Top", required = false)
		@QueryParam(value = "board") String board,

		@ApiParam(value = "Task's status to match with.\n"
			+"You can specify a value between 0 and 100, or \"active\" for active tasks, "
			+"\"completed\" for completed tasks.",
			example = "status=100", required = false)
		@QueryParam(value = "status") String status) { return null; }

	@GET
	@Path("/search/id/{projectId}")
	@ApiOperation(value = "Searches tasks in the given project.",
		notes = "Returns task records that match the specified criteria in "
		+ "the given project.\n\n"
		+ "Note: it returns at most 50 records, and recent edited first.",
		response = SimpleTask.class,
		responseContainer = "List")
	public Response searchTasksById(
		@ApiParam(value = "ID of the project to search for the tasks.\n" +
			"Specify \"-\" if you'd like to search My Tasks.",
		required = true)
		@PathParam("projectId") String projectOid,

		@ApiParam(value = "Text to do a full-text search against the name, "
			+ "description, and attachments.\n"
			+ "Note: it doesn't include the content and attachment of comments.\n"
			+ "Also note: the update of tasks can take 10 seconds or more before "
			+ "it can be found by the full-text search.",
			example = "text=important major", required = false)
		@QueryParam(value = "text") String text,

		@ApiParam(value = "Task name to match with.\n"
			+ "To specify a regular expression, you can precede it with `~`.\n"
			+ "To specify a case-insensitive regular expression, you can precede it with `~*`.\n"
			+ "For example, `name=~abc` matches if `abc` is part of the name. "
			+ "`name=~^ab.*ed$` matches if the name starts with `ab` and ends with `ed`.\n"
			+ "To do a full-text search, please use `text` instead.",
			example = "name=My first task", required = false)
		@QueryParam(value = "name") String name,

		@ApiParam(value = "Task's description to match with.\n"
			+ "To specify a regular expression, you can precede it with `~`.\n"
			+ "To specify a case-insensitive regular expression, you can precede it with `~*`.",
			example = "description=~john@gooodjob.com", required = false)
		@QueryParam(value = "description") String description,

		@ApiParam(value = "OID of task's board to match with.\n"
			+ "To search tasks without board, you can specify `board=` or `board=none`.\n"
			+ "To search tasks with any board, you can specify `board=any`.",
			example = "board=9GFBEKOH5J_aZjNhR82Gd9xx", required = false)
		@QueryParam(value = "board") String board,

		@ApiParam(value = "Task's status to match with.\n"
			+"You can specify a value between 0 and 100.",
			example = "status=100", required = false)
		@QueryParam(value = "status") String status) { return null; }

	@GET
	@Path("/{oid}")
	@ApiOperation(value = "Get an existing task by its OID.",
		notes = "Returns the full task record for a single task.",
		response = Task.class)
	public Response getTask(
		@ApiParam(value = "OID of the task that needs to be fetched",
			required = true)
		@PathParam("oid") String oid) { return null; }

	@GET
	@Path("/id/{projectId}/{id}")
	@ApiOperation(value = "Get an existing task by its ID.",
		notes = "Returns the full task record for a single task.",
		response = Task.class)
	public Response getTaskById(
		@ApiParam(value = "ID of the project that the task belongs to.\n" +
			"Specify \"-\" if you'd like to retrieve My Tasks.",
		required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "ID of the task that needs to be fetched", required = true)
		@PathParam("id") int id) { return null; }

	@PUT
	@Path("/{oid}")
	@ApiOperation(value = "Update an existing task by its OID.",
		notes = "Updates an existing task, and returns the full updated record.",
		response = Task.class)
	public Response updateTask(
		@ApiParam(value = "OID of task that needs to be updated.", required = true)
		@PathParam("oid") String oid,
		@ApiParam(value = "The new content of the task to update to.", required = true)
		UpdateTaskBody data) { return null; }

	@PUT
	@Path("/id/{projectId}/{id}")
	@ApiOperation(value = "Update an existing task by its ID.",
		notes = "Updates an existing task, and returns the full updated record.",
		response = Task.class)
	public Response updateTaskById(
		@ApiParam(value = "ID of the project that the task belongs to.\n" +
			"Specify \"-\" if you'd like to update My Tasks.",
		required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "ID of the task that needs to be updated", required = true)
		@PathParam("id") int id,
		@ApiParam(value = "The new content of the task to update to.", required = true)
		UpdateTaskBody data) { return null; }

	@DELETE
	@Path("/{oid}")
	@ApiOperation(value = "Delete a task and all of its subtasks.",
		notes = "Delete an existing task and all of its subtasks.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json",
			value = "{'success': true}")}))})
	public Response deleteTask(
		@ApiParam(value = "OID of task that needs to be deleted", required = true)
		@PathParam("oid") String oid) { return null; }
}
