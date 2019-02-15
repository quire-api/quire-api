package io.quire.api.model.project;

import io.swagger.annotations.*;

public class CreateProjectBody {
    @ApiModelProperty(example = "New Project")
    public String getName() {
        return null;
    }

    @ApiModelProperty(example = "Dyh2YkFcu9uLgLFIeN1kB4Ld", required = true,
        value = "The OID of the organization the new project belongs to.")
    public String getOrganization() {
        return null;
    }
}
