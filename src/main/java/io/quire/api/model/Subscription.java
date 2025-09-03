package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Subscription {
	@ApiModelProperty(value = "The plan, such as Free, Professional, Premium and Enterprise.",
		example = "Professional")
	public String getPlan() { return null; }

	@ApiModelProperty(value = "When this subscription is due.",
		example = "2038-02-22T02:06:58Z")
	public String getDue() { return null; }

	@ApiModelProperty(
		value = "Whether this subscription is expired.",
		example = "false")
	public boolean getExpired() { return false; }
}
