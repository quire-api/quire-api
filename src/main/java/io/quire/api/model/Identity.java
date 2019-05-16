package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Identity extends NamedIconEntity {
    @ApiModelProperty(value = "ID.", example = "My_Project")
    public String getId() { return null; }

    @ApiModelProperty(value = "Name but excluding markdown characters.")
    public String getPlainName() { return null; }
    @ApiModelProperty(value = "Name in a form of a HTML fragment converted from markdown.", example = "<b>Big</b> project")
    public String getHtmlName() { return null; }

    @ApiModelProperty(value = "Description.", example = "This is a **cool** project.")
    public String getDescription() { return null; }
    @ApiModelProperty(value = "Description but excluding markdown characters.", example = "This is a cool project.")
    public String getPlainDescription() { return null; }
    @ApiModelProperty(value = "Description in a form of a HTML fragment converted from markdown.", example = "This is a <i>cool</i> project.")
    public String getHtmlDescription() { return null; }

    @ApiModelProperty(value = "Url of this record on Quire website.", example = "https://quire.io/w/my_project")
    public String getUrl() { return null; }

    @ApiModelProperty(value = "When this record was created.", example = "2018-12-22T02:06:58.158Z", position = 99)
    public String getCreatedAt() { return null; }
    @ApiModelProperty(value = "OID of the user who created this record.", example = "Dyh2YkFcu9uLgLFIeN1kB4Ld", position = 99)
    public String getCreatedBy() { return null; }
}
