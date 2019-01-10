package io.quire.api.model.comment;

import io.quire.api.model.StampedEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Comment extends StampedEntity {

    @ApiModelProperty(example = "tvax2wFqqiUDqiVF7YSUlQ8n")
    public String getOid() {
        return null;
    }

    @ApiModelProperty(example = "Adjust style")
    public String getDescription() { return null; }

    @ApiModelProperty(example = "false")
    public boolean getPinned() { return false; }

}
