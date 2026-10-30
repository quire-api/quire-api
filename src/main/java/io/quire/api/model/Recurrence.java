package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class Recurrence {

    @ApiModelProperty(
        value = "Recurrence frequency. Besides it:\n\n"
              + "- `weekly` needs `byweekday`.\n"
              + "- `monthly` needs `bydayno`, or `byweekno` with `byweekday`.\n"
              + "- `yearly` needs `bymonth`, plus what `monthly` needs.\n\n"
              + "A property that doesn't apply to `freq` is ignored.\n\n"
              + "Examples: `{\"freq\": \"daily\"}` repeats every day; "
              + "`{\"freq\": \"weekly\", \"byweekday\": [0, 3]}` every "
              + "Monday and Thursday; `{\"freq\": \"monthly\", \"interval\": "
              + "2, \"byweekno\": 2, \"byweekday\": 1}` on the second Tuesday "
              + "of every other month.",
        example = "weekly",
        allowableValues = "daily, weekly, monthly, yearly"
    )
    public String getFreq() { return null; }

    @ApiModelProperty(
        value = "Interval between occurrences, a positive integer. For "
              + "example, `2` with `weekly` means every 2 weeks. Default: `1` "
              + "when omitted or null. `0` or a negative value is `400`.",
        example = "1"
    )
    public int getInterval() { return 0; }

    @ApiModelProperty(
        value = "End date for the recurrence (UTC, ISO 8601). If not specified, the series never ends.",
        example = "2020-12-22",
        position = 4
    )
    public String getUntil() { return null; }

    @ApiModelProperty(
        value = "Days of the week, as integers where 0=Mon, 1=Tue, …, 6=Sun.\n\n"
              + "- With `weekly`: required, the days it occurs on, as a list "
              + "(e.g., `[1]`, `[0,3]`).\n"
              + "- With `monthly` or `yearly` and `byweekno`: required, a "
              + "single integer — the day in that week (e.g., `1` for "
              + "Tuesday), and returned as one too.\n\n"
              + "Ignored otherwise.",
        example = "[1,3]"
    )
    public List<Integer> getByweekday() { return null; }

    @ApiModelProperty(
        value = "With `monthly` or `yearly`: the week of the month, `1`–`5`, "
              + "or `\"last\"` (also `6`) for the month's last week. Requires "
              + "`byweekday` for the day in that week. If both `byweekno` and "
              + "`bydayno` are given, `byweekno` wins.",
        example = "2"
    )
    public int getByweekno() { return 0; }

    @ApiModelProperty(
        value = "With `monthly` or `yearly`: the day of the month, `1`–`31`. "
              + "`31`, or `\"last\"`, means the month's last day, and is "
              + "returned as `31`. Ignored if `byweekno` is given.",
        example = "25"
    )
    public int getBydayno() { return 0; }

    @ApiModelProperty(
        value = "With `yearly`: required, the month, `1`–`12` (1 = January). "
              + "Ignored otherwise.",
        example = "10"
    )
    public int getBymonth() { return 0; }

    @ApiModelProperty(
        value = "Whether to duplicate subtasks when the task is completed. Default: true.",
        example = "false"
    )
    public boolean getDupsubtasks() { return true; }

    @ApiModelProperty(
        value = "With `daily`: whether to repeat relative to the last "
              + "completion date. Default: `false` when omitted or null. "
              + "Ignored otherwise.",
        example = "true"
    )
    public boolean getSincelatest() { return false; }
}
