package io.quire.api.model.comment;

import io.quire.api.model.NamedEntity;
import io.swagger.annotations.*;

public class CommentOwner extends NamedEntity {

    @ApiModelProperty(
        value = "ID of the owner: for a task, its ID (an integer, unique "
              + "within its project); for a chat channel, its ID (a string).",
        example = "42"
    )
    public Object getId() { return null; }

    @ApiModelProperty(
        value = "Type of the owner: `Task` or `Chat`.",
        example = "Task",
        allowableValues = "Task, Chat"
    )
    public String getType() { return null; }

    @ApiModelProperty(
        value = "URL of this owner on the Quire website.",
        example = "https://quire.io/w/my_project/42"
    )
    public String getUrl() { return null; }
}
