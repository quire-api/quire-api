package io.quire.api.model.organization;

import io.quire.api.model.project.Project;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel()
public class Organization {
    @ApiModelProperty(example = "Develop_group")
    public String getId() {
        return null;
    }

    @ApiModelProperty(example = "Develop group")
    public String getName() {
        return null;
    }

    @ApiModelProperty(example = "DG")
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

    public List<Project> getProjects() {
        return null;
    }

}
