package io.quire.api.model.organization;

import io.quire.api.model.*;
import io.swagger.annotations.*;

import java.util.List;

public class Organization extends IdentityX {

    @ApiModelProperty(
        value = "Display name of the organization (Markdown supported).",
        example = "My Organization"
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "Organization name with Markdown removed.",
        example = "My Organization"
    )
    public String getNameText() { return null; }

    @ApiModelProperty(
        value = "Organization name rendered as an HTML fragment converted from Markdown.",
        example = "My Organization"
    )
    public String getNameHtml() { return null; }

    @ApiModelProperty(
        value = "Organization ID.",
        example = "my_organization"
    )
    public String getId() { return null; }

    @ApiModelProperty(
        value = "URL of this organization on the Quire website.",
        example = "https://quire.io/c/my_organization"
    )
    public String getUrl() { return null; }

    @ApiModelProperty(
        value = "Organization email address.",
        example = "info@company.com",
        allowEmptyValue = true
    )
    public String getEmail() { return null; }

    @ApiModelProperty(
        value = "Creation timestamp in UTC (ISO 8601).",
        example = "2018-12-22T02:06:58.158Z",
        position = 99
    )
    public String getCreatedAt() { return null; }

    @ApiModelProperty(
        value = "User who created this record.",
        example = "Dyh2YkFcu9uLgLFIeN1kB4Ld",
        position = 99
    )
    public SimpleIdentity getCreatedBy() { return null; }

    @ApiModelProperty(
        value = "Users who follow this organization.",
        position = 60
    )
    public List<SimpleIdentity> getFollowers() { return null; }
}
