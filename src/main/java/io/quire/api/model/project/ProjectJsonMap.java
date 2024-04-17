package io.quire.api.model.project;

import io.swagger.annotations.*;

@ApiModel()
public class ProjectJsonMap {
    @ApiModelProperty(value="OID, aka. UUID.", example = "Dyh2YkFcu9uLgLFIeN1kB4Ld")
    public String getOid() { return null; }

    @ApiModelProperty(value="ID.",
        example = "My_Project")
    public String getId() { return null; }

    @ApiModelProperty(value="Name.",
        example = "My Project")
    public String getName() { return null; }
}
