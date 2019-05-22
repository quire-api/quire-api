package io.quire.api.model.project;

import io.quire.api.model.Identity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Project extends Identity {
    @ApiModelProperty(value="OID of the organization this project belongs to.", example = "Dyh2YkFcu9uLgLFIeN1kB4Ld")
    public String getOrganization() { return null; }
}
