package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class IdentityX extends Identity {
    @ApiModelProperty(value = "Email address.", example = "john@gmail.cc", allowEmptyValue = true)
    public String getEmail() { return null; }
    @ApiModelProperty(value = "Website.", example = "https://coolwebsites.com", allowEmptyValue = true)
    public String getWebsite() { return null; }
}
