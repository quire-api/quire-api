package io.quire.api.model.task;

import io.swagger.annotations.*;

public class CreateTaskBody {

    @ApiModelProperty(example = "Task 101",
        value = "The name of the task.")
    public String getName() { return null; }

    @ApiModelProperty(example = "**Great** task to start with.",
        value = "(Optional) An optional description about this task.")
    public String getDescription() { return null; }

    @ApiModelProperty(value = "(Optiona) An optional priority. "
        + "Its value must be between -1 (lowest) and 2 (highest). "
        + "Default: 0.",
        example = "0", position = 4)
    public int getPriority() { return 0; }

    @ApiModelProperty(value = "(Optiona) An optional status. "
        + "Its value must be between 0 and 100. "
        + "Default: 0.",
        example = "0", position = 4)
    public int getStatus() { return 0; }

    @ApiModelProperty(example = "[\"6QMKkEPBVWETLWrXqws94ALU\"]",
        value = "(Optional) OID of the tags to be added to the new created task.")
    public List<String> getTags() { return null; }

    @ApiModelProperty(example = "[\"6QMKkEPBVWETLWrXqws94ALU\"]",
        value = "(Optional) OID of the users that this task is assigned to.")
    public List<String> getAssignees() { return null; }

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

    @ApiModelProperty(value = "(Optional) OID of users who follow this task.")
    public List<String> getFollowers() { return null; }

    @ApiModelProperty(value = "(Optional) A list of subtasks to create.")
    public List<CreateTaskBody> getTasks() { return null; }
}
