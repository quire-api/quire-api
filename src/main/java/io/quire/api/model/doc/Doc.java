package io.quire.api.model.doc;

import io.quire.api.model.*;
import io.quire.api.model.work.*;
import io.swagger.annotations.*;

import java.util.List;

public class Doc extends Work {

    @Override
    @ApiModelProperty(
        value = "URL of this document on the Quire website.",
        example = "https://quire.io/w/my_project?doc=Highlight101"
    )
    public String getUrl() { return null; }

    @Override
    @ApiModelProperty(
        value = "Project this document belongs to.",
        position = 99
    )
    public DocOwner getOwner() { return null; }

    @ApiModelProperty(
        value = "The list of users following this document.",
        position = 60
    )
    public List<SimpleIdentity> getFollowers() { return null; }

    @ApiModelProperty(
        value = "The list of users who have muted this document and will not "
              + "receive notifications about it.",
        position = 60
    )
    public List<SimpleIdentity> getMutes() { return null; }
}
