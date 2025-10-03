package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class NamedEntity extends Entity {

    @ApiModelProperty(
        value = "Display name.",
        example = "Foo"
    )
    public String getName() { return null; }
}
