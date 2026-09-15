package io.quire.api.model.reminder;

import io.swagger.annotations.ApiModelProperty;

public class ReminderLead {

    @ApiModelProperty(
        value = "How many minutes before the fire time to notify — an "
              + "absolute offset, so 30 stays 30 real minutes wherever the "
              + "clocks move in between.\n\n"
              + "Exactly one of `minutes` and `days` must be given. An hour "
              + "is simply 60 minutes: there is no `hours`, and a lead is "
              + "stored and returned in whichever of the two forms it was "
              + "sent.",
        example = "30",
        position = 1
    )
    public Integer getMinutes() { return null; }

    @ApiModelProperty(
        value = "How many calendar days before the fire time to notify. "
              + "Unlike `minutes`, this counts *days*, not 24-hour spans, so "
              + "a lead spanning a daylight-saving transition still notifies "
              + "at the same wall-clock time.\n\n"
              + "Exactly one of `minutes` and `days` must be given. A week is "
              + "simply 7 days: there is no `weeks`.",
        example = "1",
        position = 2
    )
    public Integer getDays() { return null; }

    @ApiModelProperty(
        value = "(Optional, and only with `days`) The time of day to notify "
              + "at, as `HH:mm` on a 24-hour clock — `\"09:00\"`, "
              + "`\"23:30\"`. A 12-hour clock is rejected with `400`.\n\n"
              + "Omit it to notify at the fire time's own time of day, which "
              + "then follows that time if it later moves.\n\n"
              + "The clock is read in the time zone of the member who created "
              + "the reminder, since one notification serves its whole "
              + "audience and some one zone has to be chosen.",
        example = "09:00",
        position = 3
    )
    public String getAt() { return null; }
}
