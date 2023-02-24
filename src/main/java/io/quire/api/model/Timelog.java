package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Timelog {
	@ApiModelProperty(value = "When the time log started",
		example = "2023-02-20T00:00:00.000Z", position = 1)
	public String getStart() { return null; }

	@ApiModelProperty(value = "When the time log ended",
		example = "2023-02-20T00:05:35.000Z", position = 2)
	public String getStart() { return null; }

	@ApiModelProperty(value = "The user who did this.",
		position = 3)
	public SimpleIdentity getUser() { return null; }

	@ApiModelProperty(value = "Whether this time log is billable. "
		+ "Omitted if false.",
		example = "true", position = 4)
	public boolean getBillable() { return false; }

	@ApiModelProperty(value = "A note.",
		example = "A piece of cake", position = 5)
	public String getNote() { return null; }
}
