package io.quire.api.model.board;

import io.quire.api.model.Identity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Board extends Identity {
	@ApiModelProperty(value = "The column defintions of this board.",
		position = 10)
	public List<Column> getColumns() { return null; }

	@ApiModelProperty(value = "OID of the external team that this board belongs to. "
		+ "It is null if this board can't be accessed by a member of external teams.",
		example = "rcBHBYXZSiyDRrHrWPutatfF", position = 15)
	public String getPartner() { return null; }

	@ApiModelProperty(value = "When this board was archived. "
		+ "It is null if not archived.",
		example = "2020-02-22T02:06:58.158Z", position = 50)
	public String getArchivedAt() { return null; }

	@ApiModelProperty(value = "When this board was aimed to complete, "
		+ "or null if not specified.",
		example = "2020-01-22T02:06:58.158Z", position = 50)
	public String getDue() { return null; }

	@ApiModelProperty(value="OID of the project this board belongs to.",
		example = "Dyh2YkFcu9uLgLFIeN1kB4Ld", position = 99)
	public String getProject() { return null; }

    @ApiModelProperty(value = "When this record was created.", example = "2018-12-22T02:06:58.158Z", position = 99)
    public String getCreatedAt() { return null; }
    @ApiModelProperty(value = "OID of the user who created this record.", example = "Dyh2YkFcu9uLgLFIeN1kB4Ld", position = 99)
    public String getCreatedBy() { return null; }
}
