package io.quire.api.model.comment;

import io.quire.api.model.*;
import io.swagger.annotations.*;

import java.util.List;

@ApiModel()
public class Comment extends StampedEntity {
	@ApiModelProperty(value="OID, aka. UUID.", example = "iDsPd.QP_qM.hN.Trymukn8b")
	public String getOid() { return null; }

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

	@ApiModelProperty(value = "When this comment was pinned, "
		+ "or null if not pinned.",
		example = "2018-12-22T02:06:58.158Z", position = 50)
	public String getPinAt() { return null; }
	@ApiModelProperty(value = "The user who pinned this comment, "
		+ "or null if not pinned.",
		position = 50)
	public SimpleIdentity getPinBy() { return null; }

	@ApiModelProperty(value = "When this comment was edited, "
		+ "or null if not edited.",
		example = "2018-12-22T02:06:58.158Z", position = 50)
	public String getEditedAt() { return null; }
	@ApiModelProperty(value = "The user who edited this comment, "
		+ "or null if not edited.",
		position = 50)
	public SimpleIdentity getEditedBy() { return null; }

	@ApiModelProperty(value = "Url of this comment on Quire website.",
		position = 50)
	public String getUrl() { return null; }

	@ApiModelProperty(value="The object this comment was added to.",
		position = 99)
	public CommentOwner getOwner() { return null; }
}
