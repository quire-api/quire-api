package io.quire.api.model.project;

import io.quire.api.model.IconStampedEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Project extends IconStampedEntity {
    @ApiModelProperty(example = "Marketing_Project")
    public String getId() {
        return null;
    }

    @ApiModelProperty(example = "GAUpZARpeOjlHqEux6IdUZI1")
    public String getOid() {
        return null;
    }

    @ApiModelProperty(example = "Marketing Project")
    public String getName() { return null; }

    @ApiModelProperty(example = "MP")
    public String getInitials() {
        return null;
    }

}
