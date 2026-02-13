package io.quire.api.resource;

import io.quire.api.model.*;
import io.quire.api.model.task.*;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/task")
@Api(
    value = "task",
    description = "Tasks are the basic units of work you and your team collaborate on."
)
@Produces({"application/json"})
public class TaskResource {

    @POST
    @Path("/{oid}")
    @ApiOperation(
        value = "Add a new task.",
        notes = "Adds a new task to a project or to another task.",
        response = Task.class
    )
    public Response createTask(
        @ApiParam(
            value = "OID of the project or task to add the new task to. "
                  + "If the OID refers to a project, the new task becomes a root task. "
                  + "If it refers to a task, the new task becomes its subtask. "
                  + "Specify \"-\" to add it to My Tasks (no specific project).",
            required = true
        )
        @PathParam("oid") String oid,
        @ApiParam(value = "Task to create", required = true)
        CreateTaskBody data
    ) { return null; }

    @POST
    @Path("/id/{projectId}")
    @ApiOperation(
        value = "Add a new task.",
        notes = "Adds a new root task to a project.",
        response = Task.class
    )
    public Response createTaskByProject(
        @ApiParam(
            value = "ID of the project to which this new task will be added. "
                  + "The task will be created as a root task. Specify \"-\" "
                  + "to add it to personal tasks (in My Tasks).",
            required = true
        )
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Task to create", required = true)
        CreateTaskBody data
    ) { return null; }

    @POST
    @Path("/before/{oid}")
    @ApiOperation(
        value = "Add a new task before the given task.",
        notes = "Inserts a new task before the specified task.",
        response = Task.class
    )
    public Response createTaskBefore(
        @ApiParam(value = "OID of the task to insert before.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Task to create", required = true)
        CreateTaskBody data
    ) { return null; }

    @POST
    @Path("/after/{oid}")
    @ApiOperation(
        value = "Add a new task after the given task.",
        notes = "Inserts a new task after the specified task.",
        response = Task.class
    )
    public Response createTaskAfter(
        @ApiParam(value = "OID of the task to insert after.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Task to create", required = true)
        CreateTaskBody data
    ) { return null; }

    @GET
    @Path("/list/{oid}")
    @ApiOperation(
        value = "Get all root tasks of the given project or all subtasks of the given task.",
        notes = "Returns all root tasks of a project or all direct subtasks of a task by OID. "
              + "If the OID is a project, root tasks are returned. If it is a task, its direct subtasks are returned.\n"
              + "Note: only one level is returned—subtasks of subtasks are not included; retrieve them recursively.",
        response = Task.class,
        responseContainer = "List"
    )
    public Response getTasksByOid(
        @ApiParam(
            value = "OID of the project or parent task. "
                  + "Specify \"-\" to retrieve My Tasks (no specific project).",
            required = true
        )
        @PathParam("oid") String oid
    ) { return null; }

    @GET
    @Path("/list/id/{projectId}")
    @ApiOperation(
        value = "Get all root tasks of the given project.",
        notes = "Returns all root tasks of the project.\n\n"
              + "To retrieve all tasks (including all subtasks), use the search API with `limit=no`.",
        response = Task.class,
        responseContainer = "List"
    )
    public Response getRootTasks(
        @ApiParam(
            value = "ID of the project to which this new task will be added. "
                  + "The task will be created as a root task. Specify \"-\" "
                  + "to add it to personal tasks (in My Tasks).",
            required = true
        )
        @PathParam("projectId") String projectId
    ) { return null; }

    @GET
    @Path("/list/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Get all subtasks of the given task.",
        notes = "Returns all direct subtasks of the specified task.\n"
              + "Note: only one level is returned—subtasks of subtasks are not included; retrieve them recursively.",
        response = Task.class,
        responseContainer = "List"
    )
    public Response getSubtasks(
        @ApiParam(
            value = "ID of the project to which this new task will be added. "
                  + "The task will be created as a root task. Specify \"-\" "
                  + "to add it to personal tasks (in My Tasks).",
            required = true
        )
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Parent task ID.", required = true)
        @PathParam("taskId") int taskId,
        @ApiParam(
            value = "Task status filter. "
                  + "Specify a value 0–100, or use \"active\" for active tasks or \"completed\" for completed tasks.",
            example = "active",
            required = false
        )
        @QueryParam("status") String status
    ) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get an existing task by its OID.",
        notes = "Returns the full task record.",
        response = TaskWithParentInfo.class
    )
    public Response getTask(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @GET
    @Path("/id/{projectId}/{id}")
    @ApiOperation(
        value = "Get an existing task by its ID.",
        notes = "Returns the full task record.",
        response = TaskWithParentInfo.class
    )
    public Response getTaskById(
        @ApiParam(
            value = "ID of the project to which this new task will be added. "
                  + "The task will be created as a root task. Specify \"-\" "
                  + "to add it to personal tasks (in My Tasks).",
            required = true
        )
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Task ID.", required = true)
        @PathParam("id") int id
    ) { return null; }

    @PUT
    @Path("/{oid}")
    @ApiOperation(
        value = "Update an existing task by its OID.",
        notes = "Updates an existing task and returns the updated record.",
        response = Task.class
    )
    public Response updateTask(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Updated task content.", required = true)
        UpdateTaskBody data
    ) { return null; }

    @PUT
    @Path("/id/{projectId}/{id}")
    @ApiOperation(
        value = "Update an existing task by its ID.",
        notes = "Updates an existing task and returns the updated record.",
        response = Task.class
    )
    public Response updateTaskById(
        @ApiParam(
            value = "ID of the project to which this new task will be added. "
                  + "The task will be created as a root task. Specify \"-\" "
                  + "to add it to personal tasks (in My Tasks).",
            required = true
        )
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Task ID.", required = true)
        @PathParam("id") int id,
        @ApiParam(value = "Updated task content.", required = true)
        UpdateTaskBody data
    ) { return null; }

    @POST
    @Path("/attach/{taskOid}/{filename}")
    @ApiOperation(
        value = "Upload an attachment to a task by OID.",
        notes = "Uploads an attachment to an existing task.",
        response = SimpleAttachment.class
    )
    public Response attachCommentByOid(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("taskOid") String taskOid,
        @ApiParam(
            value = "Attachment file name, e.g., `readme.txt`.\n\n"
                  + "Provide a meaningful extension so the browser can recognize the MIME type "
                  + "(e.g., `revenue.pdf`, `contacts.json`).\n\n"
                  + "Alternatively, set the MIME type via the `Content-Type` header.",
            required = true
        )
        @PathParam("filename") String filename,
        @ApiParam(
            value = "Attachment content (raw bytes). For images, the body is the image itself.",
            required = true
        )
        Object data
    ) { return null; }

    @POST
    @Path("/attach/{projectId}/{id}/{filename}")
    @ApiOperation(
        value = "Upload an attachment to a task by ID.",
        notes = "Uploads an attachment to an existing task.",
        response = SimpleAttachment.class
    )
    public Response attachCommentById(
        @ApiParam(
            value = "ID of the project to which this new task will be added. "
                  + "The task will be created as a root task. Specify \"-\" "
                  + "to add it to personal tasks (in My Tasks).",
            required = true
        )
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Task ID.", required = true)
        @PathParam("id") int id,
        @ApiParam(
            value = "Attachment file name, e.g., `readme.txt`.\n\n"
                  + "Provide a meaningful extension so the browser can recognize the MIME type "
                  + "(e.g., `revenue.pdf`, `contacts.json`).\n\n"
                  + "Alternatively, set the MIME type via the `Content-Type` header.",
            required = true
        )
        @PathParam("filename") String filename,
        @ApiParam(
            value = "Attachment content (raw bytes). For images, the body is the image itself.",
            required = true
        )
        Object data
    ) { return null; }

    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete a task and all of its subtasks.",
        notes = "Deletes an existing task and all of its subtasks."
    )
    @ApiResponses({
        @ApiResponse(
            code = 200,
            message = "ok",
            examples = @Example({
                @ExampleProperty(
                    mediaType = "application/json",
                    value = "{'success': true}"
                )
            })
        )
    })
    public Response deleteTask(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @GET
    @Path("/search/{projectOid}")
    @ApiOperation(
        value = "Search tasks in a project by project OID.",
        notes = "Returns tasks that match the specified criteria in the project.",
        response = TaskWithParentInfo.class,
        responseContainer = "List"
    )
    public Response searchTasksByOid(
        @ApiParam(
            value = "Project OID. "
                  + "Specify \"-\" to search in My Tasks (no specific project).",
            required = true
        )
        @PathParam("projectOid") String projectOid,
        @ApiParam(
            value = "Full-text query against task name, description, and attachments.\n"
                  + "Note: does not include comment content or comment attachments.\n"
                  + "Indexing can take ~10 seconds or more after updates."
        )
        @QueryParam("text") String text,
        @ApiParam(
            value = "Task name filter. Prefix with `~` for regex, `~*` for case-insensitive regex. "
                  + "Use `text` for full-text search.",
            example = "My first task"
        )
        @QueryParam("name") String name,
        @ApiParam(
            value = "Task description filter. Prefix with `~` for regex, `~*` for case-insensitive regex.",
            example = "description=~john@gooodjob.com"
        )
        @QueryParam("description") String description,
        @ApiParam(
            value = "Task sublist ID or OID filter.",
            example = "Top"
        )
        @QueryParam("sublist") String sublist,
        @ApiParam(
            value = "Task status filter. Specify 0–100, or \"active\" / \"completed\".",
            example = "active"
        )
        @QueryParam("status") String status,
        @ApiParam(
            value = "Return only scheduled tasks (start or due is set). "
                  + "If `scheduled=false`, returns only tasks where neither start nor due is set.",
            example = "true"
        )
        @QueryParam("scheduled") boolean scheduled,
        @ApiParam(
            value = "Return only \"My Tasks\" (assigned to me, or created by me and scheduled but unassigned).",
            example = "true"
        )
        @QueryParam("mine") boolean mine,
        @ApiParam(
            value = "Return only tasks created or modified recently. "
                  + "Specify an integer (days); default 7 if omitted. "
                  + "Supports `d`, `h`, `m` suffixes (e.g., `8h`).",
            example = "7"
        )
        @QueryParam("modified") String modified,
        @ApiParam(
            value = "Return only tasks with recent comments. "
                  + "Specify an integer (days); default 7 if omitted. "
                  + "Supports `d`, `h`, `m` suffixes (e.g., `8h`).",
            example = "7"
        )
        @QueryParam("commented") String commented,
        @ApiParam(
            value = "Filter by source ref key.\n"
                  + "See `sourceRef` in the task creation API.",
            example = "git"
        )
        @QueryParam("sourceRef") String sourceRef,
        @ApiParam(
            value = "Maximum number of tasks to return. Default: 30. "
                  + "Use `no` to return all matches.\n\n"
                  + "Note: On free plans, this cannot exceed 30 or `no` (unlimited).",
            example = "no"
        )
        @QueryParam("limit") String limit
    ) { return null; }

    @GET
    @Path("/search/id/{projectId}")
    @ApiOperation(
        value = "Search tasks in a project by project ID.",
        notes = "Returns tasks that match the specified criteria in the project.",
        response = TaskWithParentInfo.class,
        responseContainer = "List"
    )
    public Response searchTasksById(
        @ApiParam(
            value = "ID of the project to which this new task will be added. "
                  + "The task will be created as a root task. Specify \"-\" "
                  + "to add it to personal tasks (in My Tasks).",
            required = true
        )
        @PathParam("projectId") String projectId,
        @ApiParam(
            value = "Full-text query against task name, description, and attachments.\n"
                  + "Note: does not include comment content or comment attachments.\n"
                  + "Indexing can take ~10 seconds or more after updates."
        )
        @QueryParam("text") String text,
        @ApiParam(
            value = "Task name filter. Prefix with `~` for regex, `~*` for case-insensitive regex. "
                  + "Use `text` for full-text search.",
            example = "My first task"
        )
        @QueryParam("name") String name,
        @ApiParam(
            value = "Task description filter. Prefix with `~` for regex, `~*` for case-insensitive regex.",
            example = "description=~john@gooodjob.com"
        )
        @QueryParam("description") String description,
        @ApiParam(
            value = "Task sublist ID or OID filter.",
            example = "Top"
        )
        @QueryParam("sublist") String sublist,
        @ApiParam(
            value = "Task status filter. Specify 0–100, or \"active\" / \"completed\".",
            example = "active"
        )
        @QueryParam("status") String status,
        @ApiParam(
            value = "Return only scheduled tasks (start or due is set). "
                  + "If `scheduled=false`, returns only tasks where neither start nor due is set.",
            example = "true"
        )
        @QueryParam("scheduled") boolean scheduled,
        @ApiParam(
            value = "Return only \"My Tasks\" (assigned to me, or created by me and scheduled but unassigned).",
            example = "true"
        )
        @QueryParam("mine") boolean mine,
        @ApiParam(
            value = "Return only tasks created or modified recently. "
                  + "Specify an integer (days); default 7 if omitted. "
                  + "Supports `d`, `h`, `m` suffixes (e.g., `8h`).",
            example = "7"
        )
        @QueryParam("modified") String modified,
        @ApiParam(
            value = "Return only tasks with recent comments. "
                  + "Specify an integer (days); default 7 if omitted. "
                  + "Supports `d`, `h`, `m` suffixes (e.g., `8h`).",
            example = "7"
        )
        @QueryParam("commented") String commented,
        @ApiParam(
            value = "Maximum number of tasks to return. Default: 30. "
                  + "Use `no` to return all matches.\n\n"
                  + "Note: On free plans, this cannot exceed 30 or `no` (unlimited).",
            example = "no"
        )
        @QueryParam("limit") String limit
    ) { return null; }

    @GET
    @Path("/search-organization/{organizationOid}")
    @ApiOperation(
        value = "Search tasks in an organization by organization OID.",
        notes = "Returns tasks that match the specified criteria in the given organization. "
              + "Tasks in archived projects are excluded.\n\n"
              + "> Available for Professional plans and above",
        response = TaskWithProjectParentInfo.class,
        responseContainer = "List"
    )
    public Response searchTasksByOrgOid(
        @ApiParam(value = "Organization OID.", required = true)
        @PathParam("organizationOid") String organizationOid,
        @ApiParam(
            value = "Full-text query against task name, description, and attachments.\n"
                  + "Note: does not include comment content or comment attachments.\n"
                  + "Indexing can take ~10 seconds or more after updates."
        )
        @QueryParam("text") String text,
        @ApiParam(
            value = "Task name filter. Prefix with `~` for regex, `~*` for case-insensitive regex. "
                  + "Use `text` for full-text search.",
            example = "My first task"
        )
        @QueryParam("name") String name,
        @ApiParam(
            value = "Task description filter. Prefix with `~` for regex, `~*` for case-insensitive regex.",
            example = "description=~john@gooodjob.com"
        )
        @QueryParam("description") String description,
        @ApiParam(
            value = "Task status filter. Specify 0–100, or \"active\" / \"completed\".",
            example = "active"
        )
        @QueryParam("status") String status,
        @ApiParam(
            value = "Return only scheduled tasks (start or due is set). "
                  + "If `scheduled=false`, returns only tasks where neither start nor due is set.",
            example = "true"
        )
        @QueryParam("scheduled") boolean scheduled,
        @ApiParam(
            value = "Return only \"My Tasks\" (assigned to me, or created by me and scheduled but unassigned).",
            example = "true"
        )
        @QueryParam("mine") boolean mine,
        @ApiParam(
            value = "Return only tasks created or modified recently. "
                  + "Specify an integer (days); default 7 if omitted. "
                  + "Supports `d`, `h`, `m` suffixes (e.g., `8h`).",
            example = "7"
        )
        @QueryParam("modified") String modified,
        @ApiParam(
            value = "Return only tasks with recent comments. "
                  + "Specify an integer (days); default 7 if omitted. "
                  + "Supports `d`, `h`, `m` suffixes (e.g., `8h`).",
            example = "7"
        )
        @QueryParam("commented") String commented,
        @ApiParam(
            value = "Maximum number of tasks to return. Default: 30. "
                  + "Use `no` to return all matches.\n\n"
                  + "Note: On free plans, this cannot exceed 30 or `no` (unlimited).",
            example = "no"
        )
        @QueryParam("limit") String limit
    ) { return null; }

    @GET
    @Path("/search-organization/id/{organizationId}")
    @ApiOperation(
        value = "Search tasks in an organization by organization ID.",
        notes = "Returns tasks that match the specified criteria in the given organization. "
              + "Tasks in archived projects are excluded.\n\n"
              + "> Available for Professional plans and above",
        response = TaskWithProjectParentInfo.class,
        responseContainer = "List"
    )
    public Response searchTasksByOrgId(
        @ApiParam(value = "Organization ID.", required = true)
        @PathParam("organizationId") String organizationId,
        @ApiParam(
            value = "Full-text query against task name, description, and attachments.\n"
                  + "Note: does not include comment content or comment attachments.\n"
                  + "Indexing can take ~10 seconds or more after updates."
        )
        @QueryParam("text") String text,
        @ApiParam(
            value = "Task name filter. Prefix with `~` for regex, `~*` for case-insensitive regex. "
                  + "Use `text` for full-text search.",
            example = "My first task"
        )
        @QueryParam("name") String name,
        @ApiParam(
            value = "Task description filter. Prefix with `~` for regex, `~*` for case-insensitive regex.",
            example = "description=~john@gooodjob.com"
        )
        @QueryParam("description") String description,
        @ApiParam(
            value = "Task status filter. Specify 0–100, or \"active\" / \"completed\".",
            example = "active"
        )
        @QueryParam("status") String status,
        @ApiParam(
            value = "Return only scheduled tasks (start or due is set). "
                  + "If `scheduled=false`, returns only tasks where neither start nor due is set.",
            example = "true"
        )
        @QueryParam("scheduled") boolean scheduled,
        @ApiParam(
            value = "Return only \"My Tasks\" (assigned to me, or created by me and scheduled but unassigned).",
            example = "true"
        )
        @QueryParam("mine") boolean mine,
        @ApiParam(
            value = "Return only tasks created or modified recently. "
                  + "Specify an integer (days); default 7 if omitted. "
                  + "Supports `d`, `h`, `m` suffixes (e.g., `8h`).",
            example = "7"
        )
        @QueryParam("modified") String modified,
        @ApiParam(
            value = "Return only tasks with recent comments. "
                  + "Specify an integer (days); default 7 if omitted. "
                  + "Supports `d`, `h`, `m` suffixes (e.g., `8h`).",
            example = "7"
        )
        @QueryParam("commented") String commented,
        @ApiParam(
            value = "Maximum number of tasks to return. Default: 30. "
                  + "Use `no` to return all matches.\n\n"
                  + "Note: On free plans, this cannot exceed 30 or `no` (unlimited).",
            example = "no"
        )
        @QueryParam("limit") String limit
    ) { return null; }

    @GET
    @Path("/search-folder/{folderOid}")
    @ApiOperation(
        value = "Search tasks in a folder by folder OID.",
        notes = "Returns tasks that match the specified criteria in the given folder. "
              + "Tasks in archived projects are excluded.\n\n"
              + "> Available for Professional plans and above",
        response = TaskWithProjectParentInfo.class,
        responseContainer = "List"
    )
    public Response searchTasksByFolderOid(
        @ApiParam(value = "Folder OID.", required = true)
        @PathParam("folderOid") String folderOid,
        @ApiParam(
            value = "Full-text query against task name, description, and attachments.\n"
                  + "Note: does not include comment content or comment attachments.\n"
                  + "Indexing can take ~10 seconds or more after updates."
        )
        @QueryParam("text") String text,
        @ApiParam(
            value = "Task name filter. Prefix with `~` for regex, `~*` for case-insensitive regex. "
                  + "Use `text` for full-text search.",
            example = "My first task"
        )
        @QueryParam("name") String name,
        @ApiParam(
            value = "Task description filter. Prefix with `~` for regex, `~*` for case-insensitive regex.",
            example = "description=~john@gooodjob.com"
        )
        @QueryParam("description") String description,
        @ApiParam(
            value = "Task status filter. Specify 0–100, or \"active\" / \"completed\".",
            example = "active"
        )
        @QueryParam("status") String status,
        @ApiParam(
            value = "Return only scheduled tasks (start or due is set). "
                  + "If `scheduled=false`, returns only tasks where neither start nor due is set.",
            example = "true"
        )
        @QueryParam("scheduled") boolean scheduled,
        @ApiParam(
            value = "Return only \"My Tasks\" (assigned to me, or created by me and scheduled but unassigned).",
            example = "true"
        )
        @QueryParam("mine") boolean mine,
        @ApiParam(
            value = "Return only tasks created or modified recently. "
                  + "Specify an integer (days); default 7 if omitted. "
                  + "Supports `d`, `h`, `m` suffixes (e.g., `8h`).",
            example = "7"
        )
        @QueryParam("modified") String modified,
        @ApiParam(
            value = "Return only tasks with recent comments. "
                  + "Specify an integer (days); default 7 if omitted. "
                  + "Supports `d`, `h`, `m` suffixes (e.g., `8h`).",
            example = "7"
        )
        @QueryParam("commented") String commented,
        @ApiParam(
            value = "Maximum number of tasks to return. Default: 30. "
                  + "Use `no` to return all matches.\n\n"
                  + "Note: On free plans, this cannot exceed 30 or `no` (unlimited).",
            example = "no"
        )
        @QueryParam("limit") String limit
    ) { return null; }

    @GET
    @Path("/search-folder/id/{folderId}")
    @ApiOperation(
        value = "Search tasks in a folder by folder ID.",
        notes = "Returns tasks that match the specified criteria in the given folder. "
              + "Tasks in archived projects are excluded.\n\n"
              + "> Available for Professional plans and above",
        response = TaskWithProjectParentInfo.class,
        responseContainer = "List"
    )
    public Response searchTasksByFolderId(
        @ApiParam(value = "Folder ID.", required = true)
        @PathParam("folderId") String folderId,
        @ApiParam(
            value = "Full-text query against task name, description, and attachments.\n"
                  + "Note: does not include comment content or comment attachments.\n"
                  + "Indexing can take ~10 seconds or more after updates."
        )
        @QueryParam("text") String text,
        @ApiParam(
            value = "Task name filter. Prefix with `~` for regex, `~*` for case-insensitive regex. "
                  + "Use `text` for full-text search.",
            example = "My first task"
        )
        @QueryParam("name") String name,
        @ApiParam(
            value = "Task description filter. Prefix with `~` for regex, `~*` for case-insensitive regex.",
            example = "description=~john@gooodjob.com"
        )
        @QueryParam("description") String description,
        @ApiParam(
            value = "Task status filter. Specify 0–100, or \"active\" / \"completed\".",
            example = "active"
        )
        @QueryParam("status") String status,
        @ApiParam(
            value = "Return only scheduled tasks (start or due is set). "
                  + "If `scheduled=false`, returns only tasks where neither start nor due is set.",
            example = "true"
        )
        @QueryParam("scheduled") boolean scheduled,
        @ApiParam(
            value = "Return only \"My Tasks\" (assigned to me, or created by me and scheduled but unassigned).",
            example = "true"
        )
        @QueryParam("mine") boolean mine,
        @ApiParam(
            value = "Return only tasks created or modified recently. "
                  + "Specify an integer (days); default 7 if omitted. "
                  + "Supports `d`, `h`, `m` suffixes (e.g., `8h`).",
            example = "7"
        )
        @QueryParam("modified") String modified,
        @ApiParam(
            value = "Return only tasks with recent comments. "
                  + "Specify an integer (days); default 7 if omitted. "
                  + "Supports `d`, `h`, `m` suffixes (e.g., `8h`).",
            example = "7"
        )
        @QueryParam("commented") String commented,
        @ApiParam(
            value = "Maximum number of tasks to return. Default: 30. "
                  + "Use `no` to return all matches.\n\n"
                  + "Note: On free plans, this cannot exceed 30 or `no` (unlimited).",
            example = "no"
        )
        @QueryParam("limit") String limit
    ) { return null; }
}
