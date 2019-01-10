package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Attachment extends StampedEntity {
    @ApiModelProperty(example = "file.zip")
    public String getName() {
        return null;
    }

    @ApiModelProperty(example = "https://quire.io/att/Ta/sdcQOGgeUtyaFFzb9p0IwAgi/qfqVmUtC/image.png")
    public String getUrl() {
        return null;
    }

    @ApiModelProperty(example = "2048")
    public Integer getLength() { return null; }
}
