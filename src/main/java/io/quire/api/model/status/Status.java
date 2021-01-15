package io.quire.api.model.status;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Status {
	@ApiModelProperty(value = "The name.", example = "Doing")
	public String getName() { return null; }

	@ApiModelProperty(example = "50",
		value = "The value. It is a non-negative value to indicate the progress. "
			+ "The value must be unique. "
			+ "If it is great or equals to 100, it means the task has been completed.")
	public int getValue() { return 0; }

	@ApiModelProperty(example = "35",
		value = "The color. It is an index of our predefined color palette. "
			+ "The first digit is between 0 and 5, and the second between "
			+ "0 and 7. "
			+ "The color palette can be found in our Quire's color picker.")
	public String getColor() { return null; }
}
