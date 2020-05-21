package io.quire.api.model.externalteam;

import io.quire.api.model.TaggingEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel()
public class Partner extends TaggingEntity {
    @ApiModelProperty(value = "The name.", example = "UI design team")
    public String getName() { return null; }
    @ApiModelProperty(example = "https://quire.s3.amazonaws.com/oid/image.jpg",
    	position = 2)
    public String getImage() { return null; }
}
