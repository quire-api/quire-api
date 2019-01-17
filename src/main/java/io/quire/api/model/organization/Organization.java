package io.quire.api.model.organization;

import io.quire.api.model.IconStampedEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Organization extends IconStampedEntity {

    @ApiModelProperty(example = "Dyh2YkFcu9uLgLFIeN1kB4Ld")
    public String getOid() { return null; }

    @ApiModelProperty(example = "Develop_group", position = 1)
    public String getId() { return null; }

    @ApiModelProperty(example = "Develop group", position = 2)
    public String getName() { return null; }

    @ApiModelProperty(example = "DG", position = 2)
    public String getInitials() { return null; }

    @ApiModelProperty(example = "Detail about this organization", position = 2)
    public String getDescription() { return null; }
}
