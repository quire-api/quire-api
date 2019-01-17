package io.quire.api.model.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class User {
    @ApiModelProperty(example = "john")
    public String getId() {
        return null;
    }

    @ApiModelProperty(example = "ZFrV7UGOI2oMIz48cV5IzvzZ", position = 1)
    public String getOid() {
        return null;
    }

    @ApiModelProperty(example = "John", position = 2)
    public String getName() {
        return null;
    }

    @ApiModelProperty(example = "john@gmail.com", position = 2)
    public String getEmail() {
        return null;
    }

    @ApiModelProperty(example = "J", position = 2)
    public String getInitials() {
        return null;
    }

    @ApiModelProperty(example = "#a9cb77", position = 2)
    public String getIconColor() {
        return null;
    }

    @ApiModelProperty(example = "https://quire.s3.amazonaws.com/oid/image.jpg", position = 2)
    public String getImage() {
        return null;
    }
}
