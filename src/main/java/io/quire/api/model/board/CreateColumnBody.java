package io.quire.api.model.board;

import io.quire.api.model.Identity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class CreateColumnBody extends Column {
	@ApiModelProperty(value = "(Optional) ID of the column that this new column needs to be added before. "
		+ "If specified, the new column will be put before the specified column.")
	public String getBefore() { return null; }
}
