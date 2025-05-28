package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class SimpleAttachment {
    @ApiModelProperty(example = "file.txt",
        value = "Attachment's name.")
    public String getName() { return null; }

    @ApiModelProperty(example = "https://quire.io/att/Ta/sdcQOGgeUtyaFFzb9p0IwAgi/qfqVmUtC/image.png",
        value = "URL of this attachment.")
    public String getUrl() { return null; }

    @ApiModelProperty(example = "20000",
        value = "The size of this attachment. Unit: bytes.")
    public int getLength() { return 0; }
}
