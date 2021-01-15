package io.quire.api.model.sublist;

import io.quire.api.model.*;
import io.swagger.annotations.*;

import java.util.List;

@ApiModel()
public class Sublist extends Identity {
	@ApiModelProperty(value="Name.",
		example = "Sublist 101")
	public String getName() { return null; }

	@ApiModelProperty(value = "Name but excluding markdown characters.",
		example = "Sublist 101")
	public String getNameText() { return null; }
	@ApiModelProperty(value = "Name in a form of a HTML fragment converted from markdown.",
		example = "Sublist 101")
	public String getNameHtml() { return null; }

	@ApiModelProperty(value = "ID.", example = "Sublist101")
	public String getId() { return null; }

	@ApiModelProperty(value = "Url of this record on Quire website.",
			example = "https://quire.io/w/my_project?sublist=Sublist101")
	public String getUrl() { return null; }

	@ApiModelProperty(value = "The external team that this sublist belongs to. "
		+ "It is null if this sublist can't be accessed by a member of external teams.",
		position = 15)
	public SimpleTaggingEntity getPartner() { return null; }

	@ApiModelProperty(value = "When this sublist was archived. "
		+ "It is null if not archived.",
		example = "2020-02-22T02:06:58.158Z", position = 50)
	public String getArchivedAt() { return null; }

	@ApiModelProperty(value = "When this sublist was aimed to complete, "
		+ "or null if not specified.",
		example = "2020-01-22T02:06:58.158Z", position = 50)
	public String getDue() { return null; }

	@ApiModelProperty(value="The project this sublist belongs to.",
		position = 99)
	public SublistOwner getOwner() { return null; }

    @ApiModelProperty(value = "When this record was created.", example = "2018-12-22T02:06:58.158Z", position = 99)
    public String getCreatedAt() { return null; }
    @ApiModelProperty(value = "The user who created this record.",
    	position = 99)
    public SimpleIdentity getCreatedBy() { return null; }
}
