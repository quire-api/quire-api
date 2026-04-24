package io.quire.api.resource;

import io.quire.api.model.*;
import io.quire.api.model.approval.*;
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
            value = "(Optional) Placement of the new task relative to the "
                + "task referenced by `oid`. This parameter applies only "
                + "when `oid` refers to a task:\n"
                + "- `parent` (default): child of the referenced task.\n"
                + "- `before`: sibling immediately before the referenced task.\n"
                + "- `after`: sibling immediately after the referenced task.\n\n"
                + "Omitted or empty → same as `parent`. Any other value "
                + "returns `400 Bad Request`.",
            example = "before",
            allowableValues = "parent, before, after"
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
            value = "(Optional) Placement of the new task relative to the "
                + "task referenced by `taskId`:\n"
                + "- `parent` (default): child of the referenced task.\n"
                + "- `before`: sibling immediately before the referenced task.\n"
                + "- `after`: sibling immediately after the referenced task.\n\n"
                + "Omitted or empty → same as `parent`. Any other value "
                + "returns `400 Bad Request`.",
            example = "before",
            allowableValues = "parent, before, after"
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
            + "reorders it among siblings, or sends it to the root level.\n\n"
            + "Use `?task=` to name the reference task and `?position=` to "
            + "choose placement (`parent` / `before` / `after`). "
            + "See the by-ID variant `PUT /task/move/id/{projectId}/{taskId}` "
            + "for the preferred URL form.\n\n"
            + "Returns the updated task record.",
        response = TaskWithParentInfo.class
    )
    public Response moveTask(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("oid") String oid,

        @ApiParam(
            value = "Reference task OID. How it's used depends on `position`:\n"
                + "- `parent` (default): the moved task becomes a child of `task`.\n"
                + "- `before` / `after`: the moved task is placed as a sibling, "
                + "under `task`'s parent.\n\n"
                + "Specify `root` to move to the root level (only valid with the "
                + "default `parent` position).",
            required = true,
            example = "0Mg3VQ8kWeiVbLH1JjvzUcP7"
        )
        @QueryParam("task") String task,

        @ApiParam(
            value = "(Optional) Placement relative to `task`:\n"
                + "- `parent` (default): child of `task`.\n"
                + "- `before`: sibling immediately before `task`.\n"
                + "- `after`: sibling immediately after `task`.\n\n"
                + "Omitted or empty → same as `parent`. Any other value returns "
                + "`400 Bad Request`. `before` / `after` require `task` to refer "
                + "to a task (not `root`).",
            example = "before",
            allowableValues = "parent, before, after"
        )
        @QueryParam("position") String position

    ) { return null; }

    @PUT
    @Path("/move/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Move an existing task by its ID.",
        notes = "Moves an existing task under another task as a subtask, "
            + "reorders it among siblings, or sends it to the root level.\n\n"
            + "Use `?task=` to name the reference task and `?position=` to "
            + "choose placement (`parent` / `before` / `after`).\n\n"
            + "Examples:\n"
            + "- `PUT /task/move/id/my_project/42?task=99` — reparent (default: child of task 99).\n"
            + "- `PUT /task/move/id/my_project/42?task=99&position=before` — place task 42 before task 99.\n"
            + "- `PUT /task/move/id/my_project/42?task=99&position=after` — place task 42 after task 99.\n"
            + "- `PUT /task/move/id/my_project/42?task=root` — move to root level.\n\n"
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
            value = "Reference task ID. How it's used depends on `position`:\n"
                + "- `parent` (default): the moved task becomes a child of `task`.\n"
                + "- `before` / `after`: the moved task is placed as a sibling, "
                + "under `task`'s parent.\n\n"
                + "Specify `root` to move to the root level (only valid with the "
                + "default `parent` position).",
            required = true,
            example = "253"
        )
        @QueryParam("task") String task,

        @ApiParam(
            value = "(Optional) Placement relative to `task`:\n"
                + "- `parent` (default): child of `task`.\n"
                + "- `before`: sibling immediately before `task`.\n"
                + "- `after`: sibling immediately after `task`.\n\n"
                + "Omitted or empty → same as `parent`. Any other value returns "
                + "`400 Bad Request`. `before` / `after` require `task` to refer "
                + "to a task (not `root`).",
            example = "before",
            allowableValues = "parent, before, after"
        )
        @QueryParam("position") String position

    ) { return null; }

    @PUT
    @Path("/transfer/{oid}")
    @ApiOperation(
        value = "Transfer an existing task by its OID to another project.",
        notes = "Transfers an existing task to another project, optionally "
            + "positioning it relative to an existing task in the target "
            + "(`?task=` + `?position=`). See the by-ID variant "
            + "`PUT /task/transfer/id/{projectId}/{taskId}` for the preferred "
            + "URL form.\n\n"
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
            value = "(Optional) OID of a reference task in the **target** "
                + "project. How it's used depends on `position`:\n"
                + "- `parent` (default): the transferred task becomes a "
                + "child of `task`.\n"
                + "- `before` / `after`: the transferred task is placed as "
                + "a sibling, under `task`'s parent.\n\n"
                + "If omitted (or `root`), the transferred task becomes a "
                + "root task in the target project.",
            example = "0Mg3VQ8kWeiVbLH1JjvzUcP7"
        )
        @QueryParam("task") String task,

        @ApiParam(
            value = "(Optional) Placement relative to `task` in the target "
                + "project:\n"
                + "- `parent` (default): child of `task`.\n"
                + "- `before`: sibling immediately before `task`.\n"
                + "- `after`: sibling immediately after `task`.\n\n"
                + "Omitted or empty → same as `parent`. Any other value "
                + "returns `400 Bad Request`. `before` / `after` require "
                + "`task` to refer to a task (not omitted, not `root`).",
            example = "before",
            allowableValues = "parent, before, after"
        )
        @QueryParam("position") String position,

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
        notes = "Transfers an existing task to another project, optionally "
            + "positioning it relative to an existing task in the target "
            + "(`?task=` + `?position=`).\n\n"
            + "Examples:\n"
            + "- `PUT /task/transfer/id/my_project/42?project=archive` — transfer to root of target project.\n"
            + "- `PUT /task/transfer/id/my_project/42?project=archive&task=99` — transfer as a child of task 99 in target.\n"
            + "- `PUT /task/transfer/id/my_project/42?project=archive&task=99&position=before` — transfer and place before task 99.\n"
            + "- `PUT /task/transfer/id/my_project/42?project=-` — transfer to the user's Inbox.\n\n"
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
            example = "my_archive"
        )
        @QueryParam("project") String project,

        @ApiParam(
            value = "(Optional) ID of a reference task in the **target** "
                + "project. How it's used depends on `position`:\n"
                + "- `parent` (default): the transferred task becomes a "
                + "child of `task`.\n"
                + "- `before` / `after`: the transferred task is placed as "
                + "a sibling, under `task`'s parent.\n\n"
                + "If omitted (or `root`), the transferred task becomes a "
                + "root task in the target project.",
            example = "253"
        )
        @QueryParam("task") String task,

        @ApiParam(
            value = "(Optional) Placement relative to `task` in the target "
                + "project:\n"
                + "- `parent` (default): child of `task`.\n"
                + "- `before`: sibling immediately before `task`.\n"
                + "- `after`: sibling immediately after `task`.\n\n"
                + "Omitted or empty → same as `parent`. Any other value "
                + "returns `400 Bad Request`. `before` / `after` require "
                + "`task` to refer to a task (not omitted, not `root`).",
            example = "before",
            allowableValues = "parent, before, after"
        )
        @QueryParam("position") String position,

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
    public Response attachTaskByOid(
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
    public Response attachTaskById(
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

    @PUT
    @Path("/undo-remove/{oid}")
    @ApiOperation(
        value = "Undo the removal of a task by its OID.",
        notes = "Restores a previously-removed task. "
            + "Idempotent: if the task is not currently removed, this is a "
            + "no-op and returns the current task record.\n\n"
            + "Subject to the task-creation quota (same as creating a new "
            + "task): may return `429 Too Many Requests` if the "
            + "organization is at the plan's task limit.",
        response = TaskWithParentInfo.class
    )
    public Response undoRemoveTaskByOid(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @PUT
    @Path("/undo-remove/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Undo the removal of a task by its ID.",
        notes = "Restores a previously-removed task. "
            + "Idempotent: if the task is not currently removed, this is a "
            + "no-op and returns the current task record.\n\n"
            + "Subject to the task-creation quota (same as creating a new "
            + "task): may return `429 Too Many Requests` if the "
            + "organization is at the plan's task limit.",
        response = TaskWithParentInfo.class
    )
    public Response undoRemoveTaskById(
        @ApiParam(
            value = "Project ID. Specify \"-\" for personal tasks in My Tasks.",
            required = true
        )
        @PathParam("projectId") String projectId,

        @ApiParam(value = "Task ID.", required = true)
        @PathParam("taskId") int taskId
    ) { return null; }

    // -------- Approval --------

    @POST
    @Path("/approve/{oid}")
    @ApiOperation(
        value = "Set or transition a task's approval state.",
        notes = "Sets the task's [approval](#definition-Approval), or "
              + "transitions it from its current state. The same endpoint "
              + "handles every action — the request body's `state` token "
              + "selects the transition:\n"
              + "- `request` — request approval, or roll forward from "
              + "`approved` / `rejected` / `changes` back to `awaiting`.\n"
              + "- `approve` / `reject` / `change` — approver's decision.\n\n"
              + "The caller must be a claimer (for `request`) or an "
              + "approver (for `approve` / `reject` / `change`) of the "
              + "category. The original requester is preserved across "
              + "`approve` / `reject` / `change` transitions.\n\n"
              + "To cancel an approval, use `DELETE /task/revoke-approval/id/{projectId}/{taskId}` "
              + "(or `DELETE /task/revoke-approval/{oid}` by OID).\n\n"
              + "Returns `400 Bad Request` if `state` is missing or unknown; "
              + "`403 Forbidden` if the caller isn't a claimer / approver; "
              + "`404 Not Found` if the task or `category` doesn't exist.",
        response = Approval.class
    )
    public Response approveTaskByOid(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Transition to apply.", required = true)
        ApproveTaskBody data
    ) { return null; }

    @POST
    @Path("/approve/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Set or transition a task's approval state by ID.",
        notes = "Sets or transitions a task's approval state, identifying the "
              + "task by project + task ID. The request body's `state` token "
              + "(`request` / `approve` / `reject` / `change`) selects the "
              + "transition. To cancel, use `DELETE /task/revoke-approval/id/{projectId}/{taskId}`. "
              + "See also `/task/approve/{oid}` for the OID form.",
        response = Approval.class
    )
    public Response approveTaskById(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Task ID.", required = true)
        @PathParam("taskId") int taskId,
        @ApiParam(value = "Transition to apply.", required = true)
        ApproveTaskBody data
    ) { return null; }

    @DELETE
    @Path("/revoke-approval/{oid}")
    @ApiOperation(
        value = "Cancel a task's approval.",
        notes = "Revokes the task's current approval. The effect depends "
              + "on the current state:\n"
              + "- `awaiting` / `changes` → clears the approval entirely.\n"
              + "- `approved` / `rejected` → rolls back to `awaiting` "
              + "(the original requester is preserved; the approver can "
              + "re-decide via `POST /task/approve/id/{projectId}/{taskId}`, "
              + "or `DELETE` again to clear).\n\n"
              + "Idempotent: returns `204 No Content` even when no "
              + "approval is set."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content")
    })
    public Response revokeTaskApprovalByOid(
        @ApiParam(value = "Task OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @DELETE
    @Path("/revoke-approval/id/{projectId}/{taskId}")
    @ApiOperation(
        value = "Cancel a task's approval by ID.",
        notes = "Cancels a task's approval, identifying the task by project + "
              + "task ID. Smart-dispatched: from `awaiting` / `changes` the "
              + "approval is cleared entirely; from `approved` / `rejected` it "
              + "rolls back to `awaiting` (original requester preserved). "
              + "Idempotent — returns `204` even when no approval is set. See "
              + "also `/task/revoke-approval/{oid}` for the OID form."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content")
    })
    public Response revokeTaskApprovalById(
        @ApiParam(value = "Project ID.", required = true)
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Task ID.", required = true)
        @PathParam("taskId") int taskId
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
              + "| User | User ID, email, or OID | `approvedBy=john.doe` |\n"
              + "| Task | Task ID (integer) or OID | `blockedBy=42` |\n"
              + "| Email, Hyperlink | Exact value, or prefix with `~` for regex, `~*` for case-insensitive regex | `website=~example\\.com` |\n"
              + "| Duration | Seconds, or with suffix: `d` (days), `h` (hours), `m` (minutes) | `estimate=2h` |\n"
              + "| Date | Same grammar as the `start` / `due` query params — keyword ops (`today`, `past`, `last7d`, ...), value ops (`ge:<v>`, `lt:<v>`, `between:<v1>,<v2>`, ...), and null ops (`isNull`, `isNotNull`). `<v>` is `YYYY-MM-DD` or ISO 8601 `YYYY-MM-DDTHH:MM:SSZ`; timestamp operands require a `withTime: true` field. | `approvedAt=today`, `approvedAt=ge:2026-04-01`, `approvedAt=between:2026-04-01,2026-04-30`, `approvedAt=isNull` |\n\n"
              + "Not supported: Text (use `text` for full-text search instead), Formula, File, Lookup.",
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
            value = "Filter by assignee. Each value is a user OID, user ID, "
                  + "or email.\n\n"
                  + "Combine values with `,` (AND), `|` (OR), and `!` (NOT). "
                  + "AND binds tighter than OR.\n\n"
                  + "Examples:\n"
                  + "- `alice` — a single user (by ID)\n"
                  + "- `alice@example.com` — by email\n"
                  + "- `alice,bob` — assigned to both\n"
                  + "- `alice|bob` — assigned to either\n"
                  + "- `alice,!bob` — assigned to alice but not bob\n"
                  + "- `alice,bob|carol` — `(alice AND bob) OR carol`",
            example = "alice,!bob"
        )
        @QueryParam("assignee") String assignee,
        @ApiParam(
            value = "Filter by assignor (the user who set the assignment). "
                  + "Each value is a user OID, user ID, or email. Combine "
                  + "with `,` (AND), `|` (OR), `!` (NOT); AND binds tighter "
                  + "than OR.\n\n"
                  + "Examples: `alice`, `alice,!bob`, `alice|bob`.",
            example = "alice"
        )
        @QueryParam("assignor") String assignor,
        @ApiParam(
            value = "Filter by follower. Each value is a user OID, user ID, "
                  + "or email. Combine with `,` (AND), `|` (OR), `!` (NOT); "
                  + "AND binds tighter than OR.\n\n"
                  + "Examples: `alice`, `alice,!bob`, `alice|bob`.",
            example = "alice|bob"
        )
        @QueryParam("follower") String follower,
        @ApiParam(
            value = "Filter by tag. Each value is a tag OID or a tag name "
                  + "(names are resolved within the search's "
                  + "organization).\n\n"
                  + "Combine with `,` (AND), `|` (OR), `!` (NOT); AND binds "
                  + "tighter than OR.\n\n"
                  + "Names containing `,`, `|`, `!`, `\"`, or whitespace "
                  + "must be wrapped in `\"...\"`. Inside quotes, write "
                  + "`\\\"` for a literal `\"` and `\\\\` for a literal "
                  + "`\\`; any other `\\x` is preserved as two literal "
                  + "characters.\n\n"
                  + "Examples:\n"
                  + "- `Legal` — a single tag\n"
                  + "- `Legal,!Draft` — has `Legal` but not `Draft`\n"
                  + "- `\"In Progress\",!Done` — quoted name with space\n"
                  + "- `\"Foo\\\"s Go\"|Other` — quoted with escaped `\"`",
            example = "Legal,!Draft"
        )
        @QueryParam("tag") String tag,
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
            value = "Filter by the task's `createdAt` timestamp. "
                  + "Value grammar:\n"
                  + "- Keyword: `past`, `yesterday`, `today`, `tomorrow`, `upcoming`, `last7d`, `next7d`, `lastWeek`, `thisWeek`, `nextWeek`\n"
                  + "- Value op: `{ge|gt|le|lt|eq|ne}:<timestamp>` or `{between|notBetween}:<timestamp>,<timestamp>`\n"
                  + "- `<timestamp>` is ISO 8601 `YYYY-MM-DDTHH:MM:SSZ` (UTC)\n"
                  + "- Day boundaries resolve in the caller's timezone; week start follows the caller's locale\n"
                  + "- `past` on this field is `< now()`\n"
                  + "- Tokens and keywords are case-insensitive\n\n"
                  + "Examples: `today`, `last7d`, `ge:2026-04-01T00:00:00Z`, "
                  + "`between:2026-04-01T00:00:00Z,2026-04-30T23:59:59Z`.",
            example = "ge:2026-04-01T00:00:00Z"
        )
        @QueryParam("created") String created,
        @ApiParam(
            value = "Filter by the task's `editedAt` timestamp. Same grammar "
                  + "as `created`. Note: `editedAt` is `NULL` for tasks that "
                  + "have never been edited after creation; such tasks are "
                  + "excluded by every op (use `created` to filter by creation "
                  + "time).\n\n"
                  + "Examples: `today`, `last7d`, `ge:2026-04-01T00:00:00Z`.",
            example = "last7d"
        )
        @QueryParam("edited") String edited,
        @ApiParam(
            value = "Filter by the task's `archivedAt` timestamp. Same grammar "
                  + "as `created`, plus null ops since the field is nullable:\n"
                  + "- `isNull` — not archived\n"
                  + "- `isNotNull` — archived (any time)\n\n"
                  + "Examples: `isNotNull`, `today`, `ge:2026-04-01T00:00:00Z`.",
            example = "isNotNull"
        )
        @QueryParam("archived") String archived,
        @ApiParam(
            value = "Filter by the task's `reshowAt` timestamp (scheduled "
                  + "un-archive). Same grammar as `archived` (supports "
                  + "`isNull` / `isNotNull`).\n\n"
                  + "Examples: `isNotNull`, `today`, `between:2026-04-01T00:00:00Z,2026-04-30T23:59:59Z`.",
            example = "isNotNull"
        )
        @QueryParam("unarchived") String unarchived,
        @ApiParam(
            value = "Filter by the task's `toggledAt` timestamp (when its "
                  + "completion status was last changed). Same grammar as "
                  + "`archived` (supports `isNull` / `isNotNull`).\n\n"
                  + "Examples: `today`, `isNotNull`, `ge:2026-04-01T00:00:00Z`.",
            example = "today"
        )
        @QueryParam("toggled") String toggled,
        @ApiParam(
            value = "Filter by the task's `start` date/time. Value grammar:\n"
                  + "- Keyword: `past`, `yesterday`, `today`, `tomorrow`, `upcoming`, `last7d`, `next7d`, `lastWeek`, `thisWeek`, `nextWeek`\n"
                  + "- Value op: `{ge|gt|le|lt|eq|ne}:<value>` or `{between|notBetween}:<value>,<value>`\n"
                  + "- Null op: `isNull`, `isNotNull`\n"
                  + "- `<value>` is either a date (`YYYY-MM-DD`) or a full ISO 8601 timestamp (`YYYY-MM-DDTHH:MM:SSZ`)\n"
                  + "- A date operand expands to a whole-day window in the caller's timezone (`ge:2026-04-01` means \"on or after Apr 1 in the caller's calendar\"); `gt:D`/`le:D` use end-of-day, `lt:D`/`ge:D` use start-of-day\n"
                  + "- `past` on `start`/`due` is `< today 00:00` in the caller's timezone (not `< now()`)\n"
                  + "- `upcoming` on `start`/`due` is the window `[tomorrow, today + 7 days)`\n\n"
                  + "Examples: `today`, `upcoming`, `ge:2026-04-01`, `between:2026-04-01,2026-04-30`, `isNull`.",
            example = "ge:2026-04-01"
        )
        @QueryParam("start") String start,
        @ApiParam(
            value = "Filter by the task's `due` date/time. Same grammar and "
                  + "semantics as `start` (accepts date-only operands, "
                  + "`isNull`/`isNotNull`, and the same keyword/value ops).\n\n"
                  + "Examples: `today`, `upcoming`, `le:2026-04-30`, `isNotNull`.",
            example = "today"
        )
        @QueryParam("due") String due,
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
              + "| User | User ID, email, or OID | `approvedBy=john.doe` |\n"
              + "| Task | Task ID (integer) or OID | `blockedBy=42` |\n"
              + "| Email, Hyperlink | Exact value, or prefix with `~` for regex, `~*` for case-insensitive regex | `website=~example\\.com` |\n"
              + "| Duration | Seconds, or with suffix: `d` (days), `h` (hours), `m` (minutes) | `estimate=2h` |\n"
              + "| Date | Same grammar as the `start` / `due` query params — keyword ops (`today`, `past`, `last7d`, ...), value ops (`ge:<v>`, `lt:<v>`, `between:<v1>,<v2>`, ...), and null ops (`isNull`, `isNotNull`). `<v>` is `YYYY-MM-DD` or ISO 8601 `YYYY-MM-DDTHH:MM:SSZ`; timestamp operands require a `withTime: true` field. | `approvedAt=today`, `approvedAt=ge:2026-04-01`, `approvedAt=between:2026-04-01,2026-04-30`, `approvedAt=isNull` |\n\n"
              + "Not supported: Text (use `text` for full-text search instead), Formula, File, Lookup.",
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
            value = "Filter by assignee. Each value is a user OID, user ID, "
                  + "or email.\n\n"
                  + "Combine values with `,` (AND), `|` (OR), and `!` (NOT). "
                  + "AND binds tighter than OR.\n\n"
                  + "Examples:\n"
                  + "- `alice` — a single user (by ID)\n"
                  + "- `alice@example.com` — by email\n"
                  + "- `alice,bob` — assigned to both\n"
                  + "- `alice|bob` — assigned to either\n"
                  + "- `alice,!bob` — assigned to alice but not bob\n"
                  + "- `alice,bob|carol` — `(alice AND bob) OR carol`",
            example = "alice,!bob"
        )
        @QueryParam("assignee") String assignee,
        @ApiParam(
            value = "Filter by assignor (the user who set the assignment). "
                  + "Each value is a user OID, user ID, or email. Combine "
                  + "with `,` (AND), `|` (OR), `!` (NOT); AND binds tighter "
                  + "than OR.\n\n"
                  + "Examples: `alice`, `alice,!bob`, `alice|bob`.",
            example = "alice"
        )
        @QueryParam("assignor") String assignor,
        @ApiParam(
            value = "Filter by follower. Each value is a user OID, user ID, "
                  + "or email. Combine with `,` (AND), `|` (OR), `!` (NOT); "
                  + "AND binds tighter than OR.\n\n"
                  + "Examples: `alice`, `alice,!bob`, `alice|bob`.",
            example = "alice|bob"
        )
        @QueryParam("follower") String follower,
        @ApiParam(
            value = "Filter by tag. Each value is a tag OID or a tag name "
                  + "(names are resolved within the search's "
                  + "organization).\n\n"
                  + "Combine with `,` (AND), `|` (OR), `!` (NOT); AND binds "
                  + "tighter than OR.\n\n"
                  + "Names containing `,`, `|`, `!`, `\"`, or whitespace "
                  + "must be wrapped in `\"...\"`. Inside quotes, write "
                  + "`\\\"` for a literal `\"` and `\\\\` for a literal "
                  + "`\\`; any other `\\x` is preserved as two literal "
                  + "characters.\n\n"
                  + "Examples:\n"
                  + "- `Legal` — a single tag\n"
                  + "- `Legal,!Draft` — has `Legal` but not `Draft`\n"
                  + "- `\"In Progress\",!Done` — quoted name with space\n"
                  + "- `\"Foo\\\"s Go\"|Other` — quoted with escaped `\"`",
            example = "Legal,!Draft"
        )
        @QueryParam("tag") String tag,
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
            value = "Filter by the task's `createdAt` timestamp. "
                  + "Value grammar:\n"
                  + "- Keyword: `past`, `yesterday`, `today`, `tomorrow`, `upcoming`, `last7d`, `next7d`, `lastWeek`, `thisWeek`, `nextWeek`\n"
                  + "- Value op: `{ge|gt|le|lt|eq|ne}:<timestamp>` or `{between|notBetween}:<timestamp>,<timestamp>`\n"
                  + "- `<timestamp>` is ISO 8601 `YYYY-MM-DDTHH:MM:SSZ` (UTC)\n"
                  + "- Day boundaries resolve in the caller's timezone; week start follows the caller's locale\n"
                  + "- `past` on this field is `< now()`\n"
                  + "- Tokens and keywords are case-insensitive\n\n"
                  + "Examples: `today`, `last7d`, `ge:2026-04-01T00:00:00Z`, "
                  + "`between:2026-04-01T00:00:00Z,2026-04-30T23:59:59Z`.",
            example = "ge:2026-04-01T00:00:00Z"
        )
        @QueryParam("created") String created,
        @ApiParam(
            value = "Filter by the task's `editedAt` timestamp. Same grammar "
                  + "as `created`. Note: `editedAt` is `NULL` for tasks that "
                  + "have never been edited after creation; such tasks are "
                  + "excluded by every op (use `created` to filter by creation "
                  + "time).\n\n"
                  + "Examples: `today`, `last7d`, `ge:2026-04-01T00:00:00Z`.",
            example = "last7d"
        )
        @QueryParam("edited") String edited,
        @ApiParam(
            value = "Filter by the task's `archivedAt` timestamp. Same grammar "
                  + "as `created`, plus null ops since the field is nullable:\n"
                  + "- `isNull` — not archived\n"
                  + "- `isNotNull` — archived (any time)\n\n"
                  + "Examples: `isNotNull`, `today`, `ge:2026-04-01T00:00:00Z`.",
            example = "isNotNull"
        )
        @QueryParam("archived") String archived,
        @ApiParam(
            value = "Filter by the task's `reshowAt` timestamp (scheduled "
                  + "un-archive). Same grammar as `archived` (supports "
                  + "`isNull` / `isNotNull`).\n\n"
                  + "Examples: `isNotNull`, `today`, `between:2026-04-01T00:00:00Z,2026-04-30T23:59:59Z`.",
            example = "isNotNull"
        )
        @QueryParam("unarchived") String unarchived,
        @ApiParam(
            value = "Filter by the task's `toggledAt` timestamp (when its "
                  + "completion status was last changed). Same grammar as "
                  + "`archived` (supports `isNull` / `isNotNull`).\n\n"
                  + "Examples: `today`, `isNotNull`, `ge:2026-04-01T00:00:00Z`.",
            example = "today"
        )
        @QueryParam("toggled") String toggled,
        @ApiParam(
            value = "Filter by the task's `start` date/time. Value grammar:\n"
                  + "- Keyword: `past`, `yesterday`, `today`, `tomorrow`, `upcoming`, `last7d`, `next7d`, `lastWeek`, `thisWeek`, `nextWeek`\n"
                  + "- Value op: `{ge|gt|le|lt|eq|ne}:<value>` or `{between|notBetween}:<value>,<value>`\n"
                  + "- Null op: `isNull`, `isNotNull`\n"
                  + "- `<value>` is either a date (`YYYY-MM-DD`) or a full ISO 8601 timestamp (`YYYY-MM-DDTHH:MM:SSZ`)\n"
                  + "- A date operand expands to a whole-day window in the caller's timezone (`ge:2026-04-01` means \"on or after Apr 1 in the caller's calendar\"); `gt:D`/`le:D` use end-of-day, `lt:D`/`ge:D` use start-of-day\n"
                  + "- `past` on `start`/`due` is `< today 00:00` in the caller's timezone (not `< now()`)\n"
                  + "- `upcoming` on `start`/`due` is the window `[tomorrow, today + 7 days)`\n\n"
                  + "Examples: `today`, `upcoming`, `ge:2026-04-01`, `between:2026-04-01,2026-04-30`, `isNull`.",
            example = "ge:2026-04-01"
        )
        @QueryParam("start") String start,
        @ApiParam(
            value = "Filter by the task's `due` date/time. Same grammar and "
                  + "semantics as `start` (accepts date-only operands, "
                  + "`isNull`/`isNotNull`, and the same keyword/value ops).\n\n"
                  + "Examples: `today`, `upcoming`, `le:2026-04-30`, `isNotNull`.",
            example = "today"
        )
        @QueryParam("due") String due,
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
            value = "Filter by assignee. Each value is a user OID, user ID, "
                  + "or email.\n\n"
                  + "Combine values with `,` (AND), `|` (OR), and `!` (NOT). "
                  + "AND binds tighter than OR.\n\n"
                  + "Examples:\n"
                  + "- `alice` — a single user (by ID)\n"
                  + "- `alice@example.com` — by email\n"
                  + "- `alice,bob` — assigned to both\n"
                  + "- `alice|bob` — assigned to either\n"
                  + "- `alice,!bob` — assigned to alice but not bob\n"
                  + "- `alice,bob|carol` — `(alice AND bob) OR carol`",
            example = "alice,!bob"
        )
        @QueryParam("assignee") String assignee,
        @ApiParam(
            value = "Filter by assignor (the user who set the assignment). "
                  + "Each value is a user OID, user ID, or email. Combine "
                  + "with `,` (AND), `|` (OR), `!` (NOT); AND binds tighter "
                  + "than OR.\n\n"
                  + "Examples: `alice`, `alice,!bob`, `alice|bob`.",
            example = "alice"
        )
        @QueryParam("assignor") String assignor,
        @ApiParam(
            value = "Filter by follower. Each value is a user OID, user ID, "
                  + "or email. Combine with `,` (AND), `|` (OR), `!` (NOT); "
                  + "AND binds tighter than OR.\n\n"
                  + "Examples: `alice`, `alice,!bob`, `alice|bob`.",
            example = "alice|bob"
        )
        @QueryParam("follower") String follower,
        @ApiParam(
            value = "Filter by tag. Each value is a tag OID or a tag name "
                  + "(names are resolved within the search's "
                  + "organization).\n\n"
                  + "Combine with `,` (AND), `|` (OR), `!` (NOT); AND binds "
                  + "tighter than OR.\n\n"
                  + "Names containing `,`, `|`, `!`, `\"`, or whitespace "
                  + "must be wrapped in `\"...\"`. Inside quotes, write "
                  + "`\\\"` for a literal `\"` and `\\\\` for a literal "
                  + "`\\`; any other `\\x` is preserved as two literal "
                  + "characters.\n\n"
                  + "Examples:\n"
                  + "- `Legal` — a single tag\n"
                  + "- `Legal,!Draft` — has `Legal` but not `Draft`\n"
                  + "- `\"In Progress\",!Done` — quoted name with space\n"
                  + "- `\"Foo\\\"s Go\"|Other` — quoted with escaped `\"`",
            example = "Legal,!Draft"
        )
        @QueryParam("tag") String tag,
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
            value = "Filter by the task's `createdAt` timestamp. "
                  + "Value grammar:\n"
                  + "- Keyword: `past`, `yesterday`, `today`, `tomorrow`, `upcoming`, `last7d`, `next7d`, `lastWeek`, `thisWeek`, `nextWeek`\n"
                  + "- Value op: `{ge|gt|le|lt|eq|ne}:<timestamp>` or `{between|notBetween}:<timestamp>,<timestamp>`\n"
                  + "- `<timestamp>` is ISO 8601 `YYYY-MM-DDTHH:MM:SSZ` (UTC)\n"
                  + "- Day boundaries resolve in the caller's timezone; week start follows the caller's locale\n"
                  + "- `past` on this field is `< now()`\n"
                  + "- Tokens and keywords are case-insensitive\n\n"
                  + "Examples: `today`, `last7d`, `ge:2026-04-01T00:00:00Z`, "
                  + "`between:2026-04-01T00:00:00Z,2026-04-30T23:59:59Z`.",
            example = "ge:2026-04-01T00:00:00Z"
        )
        @QueryParam("created") String created,
        @ApiParam(
            value = "Filter by the task's `editedAt` timestamp. Same grammar "
                  + "as `created`. Note: `editedAt` is `NULL` for tasks that "
                  + "have never been edited after creation; such tasks are "
                  + "excluded by every op (use `created` to filter by creation "
                  + "time).\n\n"
                  + "Examples: `today`, `last7d`, `ge:2026-04-01T00:00:00Z`.",
            example = "last7d"
        )
        @QueryParam("edited") String edited,
        @ApiParam(
            value = "Filter by the task's `archivedAt` timestamp. Same grammar "
                  + "as `created`, plus null ops since the field is nullable:\n"
                  + "- `isNull` — not archived\n"
                  + "- `isNotNull` — archived (any time)\n\n"
                  + "Examples: `isNotNull`, `today`, `ge:2026-04-01T00:00:00Z`.",
            example = "isNotNull"
        )
        @QueryParam("archived") String archived,
        @ApiParam(
            value = "Filter by the task's `reshowAt` timestamp (scheduled "
                  + "un-archive). Same grammar as `archived` (supports "
                  + "`isNull` / `isNotNull`).\n\n"
                  + "Examples: `isNotNull`, `today`, `between:2026-04-01T00:00:00Z,2026-04-30T23:59:59Z`.",
            example = "isNotNull"
        )
        @QueryParam("unarchived") String unarchived,
        @ApiParam(
            value = "Filter by the task's `toggledAt` timestamp (when its "
                  + "completion status was last changed). Same grammar as "
                  + "`archived` (supports `isNull` / `isNotNull`).\n\n"
                  + "Examples: `today`, `isNotNull`, `ge:2026-04-01T00:00:00Z`.",
            example = "today"
        )
        @QueryParam("toggled") String toggled,
        @ApiParam(
            value = "Filter by the task's `start` date/time. Value grammar:\n"
                  + "- Keyword: `past`, `yesterday`, `today`, `tomorrow`, `upcoming`, `last7d`, `next7d`, `lastWeek`, `thisWeek`, `nextWeek`\n"
                  + "- Value op: `{ge|gt|le|lt|eq|ne}:<value>` or `{between|notBetween}:<value>,<value>`\n"
                  + "- Null op: `isNull`, `isNotNull`\n"
                  + "- `<value>` is either a date (`YYYY-MM-DD`) or a full ISO 8601 timestamp (`YYYY-MM-DDTHH:MM:SSZ`)\n"
                  + "- A date operand expands to a whole-day window in the caller's timezone (`ge:2026-04-01` means \"on or after Apr 1 in the caller's calendar\"); `gt:D`/`le:D` use end-of-day, `lt:D`/`ge:D` use start-of-day\n"
                  + "- `past` on `start`/`due` is `< today 00:00` in the caller's timezone (not `< now()`)\n"
                  + "- `upcoming` on `start`/`due` is the window `[tomorrow, today + 7 days)`\n\n"
                  + "Examples: `today`, `upcoming`, `ge:2026-04-01`, `between:2026-04-01,2026-04-30`, `isNull`.",
            example = "ge:2026-04-01"
        )
        @QueryParam("start") String start,
        @ApiParam(
            value = "Filter by the task's `due` date/time. Same grammar and "
                  + "semantics as `start` (accepts date-only operands, "
                  + "`isNull`/`isNotNull`, and the same keyword/value ops).\n\n"
                  + "Examples: `today`, `upcoming`, `le:2026-04-30`, `isNotNull`.",
            example = "today"
        )
        @QueryParam("due") String due,
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
            value = "Filter by assignee. Each value is a user OID, user ID, "
                  + "or email.\n\n"
                  + "Combine values with `,` (AND), `|` (OR), and `!` (NOT). "
                  + "AND binds tighter than OR.\n\n"
                  + "Examples:\n"
                  + "- `alice` — a single user (by ID)\n"
                  + "- `alice@example.com` — by email\n"
                  + "- `alice,bob` — assigned to both\n"
                  + "- `alice|bob` — assigned to either\n"
                  + "- `alice,!bob` — assigned to alice but not bob\n"
                  + "- `alice,bob|carol` — `(alice AND bob) OR carol`",
            example = "alice,!bob"
        )
        @QueryParam("assignee") String assignee,
        @ApiParam(
            value = "Filter by assignor (the user who set the assignment). "
                  + "Each value is a user OID, user ID, or email. Combine "
                  + "with `,` (AND), `|` (OR), `!` (NOT); AND binds tighter "
                  + "than OR.\n\n"
                  + "Examples: `alice`, `alice,!bob`, `alice|bob`.",
            example = "alice"
        )
        @QueryParam("assignor") String assignor,
        @ApiParam(
            value = "Filter by follower. Each value is a user OID, user ID, "
                  + "or email. Combine with `,` (AND), `|` (OR), `!` (NOT); "
                  + "AND binds tighter than OR.\n\n"
                  + "Examples: `alice`, `alice,!bob`, `alice|bob`.",
            example = "alice|bob"
        )
        @QueryParam("follower") String follower,
        @ApiParam(
            value = "Filter by tag. Each value is a tag OID or a tag name "
                  + "(names are resolved within the search's "
                  + "organization).\n\n"
                  + "Combine with `,` (AND), `|` (OR), `!` (NOT); AND binds "
                  + "tighter than OR.\n\n"
                  + "Names containing `,`, `|`, `!`, `\"`, or whitespace "
                  + "must be wrapped in `\"...\"`. Inside quotes, write "
                  + "`\\\"` for a literal `\"` and `\\\\` for a literal "
                  + "`\\`; any other `\\x` is preserved as two literal "
                  + "characters.\n\n"
                  + "Examples:\n"
                  + "- `Legal` — a single tag\n"
                  + "- `Legal,!Draft` — has `Legal` but not `Draft`\n"
                  + "- `\"In Progress\",!Done` — quoted name with space\n"
                  + "- `\"Foo\\\"s Go\"|Other` — quoted with escaped `\"`",
            example = "Legal,!Draft"
        )
        @QueryParam("tag") String tag,
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
            value = "Filter by the task's `createdAt` timestamp. "
                  + "Value grammar:\n"
                  + "- Keyword: `past`, `yesterday`, `today`, `tomorrow`, `upcoming`, `last7d`, `next7d`, `lastWeek`, `thisWeek`, `nextWeek`\n"
                  + "- Value op: `{ge|gt|le|lt|eq|ne}:<timestamp>` or `{between|notBetween}:<timestamp>,<timestamp>`\n"
                  + "- `<timestamp>` is ISO 8601 `YYYY-MM-DDTHH:MM:SSZ` (UTC)\n"
                  + "- Day boundaries resolve in the caller's timezone; week start follows the caller's locale\n"
                  + "- `past` on this field is `< now()`\n"
                  + "- Tokens and keywords are case-insensitive\n\n"
                  + "Examples: `today`, `last7d`, `ge:2026-04-01T00:00:00Z`, "
                  + "`between:2026-04-01T00:00:00Z,2026-04-30T23:59:59Z`.",
            example = "ge:2026-04-01T00:00:00Z"
        )
        @QueryParam("created") String created,
        @ApiParam(
            value = "Filter by the task's `editedAt` timestamp. Same grammar "
                  + "as `created`. Note: `editedAt` is `NULL` for tasks that "
                  + "have never been edited after creation; such tasks are "
                  + "excluded by every op (use `created` to filter by creation "
                  + "time).\n\n"
                  + "Examples: `today`, `last7d`, `ge:2026-04-01T00:00:00Z`.",
            example = "last7d"
        )
        @QueryParam("edited") String edited,
        @ApiParam(
            value = "Filter by the task's `archivedAt` timestamp. Same grammar "
                  + "as `created`, plus null ops since the field is nullable:\n"
                  + "- `isNull` — not archived\n"
                  + "- `isNotNull` — archived (any time)\n\n"
                  + "Examples: `isNotNull`, `today`, `ge:2026-04-01T00:00:00Z`.",
            example = "isNotNull"
        )
        @QueryParam("archived") String archived,
        @ApiParam(
            value = "Filter by the task's `reshowAt` timestamp (scheduled "
                  + "un-archive). Same grammar as `archived` (supports "
                  + "`isNull` / `isNotNull`).\n\n"
                  + "Examples: `isNotNull`, `today`, `between:2026-04-01T00:00:00Z,2026-04-30T23:59:59Z`.",
            example = "isNotNull"
        )
        @QueryParam("unarchived") String unarchived,
        @ApiParam(
            value = "Filter by the task's `toggledAt` timestamp (when its "
                  + "completion status was last changed). Same grammar as "
                  + "`archived` (supports `isNull` / `isNotNull`).\n\n"
                  + "Examples: `today`, `isNotNull`, `ge:2026-04-01T00:00:00Z`.",
            example = "today"
        )
        @QueryParam("toggled") String toggled,
        @ApiParam(
            value = "Filter by the task's `start` date/time. Value grammar:\n"
                  + "- Keyword: `past`, `yesterday`, `today`, `tomorrow`, `upcoming`, `last7d`, `next7d`, `lastWeek`, `thisWeek`, `nextWeek`\n"
                  + "- Value op: `{ge|gt|le|lt|eq|ne}:<value>` or `{between|notBetween}:<value>,<value>`\n"
                  + "- Null op: `isNull`, `isNotNull`\n"
                  + "- `<value>` is either a date (`YYYY-MM-DD`) or a full ISO 8601 timestamp (`YYYY-MM-DDTHH:MM:SSZ`)\n"
                  + "- A date operand expands to a whole-day window in the caller's timezone (`ge:2026-04-01` means \"on or after Apr 1 in the caller's calendar\"); `gt:D`/`le:D` use end-of-day, `lt:D`/`ge:D` use start-of-day\n"
                  + "- `past` on `start`/`due` is `< today 00:00` in the caller's timezone (not `< now()`)\n"
                  + "- `upcoming` on `start`/`due` is the window `[tomorrow, today + 7 days)`\n\n"
                  + "Examples: `today`, `upcoming`, `ge:2026-04-01`, `between:2026-04-01,2026-04-30`, `isNull`.",
            example = "ge:2026-04-01"
        )
        @QueryParam("start") String start,
        @ApiParam(
            value = "Filter by the task's `due` date/time. Same grammar and "
                  + "semantics as `start` (accepts date-only operands, "
                  + "`isNull`/`isNotNull`, and the same keyword/value ops).\n\n"
                  + "Examples: `today`, `upcoming`, `le:2026-04-30`, `isNotNull`.",
            example = "today"
        )
        @QueryParam("due") String due,
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
            value = "Filter by assignee. Each value is a user OID, user ID, "
                  + "or email.\n\n"
                  + "Combine values with `,` (AND), `|` (OR), and `!` (NOT). "
                  + "AND binds tighter than OR.\n\n"
                  + "Examples:\n"
                  + "- `alice` — a single user (by ID)\n"
                  + "- `alice@example.com` — by email\n"
                  + "- `alice,bob` — assigned to both\n"
                  + "- `alice|bob` — assigned to either\n"
                  + "- `alice,!bob` — assigned to alice but not bob\n"
                  + "- `alice,bob|carol` — `(alice AND bob) OR carol`",
            example = "alice,!bob"
        )
        @QueryParam("assignee") String assignee,
        @ApiParam(
            value = "Filter by assignor (the user who set the assignment). "
                  + "Each value is a user OID, user ID, or email. Combine "
                  + "with `,` (AND), `|` (OR), `!` (NOT); AND binds tighter "
                  + "than OR.\n\n"
                  + "Examples: `alice`, `alice,!bob`, `alice|bob`.",
            example = "alice"
        )
        @QueryParam("assignor") String assignor,
        @ApiParam(
            value = "Filter by follower. Each value is a user OID, user ID, "
                  + "or email. Combine with `,` (AND), `|` (OR), `!` (NOT); "
                  + "AND binds tighter than OR.\n\n"
                  + "Examples: `alice`, `alice,!bob`, `alice|bob`.",
            example = "alice|bob"
        )
        @QueryParam("follower") String follower,
        @ApiParam(
            value = "Filter by tag. Each value is a tag OID or a tag name "
                  + "(names are resolved within the search's "
                  + "organization).\n\n"
                  + "Combine with `,` (AND), `|` (OR), `!` (NOT); AND binds "
                  + "tighter than OR.\n\n"
                  + "Names containing `,`, `|`, `!`, `\"`, or whitespace "
                  + "must be wrapped in `\"...\"`. Inside quotes, write "
                  + "`\\\"` for a literal `\"` and `\\\\` for a literal "
                  + "`\\`; any other `\\x` is preserved as two literal "
                  + "characters.\n\n"
                  + "Examples:\n"
                  + "- `Legal` — a single tag\n"
                  + "- `Legal,!Draft` — has `Legal` but not `Draft`\n"
                  + "- `\"In Progress\",!Done` — quoted name with space\n"
                  + "- `\"Foo\\\"s Go\"|Other` — quoted with escaped `\"`",
            example = "Legal,!Draft"
        )
        @QueryParam("tag") String tag,
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
            value = "Filter by the task's `createdAt` timestamp. "
                  + "Value grammar:\n"
                  + "- Keyword: `past`, `yesterday`, `today`, `tomorrow`, `upcoming`, `last7d`, `next7d`, `lastWeek`, `thisWeek`, `nextWeek`\n"
                  + "- Value op: `{ge|gt|le|lt|eq|ne}:<timestamp>` or `{between|notBetween}:<timestamp>,<timestamp>`\n"
                  + "- `<timestamp>` is ISO 8601 `YYYY-MM-DDTHH:MM:SSZ` (UTC)\n"
                  + "- Day boundaries resolve in the caller's timezone; week start follows the caller's locale\n"
                  + "- `past` on this field is `< now()`\n"
                  + "- Tokens and keywords are case-insensitive\n\n"
                  + "Examples: `today`, `last7d`, `ge:2026-04-01T00:00:00Z`, "
                  + "`between:2026-04-01T00:00:00Z,2026-04-30T23:59:59Z`.",
            example = "ge:2026-04-01T00:00:00Z"
        )
        @QueryParam("created") String created,
        @ApiParam(
            value = "Filter by the task's `editedAt` timestamp. Same grammar "
                  + "as `created`. Note: `editedAt` is `NULL` for tasks that "
                  + "have never been edited after creation; such tasks are "
                  + "excluded by every op (use `created` to filter by creation "
                  + "time).\n\n"
                  + "Examples: `today`, `last7d`, `ge:2026-04-01T00:00:00Z`.",
            example = "last7d"
        )
        @QueryParam("edited") String edited,
        @ApiParam(
            value = "Filter by the task's `archivedAt` timestamp. Same grammar "
                  + "as `created`, plus null ops since the field is nullable:\n"
                  + "- `isNull` — not archived\n"
                  + "- `isNotNull` — archived (any time)\n\n"
                  + "Examples: `isNotNull`, `today`, `ge:2026-04-01T00:00:00Z`.",
            example = "isNotNull"
        )
        @QueryParam("archived") String archived,
        @ApiParam(
            value = "Filter by the task's `reshowAt` timestamp (scheduled "
                  + "un-archive). Same grammar as `archived` (supports "
                  + "`isNull` / `isNotNull`).\n\n"
                  + "Examples: `isNotNull`, `today`, `between:2026-04-01T00:00:00Z,2026-04-30T23:59:59Z`.",
            example = "isNotNull"
        )
        @QueryParam("unarchived") String unarchived,
        @ApiParam(
            value = "Filter by the task's `toggledAt` timestamp (when its "
                  + "completion status was last changed). Same grammar as "
                  + "`archived` (supports `isNull` / `isNotNull`).\n\n"
                  + "Examples: `today`, `isNotNull`, `ge:2026-04-01T00:00:00Z`.",
            example = "today"
        )
        @QueryParam("toggled") String toggled,
        @ApiParam(
            value = "Filter by the task's `start` date/time. Value grammar:\n"
                  + "- Keyword: `past`, `yesterday`, `today`, `tomorrow`, `upcoming`, `last7d`, `next7d`, `lastWeek`, `thisWeek`, `nextWeek`\n"
                  + "- Value op: `{ge|gt|le|lt|eq|ne}:<value>` or `{between|notBetween}:<value>,<value>`\n"
                  + "- Null op: `isNull`, `isNotNull`\n"
                  + "- `<value>` is either a date (`YYYY-MM-DD`) or a full ISO 8601 timestamp (`YYYY-MM-DDTHH:MM:SSZ`)\n"
                  + "- A date operand expands to a whole-day window in the caller's timezone (`ge:2026-04-01` means \"on or after Apr 1 in the caller's calendar\"); `gt:D`/`le:D` use end-of-day, `lt:D`/`ge:D` use start-of-day\n"
                  + "- `past` on `start`/`due` is `< today 00:00` in the caller's timezone (not `< now()`)\n"
                  + "- `upcoming` on `start`/`due` is the window `[tomorrow, today + 7 days)`\n\n"
                  + "Examples: `today`, `upcoming`, `ge:2026-04-01`, `between:2026-04-01,2026-04-30`, `isNull`.",
            example = "ge:2026-04-01"
        )
        @QueryParam("start") String start,
        @ApiParam(
            value = "Filter by the task's `due` date/time. Same grammar and "
                  + "semantics as `start` (accepts date-only operands, "
                  + "`isNull`/`isNotNull`, and the same keyword/value ops).\n\n"
                  + "Examples: `today`, `upcoming`, `le:2026-04-30`, `isNotNull`.",
            example = "today"
        )
        @QueryParam("due") String due,
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
            value = "Filter by assignee. Each value is a user OID, user ID, "
                  + "or email.\n\n"
                  + "Combine values with `,` (AND), `|` (OR), and `!` (NOT). "
                  + "AND binds tighter than OR.\n\n"
                  + "Examples:\n"
                  + "- `alice` — a single user (by ID)\n"
                  + "- `alice@example.com` — by email\n"
                  + "- `alice,bob` — assigned to both\n"
                  + "- `alice|bob` — assigned to either\n"
                  + "- `alice,!bob` — assigned to alice but not bob\n"
                  + "- `alice,bob|carol` — `(alice AND bob) OR carol`",
            example = "alice,!bob"
        )
        @QueryParam("assignee") String assignee,
        @ApiParam(
            value = "Filter by assignor (the user who set the assignment). "
                  + "Each value is a user OID, user ID, or email. Combine "
                  + "with `,` (AND), `|` (OR), `!` (NOT); AND binds tighter "
                  + "than OR.\n\n"
                  + "Examples: `alice`, `alice,!bob`, `alice|bob`.",
            example = "alice"
        )
        @QueryParam("assignor") String assignor,
        @ApiParam(
            value = "Filter by follower. Each value is a user OID, user ID, "
                  + "or email. Combine with `,` (AND), `|` (OR), `!` (NOT); "
                  + "AND binds tighter than OR.\n\n"
                  + "Examples: `alice`, `alice,!bob`, `alice|bob`.",
            example = "alice|bob"
        )
        @QueryParam("follower") String follower,
        @ApiParam(
            value = "Filter by tag. Each value is a tag OID or a tag name "
                  + "(names are resolved within the search's "
                  + "organization).\n\n"
                  + "Combine with `,` (AND), `|` (OR), `!` (NOT); AND binds "
                  + "tighter than OR.\n\n"
                  + "Names containing `,`, `|`, `!`, `\"`, or whitespace "
                  + "must be wrapped in `\"...\"`. Inside quotes, write "
                  + "`\\\"` for a literal `\"` and `\\\\` for a literal "
                  + "`\\`; any other `\\x` is preserved as two literal "
                  + "characters.\n\n"
                  + "Examples:\n"
                  + "- `Legal` — a single tag\n"
                  + "- `Legal,!Draft` — has `Legal` but not `Draft`\n"
                  + "- `\"In Progress\",!Done` — quoted name with space\n"
                  + "- `\"Foo\\\"s Go\"|Other` — quoted with escaped `\"`",
            example = "Legal,!Draft"
        )
        @QueryParam("tag") String tag,
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
            value = "Filter by the task's `createdAt` timestamp. "
                  + "Value grammar:\n"
                  + "- Keyword: `past`, `yesterday`, `today`, `tomorrow`, `upcoming`, `last7d`, `next7d`, `lastWeek`, `thisWeek`, `nextWeek`\n"
                  + "- Value op: `{ge|gt|le|lt|eq|ne}:<timestamp>` or `{between|notBetween}:<timestamp>,<timestamp>`\n"
                  + "- `<timestamp>` is ISO 8601 `YYYY-MM-DDTHH:MM:SSZ` (UTC)\n"
                  + "- Day boundaries resolve in the caller's timezone; week start follows the caller's locale\n"
                  + "- `past` on this field is `< now()`\n"
                  + "- Tokens and keywords are case-insensitive\n\n"
                  + "Examples: `today`, `last7d`, `ge:2026-04-01T00:00:00Z`, "
                  + "`between:2026-04-01T00:00:00Z,2026-04-30T23:59:59Z`.",
            example = "ge:2026-04-01T00:00:00Z"
        )
        @QueryParam("created") String created,
        @ApiParam(
            value = "Filter by the task's `editedAt` timestamp. Same grammar "
                  + "as `created`. Note: `editedAt` is `NULL` for tasks that "
                  + "have never been edited after creation; such tasks are "
                  + "excluded by every op (use `created` to filter by creation "
                  + "time).\n\n"
                  + "Examples: `today`, `last7d`, `ge:2026-04-01T00:00:00Z`.",
            example = "last7d"
        )
        @QueryParam("edited") String edited,
        @ApiParam(
            value = "Filter by the task's `archivedAt` timestamp. Same grammar "
                  + "as `created`, plus null ops since the field is nullable:\n"
                  + "- `isNull` — not archived\n"
                  + "- `isNotNull` — archived (any time)\n\n"
                  + "Examples: `isNotNull`, `today`, `ge:2026-04-01T00:00:00Z`.",
            example = "isNotNull"
        )
        @QueryParam("archived") String archived,
        @ApiParam(
            value = "Filter by the task's `reshowAt` timestamp (scheduled "
                  + "un-archive). Same grammar as `archived` (supports "
                  + "`isNull` / `isNotNull`).\n\n"
                  + "Examples: `isNotNull`, `today`, `between:2026-04-01T00:00:00Z,2026-04-30T23:59:59Z`.",
            example = "isNotNull"
        )
        @QueryParam("unarchived") String unarchived,
        @ApiParam(
            value = "Filter by the task's `toggledAt` timestamp (when its "
                  + "completion status was last changed). Same grammar as "
                  + "`archived` (supports `isNull` / `isNotNull`).\n\n"
                  + "Examples: `today`, `isNotNull`, `ge:2026-04-01T00:00:00Z`.",
            example = "today"
        )
        @QueryParam("toggled") String toggled,
        @ApiParam(
            value = "Filter by the task's `start` date/time. Value grammar:\n"
                  + "- Keyword: `past`, `yesterday`, `today`, `tomorrow`, `upcoming`, `last7d`, `next7d`, `lastWeek`, `thisWeek`, `nextWeek`\n"
                  + "- Value op: `{ge|gt|le|lt|eq|ne}:<value>` or `{between|notBetween}:<value>,<value>`\n"
                  + "- Null op: `isNull`, `isNotNull`\n"
                  + "- `<value>` is either a date (`YYYY-MM-DD`) or a full ISO 8601 timestamp (`YYYY-MM-DDTHH:MM:SSZ`)\n"
                  + "- A date operand expands to a whole-day window in the caller's timezone (`ge:2026-04-01` means \"on or after Apr 1 in the caller's calendar\"); `gt:D`/`le:D` use end-of-day, `lt:D`/`ge:D` use start-of-day\n"
                  + "- `past` on `start`/`due` is `< today 00:00` in the caller's timezone (not `< now()`)\n"
                  + "- `upcoming` on `start`/`due` is the window `[tomorrow, today + 7 days)`\n\n"
                  + "Examples: `today`, `upcoming`, `ge:2026-04-01`, `between:2026-04-01,2026-04-30`, `isNull`.",
            example = "ge:2026-04-01"
        )
        @QueryParam("start") String start,
        @ApiParam(
            value = "Filter by the task's `due` date/time. Same grammar and "
                  + "semantics as `start` (accepts date-only operands, "
                  + "`isNull`/`isNotNull`, and the same keyword/value ops).\n\n"
                  + "Examples: `today`, `upcoming`, `le:2026-04-30`, `isNotNull`.",
            example = "today"
        )
        @QueryParam("due") String due,
        @ApiParam(
            value = "Maximum number of tasks to return. Default: 30. "
                  + "Use `no` to return all matches.\n\n"
                  + "Note: On free plans, this cannot exceed 30 or `no` (unlimited).",
            example = "no"
        )
        @QueryParam("limit") String limit
    ) { return null; }
}
