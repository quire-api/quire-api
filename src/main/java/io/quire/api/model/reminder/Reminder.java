package io.quire.api.model.reminder;

import io.quire.api.model.*;
import io.swagger.annotations.*;

import java.util.List;

public class Reminder extends StampedEntity {

    @ApiModelProperty(
        value = "Object identifier (OID), a UUID-like unique string.",
        example = "iDsPd.QP_qM.hN.Trymukn8b"
    )
    public String getOid() { return null; }

    @ApiModelProperty(
        value = "Task this reminder is attached to, or null if it stands on "
              + "its own. A reminder cannot be moved to another task.\n\n"
              + "Only `oid`, `id`, `name` and `url` are returned here; fetch "
              + "the task itself for its dates.",
        position = 1
    )
    public ReminderTask getTask() { return null; }

    @ApiModelProperty(
        value = "When this reminder fires, if it fires at a time of its own "
              + "(UTC, ISO 8601).\n\n"
              + "Null whenever `task` has a start or due date: the reminder "
              + "then fires from that date instead, and `leads` counts back "
              + "from it. The dates are not part of this response, so to show "
              + "when a reminder will fire, fetch its task and use its due "
              + "date, or its start date if it has no due date.",
        example = "2027-01-15T09:00:00.000Z",
        position = 2
    )
    public String getWhen() { return null; }

    @ApiModelProperty(
        value = "How this reminder repeats, or null if it does not. Always "
              + "null while the reminder is anchored: it then repeats with "
              + "its task's own schedule.",
        position = 3
    )
    public RecurrenceX getRecurrence() { return null; }

    @ApiModelProperty(
        value = "When to notify before the fire time, one notification each. "
              + "`[{\"minutes\": 0}]` means a single notification at the "
              + "fire time, and is what a reminder created without `leads` "
              + "has.\n\n"
              + "Each entry is an object naming exactly one of `minutes` (an "
              + "absolute offset) and `days` (calendar days, so a lead "
              + "spanning a daylight-saving transition keeps its wall-clock "
              + "time), the latter taking an optional `at` clock.\n\n"
              + "Neither may be negative — a lead counts back from the fire "
              + "time, and nothing fires after it — nor exceed 10,000 days "
              + "ahead of it, and there may be at most 30 of them.",
        position = 4
    )
    public List<ReminderLead> getLeads() { return null; }

    @ApiModelProperty(
        value = "Text shown instead of the default message (Markdown supported), "
              + "or null to use the default.",
        example = "Pay the **bill**",
        position = 5
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "Name with Markdown removed.",
        example = "Pay the bill",
        position = 5
    )
    public String getNameText() { return null; }

    @ApiModelProperty(
        value = "Name rendered as an HTML fragment converted from Markdown.",
        example = "Pay the <strong>bill</strong>",
        position = 5
    )
    public String getNameHtml() { return null; }

    @ApiModelProperty(
        value = "External team this reminder is shared with, or null. When "
              + "set, it reaches every member of the project and of that team.",
        position = 15
    )
    public SimpleTaggingEntity getPartner() { return null; }

    @ApiModelProperty(
        value = "Users who can see this reminder, and the only ones it "
              + "notifies.\n\n"
              + "- `null` — every member of the owner.\n"
              + "- `[]` — only the owner's admins.\n"
              + "- a list — only those users.\n\n"
              + "All three are returned explicitly, so the states can be told "
              + "apart. Set it when creating the reminder; it cannot be "
              + "changed afterwards.",
        position = 60
    )
    public List<SimpleIdentity> getMembers() { return null; }

    @ApiModelProperty(
        value = "Project, organization, folder or smart folder this reminder "
              + "belongs to. Fixed when the reminder is created: a reminder on "
              + "a task always belongs to that task's project, and one created "
              + "under `-` belongs to the caller's own inbox.",
        position = 99
    )
    public ReminderOwner getOwner() { return null; }

    @ApiModelProperty(
        value = "URL of the page this reminder is shown on.",
        example = "https://quire.io/w/my_project?reminder=iDsPd.QP_qM.hN.Trymukn8b",
        position = 99
    )
    public String getUrl() { return null; }
}
