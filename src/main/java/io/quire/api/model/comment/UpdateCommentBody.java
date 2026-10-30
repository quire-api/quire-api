package io.quire.api.model.comment;

import io.swagger.annotations.ApiModelProperty;

public class UpdateCommentBody {

    @ApiModelProperty(
        value = "(Optional) New comment content (Markdown supported). An embedded `data:` image is rejected — upload the image as an attachment instead.",
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

    @ApiModelProperty(
        value = "(Optional) If true, the app, not the authorizing user, is "
              + "recorded as the one who made this change: `editedBy` when "
              + "`description` is changed, and `pinBy` when the comment is "
              + "pinned. Default: false.",
        example = "true",
        required = false
    )
    public boolean getAsUser() { return false; }
}
