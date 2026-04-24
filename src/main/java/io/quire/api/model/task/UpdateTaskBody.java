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
        value = "(Optional) New priority. `-1` (lowest) through `2` (highest); "
              + "`0` is Normal. "
              + "(The server also accepts the case-insensitive English names "
              + "`Low`, `Medium`, `High`, `Urgent`, but the integer form is "
              + "recommended for typed callers.)",
        example = "0",
        allowableValues = "-1, 0, 1, 2",
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
              + "Accepts the same special values as `addAssignees`: `\"me\"` "
              + "(the current user) and `\"inherit\"` (all assignees of the parent task)."
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
              + "Accepts the same special values as `addAssignees`: `\"me\"` "
              + "(the current user) and `\"inherit\"` (all assignees of the parent task)."
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
        value = "(Optional) Peekaboo setting. Accepts a boolean or a positive integer:\n\n"
              + "- `true`: Hide indefinitely (task and subtasks).\n"
              + "- positive integer: Number of days to hide.\n"
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
              + "Special values:\n"
              + "- `\"me\"`: the current user\n"
              + "- `\"inherit\"`: include followers of the parent task\n"
              + "- `\"app\"`: the application follows (receives notifications)\n\n"
              + "App follower syntax:\n"
              + "- `app|team` or `app|team|channel`\n"
              + "- `app|/path` → appended to the hook URL (e.g., `.../standard/soc1/33456/a7`)."
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
              + "Special values:\n"
              + "- `\"me\"`: the current user\n"
              + "- `\"inherit\"`: remove followers that were inherited from the parent task\n"
              + "- `\"app\"`: the application\n\n"
              + "App follower syntax:\n"
              + "- `app|team` or `app|team|channel`\n"
              + "- `app|/path` → appended to the hook URL."
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
        value = "PLACEHOLDER — do NOT send a key literally named `yourField`. "
              + "Instead, use the custom field's own name (as defined via "
              + "`/project/add-field`) as the JSON key, with a value matching "
              + "the field's type:\n\n"
              + "- Text / Email / Hyperlink: string.\n"
              + "- Number / Money: numeric value (no currency symbol).\n"
              + "- Checkbox: `true` or `false`.\n"
              + "- Select: option name (string).\n"
              + "- Date: ISO 8601 timestamp (`yyyy-MM-dd'T'HH:mm:ssZ`) or "
              + "date-only (`yyyy-MM-dd`).\n"
              + "- Duration: number of seconds.\n"
              + "- **User**: user OID, user ID, or email — server resolves "
              + "to the user's OID.\n"
              + "- **Task**: task OID, integer task ID, or `#<id>` string "
              + "— server resolves to the task's OID (scoped to this "
              + "project).\n"
              + "- Multi-value (fields configured with `multiple: true`): "
              + "provide a list. Pass `null` to clear the field.\n\n"
              + "Example body fragment: `{\"Priority\": 3, "
              + "\"Owners\": [\"alice\", \"bob@example.com\"], "
              + "\"Depends\": [\"#42\"]}`."
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
