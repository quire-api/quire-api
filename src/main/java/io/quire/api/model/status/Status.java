package io.quire.api.model.status;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class Status {

    @ApiModelProperty(
        value = "Display name of the status.",
        example = "Doing"
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "Non-negative integer indicating progress. Must be unique within its context (e.g., project). "
              + "Values ≥ 100 are treated as completed. When listing or searching "
              + "tasks, `?status=100` (or `completed`) matches every completed "
              + "status; values above 100 can't be filtered individually.",
        example = "50"
    )
    public int getValue() { return 0; }

    @ApiModelProperty(
        value = "Color index from Quire’s predefined palette. Two-digit code: first digit 0–5, second digit 0–7 (e.g., `35`).",
        example = "35"
    )
    public String getColor() { return null; }
}
