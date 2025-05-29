package io.quire.api.model;

import io.swagger.annotations.*;

@ApiModel()
public class SimpleEntityWithId extends Entity {
	@ApiModelProperty(value = "ID.", example = "my_id")
	public String getId() { return null; }
}
