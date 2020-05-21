package io.quire.api.model.user;

import io.quire.api.model.IdentityX;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class User extends IdentityX {
    @ApiModelProperty(value="Name.",
        example = "John")
    public String getName() { return null; }

    @ApiModelProperty(value = "Name but excluding markdown characters.",
        example = "John")
    public String getNameText() { return null; }
    @ApiModelProperty(value = "Name in a form of a HTML fragment converted from markdown.",
        example = "John")
    public String getNameHtml() { return null; }

    @ApiModelProperty(value = "ID.",
        example = "john")
    public String getId() { return null; }

    @ApiModelProperty(value = "Url of this record on Quire website.",
        example = "https://quire.io/u/john")
    public String getUrl() { return null; }
	//for privacy, we don't output fdCreatedAt and fdCreatedBy
}
