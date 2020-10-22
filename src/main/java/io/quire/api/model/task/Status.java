package io.quire.api.model.task;

import io.swagger.annotations.*;

@ApiModel()
class Status {
    @ApiModelProperty(value="Name.", example = "Completed")
    public String getName() { return null; }

	@ApiModelProperty(example = "35",
		value = "The color. It is an index of our predefined color palette. "
			+ "The first digit is between 0 and 5, and the second between "
			+ "0 and 7. "
			+ "The color palette can be found in our Quire's color picker.")
	public String getColor() { return null; }

	@ApiModelProperty(value = "The status. "
		+ "Its value must be between 0 and 100. If 100 or more, it means completed.",
		example = "0", position = 4)
	public int getValue() { return 0; }
}
