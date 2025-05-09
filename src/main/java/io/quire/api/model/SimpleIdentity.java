package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class SimpleIdentity extends NamedIconEntity {
	@ApiModelProperty(value = "ID.", example = "my_id")
	public String getId() { return null; }

	@ApiModelProperty(value = "Url of this record on Quire website.",
		example = "https://quire.io/u/my_id")
	public String getUrl() { return null; }
}
