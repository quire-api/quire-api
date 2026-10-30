package io.quire.api.model.task;

import io.swagger.annotations.*;

/**
 * A task's status as task responses give it. Named {@code TaskStatus} in
 * the spec, since {@link io.quire.api.model.status.Status} takes
 * {@code Status}.
 */
@ApiModel(
    value = "TaskStatus",
    description = "A task's status: its value, name, and color."
)
public class Status {

    @ApiModelProperty(
        value = "Status value, one of the project's statuses. `0` is the "
              + "initial status; `100` or more means completed.",
        example = "0",
        position = 1
    )
    public int getValue() { return 0; }

    @ApiModelProperty(
        value = "Display name of the status. A built-in status without a "
              + "custom name gets its default name in the authorizing "
              + "user's language.",
        example = "To-do",
        position = 2
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "Palette index for the status color. Two-digit code: first digit 0–5, second digit 0–7 (e.g., `35`). "
              + "Matches the predefined colors shown in Quire’s color picker.",
        example = "50",
        position = 3
    )
    public String getColor() { return null; }
}
