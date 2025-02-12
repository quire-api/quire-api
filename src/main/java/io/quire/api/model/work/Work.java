package io.quire.api.model.work;

import io.quire.api.model.*;
import io.swagger.annotations.*;

@ApiModel()
public class Work extends Identity {
	@ApiModelProperty(value="Name.",
		example = "Highlight 101")
	public String getName() { return null; }

	@ApiModelProperty(value = "Name but excluding markdown characters.",
		example = "Highlight 101")
	public String getNameText() { return null; }
	@ApiModelProperty(value = "Name in a form of a HTML fragment converted from markdown.",
		example = "Highlight 101")
	public String getNameHtml() { return null; }

	@ApiModelProperty(value = "ID.", example = "Highlight101")
	public String getId() { return null; }

	@ApiModelProperty(value = "Url of this record on Quire website.",
			example = "https://quire.io/w/my_project?sublist=Highlight101")
	public String getUrl() { return null; }

	@ApiModelProperty(value = "The external team that this record belongs to. "
		+ "It is null if this record can't be accessed by a member of external teams.",
		position = 15)
	public SimpleTaggingEntity getPartner() { return null; }

	@ApiModelProperty(value = "When this record was archived. "
		+ "It is null if not archived.",
		example = "2020-02-22T02:06:58.158Z", position = 50)
	public String getArchivedAt() { return null; }

	@ApiModelProperty(value = "When this record was aimed to start, "
		+ "or null if not specified.",
		example = "2024-01-02", position = 50)
	public String getStart() { return null; }

	@ApiModelProperty(value = "When this record was aimed to complete, "
		+ "or null if not specified.",
		example = "2024-05-25", position = 50)
	public String getDue() { return null; }

	@ApiModelProperty(value="The project this record belongs to.",
		position = 99)
	public WorkOwner getOwner() { return null; }

    @ApiModelProperty(value = "When this record was created.", example = "2018-12-22T02:06:58.158Z", position = 99)
    public String getCreatedAt() { return null; }
    @ApiModelProperty(value = "The user who created this record.",
    	position = 99)
    public SimpleIdentity getCreatedBy() { return null; }
}
