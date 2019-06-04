package io.quire.api.model.tag;

import io.swagger.annotations.ApiModelProperty;

public class CreateTagBody {
	@ApiModelProperty(example = "Later",
		value = "The name of the tag", required = true)
	public String getName() { return null; }

	@ApiModelProperty(example = "",
		value = "(Optional) The color of the tag."
		+ "If not omitted, a color will be generated automatially.",
		required = false)
	public String getColor() { return null; }

	@ApiModelProperty(example = "true",
		value = "(Optional) Whether this tag is global. "
		+ "If omitted, it is not glboal.", required = false)
	public boolean getGlobal() { return false; }
}
