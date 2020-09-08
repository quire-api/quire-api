package io.quire.api.model.status;

import io.swagger.annotations.ApiModelProperty;

public class CreateStatusBody {
	@ApiModelProperty(example = "Doing",
		value = "The name of the status", required = true)
	public String getName() { return null; }

	@ApiModelProperty(example = "true",
		value = "The value. It is a non-negative value to indicate the progress. "
		+ "The value must be unique. "
		+ "If it is great or equals to 100, it means the task has been completed.",
		required = true)
	public int getValue() { return 0; }

	@ApiModelProperty(example = "35",
		value = "(Optional) The color of the status."
		+ "If not omitted, a color will be generated automatially.",
		required = false)
	public String getColor() { return null; }
}
