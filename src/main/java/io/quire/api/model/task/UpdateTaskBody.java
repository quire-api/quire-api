package io.quire.api.model.task;

import java.util.*;

import io.quire.api.model.Recurrence;
import io.swagger.annotations.ApiModelProperty;

public class UpdateTaskBody {

    // The follower fields share these; FOLLOW_RIGHT is the same everywhere.
    private static final String FOLLOW_RIGHT =
        "With read access only, `addFollowers` and `removeFollowers` may "
      + "name only yourself: `\"me\"`, or `\"app\"` to add the app on your "
      + "behalf. Adding or removing anyone else returns `403`, even along "
      + "with yourself. `followers` replaces every follower, so it needs "
      + "edit access even as `[\"me\"]`.";

    private static final String FOLLOW_EDITED =
        "An update that only follows or unfollows doesn't change `editedAt`.";

    @ApiModelProperty(
        example = "New idea",
        value = "(Optional) New task name."
    )
    public String getName() { return null; }

    @ApiModelProperty(
        example = "This is a **cool** task.",
        value = "(Optional) New task description. An embedded `data:` image is rejected — upload the image as an attachment instead."
    )
    public String getDescription() { return null; }

    @ApiModelProperty(
        value = "(Optional) New priority: " + CreateTaskBody.PRIORITY_VALUES,
        example = "0",
        allowableValues = "-1, 0, 1, 2",
        position = 4
    )
    public int getPriority() { return 0; }

    @ApiModelProperty(
        value = "(Optional) New status. " + CreateTaskBody.STATUS_VALUES
              + " Specify 100 to complete the task. "
              + "Completion may be rejected (409 Conflict) when project settings require all "
              + "predecessors to be completed first, or require an approval to be granted.",
        example = "100",
        position = 4
    )
    public int getStatus() { return 0; }

    @ApiModelProperty(
        value = "(Optional) Estimated time to complete, in seconds. Must be "
              + "0 to 864,000,000 (10,000 days) or `null`.\n"
              + "Specify `null` to clear the value.",
        example = "0",
        position = 4
    )
    public int getEtc() { return 0; }

    @ApiModelProperty(
        value = "(Optional) Tags to replace the current tags on this task (OID or name).\n"
              + "This replaces all existing tags. To modify incrementally, use `addTags` or `removeTags`.\n"
              + "Tag names are case-insensitive. " + CreateTaskBody.TAGS_EXIST,
        example = "[\"urgent\", \"design\"]"
    )
    public List<String> getTags() { return null; }

    @ApiModelProperty(
        value = "(Optional) Tags to add to this task (OID or name).\n"
              + "Tag names are case-insensitive. " + CreateTaskBody.TAGS_EXIST,
        example = "[\"urgent\"]"
    )
    public List<String> getAddTags() { return null; }

    @ApiModelProperty(
        value = "(Optional) Tags to remove from this task (OID or name).\n"
              + "Tag names are case-insensitive. An unknown tag is rejected "
              + "with `400`.",
        example = "[\"draft\"]"
    )
    public List<String> getRemoveTags() { return null; }

    @ApiModelProperty(
        value = "(Optional) Assignees to replace the current assignees (OID, ID, or email).\n"
              + "This replaces all existing assignees. To modify incrementally, use `addAssignees` or `removeAssignees`.\n\n"
              + "Accepts the same special values as `addAssignees`: `\"me\"` "
              + "(the current user) and `\"inherit\"` (all assignees of the parent task).\n\n"
              + CreateTaskBody.ASSIGNEES_RULE,
        example = "[\"me\", \"alice@example.com\"]"
    )
    public List<String> getAssignees() { return null; }

    @ApiModelProperty(
        value = "(Optional) Assignees to add (OID, ID, or email).\n"
              + "Special values:\n"
              + "- \"me\": the current user\n"
              + "- \"inherit\": include all assignees of the parent task\n\n"
              + CreateTaskBody.ASSIGNEES_RULE,
        example = "[\"me\"]"
    )
    public List<String> getAddAssignees() { return null; }

    @ApiModelProperty(
        value = "(Optional) Assignees to remove (OID, ID, or email).\n\n"
              + "Accepts the same special values as `addAssignees`: `\"me\"` "
              + "(the current user) and `\"inherit\"` (all assignees of the parent task).",
        example = "[\"alice@example.com\"]"
    )
    public List<String> getRemoveAssignees() { return null; }

    @ApiModelProperty(
        value = "(Optional) " + CreateTaskBody.PARTNER_USE + " A name or "
              + "OID that matches no team, or a value that isn't a string, "
              + "is rejected with `400`. The API can't remove it: `null` is "
              + "rejected too.",
        example = "UI design team"
    )
    public String getPartner() { return null; }

    @ApiModelProperty(
        example = "2026-05-01",
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
        example = "2026-05-10T17:00Z",
        value = "(Optional) Due date/time in UTC.\n\n"
              + "- With time: `yyyy-MM-dd'T'HH:mmZ` (e.g., `2020-10-30T09:30Z`).\n"
              + "- Date only: `yyyy-MM-dd` (e.g., `2020-10-30`).\n\n"
              + "Notes:\n"
              + "- Seconds are not supported.\n"
              + "- `2020-10-30T00:00:00` is treated as `2020-10-30` (date only).\n"
              + "- To specify exactly midnight UTC, use `2020-10-30T00:00` (no seconds).\n"
              + "- `null` clears it. It is rejected if the task is recurring, since a recurring "
              + "task always has a due; pass `recurrence`: `null` in the same request to stop "
              + "repeating and clear the due together."
    )
    public String getDue() { return null; }

    @ApiModelProperty(
        value = "(Optional) Makes the task repeat, replacing its current "
              + "recurrence, if any. `null` stops it repeating.\n\n"
              + CreateTaskBody.RECURRENCE_RULES + "\n\n"
              + "A recurring task always has a `due`. If the task has none and `due` is omitted, it "
              + "is derived from the recurrence: the nearest occurrence since today. The request is "
              + "rejected if the derived due precedes `start`; specify `due` explicitly in that case."
    )
    public Recurrence getRecurrence() { return null; }

    @ApiModelProperty(
        example = "true",
        value = "(Optional) Peekaboo setting. Accepts a boolean or a positive integer:\n\n"
              + "- `true`: Hide indefinitely (task and subtasks).\n"
              + "- positive integer: Number of days to hide.\n"
              + "- `false`: Undo previous peekaboo."
    )
    public Object getPeekaboo() { return false; }

    @ApiModelProperty(
        example = "true",
        value = "(Optional) `true` makes this task a section; `false` makes "
              + "it a normal task. If both `section` and `milestone` are "
              + "sent, only `section` is applied."
    )
    public boolean getSection() { return false; }

    @ApiModelProperty(
        example = "true",
        value = "(Optional) `true` makes this task a milestone; `false` "
              + "makes it a normal task. Ignored if `section` is sent too."
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
              + "- `app|/path` → appended to the hook URL (e.g., `.../standard/soc1/33456/a7`).\n\n"
              + FOLLOW_RIGHT + " " + FOLLOW_EDITED,
        example = "[\"bob@example.com\"]"
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
              + "- `app|/path` → appended to the hook URL (e.g., `.../standard/soc1/33456/a7`).\n\n"
              + FOLLOW_RIGHT + " " + FOLLOW_EDITED,
        example = "[\"me\"]"
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
              + "- `app|/path` → appended to the hook URL.\n\n"
              + FOLLOW_RIGHT + " " + FOLLOW_EDITED,
        example = "[\"bob@example.com\"]"
    )
    public List<String> getRemoveFollowers() { return null; }

    @ApiModelProperty(
        value = "(Optional) Successors to replace the current successors (task OID or ID).\n\n"
              + "IDs can be specified as `#id` or `id`. Integer IDs are also accepted.",
        example = "[\"#13\"]"
    )
    public List<String> getSuccessors() { return null; }

    @ApiModelProperty(
        value = "(Optional) Successors to add (task OID or ID).\n\n"
              + "IDs can be specified as `#id` or `id`. Integer IDs are also accepted.",
        example = "[\"#13\"]"
    )
    public List<String> getAddSuccessors() { return null; }

    @ApiModelProperty(
        value = "(Optional) Successors to remove (task OID or ID).\n\n"
              + "IDs can be specified as `#id` or `id`. Integer IDs are also accepted.",
        example = "[\"#13\"]"
    )
    public List<String> getRemoveSuccessors() { return null; }

    @ApiModelProperty(
        example = "true",
        value = "(Optional) If true, marks this update as performed by the app. "
              + "Read `asUser` as *app-as-user*: the app itself is the "
              + "user, not the authorizing user.\n"
              + "Default: false (updated by the authorizing user). "
              + "In `bulk-update`, each item has its own."
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
              + "- File: the attachment list as a task returns it (absolute "
              + "`url` accepted); send an entry back unchanged to keep that "
              + "file.\n"
              + "- Multi-value (fields configured with `multiple: true`): "
              + "provide a list. Pass `null` to clear the field.\n\n"
              + "A field you can't view or edit (the definition's `viewMembers` "
              + "and `editMembers`) is rejected with `400`; `locked` stops "
              + "editing in Quire only, not through the API.\n\n"
              + "Example body fragment: `{\"Priority\": 3, "
              + "\"Owners\": [\"alice\", \"bob@example.com\"], "
              + "\"Depends\": [\"#42\"]}`."
    )
    public Object getYourField() { return false; }

    @ApiModelProperty(
        value = "(Optional) " + CreateTaskBody.SOURCE_REF_USE + "\n"
              + "- It replaces the stored object as a whole: keys you leave "
              + "out are removed, and `null` removes `sourceRef`. Another app "
              + "may have stored its own key on the task; to keep it, read "
              + "the task first and send that key back.",
        example = CreateTaskBody.SOURCE_REF_EXAMPLE,
        position = 60
    )
    public Map<String, Object> getSourceRef() { return null; }
}
