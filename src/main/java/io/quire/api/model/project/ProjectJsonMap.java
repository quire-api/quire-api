package io.quire.api.model.project;

import io.swagger.annotations.*;

public class ProjectJsonMap {

    @ApiModelProperty(
        value = "Object identifier (OID), a UUID-like unique string.",
        example = "Dyh2YkFcu9uLgLFIeN1kB4Ld"
    )
    public String getOid() { return null; }

    @ApiModelProperty(
        value = "Project ID.",
        example = "My_Project"
    )
    public String getId() { return null; }

    @ApiModelProperty(
        value = "Display name of the project.",
        example = "My Project"
    )
    public String getName() { return null; }
}
