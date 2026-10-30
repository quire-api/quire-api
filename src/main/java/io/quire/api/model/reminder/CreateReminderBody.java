package io.quire.api.model.reminder;

import io.quire.api.model.Recurrence;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class CreateReminderBody {

    // The create and update bodies share this.
    static final String NAME_FORMAT =
        "Only inline Markdown applies: `**bold**`, `*italic*`, "
      + "`~~strikethrough~~`, `` `code` `` and links; anything else, such as "
      + "a list or a heading, stays literal text. At most 1,280 characters, "
      + "not counting the markup (`400` otherwise).";

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
        value = "(Optional) When to notify before the fire time, one "
              + "notification each. Omit it for a single notification at the "
              + "fire time, which is the default "
              + "(`[{\"minutes\": 0}]`).\n\n"
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
        value = "(Optional) Text to show instead of the default message. "
              + NAME_FORMAT,
        example = "Pay the **bill**",
        position = 5
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "(Optional) OID of the external team to share this reminder "
              + "with; it then reaches every member of the project and of that "
              + "team.\n\n"
              + "Only a project's reminder can carry one, and it cannot be "
              + "combined with a `members` list (`[]` included) — both are "
              + "`400`. Without a task, it "
              + "must be one of the project's external teams (`400` "
              + "otherwise). On a reminder attached to a task, it must be the "
              + "team the task is shared with — the task's `partner`, or the "
              + "nearest parent task's if it has none — or be omitted; any "
              + "other value, and any value for a task not shared with a "
              + "team, is `400`.\n\n"
              + "An external team member shares a reminder with every member "
              + "only through their own team. With `members` null, they must "
              + "set `partner` to one of their teams; on a reminder attached "
              + "to a task, that is the task's team. Otherwise `400` says what "
              + "to send, as it does for a team of the project that isn't "
              + "theirs.\n\n"
              + "Sharing with a team is part of Shared reminders, which needs the "
              + "Premium plan or above (`402` otherwise). Settable only when "
              + "creating the reminder.",
        example = "rcBHBYXZSiyDRrHrWPutatfF",
        position = 15
    )
    public String getPartner() { return null; }

    @ApiModelProperty(
        value = "(Optional) Users who can see this reminder, and the only ones "
              + "it notifies. Each entry is a user ID, OID, or email, or "
              + "`\"me\"` for the authenticated user.\n\n"
              + "- Omit the field — only the authenticated user. This is the "
              + "default, except with `partner`: then every member of the "
              + "project and of that team.\n"
              + "- Pass null — every member of the owner. An external team "
              + "member must also set `partner` to one of their teams.\n"
              + "- Pass `[]` — only the owner's admins.\n"
              + "- Pass a list — only those users.\n\n"
              + "> Unlike a work's `members`, omitting the field does **not** "
              + "mean every member: pass `null` explicitly for that.\n\n"
              + "Omitting it without `partner`, or naming only yourself (such "
              + "as `[\"me\"]`), keeps the reminder yours; anything else "
              + "shares it — the "
              + "Shared reminders feature, which needs the Premium plan or "
              + "above (`402` otherwise).\n\n"
              + "Rejected with `400` when a listed user is not a member of the "
              + "owner, when a non-empty list does not include the "
              + "authenticated user, or when `partner` is also set. On a "
              + "reminder attached to a task, every listed user must also be "
              + "able to see the task (`400` naming those who can't): a "
              + "project member sees every task, but an external team member "
              + "only the tasks shared with their team. A guest "
              + "sees only the reminders naming them, and creates none "
              + "(`403`: a reminder takes write access).\n\n"
              + "Settable only when creating the reminder. It cannot be "
              + "changed afterwards, so recreate the reminder to change who "
              + "can see it. A personal reminder (created under `-`) is always "
              + "its owner's alone, so no one else can be named.",
        example = "[\"me\", \"john\"]",
        position = 60
    )
    public List<String> getMembers() { return null; }
}
