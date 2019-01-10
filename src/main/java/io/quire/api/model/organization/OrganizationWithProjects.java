package io.quire.api.model.organization;

import io.quire.api.model.project.Project;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel()
public class OrganizationWithProjects extends Organization {

    public List<Project> getProjects() {
        return null;
    }

}
