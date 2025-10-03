package io.quire.api.model.work;

import io.quire.api.model.*;
import io.swagger.annotations.*;

public class WorkOwner extends SimpleIdentity {

    @ApiModelProperty(
        value = "Type of the owning object (e.g., Project).",
        example = "Project"
    )
    public String getType() { return null; }

    @ApiModelProperty(
        value = "URL of this owner on the Quire website.",
        example = "https://quire.io/w/prj_id"
    )
    public String getUrl() { return null; }
}
