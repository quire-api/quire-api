package io.quire.api.model.comment;

import io.quire.api.model.User;
import io.swagger.annotations.ApiModelProperty;

public class Comment {

    @ApiModelProperty(example = "Adjust style")
    public String getDescription() { return null; }

    @ApiModelProperty(example = "false")
    public boolean getPinned() { return false; }

    @ApiModelProperty(example = "2018-12-22T02:06:58.158Z")
    public String getCreatedAt() { return null; }

    public User getCreatedBy() { return null; }
}
