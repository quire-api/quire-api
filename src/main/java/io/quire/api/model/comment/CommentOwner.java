package io.quire.api.model.comment;

import io.quire.api.model.Attachment;
import io.quire.api.model.StampedEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class CommentOwner extends NamedEntity {
    @ApiModelProperty(value = "The type of this object.", example = "Project")
    public String getType() { return null; }

	@ApiModelProperty(value = "Url of this record on Quire website.",
		example = "https://quire.io/u/my_id")
	public String getUrl() { return null; }
}
