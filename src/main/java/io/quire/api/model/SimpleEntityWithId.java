package io.quire.api.model;

import io.swagger.annotations.*;

public class SimpleEntityWithId extends Entity {

    @ApiModelProperty(
        value = "Identifier for this record.",
        example = "my_id"
    )
    public String getId() { return null; }
}
