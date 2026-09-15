package io.quire.api.model.reminder;

import io.quire.api.model.Recurrence;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class CreateReminderBody {

    @ApiModelProperty(
        value = "When this reminder fires (UTC, ISO 8601). A plain instant — "
              + "there is no all-day reminder, so a bare date is midnight UTC.\n\n"
              + "**Required** for a reminder that has no task, or whose task "
              + "has neither a start nor a due date.\n\n"
              + "**Rejected with `400`** when the task already has a start or "
              + "due date: the reminder then fires from the task's own "
              + "schedule, and a `when` of its own would be ignored. Send "
              + "`leads` alone in that case.",
        example = "2027-01-15T09:00:00.000Z",
        position = 2
    )
    public String getWhen() { return null; }

    @ApiModelProperty(
        value = "(Optional) How this reminder repeats; `when` is the first "
              + "occurrence.\n\n"
              + "**Rejected with `400`** on a reminder whose task has a start "
              + "or due date — it then repeats with its task's schedule, so a "
              + "rule of its own would be ignored.",
        position = 3
    )
    public Recurrence getRecurrence() { return null; }

    @ApiModelProperty(
        value = "(Optional) Minutes before the fire time to notify, one "
              + "notification each. Omit it for a single notification at the "
              + "fire time, which is the default (`[0]`).\n\n"
              + "Each entry must be 0 or greater — a lead counts back from the "
              + "fire time, and nothing fires after it — and there may be at "
              + "most 30 of them.",
        example = "[0, 60]",
        position = 4
    )
    public List<Integer> getLeads() { return null; }

    @ApiModelProperty(
        value = "(Optional) Text to show instead of the default message "
              + "(Markdown supported).",
        example = "Pay the **bill**",
        position = 5
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "(Optional) OID of the external team to share this reminder "
              + "with; it then reaches every member of the project and of that "
              + "team.\n\n"
              + "Only a project's reminder can carry one, and it cannot be "
              + "combined with `members` — both are `400`. On a reminder "
              + "attached to a task, it must be that task's own team. Settable "
              + "only when creating the reminder.",
        example = "rcBHBYXZSiyDRrHrWPutatfF",
        position = 15
    )
    public String getPartner() { return null; }

    @ApiModelProperty(
        value = "(Optional) Users who can see this reminder, and the only ones "
              + "it notifies. Each entry is a user ID, OID, or email, or "
              + "`\"me\"` for the authenticated user.\n\n"
              + "- Omit the field, or pass null — every member of the owner. "
              + "This is the default.\n"
              + "- Pass `[]` — only the owner's admins.\n"
              + "- Pass a list — only those users.\n\n"
              + "Rejected with `400` when a listed user is not a member of the "
              + "owner, when a non-empty list does not include the "
              + "authenticated user, or when `partner` is also set.\n\n"
              + "Settable only when creating the reminder. It cannot be "
              + "changed afterwards, so recreate the reminder to change who "
              + "can see it. A personal reminder (created under `-`) is always "
              + "its owner's alone, so no one else can be named.",
        example = "[\"me\", \"john\"]",
        position = 60
    )
    public List<String> getMembers() { return null; }
}
