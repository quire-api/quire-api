package io.quire.api.model.chat;

import io.quire.api.model.*;
import io.quire.api.model.work.*;
import io.swagger.annotations.*;

@ApiModel()
public class Chat extends Work {
	@Override
	@ApiModelProperty(value = "Url of this record on Quire website.",
			example = "https://quire.io/w/my_project?chat=Highlight101")
	public String getUrl() { return null; }

	@Override
	@ApiModelProperty(value="The project this chat channel belongs to.",
		position = 99)
	public ChatOwner getOwner() { return null; }
}
