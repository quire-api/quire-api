package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class Attachment extends SimpleAttachment {

    @ApiModelProperty(
        value = "Where the attachment is stored: `1` Google Drive, `2` Quire "
              + "storage, `3` OneDrive, `4` Dropbox, `5` Box.",
        example = "2"
    )
    public int getType() { return 0; }

    @ApiModelProperty(
        value = "Creation timestamp in UTC (ISO 8601).",
        example = "2018-12-22T02:06:58.158Z",
        position = 99
    )
    public String getCreatedAt() { return null; }

    @ApiModelProperty(
        value = "User who created this record.",
        position = 99
    )
    public SimpleIdentity getCreatedBy() { return null; }
}
