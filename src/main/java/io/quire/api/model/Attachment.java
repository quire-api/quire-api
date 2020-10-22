package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Attachment {
    @ApiModelProperty(example = "2048",
        value = "The type of this attachment."
        + "It is 1 if it is from Google Drive. "
        + "It is 2 if it is stored in Quire.")
    public int getType() { return 0; }

    @ApiModelProperty(example = "file.zip",
        value = "Attachment's name.")
    public String getName() { return null; }

    @ApiModelProperty(example = "https://quire.io/att/Ta/sdcQOGgeUtyaFFzb9p0IwAgi/qfqVmUtC/image.png",
        value = "URL of this attachment.")
    public String getUrl() { return null; }

    @ApiModelProperty(example = "2048",
        value = "The size of this attachment. Unit: bytes.")
    public int getLength() { return 0; }

    @ApiModelProperty(value = "When this record was created.", example = "2018-12-22T02:06:58.158Z", position = 99)
    public String getCreatedAt() { return null; }
    @ApiModelProperty(value = "The user who created this record.",
        example = "{\"oid\": \"rcBHBYXZSiyDRrHrWPutatfF\", \"name\": \"John\"}",
        position = 99)
    public SimpleIdentity getCreatedBy() { return null; }
}
