package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class IdentityX extends Identity {

    @ApiModelProperty(
        value = "Email address.",
        example = "john@gmail.com",
        allowEmptyValue = true
    )
    public String getEmail() { return null; }

    @ApiModelProperty(
        value = "Website URL.",
        example = "https://coolwebsites.com",
        allowEmptyValue = true
    )
    public String getWebsite() { return null; }
}
