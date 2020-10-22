package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class SimpleTaggingEntity extends NamedEntity {
	@ApiModelProperty(example = "35",
		value = "The color. It is an index of our predefined color palette. "
			+ "The first digit is between 0 and 5, and the second between "
			+ "0 and 7. "
			+ "The color palette can be found in our Quire's color picker.")
	public String getColor() { return null; }
}
