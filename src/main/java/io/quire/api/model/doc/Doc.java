package io.quire.api.model.doc;

import io.quire.api.model.work.*;
import io.swagger.annotations.*;

@ApiModel()
public class Doc extends Work {
	@Override
	@ApiModelProperty(value = "Url of this record on Quire website.",
			example = "https://quire.io/w/my_project?doc=Highlight101")
	public String getUrl() { return null; }

	@Override
	@ApiModelProperty(value="The project this document belongs to.",
		position = 99)
	public DocOwner getOwner() { return null; }
}
