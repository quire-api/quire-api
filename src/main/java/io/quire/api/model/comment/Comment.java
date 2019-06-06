package io.quire.api.model.comment;

import io.quire.api.model.StampedEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Comment extends StampedEntity {

	@ApiModelProperty(value = "The content.",
		example = "It is *cool*!", position = 1)
	public String getDescription() { return null; }
	@ApiModelProperty(value = "The content but excluding markdown characters.",
		example = "It is cool!", position = 1)
	public String getDescriptionText() { return null; }
	@ApiModelProperty(value = "The content in a form of a HTML fragment converted from markdown.",
		example = "It is <i>cool</i>!", position = 1)
	public String getDescriptionHtml() { return null; }

	@ApiModelProperty(value = "The attachments of this task.", position = 8)
	public List<Attachment> getAttachments() { return null; }

	@ApiModelProperty(value = "When this comment was pinned, ",
		+ "or null if not pinned."
		example = "2018-12-22T02:06:58.158Z", position = 50)
	public String getPinAt() { return null; }
	@ApiModelProperty(value = "OID of the user who pinned this comment, "
		+ "or null if not pinned.",
		example = "rcBHBYXZSiyDRrHrWPutatfF", position = 50)
	public String getPinBy() { return null; }

	@ApiModelProperty(value = "When this comment was edited, ",
		+ "or null if not edited."
		example = "2018-12-22T02:06:58.158Z", position = 50)
	public String getEditedAt() { return null; }
	@ApiModelProperty(value = "OID of the user who edited this comment, "
		+ "or null if not edited.",
		example = "rcBHBYXZSiyDRrHrWPutatfF", position = 50)
	public String getEditedBy() { return null; }

	@ApiModelProperty(value = "Url of this comment on Quire website.",
		example = "https://quire.io/w/my_project70/Cello_and_voilin#comment-iDsPd.QP_qM.hN.Trymukn8b",
		position = 50)
	public String getUrl() { return null; }

	@ApiModelProperty(value="OID of the object this comment was added to.",
		example = "Dyh2YkFcu9uLgLFIeN1kB4Ld", position = 99)
	public String getOwner() { return null; }

	@ApiModelProperty(value="The type of the object this comment was added to. "
		+ "It can be \"Task\" or \"Project\".",
		example = "Task", position = 99)
	public String getOwnerType() { return null; }
}
