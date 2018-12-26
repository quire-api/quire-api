package io.quire.api.model.project;

import io.swagger.annotations.ApiModelProperty;

public class Project {
    @ApiModelProperty(example = "Marketing_Project")
    public String getId() {
        return null;
    }

    @ApiModelProperty(example = "Marketing Project")
    public String getName() { return null; }

    @ApiModelProperty(example = "MP")
    public String getInitials() {
        return null;
    }

    @ApiModelProperty(example = "#a9cb77")
    public String getIconColor() {
        return null;
    }

    @ApiModelProperty(example = "https://quire.s3.amazonaws.com/oid/image.jpg")
    public String getImage() {
        return null;
    }
}
