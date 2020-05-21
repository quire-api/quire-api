package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Identity extends NamedIconEntity {

	@ApiModelProperty(value = "Name but excluding markdown characters.",
		example = "My Name")
	public String getNameText() { return null; }
	@ApiModelProperty(value = "Name in a form of a HTML fragment converted from markdown.",
		example = "My Name")
	public String getNameHtml() { return null; }

	@ApiModelProperty(value = "ID.",
		example = "my_id")
	public String getId() { return null; }

	@ApiModelProperty(value = "Url of this record on Quire website.",
		example = "https://quire.io/w/my_id")
	public String getUrl() { return null; }

	@ApiModelProperty(value = "Description.",
		example = "This is *cool*!")
	public String getDescription() { return null; }
	@ApiModelProperty(value = "Description but excluding markdown characters.",
		example = "This is cool!")
	public String getDescriptionText() { return null; }
	@ApiModelProperty(value = "Description in a form of a HTML fragment converted from markdown.",
		example = "This is <i>cool</i>!")
	public String getDescriptionHtml() { return null; }

	//Not output for privacy
	//@ApiModelProperty(value = "When this record was created.", example = "2018-12-22T02:06:58.158Z", position = 99)
	//public String getCreatedAt() { return null; }
	//@ApiModelProperty(value = "OID of the user who created this record.", example = "Dyh2YkFcu9uLgLFIeN1kB4Ld", position = 99)
	//public String getCreatedBy() { return null; }
}
