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
        value = "Absolute URL of this attachment. A file stored in Quire "
              + "is under `https://quire.io/att/`; a file from an external "
              + "storage, such as Google Drive, links to that service.",
        example = "https://quire.io/att/Ta/sdcQOGgeUtyaFFzb9p0IwAgi/qfqVmUtC/image.png"
    )
    public String getUrl() { return null; }

    @ApiModelProperty(
        value = "Size of the attachment in bytes.",
        example = "20000"
    )
    public int getLength() { return 0; }
}
