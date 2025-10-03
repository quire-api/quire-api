package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class SimpleAttachment {

    @ApiModelProperty(
        value = "File name of the attachment.",
        example = "file.txt"
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "Direct URL to access this attachment.",
        example = "https://quire.io/att/Ta/sdcQOGgeUtyaFFzb9p0IwAgi/qfqVmUtC/image.png"
    )
    public String getUrl() { return null; }

    @ApiModelProperty(
        value = "Size of the attachment in bytes.",
        example = "20000"
    )
    public int getLength() { return 0; }
}
