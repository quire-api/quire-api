package io.quire.api.model.comment;

import io.swagger.annotations.ApiModelProperty;

public class CreateCommentBody {
    @ApiModelProperty(value = "The content of the new comment.",
    	example = "Adjust style", required = true)
    public String getDescription() { return null; }
}
