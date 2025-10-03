package io.quire.api.model.chat;

import io.quire.api.model.work.*;
import io.swagger.annotations.*;

public class Chat extends Work {

    @Override
    @ApiModelProperty(
        value = "URL of this chat on the Quire website.",
        example = "https://quire.io/w/my_project?chat=Highlight101"
    )
    public String getUrl() { return null; }

    @Override
    @ApiModelProperty(
        value = "Project this chat channel belongs to.",
        position = 99
    )
    public ChatOwner getOwner() { return null; }
}
