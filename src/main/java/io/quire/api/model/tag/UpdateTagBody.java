package io.quire.api.model.tag;

import io.swagger.annotations.ApiModelProperty;

public class UpdateTagBody {
	@ApiModelProperty(example = "Later",
		value = "(Optional) The new name of the tag.", required = false)
	public String getName() { return null; }

	@ApiModelProperty(example = "35",
		value = "(Optional) The color of the tag.", required = false)
	public String getColor() { return null; }

	@ApiModelProperty(example = "true",
		value = "(Optional) Whether this tag is global. "
		+ "If you specify false here, you have to specify \"project\" for "
		+ "what project you'd like to put the tag to.",
		required = false)
	public boolean getGlobal() { return false; }

	@ApiModelProperty(value="(Optional) OID of the project this tag shall "
		+ "be limited to. "
		+ "It is used only if \"global\" is also specified and false. "
		+ "Otherwise, it is simply ignored.",
		example = "Dyh2YkFcu9uLgLFIeN1kB4Ld", required = false)
	public String getProject() { return null; }
}
