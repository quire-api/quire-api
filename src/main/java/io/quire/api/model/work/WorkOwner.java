package io.quire.api.model.work;

import io.quire.api.model.*;
import io.swagger.annotations.*;

@ApiModel()
public class WorkOwner extends SimpleIdentity {
	@ApiModelProperty(value = "The type of this object.", example = "Project")
	public String getType() { return null; }

	@ApiModelProperty(value = "Url of this record on Quire website.",
		example = "https://quire.io/w/prj_id")
	public String getUrl() { return null; }
}
