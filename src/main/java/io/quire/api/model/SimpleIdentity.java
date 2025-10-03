package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class SimpleIdentity extends NamedIconEntity {

    @ApiModelProperty(
        value = "Identifier for this record.",
        example = "my_id"
    )
    public String getId() { return null; }

    @ApiModelProperty(
        value = "URL of this record on the Quire website.",
        example = "https://quire.io/u/my_id"
    )
    public String getUrl() { return null; }
}
