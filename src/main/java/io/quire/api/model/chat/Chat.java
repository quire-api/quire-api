package io.quire.api.model.chat;

import io.quire.api.model.*;
import io.quire.api.model.work.*;
import io.swagger.annotations.*;

import java.util.List;

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

    @ApiModelProperty(
        value = "The list of users following this chat channel.",
        position = 60
    )
    public List<SimpleIdentity> getFollowers() { return null; }

    @ApiModelProperty(
        value = "The list of users who have muted this chat channel and will not "
              + "receive notifications about it.",
        position = 60
    )
    public List<SimpleIdentity> getMutes() { return null; }
}
