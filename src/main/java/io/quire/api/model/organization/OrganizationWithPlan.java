package io.quire.api.model.organization;

import io.quire.api.model.*;
import io.swagger.annotations.*;

import java.util.List;

public class OrganizationWithPlan extends Organization {

    @ApiModelProperty(
        value = "Subscription details for this organization.",
        position = 9
    )
    public Subscription getSubscription() { return null; }
}
