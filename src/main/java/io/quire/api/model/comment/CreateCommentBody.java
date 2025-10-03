package io.quire.api.model.comment;

import io.swagger.annotations.ApiModelProperty;

public class CreateCommentBody {

    @ApiModelProperty(
        value = "Content of the new comment (Markdown supported).",
        example = "Adjust style",
        required = true
    )
    public String getDescription() { return null; }

    @ApiModelProperty(
        value = "(Optional) Whether to pin this comment. Default: false.",
        example = "false",
        required = false
    )
    public boolean getPinned() { return false; }

    @ApiModelProperty(
        value = "(Optional) If true, marks this comment as created by the app. "
              + "Default: false (created by the authorizing user).",
        example = "true",
        required = false
    )
    public boolean getAsUser() { return false; }
}
