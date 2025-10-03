package io.quire.api.model.status;

import io.swagger.annotations.ApiModelProperty;

public class UpdateStatusBody {

    @ApiModelProperty(
        value = "(Optional) New display name for the status.",
        example = "Later",
        required = false
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "(Optional) New numeric status value. Non-negative integer indicating progress. "
              + "Must be unique within the context (e.g., project). Values ≥ 100 are treated as completed.",
        example = "50",
        required = false
    )
    public int getValue() { return 0; }

    @ApiModelProperty(
        value = "(Optional) Status color index from Quire’s predefined palette (two digits: first 0–5, second 0–7; e.g., `35`).",
        example = "35",
        required = false
    )
    public String getColor() { return null; }
}
