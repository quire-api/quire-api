package io.quire.api.model.user;

import io.quire.api.model.IdentityX;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class User extends IdentityX {

    @ApiModelProperty(
        value = "Display name of the user (Markdown supported).",
        example = "John"
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "User name with Markdown removed.",
        example = "John"
    )
    public String getNameText() { return null; }

    @ApiModelProperty(
        value = "User name rendered as an HTML fragment converted from Markdown.",
        example = "John"
    )
    public String getNameHtml() { return null; }

    @ApiModelProperty(
        value = "User ID.",
        example = "john"
    )
    public String getId() { return null; }

    @ApiModelProperty(
        value = "URL of this user on the Quire website.",
        example = "https://quire.io/u/john"
    )
    public String getUrl() { return null; }

    // For privacy, createdAt/createdBy are not exposed.
}
