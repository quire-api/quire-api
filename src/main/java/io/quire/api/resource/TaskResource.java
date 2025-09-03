package io.quire.api.resource;

import io.quire.api.model.*;
import io.quire.api.model.task.*;
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
		+ "Specify \"-\" if you'd like to add it to the personal tasks that belong to no specific projects in My Tasks.",
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
		+ "Specify \"-\" if you'd like to add it to the personal tasks that belong to no specific projects in My Tasks.",
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
			"Specify \"-\" if you'd like to retrieve the personal tasks that belong to no specific projects in My Tasks.",
		required = true)
		@PathParam("oid") String oid) { return null; }

	@GET
	@Path("/list/id/{projectId}")
	@ApiOperation(value = "Get all root tasks of the given project.",
		notes = "Returns all root task records of the given project.\n\n"
		"To retrieve all tasks, including all subtasks, you can use "
		+ "[the search api](https://quire.io/dev/api/#operation--task-search-id--projectId--get), "
		+ "by specifying `limit=no`.",
		response = Task.class,
		responseContainer = "List")
	public Response getRootTasks(
		@ApiParam(value = "ID of project.\n" +
			"Specify \"-\" if you'd like to retrieve the personal tasks that belong to no specific projects in My Tasks.",
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
			"Specify \"-\" if you'd like to retrieve the personal tasks that belong to no specific projects in My Tasks",
		required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "ID of the parent task.", required = true)
		@PathParam("taskId") int taskId,

		@ApiParam(value = "Task's status to match with.\n"
			+"You can specify a value between 0 and 100, or \"active\" for active tasks, "
			+"\"completed\" for completed tasks.",
			example = "status=active", required = false)
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
			"Specify \"-\" if you'd like to retrieve the personal tasks that belong to no specific projects in My Tasks.",
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
			"Specify \"-\" if you'd like to update the personal tasks that belong to no specific projects in My Tasks.",
		required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "ID of the task that needs to be updated", required = true)
		@PathParam("id") int id,
		@ApiParam(value = "The new content of the task to update to.", required = true)
		UpdateTaskBody data) { return null; }

	@POST
	@Path("/attach/{taskOid}/{filename}")
	@ApiOperation(value = "Uploads an attachment to an existing task by its OID",
		notes = "Uploads an attachment to an existing task.",
		response = SimpleAttachment.class)
	public Response attachCommentByOid(
		@ApiParam(value = "OID of the task to upload an attachment.", required = true)
		@PathParam("taskOid") String taskOid,
		@ApiParam(value = "The attachment's name, such as `readme.txt`.\n\n"
			+ "It is better to provide a meaningful extension so browser can "
			+ "recognize the mime type properly. "
			+ "For example, `revenue.pdf` and `contacts.json`.\n\n"
			+ "Alternatively, you can specify the mime type in the content-type header.",
			required = true)
		@PathParam("filename") String filename,
		@ApiParam(value = "The attachment's content.\n\n"
			+ "For example, if you're uploading an image, the request body "
			+ "is the image itself.",
			required = true)
		Object data) { return null; }

	@POST
	@Path("/attach/{projectId}/{id}/{filename}")
	@ApiOperation(value = "Uploads an attachment to an existing task by its ID",
		notes = "Uploads an attachment to an existing task.",
		response = SimpleAttachment.class)
	public Response attachCommentByOid(
		@ApiParam(value = "ID of the project that the task belongs to.\n" +
			"Specify \"-\" if you'd like to update the personal tasks that belong to no specific projects in My Tasks.",
		required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "ID of the task to upload an attachment", required = true)
		@PathParam("id") int id,
		@ApiParam(value = "The attachment's name, such as `readme.txt`.\n\n"
			+ "It is better to provide a meaningful extension so browser can "
			+ "recognize the mime type properly. "
			+ "For example, `revenue.pdf` and `contacts.json`.\n\n"
			+ "Alternatively, you can specify the mime type in the content-type header.",
			required = true)
		@PathParam("filename") String filename,
		@ApiParam(value = "The attachment's content.\n\n"
			+ "For example, if you're uploading an image, the request body "
			+ "is the image itself.",
			required = true)
		Object data) { return null; }

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

	@GET
	@Path("/search/{projectOid}")
	@ApiOperation(value = "Searches tasks in the project of the given OID.",
		notes = "Returns task records that match the specified criteria in "
		+ "the given project.",
		response = SimpleTask.class,
		responseContainer = "List")
	public Response searchTasksByOid(
		@ApiParam(value = "OID of the project to search for the tasks.\n" +
			"Specify \"-\" if you'd like to search the personal tasks that belong to no specific projects in My Tasks.",
		required = true)
		@PathParam("projectOid") String projectOid,

		@ApiParam(value = "Text to do a full-text search against the name, "
			+ "description, and attachments.\n"
			+ "Note: it doesn't include the content and attachments of comments.\n"
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

		@ApiParam(value = "ID or OID of task's sublist to match with.",
			example = "sublist=Top", required = false)
		@QueryParam(value = "sublist") String sublist,

		@ApiParam(value = "Task's status to match with.\n"
			+"You can specify a value between 0 and 100, or \"active\" for active tasks, "
			+"\"completed\" for completed tasks.",
			example = "status=active", required = false)
		@QueryParam(value = "status") String status,

		@ApiParam(value = "Whether to return only tasks that are scheduled.\n"
			+"By scheduled we mean either `start` or `due` is scheduled.\n"
			+"If `scheduled=false` is specified, it returns only tasks "
			+"that neither start nor due is scheduled.",
			example = "scheduled=true", required = false)
		@QueryParam(value = "scheduled") boolean scheduled,

		@ApiParam(value = "Whether to return only My Tasks.\n"
			+"By My Tasks we mean tasks that are assigned to me, "
			+"or tasks that are created by me and scheduled, but not assigned to anyone.",
			example = "mine=true", required = false)
		@QueryParam(value = "mine") boolean mine,

		@ApiParam(value = "Whether to return only tasks created or modified recently.\n"
			+"You can specify an integer as the value. If omitted, 7 is assumed.\n"
			+"For example, to search tasks created or modified within 30 days, "
			+"specify: \"modified=30\"\n"
			+"You can also specify \"d\", \"h\" or \"m\" for days, hours and minutes. "
			+"For example, specify \"modified=8h\" to indicate 8 hours.",
			example = "modified=7", required = false)
		@QueryParam(value = "modified") String modified,

		@ApiParam(value = "Whether to return only tasks commented recently.\n"
			+"You can specify an integer as the value. If omitted, 7 is assumed.\n"
			+"For example, to search tasks that has a comment posted within 30 days, "
			+"specify: \"commented=30\"\n"
			+"You can also specify \"d\", \"h\" or \"m\" for days, hours and minutes. "
			+"For example, specify \"commented=8h\" to indicate 8 hours.",
			example = "commented=7", required = false)
		@QueryParam(value = "commented") String commented,

		@ApiParam(value = "Whether to return only tasks with the given source ref.\n"
			+"The value must be the key of the source ref to search for.\n\n"
			+"> Refer to the `sourceRef` parameter [here](https://quire.io/dev/api/#operation--task-id--projectId--post).",
			example = "sourceRef=git", required = false)
		@QueryParam(value = "sourceRef") String sourceRef,

		@ApiParam(value = "The maximal number of tasks to return.\n"
			+"Default: 30. That is, at most 30 tasks will be returned.\n"
			+"You can specify \"no\" to return all matched tasks.\n\n"
			+"Note: If the project is on a free plan, the value cannot "
			+"be larger than 30 or \"no\" (unlimited).",
			example = "limit=no", required = false)
		@QueryParam(value = "limit") String limit) { return null; }

	@GET
	@Path("/search/id/{projectId}")
	@ApiOperation(value = "Searches tasks in the project of the given ID.",
		notes = "Returns task records that match the specified criteria in "
		+ "the given project.\n\n"
		+ "To retrieve all tasks including subtasks, you can specify nothing but "
		+ "`limit=no` in the query string, "
		+ "such as `https//quire.io/api/task/search/id/your_project?limit=no`.",
		response = SimpleTask.class,
		responseContainer = "List")
	public Response searchTasksById(
		@ApiParam(value = "ID of the project to search for the tasks.\n" +
			"Specify \"-\" if you'd like to search for the personal tasks that belong to no specific projects in My Tasks.",
		required = true)
		@PathParam("projectId") String projectId,

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

		@ApiParam(value = "ID or OID of task's sublist to match with.",
			example = "sublist=Top", required = false)
		@QueryParam(value = "sublist") String sublist,

		@ApiParam(value = "Task's status to match with.\n"
			+"You can specify a value between 0 and 100, or \"active\" for active tasks, "
			+"\"completed\" for completed tasks.",
			example = "status=active", required = false)
		@QueryParam(value = "status") String status,
		@ApiParam(value = "Whether to return only tasks that are scheduled.\n"
			+"By scheduled we mean either `start` or `due` is scheduled.\n"
			+"If `scheduled=false` is specified, it returns only tasks "
			+"that neither start nor due is scheduled.",
			example = "scheduled=true", required = false)
		@QueryParam(value = "scheduled") boolean scheduled,

		@ApiParam(value = "Whether to return only My Tasks.\n"
			+"By My Tasks we mean tasks that are assigned to me, "
			+"or tasks that are created by me and scheduled, but not assigned to anyone.",
			example = "mine=true", required = false)
		@QueryParam(value = "mine") boolean mine,

		@ApiParam(value = "Whether to return only tasks created or modified recently.\n"
			+"You can specify an integer as the value. If omitted, 7 is assumed.\n"
			+"For example, to search tasks created or modified within 30 days, "
			+"specify: \"modified=30\"\n"
			+"You can also specify \"d\", \"h\" or \"m\" for days, hours and minutes. "
			+"For example, specify \"modified=8h\" to indicate 8 hours.",
			example = "modified=7", required = false)
		@QueryParam(value = "modified") String modified,

		@ApiParam(value = "Whether to return only tasks commented recently.\n"
			+"You can specify an integer as the value. If omitted, 7 is assumed.\n"
			+"For example, to search tasks that has a comment posted within 30 days, "
			+"specify: \"commented=30\"\n"
			+"You can also specify \"d\", \"h\" or \"m\" for days, hours and minutes. "
			+"For example, specify \"commented=8h\" to indicate 8 hours.",
			example = "commented=7", required = false)
		@QueryParam(value = "commented") String commented,

		@ApiParam(value = "The maximal number of tasks to return.\n"
			+"Default: 30. That is, at most 30 tasks will be returned.\n"
			+"You can specify \"no\" to return all matched tasks.\n\n"
			+"Note: If the project is on a free plan, the value cannot "
			+"be larger than 30 or \"no\" (unlimited).",
			example = "limit=no", required = false)
		@QueryParam(value = "limit") String limit) { return null; }

  @GET
  @Path("/search-organization/{organizationOid}")
  @ApiOperation(value = "Searches tasks in the organization of the given OID.",
    notes = "Returns task records that match the specified criteria in "
    + "the given organization.\n"
    + "Tasks in the archived projects are excluded.\n\n"
    + "> Available for Professional plans and above",
    response = SimpleTaskWithProject.class,
    responseContainer = "List")
  public Response searchTasksByOrgOid(
    @ApiParam(value = "OID of the organization to search for the tasks.",
    required = true)
    @PathParam("organizationOid") String organizationOid,

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

    @ApiParam(value = "Task's status to match with.\n"
      +"You can specify a value between 0 and 100, or \"active\" for active tasks, "
      +"\"completed\" for completed tasks.",
      example = "status=active", required = false)
    @QueryParam(value = "status") String status,

    @ApiParam(value = "Whether to return only tasks that are scheduled.\n"
      +"By scheduled we mean either `start` or `due` is scheduled.\n"
      +"If `scheduled=false` is specified, it returns only tasks "
      +"that neither start nor due is scheduled.",
      example = "scheduled=true", required = false)
    @QueryParam(value = "scheduled") boolean scheduled,

    @ApiParam(value = "Whether to return only My Tasks.\n"
      +"By My Tasks we mean tasks that are assigned to me, "
      +"or tasks that are created by me and scheduled, but not assigned to anyone.",
      example = "mine=true", required = false)
    @QueryParam(value = "mine") boolean mine,

    @ApiParam(value = "Whether to return only tasks created or modified recently.\n"
      +"You can specify an integer as the value. If omitted, 7 is assumed.\n"
      +"For example, to search tasks created or modified within 30 days, "
      +"specify: \"modified=30\"\n"
      +"You can also specify \"d\", \"h\" or \"m\" for days, hours and minutes. "
      +"For example, specify \"modified=8h\" to indicate 8 hours.",
      example = "modified=7", required = false)
    @QueryParam(value = "modified") String modified,

    @ApiParam(value = "Whether to return only tasks commented recently.\n"
      +"You can specify an integer as the value. If omitted, 7 is assumed.\n"
      +"For example, to search tasks that has a comment posted within 30 days, "
      +"specify: \"commented=30\"\n"
      +"You can also specify \"d\", \"h\" or \"m\" for days, hours and minutes. "
      +"For example, specify \"commented=8h\" to indicate 8 hours.",
      example = "commented=7", required = false)
    @QueryParam(value = "commented") String commented,

    @ApiParam(value = "The maximal number of tasks to return.\n"
      +"Default: 30. That is, at most 30 tasks will be returned.\n"
      +"You can specify \"no\" to return all matched tasks.\n\n"
      +"Note: If the organization is on a free plan, the value cannot "
      +"be larger than 30 or \"no\" (unlimited).",
      example = "limit=no", required = false)
    @QueryParam(value = "limit") String limit) { return null; }

  @GET
  @Path("/search-organization/id/{organizationId}")
  @ApiOperation(value = "Searches tasks in the organization of the given ID.",
    notes = "Returns task records that match the specified criteria in "
    + "the given organization.\n"
    + "Tasks in the archived projects are excluded.\n\n"
    + "> Available for Professional plans and above",
    response = SimpleTaskWithProject.class,
    responseContainer = "List")
  public Response searchTasksByOrgId(
    @ApiParam(value = "ID of the organization to search for the tasks.",
    required = true)
    @PathParam("orgnizationId") String organizationId,

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

    @ApiParam(value = "Task's status to match with.\n"
      +"You can specify a value between 0 and 100, or \"active\" for active tasks, "
      +"\"completed\" for completed tasks.",
      example = "status=active", required = false)
    @QueryParam(value = "status") String status,
    @ApiParam(value = "Whether to return only tasks that are scheduled.\n"
      +"By scheduled we mean either `start` or `due` is scheduled.\n"
      +"If `scheduled=false` is specified, it returns only tasks "
      +"that neither start nor due is scheduled.",
      example = "scheduled=true", required = false)
    @QueryParam(value = "scheduled") boolean scheduled,

    @ApiParam(value = "Whether to return only My Tasks.\n"
      +"By My Tasks we mean tasks that are assigned to me, "
      +"or tasks that are created by me and scheduled, but not assigned to anyone.",
      example = "mine=true", required = false)
    @QueryParam(value = "mine") boolean mine,

    @ApiParam(value = "Whether to return only tasks created or modified recently.\n"
      +"You can specify an integer as the value. If omitted, 7 is assumed.\n"
      +"For example, to search tasks created or modified within 30 days, "
      +"specify: \"modified=30\"\n"
      +"You can also specify \"d\", \"h\" or \"m\" for days, hours and minutes. "
      +"For example, specify \"modified=8h\" to indicate 8 hours.",
      example = "modified=7", required = false)
    @QueryParam(value = "modified") String modified,

    @ApiParam(value = "Whether to return only tasks commented recently.\n"
      +"You can specify an integer as the value. If omitted, 7 is assumed.\n"
      +"For example, to search tasks that has a comment posted within 30 days, "
      +"specify: \"commented=30\"\n"
      +"You can also specify \"d\", \"h\" or \"m\" for days, hours and minutes. "
      +"For example, specify \"commented=8h\" to indicate 8 hours.",
      example = "commented=7", required = false)
    @QueryParam(value = "commented") String commented,

    @ApiParam(value = "The maximal number of tasks to return.\n"
      +"Default: 30. That is, at most 30 tasks will be returned.\n"
      +"You can specify \"no\" to return all matched tasks.\n\n"
      +"Note: If the organization is on a free plan, the value cannot "
      +"be larger than 30 or \"no\" (unlimited).",
      example = "limit=no", required = false)
    @QueryParam(value = "limit") String limit) { return null; }

  @GET
  @Path("/search-folder/{folderOid}")
  @ApiOperation(value = "Searches tasks in the folder of the given OID.",
    notes = "Returns task records that match the specified criteria in "
    + "the given folder.\n"
    + "Tasks in the archived projects are excluded.\n\n"
    + "> Available for Professional plans and above",
    response = SimpleTaskWithProject.class,
    responseContainer = "List")
  public Response searchTasksByFolderOid(
    @ApiParam(value = "OID of the folder to search for the tasks.",
    required = true)
    @PathParam("folderOid") String folderOid,

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

    @ApiParam(value = "Task's status to match with.\n"
      +"You can specify a value between 0 and 100, or \"active\" for active tasks, "
      +"\"completed\" for completed tasks.",
      example = "status=active", required = false)
    @QueryParam(value = "status") String status,

    @ApiParam(value = "Whether to return only tasks that are scheduled.\n"
      +"By scheduled we mean either `start` or `due` is scheduled.\n"
      +"If `scheduled=false` is specified, it returns only tasks "
      +"that neither start nor due is scheduled.",
      example = "scheduled=true", required = false)
    @QueryParam(value = "scheduled") boolean scheduled,

    @ApiParam(value = "Whether to return only My Tasks.\n"
      +"By My Tasks we mean tasks that are assigned to me, "
      +"or tasks that are created by me and scheduled, but not assigned to anyone.",
      example = "mine=true", required = false)
    @QueryParam(value = "mine") boolean mine,

    @ApiParam(value = "Whether to return only tasks created or modified recently.\n"
      +"You can specify an integer as the value. If omitted, 7 is assumed.\n"
      +"For example, to search tasks created or modified within 30 days, "
      +"specify: \"modified=30\"\n"
      +"You can also specify \"d\", \"h\" or \"m\" for days, hours and minutes. "
      +"For example, specify \"modified=8h\" to indicate 8 hours.",
      example = "modified=7", required = false)
    @QueryParam(value = "modified") String modified,

    @ApiParam(value = "Whether to return only tasks commented recently.\n"
      +"You can specify an integer as the value. If omitted, 7 is assumed.\n"
      +"For example, to search tasks that has a comment posted within 30 days, "
      +"specify: \"commented=30\"\n"
      +"You can also specify \"d\", \"h\" or \"m\" for days, hours and minutes. "
      +"For example, specify \"commented=8h\" to indicate 8 hours.",
      example = "commented=7", required = false)
    @QueryParam(value = "commented") String commented,

    @ApiParam(value = "The maximal number of tasks to return.\n"
      +"Default: 30. That is, at most 30 tasks will be returned.\n"
      +"You can specify \"no\" to return all matched tasks.\n\n"
      +"Note: If the folder is on a free plan, the value cannot "
      +"be larger than 30 or \"no\" (unlimited).",
      example = "limit=no", required = false)
    @QueryParam(value = "limit") String limit) { return null; }

  @GET
  @Path("/search-folder/id/{folderId}")
  @ApiOperation(value = "Searches tasks in the folder of the given ID.",
    notes = "Returns task records that match the specified criteria in "
    + "the given folder.\n"
    + "Tasks in the archived projects are excluded.\n\n"
    + "> Available for Professional plans and above",
    response = SimpleTaskWithProject.class,
    responseContainer = "List")
  public Response searchTasksByFolderId(
    @ApiParam(value = "ID of the folder to search for the tasks.",
    required = true)
    @PathParam("orgnizationId") String folderId,

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

    @ApiParam(value = "Task's status to match with.\n"
      +"You can specify a value between 0 and 100, or \"active\" for active tasks, "
      +"\"completed\" for completed tasks.",
      example = "status=active", required = false)
    @QueryParam(value = "status") String status,
    @ApiParam(value = "Whether to return only tasks that are scheduled.\n"
      +"By scheduled we mean either `start` or `due` is scheduled.\n"
      +"If `scheduled=false` is specified, it returns only tasks "
      +"that neither start nor due is scheduled.",
      example = "scheduled=true", required = false)
    @QueryParam(value = "scheduled") boolean scheduled,

    @ApiParam(value = "Whether to return only My Tasks.\n"
      +"By My Tasks we mean tasks that are assigned to me, "
      +"or tasks that are created by me and scheduled, but not assigned to anyone.",
      example = "mine=true", required = false)
    @QueryParam(value = "mine") boolean mine,

    @ApiParam(value = "Whether to return only tasks created or modified recently.\n"
      +"You can specify an integer as the value. If omitted, 7 is assumed.\n"
      +"For example, to search tasks created or modified within 30 days, "
      +"specify: \"modified=30\"\n"
      +"You can also specify \"d\", \"h\" or \"m\" for days, hours and minutes. "
      +"For example, specify \"modified=8h\" to indicate 8 hours.",
      example = "modified=7", required = false)
    @QueryParam(value = "modified") String modified,

    @ApiParam(value = "Whether to return only tasks commented recently.\n"
      +"You can specify an integer as the value. If omitted, 7 is assumed.\n"
      +"For example, to search tasks that has a comment posted within 30 days, "
      +"specify: \"commented=30\"\n"
      +"You can also specify \"d\", \"h\" or \"m\" for days, hours and minutes. "
      +"For example, specify \"commented=8h\" to indicate 8 hours.",
      example = "commented=7", required = false)
    @QueryParam(value = "commented") String commented,

    @ApiParam(value = "The maximal number of tasks to return.\n"
      +"Default: 30. That is, at most 30 tasks will be returned.\n"
      +"You can specify \"no\" to return all matched tasks.\n\n"
      +"Note: If the folder is on a free plan, the value cannot "
      +"be larger than 30 or \"no\" (unlimited).",
      example = "limit=no", required = false)
    @QueryParam(value = "limit") String limit) { return null; }
}
