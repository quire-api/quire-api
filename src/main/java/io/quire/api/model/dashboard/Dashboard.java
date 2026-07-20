package io.quire.api.model.dashboard;

import io.quire.api.model.work.*;
import io.swagger.annotations.*;

public class Dashboard extends Work {

    @Override
    @ApiModelProperty(
        value = "URL of this dashboard on the Quire website.",
        example = "https://quire.io/w/my_project?dashboard=Board101"
    )
    public String getUrl() { return null; }

    @Override
    @ApiModelProperty(
        value = "Owner this dashboard belongs to.",
        position = 99
    )
    public DashboardOwner getOwner() { return null; }
}
