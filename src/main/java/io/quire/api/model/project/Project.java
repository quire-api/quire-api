package io.quire.api.model.project;

import io.quire.api.model.Identity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel()
public class Project extends Identity {
	@ApiModelProperty(value="Name.",
		example = "My Project")
	public String getName() { return null; }

	@ApiModelProperty(value = "Name but excluding markdown characters.",
		example = "My Project")
	public String getNameText() { return null; }
	@ApiModelProperty(value = "Name in a form of a HTML fragment converted from markdown.",
		example = "My Project")
	public String getNameHtml() { return null; }

	@ApiModelProperty(value = "ID.",
		example = "my_project")
	public String getId() { return null; }

	@ApiModelProperty(value = "Url of this record on Quire website.",
		example = "https://quire.io/w/my_project")
	public String getUrl() { return null; }

	@ApiModelProperty(value="OID of the organization this project belongs to.",
		example = "Dyh2YkFcu9uLgLFIeN1kB4Ld")
	public String getOrganization() { return null; }

	@ApiModelProperty(value = "Total number of tasks in this project.",
		example = "30")
	public int getTaskCount() { return 0; }

	@ApiModelProperty(value = "Number of active tasks in this project.",
		example = "20")
	public int getActiveCount() { return 0; }

	@ApiModelProperty(value = "Number of root tasks in this project.",
		example = "5")
	public int getRootCount() { return 0; }

	@ApiModelProperty(value = "When this record was edited last time.", example = "2018-12-22T02:06:58.158Z", position = 50)
	public String getEditedAt() { return null; }

	@ApiModelProperty(value = "When this project was archived (aka., peekaboo). "
		+ "It is null if not archived.",
		example = "2018-12-22T02:06:58.158Z", position = 50)
	public String getArchivedAt() { return null; }

    @ApiModelProperty(value = "When this record was created.", example = "2018-12-22T02:06:58.158Z", position = 99)
    public String getCreatedAt() { return null; }
    @ApiModelProperty(value = "OID of the user who created this record.", example = "Dyh2YkFcu9uLgLFIeN1kB4Ld", position = 99)
    public String getCreatedBy() { return null; }

	@ApiModelProperty(value = "OID of users who follow this task.", position = 60)
	public List<String> getFollowers() { return null; }
}
