package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class NamedIconEntity extends NamedEntity {

    @ApiModelProperty(
        value = "Color index for the icon from Quire’s predefined palette. Two-digit code: first digit 0–5, second digit 0–7 (e.g., `37`).",
        example = "37"
    )
    public String getIconColor() {
        return null;
    }

    @ApiModelProperty(
        value = "Image URL representing this record.",
        example = "https://quire.s3.amazonaws.com/oid/image.jpg",
        allowEmptyValue = true
    )
    public String getImage() {
        return null;
    }
}
