package io.quire.api.model.project;

import io.quire.api.model.IconStampedEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Project extends IconStampedEntity {

    @ApiModelProperty(example = "GAUpZARpeOjlHqEux6IdUZI1")
    public String getOid() {
        return null;
    }

    @ApiModelProperty(example = "Marketing_Project", position = 1)
    public String getId() {
        return null;
    }

    @ApiModelProperty(example = "Marketing Project", position = 2)
    public String getName() { return null; }

    @ApiModelProperty(example = "MP", position = 2)
    public String getInitials() {
        return null;
    }

    @ApiModelProperty(example = "Detail about this project", position = 2)
    public String getDescription() { return null; }

}
