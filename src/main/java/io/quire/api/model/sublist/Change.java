package io.quire.api.model.sublist;

import io.quire.api.model.NamedEntity;
import io.swagger.annotations.*;

@ApiModel()
public class Change {
	@ApiModelProperty(value = "The task's OID to be included or excluded.", required = true,
		example = "2MmYOpJH_ZLeehIjjytH1Rwr")
	public String getTask() { return null; }

	@ApiModelProperty(value = "Whether to exclude the task.\n"
		+ "Default: false (include).",
		example = "false")
	public Boolean getExclude() { return false; }

	@ApiModelProperty(value = "Whether to include or exclude only the task.\n"
		+ "Default: false -- all descendants tasks will be included or excluded too.\n"
		+ "Note: it won't affect descendants that are explicitly excluded or included "
		+ "before this invocation.",
		example = "false")
	public Boolean getSingle() { return false; }
}
