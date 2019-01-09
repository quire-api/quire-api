package io.quire.api.model.comment;

import io.swagger.annotations.ApiModelProperty;

public class UpdateCommentBody {
    @ApiModelProperty(example = "Adjust style", required = true)
    public String getDescription() { return null; }

    @ApiModelProperty(example = "false")
    public boolean getPinned() { return false; }
}
