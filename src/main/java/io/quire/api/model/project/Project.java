package io.quire.api.model.project;

import io.quire.api.model.Identity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Project extends Identity {
	@ApiModelProperty(value="OID of the organization this project belongs to.",
		example = "Dyh2YkFcu9uLgLFIeN1kB4Ld")
	public String getOrganization() { return null; }

	@ApiModelProperty(value = "Total number of tasks in this project.")
	public int getTaskCount() { return 0; }

	@ApiModelProperty(value = "Number of active tasks in this project.")
	public int getActiveCount() { return 0; }

	@ApiModelProperty(value = "Number of root tasks in this project.")
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
}
