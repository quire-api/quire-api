package io.quire.api.model.organization;

import io.quire.api.model.*;
import io.swagger.annotations.*;

import java.util.List;

@ApiModel()
public class OrganizationWithPlan extends Organization {
	@ApiModelProperty(value = "The subscription of this organization.",
		position = 9)
	public Subscription getSubscription() { return null; }
}
