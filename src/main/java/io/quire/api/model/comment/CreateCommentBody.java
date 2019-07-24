package io.quire.api.model.comment;

import io.swagger.annotations.ApiModelProperty;

public class CreateCommentBody {
    @ApiModelProperty(value = "The content of the new comment.",
    	example = "Adjust style", required = true)
    public String getDescription() { return null; }

	@ApiModelProperty(value = "(Optional) Whether to pin this comment. "
		+ "Default: false", example = "false", required = false)
	public boolean getPinned() { return false; }

	@ApiModelProperty(example = "true",
		value = "(Optional) Specify true if you'd like to make "
		+ "this new comment as created by the app.\n"
		+ "Default: false -- the comment is marked as created by the user authorizing "
		+ "the app.")
	public boolean getAsUser() { return false; }
}
