package io.quire.api.model.board;

import io.quire.api.model.Identity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class CreateBoardBody {
	@ApiModelProperty(example = "Board101",
		value = "(Optional) ID of the board. "
		+ "If omitted, Quire will generate one automatically. "
		+ "It must be unquie in the whole project if specified")
	public String getId() { return null; }

	@ApiModelProperty(example = "Board 101",
		value = "The name of the board.", required = true)
	public String getName() { return null; }

	@ApiModelProperty(example = "**Great** board to start with.",
		value = "(Optional) An optional description about this task.")
	public String getDescription() { return null; }

	@ApiModelProperty(value = "(Optional) The column defintions of this board. "
		+ "If omitted, the default column defintions will be generated. "
		+ "If specified, it must have at least one column whose status is 100.")
	public List<CreateColumnBody> getColumns() { return null; }

	@ApiModelProperty(value = "(Optional) OID of the external team that this board belongs to. "
		+ "If the current user is a member of an external team, this field is ignored. ",
		example = "rcBHBYXZSiyDRrHrWPutatfF", position = 15)
	public String getPartner() { return null; }

	@ApiModelProperty(value = "(Optional) When this board was aimed to complete. ",
		example = "2020-01-22T02:06:58.158Z", position = 50)
	public String getDue() { return null; }
}
