package io.quire.api.model.task;

import io.quire.api.model.Recurrence;
import io.swagger.annotations.*;

import java.util.*;

public class CreateTaskBody {

    @ApiModelProperty(
        example = "Design new **logo**",
        value = "The name of the task.", 
        required = true
    )
    public String getName() { return null; }

    @ApiModelProperty(
        example = "This is a *cool* task.",
        value = "(Optional) A description of the task."
    )
    public String getDescription() { return null; }

    @ApiModelProperty(
        value = "(Optional) Task priority. Must be between -1 (lowest) and 2 (highest). Default: 0.",
        example = "0",
        position = 4
    )
    public int getPriority() { return 0; }

    @ApiModelProperty(
        value = "(Optional) Task status. Must be between 0 and 100. Default: 0.",
        example = "0",
        position = 4
    )
    public int getStatus() { return 0; }

    @ApiModelProperty(
        value = "(Optional) Estimated time to complete, in seconds. Must be non-negative.",
        example = "0",
        position = 4
    )
    public int getEtc() { return 0; }

    @ApiModelProperty(
        value = "(Optional) A list of tag OIDs or names to be added to the new task.\n"
              + "Tag names are case-insensitive."
    )
    public List<String> getTags() { return null; }

    @ApiModelProperty(
        value = "(Optional) A list of user identifiers (OID, ID, or email) to assign this task to.\n\n"
              + "- Use \"me\" to indicate the current user.\n"
              + "- Use \"inherit\" to include all assignees of the parent task.\n\n"
              + "Example: `{\"addAssignees\": [\"me\", \"inherit\", \"foo@domain.com\"]}`"
    )
    public List<String> getAssignees() { return null; }

    @ApiModelProperty(
        example = "2018-12-20T00:00:00.000Z",
        value = "(Optional) Start date/time in UTC.\n\n"
              + "- With time: `yyyy-MM-dd'T'HH:mmZ` (e.g., `2020-10-30T09:30Z`).\n"
              + "- Date only: `yyyy-MM-dd` (e.g., `2020-10-30`).\n\n"
              + "Notes:\n"
              + "- Seconds are not supported.\n"
              + "- `2020-10-30T00:00:00` is treated as `2020-10-30` (date only).\n"
              + "- To specify exactly midnight UTC, use `2020-10-30T00:00` (no seconds)."
    )
    public String getStart() { return null; }

    @ApiModelProperty(
        example = "2018-12-22T00:00:00.000Z",
        value = "(Optional) Due date/time in UTC.\n\n"
              + "- With time: `yyyy-MM-dd'T'HH:mmZ` (e.g., `2020-10-30T09:30Z`).\n"
              + "- Date only: `yyyy-MM-dd` (e.g., `2020-10-30`).\n\n"
              + "Notes:\n"
              + "- Seconds are not supported.\n"
              + "- `2020-10-30T00:00:00` is treated as `2020-10-30` (date only).\n"
              + "- To specify exactly midnight UTC, use `2020-10-30T00:00` (no seconds)."
    )
    public String getDue() { return null; }

    @ApiModelProperty(
        value = "(Optional) Recurrence details. Null if the task is not recurring.\n\n"
              + "- `freq`: `daily`, `weekly`, `monthly`, `yearly`.\n"
              + "- `interval`: Interval between occurrences. Default: 1.\n"
              + "- `until`: End date. Default: never ends.\n"
              + "- `bymonth`: Month (1 = January). Supported only with `yearly`. Default: 1.\n"
              + "- `byweekno`: Week number (starting from 1) or `last`. Supported with `monthly`/`yearly`.\n"
              + "- `byweekday`: Day(s) of week (0 = Monday ... 6 = Sunday). For weekly, use a list (e.g., `[0,3]`).\n"
              + "- `bydayno`: Day of month (1 = first day). Supported with `monthly`/`yearly`.\n"
              + "  Note: `byweekday` and `bydayno` cannot both be specified.\n"
              + "- `dupsubtasks`: Whether to duplicate subtasks when completed. Default: true.\n"
              + "- `sincelatest`: For `daily` only. Whether to repeat based on last completion date. Default: false."
    )
    public Recurrence getRecurrence() { return null; }

    @ApiModelProperty(
        example = "true",
        value = "(Optional) Peekaboo setting.\n\n"
              + "- `true`: Hide indefinitely.\n"
              + "- Positive integer: Number of days to hide.\n"
              + "- `false`: Cancel previous peekaboo.\n\n"
              + "Default: false."
    )
    public Object getPeekaboo() { return false; }

    @ApiModelProperty(
        example = "true",
        value = "(Optional) Whether this task is a section. Default: false."
    )
    public boolean getSection() { return false; }

    @ApiModelProperty(
        example = "true",
        value = "(Optional) Whether this task is a milestone. Default: false."
    )
    public boolean getMilestone() { return false; }

    @ApiModelProperty(
        example = "true",
        value = "(Optional) If true, marks this task as created by the app.\n"
              + "Default: false (created by the authorizing user)."
    )
    public boolean getAsUser() { return false; }

    @ApiModelProperty(
        value = "(Optional) List of user identifiers (OID, ID, or email) who follow this task.\n\n"
              + "- Use \"me\" for the current user.\n"
              + "- Use \"inherit\" to include followers of the parent task.\n"
              + "- Use \"app\" for the application itself to follow (receive notifications).\n\n"
              + "For app followers, additional syntaxes are supported:\n"
              + "- `app|team` or `app|team|channel`\n"
              + "- `app|/path` → appended to the hook URL (e.g., `.../standard/soc1/33456/a7`)."
    )
    public List<String> getFollowers() { return null; }

    @ApiModelProperty(
        value = "(Optional) List of successor task identifiers (OID or ID).\n\n"
              + "IDs can be specified as `#id` or `id`.\n\n"
              + "Examples: `'AMZ0-59R125-35KTK2356G'`, `'#13'`, `135`."
    )
    public List<String> getSuccessors() { return null; }

    @ApiModelProperty(
        value = "(Optional) Value for a custom field, depending on field definition.\n\n"
              + "- Money: specify the numeric value only (no currency).\n"
              + "- User/Task: specify the OID.\n"
              + "- Duration: specify number of seconds.\n"
              + "- Multi-value: specify a list."
    )
    public Object getYourField() { return false; }

    @ApiModelProperty(
        value = "(Optional) List of subtasks to create along with this task."
    )
    public List<CreateTaskBody> getTasks() { return null; }

    @ApiModelProperty(
        value = "(Optional) Arbitrary source reference data to attach to the task.\n"
              + "Available via API when retrieving the task.\n\n"
              + "If the entry contains a key `text`, its value will be displayed client-side "
              + "and should be formatted in Markdown. It is recommended to include a source link.\n\n",
        example = "{'text': 'Source: [Gmail](https://gmail.com/link)'}",
        position = 60
    )
    public Map<String, Object> getSourceRef() { return null; }
}
