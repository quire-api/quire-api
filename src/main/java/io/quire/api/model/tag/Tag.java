package io.quire.api.model.tag;

import io.quire.api.model.TaggingEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Tag extends TaggingEntity {
	@ApiModelProperty(example = "true",
		value = "Whether this is a global tag. "
		+ "Note: it won't be returned if this is not a global tag.")
	public boolean getGlobal() { return false; }
}
