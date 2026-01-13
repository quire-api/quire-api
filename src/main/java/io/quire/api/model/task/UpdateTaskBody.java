package io.quire.api.model.task;

import java.util.*;

import io.quire.api.model.Recurrence;
import io.swagger.annotations.ApiModelProperty;

public class UpdateTaskBody {

    @ApiModelProperty(
        example = "New idea",
        value = "(Optional) New task name."
    )
    public String getName() { return null; }

    @ApiModelProperty(
        example = "This is a **cool** task.",
        value = "(Optional) New task description."
    )
    public String getDescription() { return null; }

    @ApiModelProperty(
        value = "(Optional) New priority. Must be between -1 (lowest) and 2 (highest).",
        example = "0",
        position = 4
    )
    public int getPriority() { return 0; }

    @ApiModelProperty(
        value = "(Optional) New status, between 0 and 100. Specify 100 to complete the task.",
        example = "100",
        position = 4
    )
    public int getStatus() { return 0; }

    @ApiModelProperty(
        value = "(Optional) Estimated time to complete, in seconds. Must be non-negative or `null`.\n"
              + "Specify `null` to clear the value.",
        example = "0",
        position = 4
    )
    public int getEtc() { return 0; }

    @ApiModelProperty(
        value = "(Optional) Tags to replace the current tags on this task (OID or name).\n"
              + "This replaces all existing tags. To modify incrementally, use `addTags` or `removeTags`.\n"
              + "Tag names are case-insensitive."
    )
    public List<String> getTags() { return null; }

    @ApiModelProperty(
        value = "(Optional) Tags to add to this task (OID or name).\n"
              + "Tag names are case-insensitive."
    )
    public List<String> getAddTags() { return null; }

    @ApiModelProperty(
        value = "(Optional) Tags to remove from this task (OID or name).\n"
              + "Tag names are case-insensitive."
    )
    public List<String> getRemoveTags() { return null; }

    @ApiModelProperty(
        value = "(Optional) Assignees to replace the current assignees (OID, ID, or email).\n"
              + "This replaces all existing assignees. To modify incrementally, use `addAssignees` or `removeAssignees`.\n\n"
              + "See `addAssignees` for special values."
    )
    public List<String> getAssignees() { return null; }

    @ApiModelProperty(
        value = "(Optional) Assignees to add (OID, ID, or email).\n"
              + "Special values:\n"
              + "- \"me\": the current user\n"
              + "- \"inherit\": include all assignees of the parent task\n\n"
              + "Example: `{\"addAssignees\": [\"me\", \"inherit\", \"foo@domain.com\"]}`"
    )
    public List<String> getAddAssignees() { return null; }

    @ApiModelProperty(
        value = "(Optional) Assignees to remove (OID, ID, or email).\n\n"
              + "See `addAssignees` for details on special values."
    )
    public List<String> getRemoveAssignees() { return null; }

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
        value = "(Optional) Recurrence details. `null` if the task is not recurring.\n\n"
              + "- `freq`: `daily`, `weekly`, `monthly`, `yearly`.\n"
              + "- `interval`: Interval between occurrences. Default: 1.\n"
              + "- `until`: End date. Default: never ends.\n"
              + "- `bymonth`: Month (1 = January). Supported only with `yearly`. Default: 1.\n"
              + "- `byweekno`: Week number (starting from 1) or `last`. Supported with `monthly`/`yearly`.\n"
              + "- `byweekday`: Day(s) of week (0 = Monday ... 6 = Sunday). For weekly, use a list (e.g., `[1]`, `[0,3]`).\n"
              + "- `bydayno`: Day of month (1 = first day). Supported with `monthly`/`yearly`.\n"
              + "  Note: `byweekday` and `bydayno` cannot both be specified.\n"
              + "- `dupsubtasks`: Duplicate subtasks when the task is completed. Default: true.\n"
              + "- `sincelatest`: Daily only. Repeat based on the last completion date. Default: false."
    )
    public Recurrence getRecurrence() { return null; }

    @ApiModelProperty(
        example = "true",
        value = "(Optional) Peekaboo setting.\n\n"
              + "- `true`: Hide indefinitely (task and subtasks).\n"
              + "- Positive integer: Number of days to hide.\n"
              + "- `false`: Undo previous peekaboo.\n\n"
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
        value = "(Optional) Followers to replace the current followers (OID, ID, or email).\n\n"
              + "See `addFollowers` for details."
    )
    public List<String> getFollowers() { return null; }

    @ApiModelProperty(
        value = "(Optional) Followers to add (OID, ID, or email).\n"
              + "Special values:\n"
              + "- \"me\": the current user\n"
              + "- \"inherit\": include followers of the parent task\n"
              + "- \"app\": the application follows (receives notifications)\n\n"
              + "App follower syntax:\n"
              + "- `app|team` or `app|team|channel`\n"
              + "- `app|/path` → appended to the hook URL (e.g., `.../standard/soc1/33456/a7`)."
    )
    public List<String> getAddFollowers() { return null; }

    @ApiModelProperty(
        value = "(Optional) Followers to remove (OID, ID, or email).\n\n"
              + "See `addFollowers` for details on special values."
    )
    public List<String> getRemoveFollowers() { return null; }

    @ApiModelProperty(
        value = "(Optional) Successors to replace the current successors (task OID or ID).\n\n"
              + "IDs can be specified as `#id` or `id`.\n\n"
              + "Examples: `'AMZ0-59R125-35KTK2356G'`, `'#13'`, `135`."
    )
    public List<String> getSuccessors() { return null; }

    @ApiModelProperty(
        value = "(Optional) Successors to add (task OID or ID).\n\n"
              + "IDs can be specified as `#id` or `id`.\n\n"
              + "Examples: `'AMZ0-59R125-35KTK2356G'`, `'#13'`, `135`."
    )
    public List<String> getAddSuccessors() { return null; }

    @ApiModelProperty(
        value = "(Optional) Successors to remove (task OID or ID).\n\n"
              + "IDs can be specified as `#id` or `id`.\n\n"
              + "Examples: `'AMZ0-59R125-35KTK2356G'`, `'#13'`, `135`."
    )
    public List<String> getRemoveSuccessors() { return null; }

    @ApiModelProperty(
        example = "true",
        value = "(Optional) If true, marks this update as performed by the app.\n"
              + "Default: false (updated by the authorizing user)."
    )
    public boolean getAsUser() { return false; }

    @ApiModelProperty(
        value = "(Optional) Value for a custom field. Type depends on field definition.\n\n"
              + "- Money: numeric value only (no currency).\n"
              + "- User/Task: OID.\n"
              + "- Duration: number of seconds.\n"
              + "- Multi-value: provide a list."
    )
    public Object getYourField() { return false; }

    @ApiModelProperty(
        value = "(Optional) Arbitrary source reference data to store with the task. Available via API on retrieval.\n\n"
              + "If the map contains `text`, its value will be displayed client-side and should be Markdown. "
              + "Including a source link is recommended.",
        example = "{'text': 'Source: [Gmail](https://gmail.com/link)'}",
        position = 60
    )
    public Map<String, Object> getSourceRef() { return null; }
}
