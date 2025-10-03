package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class Identity extends SimpleIdentity {

    @ApiModelProperty(
        value = "Display name with Markdown removed.",
        example = "My Name"
    )
    public String getNameText() { return null; }

    @ApiModelProperty(
        value = "Display name rendered as an HTML fragment converted from Markdown.",
        example = "My Name"
    )
    public String getNameHtml() { return null; }

    @ApiModelProperty(
        value = "Description (Markdown supported).",
        example = "This is *cool*!"
    )
    public String getDescription() { return null; }

    @ApiModelProperty(
        value = "Description with Markdown removed.",
        example = "This is cool!"
    )
    public String getDescriptionText() { return null; }

    @ApiModelProperty(
        value = "Description rendered as an HTML fragment converted from Markdown.",
        example = "This is <i>cool</i>!"
    )
    public String getDescriptionHtml() { return null; }

    // Not output for privacy
    // @ApiModelProperty(value = "Creation timestamp in UTC (ISO 8601).", example = "2018-12-22T02:06:58.158Z", position = 99)
    // public String getCreatedAt() { return null; }
    // @ApiModelProperty(value = "User who created this record.", example = "Dyh2YkFcu9uLgLFIeN1kB4Ld", position = 99)
    // public SimpleIdentity getCreatedBy() { return null; }
}
