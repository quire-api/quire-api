package io.quire.api.model.insight;

import io.quire.api.model.work.*;
import io.swagger.annotations.*;

public class Insight extends Work {

    @Override
    @ApiModelProperty(
        value = "URL of this insight view on the Quire website.",
        example = "https://quire.io/w/my_project?insight=View101"
    )
    public String getUrl() { return null; }

    @Override
    @ApiModelProperty(
        value = "Owner this insight belongs to.",
        position = 99
    )
    public InsightOwner getOwner() { return null; }
}
