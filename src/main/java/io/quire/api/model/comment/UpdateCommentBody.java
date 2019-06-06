package io.quire.api.model.comment;

import io.swagger.annotations.ApiModelProperty;

public class UpdateCommentBody {
	@ApiModelProperty(value = "(Optional) The new content of the comment.",
		example = "Adjust style", required = false)
	public String getDescription() { return null; }

	@ApiModelProperty(value = "(Optional) Whether to pin this comment.",
		example = "false", required = false)
	public boolean getPinned() { return false; }
}
