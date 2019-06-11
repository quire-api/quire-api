package io.quire.api.model.board;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class CreateColumnBody {
	@ApiModelProperty(example = "TODO",
		value = "The name.")
	public String getName() { return null; }

	@ApiModelProperty(value = "The status that this column represents. "
		+ "Its value must be between 0 and 100. If 100, it means completed. "
		+ "There is exactly one column with completed status.",
		example = "0", position = 4)
	public int getValue() { return 0; }

	@ApiModelProperty(example = "35",
		value = "(Optional) The color. It is an index of our predefined color palette. "
			+ "The first digit is between 0 and 5, and the second between "
			+ "0 and 7. "
			+ "The color palette can be found in our Quire's color picker.")
	public String getColor() { return null; }
}
