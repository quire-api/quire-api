package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class NamedEntity extends Entity {
    @ApiModelProperty(value="Name.", example = "Foo")
    public String getName() { return null; }
}
