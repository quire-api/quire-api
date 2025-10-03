package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class SimpleTaggingEntity extends NamedEntity {

    @ApiModelProperty(
        value = "Color index from Quire’s predefined palette. Two-digit code: first digit 0–5, second digit 0–7 (e.g., `35`). The palette appears in Quire’s color picker.",
        example = "35"
    )
    public String getColor() { return null; }
}
