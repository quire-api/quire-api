package io.quire.api.model.comment;

import io.quire.api.model.NamedEntity;
import io.swagger.annotations.*;

public class CommentOwner extends NamedEntity {

    @ApiModelProperty(
        value = "Type of the owning object (e.g., Project).",
        example = "Project"
    )
    public String getType() { return null; }

    @ApiModelProperty(
        value = "URL of this owner on the Quire website.",
        example = "https://quire.io/w/my_id"
    )
    public String getUrl() { return null; }
}
