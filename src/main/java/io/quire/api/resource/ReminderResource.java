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
      + "**Always required.** The sublist, doc, chat, insight and dashboard "
      + "endpoints let you omit it and assume `project`; this one does not.\n\n"
      + "With `task`, the reminder is attached to that task and belongs to "
      + "the task's project. A reminder cannot be moved to another task "
      + "afterwards, and the task is chosen here rather than in the body — "
      + "there is no `task` field to send.";

    private static final String OWNER_OID =
        "OID of what the reminder belongs to — a project, organization, "
      + "folder, smart folder, or task, per `ownerType`.\n\n"
      + "Pass `-` with `ownerType` `project` for the caller's own inbox, "
      + "i.e. a personal reminder, visible to nobody else.";

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

    private static final String RETURN_MODE =
        "(Optional) Response shape: `full` (default) for the full record, or "
      + "`compact` for identifiers only. See API description for `?return=` "
      + "semantics.";

    @POST
    @Path("/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "Create a reminder by owner OID.",
        notes = "Creates a reminder on the given task, or under the given "
              + "project, organization, folder or smart folder.\n\n"
              + "What the reminder fires from depends on the task: a task with "
              + "a start or due date supplies the time, so send `leads` alone; "
              + "otherwise `when` is required. Sending `when` or `recurrence` "
              + "in the first case is rejected with `400`, since they would be "
              + "ignored.",
        response = Reminder.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created reminder record.", response = Reminder.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 402, message = "Payment Required — reminders need a Professional plan or above, and Shared reminders (`members` other than yourself, or `partner`) need Premium or above."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to create a reminder here."),
        @ApiResponse(code = 404, message = "Not Found — the owner does not exist, or the task is in the trash.")
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
              + "> For a reminder on a task, use "
              + "`POST /reminder/id/task/{projectId}/{taskId}` — a task ID is "
              + "unique only within its project, so it needs both.",
        response = Reminder.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created reminder record.", response = Reminder.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 402, message = "Payment Required — reminders need a Professional plan or above, and Shared reminders (`members` other than yourself, or `partner`) need Premium or above."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to create a reminder here."),
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
                  + "`ownerType` `project` for the caller's own inbox.",
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
              + "to that task's project.",
        response = Reminder.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created reminder record.", response = Reminder.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 402, message = "Payment Required — reminders need a Professional plan or above, and Shared reminders (`members` other than yourself, or `partner`) need Premium or above."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to create a reminder here."),
        @ApiResponse(code = 404, message = "Not Found — the task does not exist, or is in the trash.")
    })
    public Response createTaskReminderById(
        @ApiParam(value = "ID of the project the task belongs to.", required = true, example = "my_project")
        @PathParam("projectId") String projectId,
        @ApiParam(value = "ID of the task.", required = true, example = "1234")
        @PathParam("taskId") String taskId,
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
              + "A project's list includes the reminders on its tasks, not "
              + "only the standalone ones — narrow it with `ownerType` `task` "
              + "to get a single task's. Reminders on a task that is in the "
              + "trash are left out, since there is nothing to act on while it "
              + "is there; they come back if the task is restored, and stay "
              + "reachable by OID meanwhile.",
        response = Reminder.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of reminder records (may be empty).", response = Reminder.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request — invalid `limit` or `cursor`."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to read this owner."),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist.")
    })
    public Response getRemindersByOid(
        @ApiParam(value = OWNER_TYPE, required = true, example = "project")
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = OWNER_OID, required = true)
        @PathParam("ownerOid") String ownerOid,

        @ApiParam(value = LIMIT, example = "100")
        @QueryParam("limit") String limit,
        @ApiParam(value = CURSOR)
        @QueryParam("cursor") String cursor
    ) { return null; }

    @GET
    @Path("/list/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "List reminders by owner ID.",
        notes = "Returns the reminders the caller can see under the given "
              + "owner (by ID).\n\n"
              + "> For a single task's reminders, use "
              + "`GET /reminder/list/id/task/{projectId}/{taskId}`.",
        response = Reminder.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of reminder records (may be empty).", response = Reminder.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request — invalid `limit` or `cursor`."),
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
                  + "`ownerType` `project` for the caller's own inbox.",
            required = true,
            example = "my_project"
        )
        @PathParam("ownerId") String ownerId,

        @ApiParam(value = LIMIT, example = "100")
        @QueryParam("limit") String limit,
        @ApiParam(value = CURSOR)
        @QueryParam("cursor") String cursor
    ) { return null; }

    @GET
    @Path("/list/id/task/{projectId}/{taskId}")
    @ApiOperation(
        value = "List a task's reminders, by ID.",
        notes = "Returns the reminders the caller can see on the given task.",
        response = Reminder.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of reminder records (may be empty).", response = Reminder.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request — invalid `limit` or `cursor`."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to read this task."),
        @ApiResponse(code = 404, message = "Not Found — task does not exist.")
    })
    public Response getTaskRemindersById(
        @ApiParam(value = "ID of the project the task belongs to.", required = true, example = "my_project")
        @PathParam("projectId") String projectId,
        @ApiParam(value = "ID of the task.", required = true, example = "1234")
        @PathParam("taskId") String taskId,

        @ApiParam(value = LIMIT, example = "100")
        @QueryParam("limit") String limit,
        @ApiParam(value = CURSOR)
        @QueryParam("cursor") String cursor
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
        @ApiResponse(code = 403, message = "Forbidden — the reminder does not name the caller among those who can see it."),
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
        @ApiResponse(code = 403, message = "Forbidden — the reminder does not name the caller among those who can see it.")
    })
    public Response deleteReminderByOid(
        @ApiParam(value = "OID of the reminder to delete.", required = true)
        @PathParam("oid") String oid
    ) { return null; }
}
