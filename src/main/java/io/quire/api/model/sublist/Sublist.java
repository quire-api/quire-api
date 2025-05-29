package io.quire.api.model.sublist;

import io.quire.api.model.work.*;
import io.swagger.annotations.*;

@ApiModel()
public class Sublist extends Work {
	@Override
	@ApiModelProperty(value = "Url of this record on Quire website.",
			example = "https://quire.io/w/my_project?sublist=Highlight101")
	public String getUrl() { return null; }

	@Override
	@ApiModelProperty(value="The project this sublist belongs to.",
		position = 99)
	public SublistOwner getOwner() { return null; }
}
