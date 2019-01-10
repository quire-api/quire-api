package io.quire.api.model.organization;

import io.quire.api.model.IconStampedEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Organization extends IconStampedEntity {
    @ApiModelProperty(example = "Develop_group")
    public String getId() { return null; }

    @ApiModelProperty(example = "Dyh2YkFcu9uLgLFIeN1kB4Ld")
    public String getOid() { return null; }

    @ApiModelProperty(example = "Develop group")
    public String getName() { return null; }

    @ApiModelProperty(example = "DG")
    public String getInitials() { return null; }
}
