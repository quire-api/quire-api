package io.quire.api.model.reminder;

import io.quire.api.model.Recurrence;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class UpdateReminderBody {

    @ApiModelProperty(
        value = "(Optional) When this reminder fires (UTC, ISO 8601).\n\n"
              + "**Rejected with `400`** on a reminder whose task has a start "
              + "or due date — it fires from the task's schedule, so a `when` "
              + "of its own would be ignored — and also when set to null on "
              + "one that has no other time to fire from.",
        example = "2027-03-20T18:30:00.000Z",
        position = 2
    )
    public String getWhen() { return null; }

    @ApiModelProperty(
        value = "(Optional) How this reminder repeats; null stops it "
              + "repeating.\n\n"
              + "**Rejected with `400`** on a reminder whose task has a start "
              + "or due date, for the same reason as `when`.",
        position = 3
    )
    public Recurrence getRecurrence() { return null; }

    @ApiModelProperty(
        value = "(Optional) When to notify before the fire time, one "
              + "notification each. Replaces the current list.\n\n"
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
        value = "(Optional) Text to show instead of the default message "
              + "(Markdown supported). Pass null to fall back to the default.",
        example = "Pay the **bill** today",
        position = 5
    )
    public String getName() { return null; }
}
