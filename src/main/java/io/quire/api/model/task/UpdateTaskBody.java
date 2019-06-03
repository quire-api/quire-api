package io.quire.api.model.task;

import io.swagger.annotations.ApiModelProperty;

public class UpdateTaskBody {
    @ApiModelProperty(example = "New idea",
        value = "(Optional) New name of the task.")
    public String getName() { return null; }

    @ApiModelProperty(value = "(Optional) Change the description about this task.")
    public String getDescription() { return null; }

    @ApiModelProperty(value = "(Optiona) Change the priority of this task. "
        + "Its value must be between -1 (lowest) and 2 (highest). ",
        example = "0", position = 4)
    public int getPriority() { return 0; }

    @ApiModelProperty(value = "(Optiona) An optional status to change to. "
        + "Its value must be between 0 and 100. "
        + "To complete a task, specify 100.",
        example = "100", position = 4)
    public int getStatus() { return 0; }

    @ApiModelProperty(example = "[\"6QMKkEPBVWETLWrXqws94ALU\"]",
        value = "(Optional) OID of the tags to replace existing tags of to this task. "
        + "If specified, it will replace any existing tag(s). "
        + "Instead of replacment, you can use \"addTags\" or \"removeTags\".")
    public List<String> getTags() { return null; }
    @ApiModelProperty(example = "[\"6QMKkEPBVWETLWrXqws94ALU\"]",
        value = "(Optional) OID of the tags to be added to this task.")
    public List<String> getAddTags() { return null; }
    @ApiModelProperty(example = "[\"6QMKkEPBVWETLWrXqws94ALU\"]",
        value = "(Optional) OID of the tags to be removed from this task.")
    public List<String> getRemoveTags() { return null; }

    @ApiModelProperty(example = "[\"6QMKkEPBVWETLWrXqws94ALU\"]",
        value = "(Optional) OID of the users that this task is assigned to. "
        + "If specified, it will replace any existing assignee(s). "
        + "Instead of replacment, you can use \"addAssignees\" or \"removeAssignees\".")
    public List<String> getAssignees() { return null; }
    @ApiModelProperty(example = "[\"6QMKkEPBVWETLWrXqws94ALU\"]",
        value = "(Optional) OID of the assignees to be added to this task.")
    public List<String> getAddAssignees() { return null; }
    @ApiModelProperty(example = "[\"6QMKkEPBVWETLWrXqws94ALU\"]",
        value = "(Optional) OID of the assignees to be removed from this task.")
    public List<String> getRemoveAssignees() { return null; }

    @ApiModelProperty(example = "2018-12-20T00:00:00.000Z",
        value = "(Optional) An optional start time. "
        + "Note: if time is specified, the millisecond must be `001`. "
        + "Otherwise, it is `000` (so are the hour, minute and second fields).")
    public String getStart() { return null; }

    @ApiModelProperty(example = "2018-12-22T00:00:00.000Z",
        value = "(Optional) An optional start time. "
        + "Note: if time is specified, the millisecond must be `001`. "
        + "Otherwise, it is `000` (so are the hour, minute and second fields).")
    public String getDue() { return null; }

    @ApiModelProperty(example = "true",
        value = "(Optional) Specify true to peekaboo this task and its subtasks. "
        + "Or, specify false to undo the previous peekaboo if any.")
    public boolean getPeekaboo() { return null; }

    @ApiModelProperty(example = "[\"6QMKkEPBVWETLWrXqws94ALU\"]",
        value = "(Optional) OID of the followers to be added to this task.")
    public List<String> getAddFollowers() { return null; }
    @ApiModelProperty(example = "[\"6QMKkEPBVWETLWrXqws94ALU\"]",
        value = "(Optional) OID of the followers to be removed from this task.")
    public List<String> getRemoveFollowers() { return null; }
}
