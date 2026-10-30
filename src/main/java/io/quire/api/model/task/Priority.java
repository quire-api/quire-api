package io.quire.api.model.task;

import io.swagger.annotations.*;

/**
 * A task's priority as task responses give it.
 */
@ApiModel(
    value = "TaskPriority",
    description = "A task's priority: its value and name."
)
public class Priority {

    @ApiModelProperty(
        value = "Priority value: `-1` Low, `0` Medium, `1` High, `2` Urgent.",
        example = "0",
        allowableValues = "-1, 0, 1, 2",
        position = 1
    )
    public int getValue() { return 0; }

    @ApiModelProperty(
        value = "Name of the priority in the authorizing user's language, "
              + "e.g. `Medium` in English.",
        example = "Medium",
        position = 2
    )
    public String getName() { return null; }
}
