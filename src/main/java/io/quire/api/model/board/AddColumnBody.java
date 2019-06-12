package io.quire.api.model.board;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class AddColumnBody extends CreateColumnBody {
	@ApiModelProperty(value = "(Optional) The value of the column that "
		+ "this new column needs to be added before. "
		+ "If specified, the new column will be put before the specified column.")
	public int getBefore() { return 0; }
}
