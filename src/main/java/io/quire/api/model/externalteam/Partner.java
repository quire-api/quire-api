package io.quire.api.model.externalteam;

import io.quire.api.model.TaggingEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class Partner extends TaggingEntity {

    @ApiModelProperty(
        value = "Display name of the external team.",
        example = "UI design team"
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "Image URL representing this team.",
        example = "https://quire.s3.amazonaws.com/oid/image.jpg",
        position = 2
    )
    public String getImage() { return null; }
}
