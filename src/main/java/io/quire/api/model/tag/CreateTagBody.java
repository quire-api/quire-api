package io.quire.api.model.tag;

import io.swagger.annotations.ApiModelProperty;

public class CreateTagBody {

    @ApiModelProperty(
        value = "Display name of the tag.",
        example = "Later",
        required = true
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "(Optional) Tag color index from Quire’s predefined palette. "
              + "If omitted, a color will be generated automatically. "
              + "Two-digit code: first digit 0–5, second digit 0–7 (e.g., `35`).",
        example = "35",
        required = false
    )
    public String getColor() { return null; }

    @ApiModelProperty(
        value = "(Optional) Whether this tag is global (available across projects). "
              + "If omitted, the tag is not global.",
        example = "true",
        required = false
    )
    public boolean getGlobal() { return false; }
}
