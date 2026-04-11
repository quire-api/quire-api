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
        value = "Add a new root task, or relative to another task.",
        notes = "Adds a new task to a project, under another task, or before or after another task.",
        response = Task.class
    )
    public Response createTask(
        @ApiParam(
            value = "OID of the project or task to which the new task will be added. "
                  + "If the OID refers to a project, the new task becomes a root task. "
                  + "If it refers to a task, the new task becomes its subtask. "
                  + "Specify \"-\" to add the task to My Tasks.",
            required = true
        )
        @PathParam("oid") String oid,

        @ApiParam(value = "Task to create.", required = true)
        CreateTaskBody data,

        @ApiParam(
            value = "Position of the new task relative to the task specified by `oid`. "
                + "Allowed values are `before`, `after`, and `parent`. "
                + "If omitted, `parent` is assumed. "
                + "This parameter applies only when `oid` refers to a task.\n\n"
                + "- `before`: before the specified task\n"
                + "- `after`: after the specified task\n"
                + "- `parent`: under the specified task",
            example = "before"
        )
        @QueryParam("position") String position
    ) { return null; }

    @POST
    @Path("/id/{projectId}")
    @ApiOperation(
        value = "Add a new root task.",
        notes = "Adds a new root task to a project.",
        response = Task.class
    )
    public Response createTaskByProjectId(
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
    @Path("/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Add a new task relative to another task.",
        notes = "Adds a new task under another task, or before or after another task.",
        response = Task.class
    )
    public Response createTaskByTaskId(
        @ApiParam(
            value = "ID of the project to which the new task will be added. "
                  + "Specify \"-\" to add it to personal tasks in My Tasks.",
            required = true
        )
        @PathParam("projectId") String projectId,

        @ApiParam(value = "ID of the referenced task.", required = true)
        @PathParam("taskId") int taskId,

        @ApiParam(value = "Task to create.", required = true)
        CreateTaskBody data,

        @ApiParam(
            value = "Position of the new task relative to the task specified by `taskId`. "
                + "Allowed values are `before`, `after`, and `parent`. "
                + "If omitted, `parent` is assumed.\n\n"
                + "- `before`: before the specified task\n"
                + "- `after`: after the specified task\n"
                + "- `parent`: under the specified task",
            example = "before"
        )
        @QueryParam("position") String position
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
            value = "ID of the task's project. "
                  + "Specify \"-\" for personal tasks in My Tasks.",
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
            value = "ID of the task's project. "
                  + "Specify \"-\" for personal tasks in My Tasks.",
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
    @Path("/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Get an existing task by its ID.",
        notes = "Returns the full task record.",
        response = TaskWithParentInfo.class
    )
    public Response getTaskById(
        @ApiParam(
            value = "ID of the task's project. "
                  + "Specify \"-\" for personal tasks in My Tasks.",
            required = true
        )
        @PathParam("projectId") String projectId,

        @ApiParam(value = "Task ID.", required = true)
        @PathParam("taskId") int taskId
    ) { return null; }

    @PUT
    @Path("/{oid}")
    @ApiOperation(
        value = "Update an existing task by its OID.",
        notes = "Updates an existing task and returns the updated record.",
        response = TaskWithParentInfo.class
    )
    public Response updateTask(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Updated task content.", required = true)
        UpdateTaskBody data
    ) { return null; }

    @PUT
    @Path("/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Update an existing task by its ID.",
        notes = "Updates an existing task and returns the updated record.",
        response = TaskWithParentInfo.class
    )
    public Response updateTaskById(
        @ApiParam(
            value = "ID of the task's project. "
                  + "Specify \"-\" for personal tasks in My Tasks.",
            required = true
        )
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Task ID.", required = true)

        @PathParam("taskId") int taskId,
        @ApiParam(value = "Updated task content.", required = true)
        UpdateTaskBody data
    ) { return null; }

    @PUT
    @Path("/move/{oid}")
    @ApiOperation(
        value = "Move an existing task by its OID.",
        notes = "Moves an existing task under another task as a subtask, "
            + "or to the root level. "
            + "Returns the updated task record.",
        response = TaskWithParentInfo.class
    )
    public Response moveTask(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("oid") String oid,

        @ApiParam(
            value = "The task that will become the new parent of the moved task. "
                + "If `root` is specified, the moved task becomes a root task.",
            required = true,
            example = "0Mg3VQ8kWeiVbLH1JjvzUcP7"
        )
        @QueryParam("task") String task

    ) { return null; }

    @PUT
    @Path("/move/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Move an existing task by its ID.",
        notes = "Moves an existing task under another task as a subtask, "
            + "or to the root level. "
            + "Returns the updated task record.",
        response = TaskWithParentInfo.class
    )
    public Response moveTaskById(
        @ApiParam(
            value = "ID of the task's project. "
                  + "Specify \"-\" for personal tasks in My Tasks.",
            required = true
        )
        @PathParam("projectId") String projectId,

        @ApiParam(value = "Task ID.", required = true)
        @PathParam("taskId") int taskId,

        @ApiParam(
            value = "ID of the task that will become the new parent of the moved task. "
                + "If `root` is specified, the moved task becomes a root task.",
            required = true,
            example = "253"
        )
        @QueryParam("task") String task

    ) { return null; }

    @PUT
    @Path("/transfer/{oid}")
    @ApiOperation(
        value = "Transfer an existing task by its OID to another project.",
        notes = "Transfers an existing task to another project. "
            + "Returns the updated task record.",
        response = TaskWithParentInfo.class
    )
    public Response transferTask(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("oid") String oid,

        @ApiParam(
            value = "OID of the target project to which the task will be transferred. "
                  + "Specify \"-\" for personal tasks in My Tasks.",
            required = true,
            example = "0Mg3VQ8kWeiVbLH1PrjzUc89"
        )
        @QueryParam("project") String project,

        @ApiParam(
            value = "Optional. OID of the task that will become the new parent "
                + "of the transferred task. If omitted, the transferred task "
                + "becomes a root task in the target project.",
            example = "0Mg3VQ8kWeiVbLH1JjvzUcP7"
        )
        @QueryParam("task") String task,

        @ApiParam(
            value = "Whether to invite assignees to the target project if they "
                + "are not already members.\n\n"
                + "If omitted, `true` is assumed. Specify `false` to disable this.",
            example = "true"
        )
        @QueryParam("invite") String invite,
        @ApiParam(
            value = "Whether to add tags to the target project if they are not "
                + "already present.\n\n"
                + "If omitted, `true` is assumed. Specify `false` to disable this.",
            example = "true"
        )
        @QueryParam("tag") String tag,
        @ApiParam(
            value = "Whether to add statuses to the target project if they are "
                + "not already present.\n\n"
                + "If omitted, `true` is assumed. Specify `false` to disable this.",
            example = "true"
        )
        @QueryParam("status") String status,
        @ApiParam(
            value = "Whether to add non-empty custom fields to the target project "
                + "if they are not already present.\n\n"
                + "If omitted, `true` is assumed. Specify `false` to disable this.",
            example = "true"
        )
        @QueryParam("custom-field") String customField

    ) { return null; }

    @PUT
    @Path("/transfer/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Transfer an existing task by its ID to another project.",
        notes = "Transfers an existing task to another project. "
            + "Returns the updated task record.",
        response = TaskWithParentInfo.class
    )
    public Response transferTaskById(
        @ApiParam(
            value = "ID of the task's project. "
                  + "Specify \"-\" for personal tasks in My Tasks.",
            required = true
        )
        @PathParam("projectId") String projectId,

        @ApiParam(value = "Task ID.", required = true)
        @PathParam("taskId") int taskId,

        @ApiParam(
            value = "ID of the target project to which the task will be transferred. "
                  + "Specify \"-\" for personal tasks in My Tasks.",
            required = true,
            example = "MyArchived"
        )
        @QueryParam("project") String project,

        @ApiParam(
            value = "Optional. ID of the task that will become the new parent "
                + "of the transferred task. If omitted, the transferred task "
                + "becomes a root task in the target project.",
            example = "253"
        )
        @QueryParam("task") String task,

        @ApiParam(
            value = "Whether to invite assignees to the target project if they "
                + "are not already members.\n\n"
                + "If omitted, `true` is assumed. Specify `false` to disable this.",
            example = "true"
        )
        @QueryParam("invite") String invite,
        @ApiParam(
            value = "Whether to add tags to the target project if they are not "
                + "already present.\n\n"
                + "If omitted, `true` is assumed. Specify `false` to disable this.",
            example = "true"
        )
        @QueryParam("tag") String tag,
        @ApiParam(
            value = "Whether to add statuses to the target project if they are "
                + "not already present.\n\n"
                + "If omitted, `true` is assumed. Specify `false` to disable this.",
            example = "true"
        )
        @QueryParam("status") String status,
        @ApiParam(
            value = "Whether to add non-empty custom fields to the target project "
                + "if they are not already present.\n\n"
                + "If omitted, `true` is assumed. Specify `false` to disable this.",
            example = "true"
        )
        @QueryParam("custom-field") String customField

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
    @Path("/attach/id/{projectId}/{taskId}/{filename}")
    @ApiOperation(
        value = "Upload an attachment to a task by ID.",
        notes = "Uploads an attachment to an existing task.",
        response = SimpleAttachment.class
    )
    public Response attachCommentById(
        @ApiParam(
            value = "ID of the task's project. "
                  + "Specify \"-\" for personal tasks in My Tasks.",
            required = true
        )
        @PathParam("projectId") String projectId,

        @ApiParam(value = "Task ID.", required = true)
        @PathParam("taskId") int taskId,

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
        value = "Delete a task and all of its subtasks by its OID.",
        notes = "Deletes an existing task and all of its subtasks.\n\n"
            + "> Note: Returns `204 No Content` regardless of whether the task exists."
    )
    @ApiResponses({
        @ApiResponse(
            code = 204,
            message = "No Content"
        )
    })
    public Response deleteTaskByOid(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("oid") String oid
    ) {
        return null;
    }

    @DELETE
    @Path("/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Delete a task and all of its subtasks by its ID.",
        notes = "Deletes an existing task and all of its subtasks.\n\n"
            + "> Note: Returns `204 No Content` regardless of whether the task exists."
    )
    @ApiResponses({
        @ApiResponse(
            code = 204,
            message = "No Content"
        )
    })
    public Response deleteTaskById(
        @ApiParam(
            value = "Project ID. Specify \"-\" to remove from personal tasks in My Tasks.",
            required = true
        )
        @PathParam("projectId") String projectId,

        @ApiParam(value = "Task ID.", required = true)
        @PathParam("taskId") int taskId
    ) {
        return null;
    }

    @GET
    @Path("/search/{projectOid}")
    @ApiOperation(
        value = "Search tasks in a project by project OID.",
        notes = "Returns tasks that match the specified criteria in the project.\n\n"
              + "### Custom Field Filtering\n\n"
              + "You can filter by custom fields by passing the field name as a query parameter "
              + "(case-insensitive). For example, `?cost=13` or `?approvedBy=john.doe`.\n\n"
              + "Supported field types and value formats:\n\n"
              + "| Type | Value format | Example |\n"
              + "| --- | --- | --- |\n"
              + "| Number, Money | Numeric value | `cost=13` |\n"
              + "| Checkbox | `true` or `false` | `done=true` |\n"
              + "| Select | Option name | `priority=High` |\n"
              + "| User | User ID or OID | `approvedBy=john.doe` |\n"
              + "| Task | Task ID (integer) or OID | `blockedBy=42` |\n"
              + "| Email, Hyperlink | Exact value, or prefix with `~` for regex, `~*` for case-insensitive regex | `website=~example\\.com` |\n"
              + "| Duration | Seconds, or with suffix: `d` (days), `h` (hours), `m` (minutes) | `estimate=2h` |\n\n"
              + "Not supported: Text (use `text` for full-text search instead), Date, Formula, File, Lookup.",
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
        notes = "Returns tasks that match the specified criteria in the project.\n\n"
              + "### Custom Field Filtering\n\n"
              + "You can filter by custom fields by passing the field name as a query parameter "
              + "(case-insensitive). For example, `?cost=13` or `?approvedBy=john.doe`.\n\n"
              + "Supported field types and value formats:\n\n"
              + "| Type | Value format | Example |\n"
              + "| --- | --- | --- |\n"
              + "| Number, Money | Numeric value | `cost=13` |\n"
              + "| Checkbox | `true` or `false` | `done=true` |\n"
              + "| Select | Option name | `priority=High` |\n"
              + "| User | User ID or OID | `approvedBy=john.doe` |\n"
              + "| Task | Task ID (integer) or OID | `blockedBy=42` |\n"
              + "| Email, Hyperlink | Exact value, or prefix with `~` for regex, `~*` for case-insensitive regex | `website=~example\\.com` |\n"
              + "| Duration | Seconds, or with suffix: `d` (days), `h` (hours), `m` (minutes) | `estimate=2h` |\n\n"
              + "Not supported: Text (use `text` for full-text search instead), Date, Formula, File, Lookup.",
        response = TaskWithParentInfo.class,
        responseContainer = "List"
    )
    public Response searchTasksById(
        @ApiParam(
            value = "ID of the task's project. "
                  + "Specify \"-\" for personal tasks in My Tasks.",
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
