package io.quire.api.model.status;

import io.swagger.annotations.ApiModelProperty;

public class CreateStatusBody {

    @ApiModelProperty(
        value = "Display name of the status.",
        example = "Doing",
        required = true
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "Non-negative integer indicating progress. Must be unique within its context (e.g., project). "
              + "Values ≥ 100 are treated as completed.",
        example = "50",
        required = true
    )
    public int getValue() { return 0; }

    @ApiModelProperty(
        value = "(Optional) Status color index from Quire’s predefined palette. "
              + "If omitted, a color will be generated automatically. "
              + "Two-digit code: first digit 0–5, second digit 0–7 (e.g., `35`).",
        example = "35",
        required = false
    )
    public String getColor() { return null; }
}
