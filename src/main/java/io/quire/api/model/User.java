package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class User {
    @ApiModelProperty(example = "john")
    public String getId() {
        return null;
    }

    @ApiModelProperty(example = "ZFrV7UGOI2oMIz48cV5IzvzZ")
    public String getOid() {
        return null;
    }

    @ApiModelProperty(example = "John")
    public String getName() {
        return null;
    }

    @ApiModelProperty(example = "J")
    public String getInitials() {
        return null;
    }

    @ApiModelProperty(example = "#a9cb77")
    public String getIconColor() {
        return null;
    }

    @ApiModelProperty(example = "https://quire.s3.amazonaws.com/oid/image.jpg")
    public String getImage() {
        return null;
    }
}
