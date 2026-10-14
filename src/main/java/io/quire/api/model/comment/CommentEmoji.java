package io.quire.api.model.comment;

import io.quire.api.model.SimpleIdentity;
import io.swagger.annotations.*;

import java.util.List;

@ApiModel(description = "An emoji reaction to a comment, with the users who reacted with it.")
public class CommentEmoji {

    @ApiModelProperty(
        value = "The emoji, as a Unicode character. To add or remove it, "
              + "pass this character or its name (see Emojis) to "
              + "`POST /comment/add-emoji/{commentOid}/{emojis}` or "
              + "`DELETE /comment/remove-emoji/{commentOid}/{emojis}`.",
        example = "👍"
    )
    public String getEmoji() { return null; }

    @ApiModelProperty(
        value = "Users who reacted with this emoji.",
        position = 1
    )
    public List<SimpleIdentity> getUsers() { return null; }
}
