package io.quire.api.model.externalteam;

import io.quire.api.model.TaggingEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel()
public class ExternalTeam extends TaggingEntity {
    @ApiModelProperty(example = "Super Rock Inc.", position = 2)
    public String getName() { return null; }

    @ApiModelProperty(example = "#a9cb77", position = 2)
    public String getIconColor() { return null; }

    @ApiModelProperty(example = "https://quire.s3.amazonaws.com/oid/image.jpg", position = 2)
    public String getImage() { return null; }
}
