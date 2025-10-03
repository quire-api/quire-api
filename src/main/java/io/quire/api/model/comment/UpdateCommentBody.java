package io.quire.api.model.comment;

import io.swagger.annotations.ApiModelProperty;

public class UpdateCommentBody {

    @ApiModelProperty(
        value = "(Optional) New comment content (Markdown supported).",
        example = "Adjust style",
        required = false
    )
    public String getDescription() { return null; }

    @ApiModelProperty(
        value = "(Optional) Whether the comment is pinned.",
        example = "false",
        required = false
    )
    public boolean getPinned() { return false; }
}
