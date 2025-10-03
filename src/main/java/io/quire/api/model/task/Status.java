package io.quire.api.model.task;

import io.swagger.annotations.*;

class Status {

    @ApiModelProperty(
        value = "Display name of the status.",
        example = "Completed"
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "Palette index for the status color. Two-digit code: first digit 0–5, second digit 0–7 (e.g., `35`). "
              + "Matches the predefined colors shown in Quire’s color picker.",
        example = "35"
    )
    public String getColor() { return null; }

    @ApiModelProperty(
        value = "Numeric status value from 0 to 100. Values ≥ 100 are treated as completed.",
        example = "0",
        position = 4
    )
    public int getValue() { return 0; }
}
