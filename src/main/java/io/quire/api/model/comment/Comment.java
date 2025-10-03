package io.quire.api.model.comment;

import io.quire.api.model.*;
import io.swagger.annotations.*;

import java.util.List;

public class Comment extends StampedEntity {

    @ApiModelProperty(
        value = "Object identifier (OID), a UUID-like unique string.",
        example = "iDsPd.QP_qM.hN.Trymukn8b"
    )
    public String getOid() { return null; }

    @ApiModelProperty(
        value = "Comment content (Markdown supported).",
        example = "It is *cool*!",
        position = 1
    )
    public String getDescription() { return null; }

    @ApiModelProperty(
        value = "Comment content with Markdown removed.",
        example = "It is cool!",
        position = 1
    )
    public String getDescriptionText() { return null; }

    @ApiModelProperty(
        value = "Comment content rendered as an HTML fragment converted from Markdown.",
        example = "It is <i>cool</i>!",
        position = 1
    )
    public String getDescriptionHtml() { return null; }

    @ApiModelProperty(
        value = "Attachments associated with this comment.",
        position = 8
    )
    public List<Attachment> getAttachments() { return null; }

    @ApiModelProperty(
        value = "Timestamp (UTC, ISO 8601) when this comment was pinned, or null if not pinned.",
        example = "2018-12-22T02:06:58.158Z",
        position = 50
    )
    public String getPinAt() { return null; }

    @ApiModelProperty(
        value = "User who pinned this comment, or null if not pinned.",
        position = 50
    )
    public SimpleIdentity getPinBy() { return null; }

    @ApiModelProperty(
        value = "Timestamp (UTC, ISO 8601) when this comment was last edited, or null if not edited.",
        example = "2018-12-22T02:06:58.158Z",
        position = 50
    )
    public String getEditedAt() { return null; }

    @ApiModelProperty(
        value = "User who last edited this comment, or null if not edited.",
        position = 50
    )
    public SimpleIdentity getEditedBy() { return null; }

    @ApiModelProperty(
        value = "URL of this comment on the Quire website.",
        position = 50
    )
    public String getUrl() { return null; }

    @ApiModelProperty(
        value = "Object this comment was added to.",
        position = 99
    )
    public CommentOwner getOwner() { return null; }
}
