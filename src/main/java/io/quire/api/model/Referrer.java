package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Referrer {
	@ApiModelProperty(value = "OID of the task that refers another task."
		example = "wrSpgghWFCzPHBqiShSurDeD")
	public String getTask() { return null; }

	@ApiModelProperty(value = "OID of the user who made this reference.",
		example = "wrSpgghWFCzPHBqiShSurDeD")
	public String getUser() {
		return null;
	}

	@ApiModelProperty(value = "When this reference is made.",
		example = "2018-12-22T02:06:58.158Z")
	public String getWhen() { return null; }
}
