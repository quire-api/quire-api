package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class NamedIconEntity extends NamedEntity {
    @ApiModelProperty(value = "The color of the icon representing this record.", example = "#a9cb77")
    public String getIconColor() {
        return null;
    }

    @ApiModelProperty(value = "The image representing this record.", example = "https://quire.s3.amazonaws.com/oid/image.jpg", allowEmptyValue = true)
    public String getImage() {
        return null;
    }
}
