package io.quire.api.model.project;

import io.swagger.annotations.*;

public class CreateProjectBody {
    @ApiModelProperty(example = "New Project")
    public String getName() {
        return null;
    }

    @ApiModelProperty(example = "Develop_group")
    public String getOrganization() {
        return null;
    }
}
