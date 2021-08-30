package io.quire.api.model.notification;

import io.swagger.annotations.ApiModelProperty;

public class CreateNotificationBody {
	@ApiModelProperty(example = "Unable to synchronize",
		value = "The message", required = true)
	public String getMessage() { return null; }

	@ApiModelProperty(example = "https://superheros.com/sync",
		value = "(Optional) The URL of the message."
		+ "If not omitted, a hyperlink will be generated to enclose the message.",
		required = false)
	public String getUrl() { return null; }
}
