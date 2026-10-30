package io.quire.api.model.task;

import io.quire.api.model.*;
import io.swagger.annotations.*;

import java.util.*;

public class SimpleTask extends Entity {

    @ApiModelProperty(
        value = "Task id.",
        example = "12",
        position = 1
    )
    public int getId() { return 0; }

    @ApiModelProperty(
        value = "Task name (Markdown supported).",
        example = "Design new **logo**",
        position = 2
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "Task name with Markdown removed.",
        example = "Design new logo",
        position = 2
    )
    public String getNameText() { return null; }

    @ApiModelProperty(
        value = "Task name rendered as an HTML fragment converted from Markdown.",
        example = "Design new <b>logo</b>",
        position = 2
    )
    public String getNameHtml() { return null; }

    @ApiModelProperty(
        value = "Task description (Markdown supported).",
        example = "This is a *cool* task.",
        position = 3
    )
    public String getDescription() { return null; }

    @ApiModelProperty(
        value = "Task description with Markdown removed.",
        example = "This is a cool task.",
        position = 3
    )
    public String getDescriptionText() { return null; }

    @ApiModelProperty(
        value = "Task description rendered as an HTML fragment converted from Markdown.",
        example = "This is a <i>cool</i> task.",
        position = 3
    )
    public String getDescriptionHtml() { return null; }

    @ApiModelProperty(
        value = "Task status as `{value, name, color}`, e.g. "
              + "`{\"value\": 0, \"name\": \"To-do\", \"color\": \"50\"}`. "
              + "A `value` of `100` or more means the task is completed.",
        position = 4
    )
    public Status getStatus() { return null; }

    @ApiModelProperty(
        value = "Task priority as `{value, name}`, e.g. "
              + "`{\"value\": 0, \"name\": \"Medium\"}`: `-1` Low, `0` Medium, "
              + "`1` High, `2` Urgent.",
        position = 4
    )
    public Priority getPriority() { return null; }

    @ApiModelProperty(
        value = "Tags applied to this task."
    )
    public List<SimpleTaggingEntity> getTags() { return null; }

    @ApiModelProperty(
        value = "Start date, in UTC: `yyyy-MM-dd` for a date without a "
              + "time, or `yyyy-MM-dd'T'HH:mm'Z'` with one (e.g. "
              + "`2026-05-01T09:30Z`). Omitted if not set.",
        example = "2026-05-01",
        position = 4
    )
    public String getStart() { return null; }

    @ApiModelProperty(
        value = "Due date, in UTC: `yyyy-MM-dd` for a date without a "
              + "time, or `yyyy-MM-dd'T'HH:mm'Z'` with one (e.g. "
              + "`2026-05-10T17:00Z`). Omitted if not set.",
        example = "2026-05-10",
        position = 4
    )
    public String getDue() { return null; }

    @ApiModelProperty(
        value = "Users assigned to this task, each as `{oid, id, name}`.",
        position = 5
    )
    public List<TaskUser> getAssignees() { return null; }

    @ApiModelProperty(
        value = "IDs of tasks that depend on this task, as `#<id>` "
              + "strings. Omitted if none.\n\n"
              + "For ID-based access, prefer `successorRefs`, which "
              + "returns `{oid, id}` pairs.",
        example = "[\"#135\", \"#26\"]"
    )
    public List<String> getSuccessors() { return null; }
    @ApiModelProperty(
        value = "IDs of tasks that this task depends on, as `#<id>` "
              + "strings. Omitted if none.\n\n"
              + "For ID-based access, prefer `predecessorRefs`, which "
              + "returns `{oid, id}` pairs.",
        example = "[\"#17\", \"#66\", \"#91\"]"
    )
    public List<String> getPredecessors() { return null; }

    @ApiModelProperty(
        value = "Successors as `{oid, id}` pairs — same shape as "
              + "`?return=compact`. Recommended over `successors` for "
              + "callers using ID-based access."
    )
    public List<TaskRef> getSuccessorRefs() { return null; }
    @ApiModelProperty(
        value = "Predecessors as `{oid, id}` pairs — same shape as "
              + "`?return=compact`. Recommended over `predecessors` for "
              + "callers using ID-based access."
    )
    public List<TaskRef> getPredecessorRefs() { return null; }

    @ApiModelProperty(
        value = "`true` if the task is archived (hidden by peekaboo); "
              + "omitted otherwise.",
        example = "true",
        position = 50
    )
    public boolean getPeekaboo() { return false; }

    @ApiModelProperty(
        value = "`true` if this task is a section; omitted otherwise.",
        example = "true",
        position = 51
    )
    public boolean getSection() { return false; }

    @ApiModelProperty(
        value = "`true` if this task is a milestone; omitted otherwise.",
        example = "true",
        position = 52
    )
    public boolean getMilestone() { return false; }

    @ApiModelProperty(
        value = "URL of this task on the Quire website.",
        example = "https://quire.io/w/my_project/123",
        position = 53
    )
    public String getUrl() { return null; }

    @ApiModelProperty(
        value = "The JSON object an app stored with the task, as is; omitted "
              + "if none. Its `text`, if any, is the Markdown Quire shows as "
              + "the task's source line; other keys are the app's own data. "
              + "Find tasks by one of its keys with the search endpoints' "
              + "`?source-ref=<key>`.",
        example = CreateTaskBody.SOURCE_REF_EXAMPLE,
        position = 60
    )
    public Map<String, Object> getSourceRef() { return null; }

    @ApiModelProperty(
        value = "Creation timestamp in UTC (ISO 8601).",
        example = "2018-12-22T02:06:58.158Z",
        position = 99
    )
    public String getCreatedAt() { return null; }

    @ApiModelProperty(
        value = "User who created this task, as `{oid, id, name}`.",
        position = 99
    )
    public TaskUser getCreatedBy() { return null; }
}
