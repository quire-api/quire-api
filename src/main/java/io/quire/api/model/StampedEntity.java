package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class StampedEntity extends Entity {
    @ApiModelProperty(value = "When this record was created.", example = "2018-12-22T02:06:58.158Z", position = 99)
    public String getCreatedAt() { return null; }
    @ApiModelProperty(value = "The user who created this record.", example = "Dyh2YkFcu9uLgLFIeN1kB4Ld", position = 99)
    public SimpleIdentity getCreatedBy() { return null; }
}
