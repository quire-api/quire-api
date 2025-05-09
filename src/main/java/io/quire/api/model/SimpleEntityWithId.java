package io.quire.api.model;

import io.quire.api.model.*;
import io.swagger.annotations.*;

import java.util.List;

@ApiModel()
public class SimpleEntityWithId extends Entity {
	@ApiModelProperty(value = "ID.", example = "my_id")
	public String getId() { return null; }
}
