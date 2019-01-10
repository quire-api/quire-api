package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class TaggingEntity extends StampedEntity {
    @ApiModelProperty(example = "#a9cb77")
    public String getColor() {
        return null;
    }
}
