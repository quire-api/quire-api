package io.quire.api.model.task;

import io.quire.api.model.Recurrence;
import io.swagger.annotations.*;

import java.util.*;

public class CreateTaskBody {

    // UpdateTaskBody shares these.
    static final String SOURCE_REF_USE =
        "A JSON object your app stores with the task, such as where the task "
      + "came from. Quire keeps it as is and returns it as `sourceRef` "
      + "whenever the task is read.\n\n"
      + "- `text` (optional): a Markdown string Quire shows as the task's "
      + "source line in the task details. Include a link to the source, "
      + "e.g. `Source: [GitHub issue 42](https://github.com/acme/web/issues/42)`.\n"
      + "- Any other key: your app's own data, not shown in Quire. Put it "
      + "under a key named after your app, e.g. `github`, so it won't clash "
      + "with another app's.\n"
      + "- To find these tasks later, search with `?source-ref=github`: it "
      + "returns the tasks whose `sourceRef` has that key, whatever its "
      + "value.\n"
      + "- Size: at most about 2,048 characters, keys and values together, "
      + "and `text` must be a string; otherwise `400`.";
    static final String SOURCE_REF_EXAMPLE =
        "{\"github\": {\"repo\": \"acme/web\", \"issue\": 42}, "
      + "\"text\": \"Source: [GitHub issue 42](https://github.com/acme/web/issues/42)\"}";

    // UpdateTaskBody shares these too.
    static final String PRIORITY_VALUES =
        "`-1` Low, `0` Medium, `1` High, `2` Urgent. "
      + "(The server also accepts the case-insensitive English names "
      + "`Low`, `Medium`, `High`, `Urgent`, but the integer form is "
      + "recommended for typed callers.)";
    static final String STATUS_VALUES =
        "Must be one of the project's status values "
      + "(`GET /status/list/id/{projectId}` lists them); any other value "
      + "is rejected with `400`. Every project has `0`, the initial "
      + "status, and `100`, completed; `100` or more means completed.";
    static final String TAGS_EXIST =
        "Each must be an existing tag of the project, or a global tag of "
      + "its organization; an unknown tag is rejected with `400`, not "
      + "created.";
    static final String ASSIGNEES_RULE =
        "Only project members (external-team members included) can be "
      + "assigned; anyone else is silently left out. An unknown user is "
      + "rejected with `400`.";
    static final String RECURRENCE_RULES =
        "- `freq` (required): `daily`, `weekly`, `monthly`, or `yearly`.\n"
      + "- `interval`: a positive integer; `2` with `weekly` means every "
      + "2 weeks. Default: `1`.\n"
      + "- `until`: end date, e.g. `2026-12-31`. Default: never ends.\n"
      + "- `weekly` needs `byweekday`: the days of the week as a list, "
      + "where 0 = Monday … 6 = Sunday, e.g. `[0, 3]`.\n"
      + "- `monthly` needs `bydayno`, the day of the month (`1`–`31`, or "
      + "`\"last\"`), or `byweekno`, the week of the month (`1`–`5`, or "
      + "`\"last\"`), with `byweekday` as a single day in that week, e.g. "
      + "`1` for Tuesday. If both `byweekno` and `bydayno` are given, "
      + "`byweekno` wins.\n"
      + "- `yearly` needs `bymonth`, the month (`1`–`12`, 1 = January), "
      + "plus what `monthly` needs.\n"
      + "- `dupsubtasks`: whether to duplicate subtasks when the task is "
      + "completed. Default: `true`.\n"
      + "- `sincelatest`: with `daily` only, whether to repeat from the "
      + "last completion date. Default: `false`.\n\n"
      + "A property that doesn't apply to `freq` is ignored; an invalid "
      + "value, or a missing required one, is rejected with `400`.";
    static final String PARTNER_USE =
        "The external team to share this task with: the name or OID of "
      + "one of the project's external teams (`GET /partner/list/id/{projectId}` "
      + "lists them). The team's members then get access to the task and "
      + "its subtasks. Setting it takes the right to assign tasks (`403` "
      + "otherwise).";

    @ApiModelProperty(
        example = "Design new **logo**",
        value = "The name of the task.", 
        required = true
    )
    public String getName() { return null; }

    @ApiModelProperty(
        example = "This is a *cool* task.",
        value = "(Optional) A description of the task. An embedded `data:` image is rejected — upload the image as an attachment instead."
    )
    public String getDescription() { return null; }

    @ApiModelProperty(
        value = "(Optional) Task priority: " + PRIORITY_VALUES
              + " Default: `0` (Medium).",
        example = "0",
        allowableValues = "-1, 0, 1, 2",
        position = 4
    )
    public int getPriority() { return 0; }

    @ApiModelProperty(
        value = "(Optional) Task status. " + STATUS_VALUES + " Default: `0`.",
        example = "0",
        position = 4
    )
    public int getStatus() { return 0; }

    @ApiModelProperty(
        value = "(Optional) Estimated time to complete, in seconds: an "
              + "integer from 0 to 864,000,000 (10,000 days). `null` is the "
              + "same as leaving it out.",
        example = "0",
        position = 4
    )
    public int getEtc() { return 0; }

    @ApiModelProperty(
        value = "(Optional) A list of tag OIDs or names to be added to the new task.\n"
              + "Tag names are case-insensitive. " + TAGS_EXIST,
        example = "[\"urgent\", \"design\"]"
    )
    public List<String> getTags() { return null; }

    @ApiModelProperty(
        value = "(Optional) A list of user identifiers (OID, ID, or email) to assign this task to.\n\n"
              + "- Use \"me\" to indicate the current user.\n"
              + "- Use \"inherit\" to include all assignees of the parent task.\n\n"
              + ASSIGNEES_RULE,
        example = "[\"me\", \"alice@example.com\"]"
    )
    public List<String> getAssignees() { return null; }

    @ApiModelProperty(
        value = "(Optional) " + PARTNER_USE + " A name or OID that "
              + "matches no team, or a value that isn't a string, such as "
              + "`null`, is rejected with `400`.",
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
              + "- To specify exactly midnight UTC, use `2020-10-30T00:00` (no seconds)."
    )
    public String getDue() { return null; }

    @ApiModelProperty(
        value = "(Optional) Makes the task repeat. Omit it for a task "
              + "that doesn't repeat.\n\n"
              + RECURRENCE_RULES + "\n\n"
              + "A recurring task always has a `due`. If `due` is omitted, it is derived from the "
              + "recurrence: the nearest occurrence since today. The request is rejected if the "
              + "derived due precedes `start`; specify `due` explicitly in that case."
    )
    public Recurrence getRecurrence() { return null; }

    @ApiModelProperty(
        example = "true",
        value = "(Optional) Peekaboo setting. Accepts a boolean or a positive integer:\n\n"
              + "- `true`: Hide indefinitely.\n"
              + "- positive integer: Number of days to hide.\n"
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
        value = "(Optional) If true, marks this task as created by the app. "
              + "Read `asUser` as *app-as-user*: the app itself is the "
              + "user, not the authorizing user.\n"
              + "Default: false (created by the authorizing user). "
              + "In `bulk-add`, each item has its own. A nested task's "
              + "`asUser` must match its top-level task's (a tree is "
              + "created by one author); otherwise the call is rejected "
              + "with `400`."
    )
    public boolean getAsUser() { return false; }

    @ApiModelProperty(
        value = "(Optional) List of user identifiers (OID, ID, or email) who follow this task. "
              + "Only those listed, and anyone @mentioned in the description, follow it, "
              + "so include `\"me\"` for the current user to follow.\n\n"
              + "- Use \"me\" for the current user.\n"
              + "- Use \"inherit\" to include followers of the parent task.\n"
              + "- Use \"app\" for the application itself to follow (receive notifications).\n\n"
              + "For app followers, additional syntaxes are supported:\n"
              + "- `app|team` or `app|team|channel`\n"
              + "- `app|/path` → appended to the hook URL (e.g., `.../standard/soc1/33456/a7`).",
        example = "[\"bob@example.com\"]"
    )
    public List<String> getFollowers() { return null; }

    @ApiModelProperty(
        value = "(Optional) List of successor task identifiers (OID or ID).\n\n"
              + "IDs can be specified as `#id` or `id`. Integer IDs are also accepted.",
        example = "[\"#13\"]"
    )
    public List<String> getSuccessors() { return null; }

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
              + "`url` accepted).\n"
              + "- Multi-value (fields configured with `multiple: true`): "
              + "provide a list.\n\n"
              + "Example body fragment: `{\"Priority\": 3, "
              + "\"Owners\": [\"alice\", \"bob@example.com\"], "
              + "\"Depends\": [\"#42\"]}`."
    )
    public Object getYourField() { return false; }

    @ApiModelProperty(
        value = "(Optional) List of subtasks to create along with this task. "
              + "Each one, at any depth, costs 1 rate-limit unit and counts "
              + "toward the per-call cap of 300 tasks and the plan's "
              + "per-minute limit; see [Rate Limits](#rate-limits)."
    )
    public List<CreateTaskBody> getTasks() { return null; }

    @ApiModelProperty(
        value = "(Optional) " + SOURCE_REF_USE,
        example = SOURCE_REF_EXAMPLE,
        position = 60
    )
    public Map<String, Object> getSourceRef() { return null; }
}
