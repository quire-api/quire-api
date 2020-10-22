package io.quire.api.model.board;

import io.quire.api.model.Identity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel()
public class Board extends Identity {
	@ApiModelProperty(value="Name.",
		example = "Board 101")
	public String getName() { return null; }

	@ApiModelProperty(value = "Name but excluding markdown characters.",
		example = "Board 101")
	public String getNameText() { return null; }
	@ApiModelProperty(value = "Name in a form of a HTML fragment converted from markdown.",
		example = "Board 101")
	public String getNameHtml() { return null; }

	@ApiModelProperty(value = "ID.", example = "Board101")
	public String getId() { return null; }

	@ApiModelProperty(value = "Url of this record on Quire website.",
			example = "https://quire.io/w/my_project?board=Board101")
	public String getUrl() { return null; }

	@ApiModelProperty(value = "The column definitions of this board.",
		position = 10)
	public List<Column> getColumns() { return null; }

	@ApiModelProperty(value = "The external team that this board belongs to. "
		+ "It is null if this board can't be accessed by a member of external teams.",
		example = "{\"oid\": \"rcBHBYXZSiyDRrHrWPutatfF\", \"name\": \"Friends\"}",
		position = 15)
	public SimpleTaggingEntity getPartner() { return null; }

	@ApiModelProperty(value = "When this board was archived. "
		+ "It is null if not archived.",
		example = "2020-02-22T02:06:58.158Z", position = 50)
	public String getArchivedAt() { return null; }

	@ApiModelProperty(value = "When this board was aimed to complete, "
		+ "or null if not specified.",
		example = "2020-01-22T02:06:58.158Z", position = 50)
	public String getDue() { return null; }

	@ApiModelProperty(value="The project this board belongs to.",
		example = "{\"oid\": \"rcBHBYXZSiyDRrHrWPutatfF\", \"name\": \"Foo\"}",
		position = 99)
	public SimpleIdentity getProject() { return null; }

    @ApiModelProperty(value = "When this record was created.", example = "2018-12-22T02:06:58.158Z", position = 99)
    public String getCreatedAt() { return null; }
    @ApiModelProperty(value = "The user who created this record.",
    	example = "{\"oid\": \"rcBHBYXZSiyDRrHrWPutatfF\", \"name\": \"John\"}",
    	position = 99)
    public SimpleIdentity getCreatedBy() { return null; }
}
