package io.quire.api.model.comment;

import io.quire.api.model.NamedEntity;
import io.swagger.annotations.*;

@ApiModel()
public class CommentOwner extends NamedEntity {
    @ApiModelProperty(value = "The type of this object.", example = "Project")
    public String getType() { return null; }

	@ApiModelProperty(value = "Url of this record on Quire website.",
		example = "https://quire.io/u/my_id")
	public String getUrl() { return null; }
}
