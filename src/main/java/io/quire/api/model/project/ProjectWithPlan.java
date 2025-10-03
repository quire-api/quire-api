package io.quire.api.model.project;

import io.quire.api.model.*;
import io.swagger.annotations.*;

import java.util.List;

public class ProjectWithPlan extends Project {

    @ApiModelProperty(
        value = "Subscription details for this project.",
        position = 9
    )
    public Subscription getSubscription() { return null; }
}
