package io.quire.api.model.tag;

import io.quire.api.model.TaggingEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class Tag extends TaggingEntity {

    @ApiModelProperty(
        value = "Whether this tag is global (available across projects). May be omitted in responses when false.",
        example = "true"
    )
    public boolean getGlobal() { return false; }
}
