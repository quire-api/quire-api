package io.quire.api.model.organization;

import io.quire.api.model.IdentityX;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Organization extends IdentityX {

    @ApiModelProperty(value="Name.", example = "My Organization")
    public String getName() { return null; }

    @ApiModelProperty(value = "Name but excluding markdown characters.",
        example = "My Organization")
    public String getNameText() { return null; }
    @ApiModelProperty(value = "Name in a form of a HTML fragment converted from markdown.",
        example = "My Organization")
    public String getNameHtml() { return null; }

    @ApiModelProperty(value = "ID.", example = "my_organization")
    public String getId() { return null; }

    @ApiModelProperty(value = "Url of this record on Quire website.",
            example = "https://quire.io/c/my_organization")
    public String getUrl() { return null; }

    @ApiModelProperty(value = "Email address.", example = "info@compony.com", allowEmptyValue = true)
    public String getEmail() { return null; }
    @ApiModelProperty(value = "When this record was created.", example = "2018-12-22T02:06:58.158Z", position = 99)
    public String getCreatedAt() { return null; }
    @ApiModelProperty(value = "The user who created this record.", example = "Dyh2YkFcu9uLgLFIeN1kB4Ld", position = 99)
    public SimpleIdentity getCreatedBy() { return null; }
}
