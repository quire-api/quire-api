package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class TaggingEntity extends StampedEntity {

    @ApiModelProperty(
        value = "Display name of the tag.",
        example = "Later"
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "Color index from Quire’s predefined color palette. "
              + "Two-digit code where the first digit is 0–5 and the second digit is 0–7 (e.g., `35`). "
              + "The palette is available in Quire’s color picker.",
        example = "35"
    )
    public String getColor() { return null; }

    @ApiModelProperty(
        value = "The project this tag belongs to.",
        position = 99
    )
    public SimpleIdentity getProject() { return null; }
}
