package io.quire.api.resource;

import io.quire.api.model.reminder.Reminder;
import io.quire.api.model.reminder.CreateReminderBody;
import io.quire.api.model.reminder.UpdateReminderBody;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/reminder")
@Api(
    value = "reminder",
    description = "A reminder notifies the people it names, either before a "
                + "task's start or due date or at a time of its own."
)
@Produces({"application/json"})
public class ReminderResource {

    private static final String OWNER_TYPE =
        "What the reminder belongs to: `project`, `organization`, `folder`, "
      + "`smart-folder`, or `task`.\n\n"
      + "**Always required**; it doesn't default to `project`.\n\n"
      + "With `task`, the reminder is attached to that task and belongs to "
      + "the task's project. A reminder cannot be moved to another task "
      + "afterwards, and the task is chosen here rather than in the body — "
      + "there is no `task` field to send.";

    private static final String OWNER_OID =
        "OID of what the reminder belongs to — a project, organization, "
      + "folder, smart folder, or task, per `ownerType`.\n\n"
      + "Pass `-` with `ownerType` `project` for My Tasks, i.e. a personal "
      + "reminder, visible to nobody else.";

    private static final String LIMIT =
        "(Optional) Page size, 1..1000. Omit it to return every reminder in "
      + "one bare array, which is the default; pass `no` to ask for that "
      + "explicitly.\n\n"
      + "With `?limit=N`, the **last** item of the response carries an extra "
      + "`cursor` field when more reminders remain; pass it back as "
      + "`?cursor=` for the next page. The last page carries none, which is "
      + "how you know you are done. Nothing carries one in an unpaginated "
      + "response.";

    private static final String CURSOR =
        "(Optional) Resume after this item. Pass the `cursor` of the last item "
      + "of the previous page, verbatim.\n\n"
      + "Reminders are ordered by `oid`. That order is stable, but it is "
      + "**not** chronological — do not read it as oldest-first, and sort by "
      + "the field you care about after fetching.";

    private static final String TIMING =
        "(Optional) Lists only the reminders that fire:\n\n"
      + "- `standalone`: at their own `when` — those with no task, or on a "
      + "task with neither a start nor a due date.\n"
      + "- `anchored`: from their task's due date, or its start date if it "
      + "has no due date. Their `when` is omitted.\n\n"
      + "Omit it to list both. `?timing=standalone&status=active` lists the "
      + "reminders still to fire at a time of their own, e.g. to sync them "
      + "to a calendar.";

    private static final String STATUS =
        "(Optional) Which reminders to list:\n\n"
      + "- `active`: only those with a notification still to come — not one "
      + "whose notifications are all past, nor one on a completed or "
      + "archived task.\n"
      + "- `all` (default): every reminder.\n\n"
      + "A page still holds `limit` reminders while that many remain.";

    private static final String RETURN_MODE =
        "(Optional) Response shape:\n"
      + "- `full` (default): the full reminder record.\n"
      + "- `compact`: the OID only (`{\"oid\": \"…\"}`). Saves payload, "
      + "tokens, and a server-side reload + render, so prefer it when you "
      + "only need the OID to chain follow-up calls.\n\n"
      + "Any other value returns `400 Bad Request`.";

    // The create endpoints share these.
    private static final String FIRES_FROM_TASK =
        "What a reminder on a task fires from depends on the task: one with "
      + "a start or due date supplies the time — its due date, or its start "
      + "date if it has no due date — so send `leads` alone. Sending `when` "
      + "or `recurrence` then is rejected with `400`, since they would be "
      + "ignored. On a task with neither date, `when` is required.";

    private static final String WHEN_REQUIRED =
        "Without a task, `when` is required: the reminder has no other time "
      + "to fire from.";

    private static final String TASK_EXAMPLE =
        "`{\"leads\": [{\"days\": 1, \"at\": \"09:00\"}]}` — on a task "
      + "with a due date, notifies you at 9:00 the day before it is due";

    private static final String TIMED_EXAMPLE =
        "`{\"when\": \"2027-01-15T09:00:00.000Z\", \"name\": "
      + "\"Pay the **bill**\"}` — notifies you at that time";

    // The list endpoints share these.
    private static final String PROJECT_LIST =
        "A project's list includes the reminders on its tasks, not only the "
      + "task-less ones; narrow it with `timing` and `status`. Reminders on "
      + "a task that is in the trash are left out, since there is nothing "
      + "to act on while it is there; they come back if the task is "
      + "restored, and stay reachable by OID meanwhile.";

    // The `/id/task/` endpoints share this.
    private static final String TASK_ID =
        "ID of the task, an integer unique within its project.";

    private static final String GUEST_CREATES_NONE =
        "A guest can't create one: a reminder takes write access, which a "
      + "guest lacks, so `403`.";

    // The update and delete endpoints share this.
    private static final String WRITE_403 =
        "Forbidden — the reminder does not name the caller among those who "
      + "can see it, or the caller lacks write access (a guest named in it "
      + "can read but not change or delete it).";

    private static final String WHO_SEES =
        "A guest sees only the reminders naming them. A visitor of a public "
      + "project who is not a member sees none, as in the project view.";

    @POST
    @Path("/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "Create a reminder by owner OID.",
        notes = "Creates a reminder on the given task, or under the given "
              + "project, organization, folder or smart folder.\n\n"
              + FIRES_FROM_TASK + " " + WHEN_REQUIRED + "\n\n"
              + GUEST_CREATES_NONE + "\n\n"
              + "Examples:\n\n"
              + "- `POST /reminder/task/{taskOid}` with " + TASK_EXAMPLE + ".\n"
              + "- `POST /reminder/project/-` with " + TIMED_EXAMPLE
              + ", as a personal reminder.",
        response = Reminder.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created reminder record.", response = Reminder.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed, for example a `partner` that isn't an external team of the project, or, on a task, other than the team the task is shared with, or `members` naming a user who can't see the task."),
        @ApiResponse(code = 402, message = "Payment Required — reminders need a Professional plan or above, and Shared reminders (`members` other than yourself, or `partner`) need Premium or above."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to create a reminder here (a guest included: a reminder takes write access)."),
        @ApiResponse(code = 404, message = "Not Found — the owner does not exist, the task is in the trash, or the caller can't see the task (an external team member can't see one that isn't shared with their team).")
    })
    public Response createReminderByOid(
        @ApiParam(value = OWNER_TYPE, required = true, example = "task")
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = OWNER_OID, required = true)
        @PathParam("ownerOid") String ownerOid,
        @ApiParam(value = "Reminder to create", required = true)
        CreateReminderBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @POST
    @Path("/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "Create a reminder by owner ID.",
        notes = "Creates a reminder under the given project, organization, "
              + "folder or smart folder (by ID).\n\n"
              + WHEN_REQUIRED + "\n\n"
              + "> For a reminder on a task, use "
              + "`POST /reminder/id/task/{projectId}/{taskId}` — a task ID is "
              + "unique only within its project, so it needs both.\n\n"
              + GUEST_CREATES_NONE + "\n\n"
              + "Example: `POST /reminder/id/project/my_project` with "
              + TIMED_EXAMPLE + ".",
        response = Reminder.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created reminder record.", response = Reminder.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed, for example a `partner` that isn't an external team of the project."),
        @ApiResponse(code = 402, message = "Payment Required — reminders need a Professional plan or above, and Shared reminders (`members` other than yourself, or `partner`) need Premium or above."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to create a reminder here (a guest included: a reminder takes write access)."),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist.")
    })
    public Response createReminderById(
        @ApiParam(
            value = "What the reminder belongs to: `project`, `organization`, "
                  + "`folder`, or `smart-folder`. Always required.",
            required = true,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(
            value = "ID of what the reminder belongs to. Pass `-` with "
                  + "`ownerType` `project` for My Tasks, i.e. a personal "
                  + "reminder, visible to nobody else.",
            required = true,
            example = "my_project"
        )
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Reminder to create", required = true)
        CreateReminderBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @POST
    @Path("/id/task/{projectId}/{taskId}")
    @ApiOperation(
        value = "Create a reminder on a task, by ID.",
        notes = "Creates a reminder on the given task. The reminder belongs "
              + "to that task's project.\n\n"
              + FIRES_FROM_TASK + "\n\n"
              + GUEST_CREATES_NONE + "\n\n"
              + "Example: `POST /reminder/id/task/my_project/42` with "
              + TASK_EXAMPLE + ".",
        response = Reminder.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created reminder record.", response = Reminder.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed, for example a `partner` other than the team the task is shared with, or `members` naming a user who can't see the task."),
        @ApiResponse(code = 402, message = "Payment Required — reminders need a Professional plan or above, and Shared reminders (`members` other than yourself, or `partner`) need Premium or above."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to create a reminder here (a guest included: a reminder takes write access)."),
        @ApiResponse(code = 404, message = "Not Found — the task does not exist, is in the trash, or the caller can't see the task (an external team member can't see one that isn't shared with their team).")
    })
    public Response createTaskReminderById(
        @ApiParam(value = "ID of the project the task belongs to.", required = true, example = "my_project")
        @PathParam("projectId") String projectId,
        @ApiParam(value = TASK_ID, required = true, example = "42")
        @PathParam("taskId") int taskId,
        @ApiParam(value = "Reminder to create", required = true)
        CreateReminderBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get a reminder by OID.",
        notes = "Returns the reminder with the given OID.\n\n"
              + "> A reminder has no ID of its own, so this is the only way to "
              + "fetch one; the `/id/` forms address its owner, not the "
              + "reminder.",
        response = Reminder.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — reminder record.", response = Reminder.class),
        @ApiResponse(code = 403, message = "Forbidden — the reminder does not name the caller among those who can see it."),
        @ApiResponse(code = 404, message = "Not Found — reminder does not exist.")
    })
    public Response getReminderByOid(
        @ApiParam(value = "OID of the reminder.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @GET
    @Path("/list/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "List reminders by owner OID.",
        notes = "Returns the reminders the caller can see under the given "
              + "owner.\n\n"
              + PROJECT_LIST + " For a single task's, use `ownerType` "
              + "`task`.\n\n"
              + WHO_SEES,
        response = Reminder.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of reminder records (may be empty).", response = Reminder.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request — invalid `limit`, `cursor`, `timing` or `status`."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to read this owner."),
        @ApiResponse(code = 404, message = "Not Found — the owner does not exist, or, for a task, it is in the trash or the caller can't see it (an external team member can't see one that isn't shared with their team).")
    })
    public Response getRemindersByOid(
        @ApiParam(value = OWNER_TYPE, required = true, example = "project")
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = OWNER_OID, required = true)
        @PathParam("ownerOid") String ownerOid,

        @ApiParam(value = LIMIT, example = "100")
        @QueryParam("limit") String limit,
        @ApiParam(value = CURSOR)
        @QueryParam("cursor") String cursor,
        @ApiParam(value = TIMING, allowableValues = "standalone, anchored",
            example = "standalone")
        @QueryParam("timing") String timing,
        @ApiParam(value = STATUS, allowableValues = "active, all",
            example = "active")
        @QueryParam("status") String status
    ) { return null; }

    @GET
    @Path("/list/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "List reminders by owner ID.",
        notes = "Returns the reminders the caller can see under the given "
              + "owner (by ID).\n\n"
              + PROJECT_LIST + "\n\n"
              + "> For a single task's reminders, use "
              + "`GET /reminder/list/id/task/{projectId}/{taskId}`.\n\n"
              + WHO_SEES,
        response = Reminder.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of reminder records (may be empty).", response = Reminder.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request — invalid `limit`, `cursor`, `timing` or `status`."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to read this owner."),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist.")
    })
    public Response getRemindersById(
        @ApiParam(
            value = "What the reminders belong to: `project`, `organization`, "
                  + "`folder`, or `smart-folder`. Always required.",
            required = true,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(
            value = "ID of what the reminders belong to. Pass `-` with "
                  + "`ownerType` `project` for My Tasks.",
            required = true,
            example = "my_project"
        )
        @PathParam("ownerId") String ownerId,

        @ApiParam(value = LIMIT, example = "100")
        @QueryParam("limit") String limit,
        @ApiParam(value = CURSOR)
        @QueryParam("cursor") String cursor,
        @ApiParam(value = TIMING, allowableValues = "standalone, anchored",
            example = "standalone")
        @QueryParam("timing") String timing,
        @ApiParam(value = STATUS, allowableValues = "active, all",
            example = "active")
        @QueryParam("status") String status
    ) { return null; }

    @GET
    @Path("/list/id/task/{projectId}/{taskId}")
    @ApiOperation(
        value = "List a task's reminders, by ID.",
        notes = "Returns the reminders the caller can see on the given task.\n\n"
              + WHO_SEES,
        response = Reminder.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of reminder records (may be empty).", response = Reminder.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request — invalid `limit`, `cursor`, `timing` or `status`."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to read the task's project, such as one not granted to this app."),
        @ApiResponse(code = 404, message = "Not Found — the task does not exist, is in the trash, or the caller can't see the task (an external team member can't see one that isn't shared with their team).")
    })
    public Response getTaskRemindersById(
        @ApiParam(value = "ID of the project the task belongs to.", required = true, example = "my_project")
        @PathParam("projectId") String projectId,
        @ApiParam(value = TASK_ID, required = true, example = "42")
        @PathParam("taskId") int taskId,

        @ApiParam(value = LIMIT, example = "100")
        @QueryParam("limit") String limit,
        @ApiParam(value = CURSOR)
        @QueryParam("cursor") String cursor,
        @ApiParam(value = TIMING, allowableValues = "standalone, anchored",
            example = "standalone")
        @QueryParam("timing") String timing,
        @ApiParam(value = STATUS, allowableValues = "active, all",
            example = "active")
        @QueryParam("status") String status
    ) { return null; }

    @PUT
    @Path("/{oid}")
    @ApiOperation(
        value = "Update a reminder by OID.",
        notes = "Updates the reminder with the given OID.\n\n"
              + "Only `when`, `recurrence`, `leads` and `name` can be changed. "
              + "What the reminder belongs to, the task it is attached to, its "
              + "`members` and its `partner` are all fixed when it is created "
              + "— recreate the reminder to change any of them.",
        response = Reminder.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated reminder record.", response = Reminder.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed, or a field that cannot be changed was sent."),
        @ApiResponse(code = 403, message = WRITE_403),
        @ApiResponse(code = 404, message = "Not Found — reminder does not exist.")
    })
    public Response updateReminderByOid(
        @ApiParam(value = "OID of the reminder to update.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Fields to update", required = true)
        UpdateReminderBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete a reminder by OID.",
        notes = "Deletes the reminder with the given OID. Unlike a task or a "
              + "sublist, a reminder is not moved to the trash and cannot be "
              + "restored.\n\n"
              + "> Note: Returns `204 No Content` regardless of whether the "
              + "reminder exists."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 403, message = WRITE_403)
    })
    public Response deleteReminderByOid(
        @ApiParam(value = "OID of the reminder to delete.", required = true)
        @PathParam("oid") String oid
    ) { return null; }
}
