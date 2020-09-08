package io.quire.api.model.status;

import io.swagger.annotations.ApiModelProperty;

public class UpdateStatusBody {
	@ApiModelProperty(example = "Later",
		value = "(Optional) The new name of the status.", required = false)
	public String getName() { return null; }

	@ApiModelProperty(example = "true",
		value = "(Optional) The new value of the status. "
		+ "It is a non-negative value to indicate the progress. "
		+ "The value must be unique. "
		+ "If it is great or equals to 100, it means the task has been completed.",
		required = false)
	public int getValue() { return 0; }

	@ApiModelProperty(example = "35",
		value = "(Optional) The color of the status.", required = false)
	public String getColor() { return null; }
}
