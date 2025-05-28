package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Attachment extends SimpleAttachment {
    @ApiModelProperty(example = "1",
        value = "The type of this attachment."
        + "It is 1 if it is from Google Drive. "
        + "It is 2 if it is stored in Quire.")
    public int getType() { return 0; }

    @ApiModelProperty(value = "When this record was created.", example = "2018-12-22T02:06:58.158Z", position = 99)
    public String getCreatedAt() { return null; }
    @ApiModelProperty(value = "The user who created this record.",
        position = 99)
    public SimpleIdentity getCreatedBy() { return null; }
}
