package io.quire.api.model.user;

import io.quire.api.model.IdentityX;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class User extends IdentityX {
	//for privacy, we don't output fdCreatedAt and fdCreatedBy
}
