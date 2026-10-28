package io.quire.api.model.comment;

import io.swagger.annotations.ApiModelProperty;

public class CreateCommentBody {

    @ApiModelProperty(
        value = "Content of the new comment (Markdown supported). An embedded `data:` image is rejected — upload the image as an attachment instead.",
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
              + "Read `asUser` as *app-as-user*: the app itself is the "
              + "user, not the authorizing user. "
              + "Default: false (created by the authorizing user). "
              + "Either way, the authorizing user, not the app, follows "
              + "the task or chat commented on.",
        example = "true",
        required = false
    )
    public boolean getAsUser() { return false; }
}
