package io.quire.api.model.task;

import io.quire.api.model.*;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class Task extends StampedEntity {

	@ApiModelProperty(example = "12", position = 1)
	public int getId() { return 0; }

	@ApiModelProperty(value = "This task's name.", example = "Design new logo", position = 2)
	public String getName() { return null; }
	@ApiModelProperty(value = "This task's name but excluding markdown characters.", position = 2)
	public String getNameText() { return null; }
	@ApiModelProperty(value = "This task's name in a form of a HTML fragment converted from markdown.", example = "<b>Big</b> task", position = 2)
	public String getNameHtml() { return null; }

	@ApiModelProperty(value = "Description about this task.", example = "This is a **cool** task.", position = 3)
	public String getDescription() { return null; }
	@ApiModelProperty(value = "Description but excluding markdown characters.", example = "This is a cool task.", position = 3)
	public String getDescriptionText() { return null; }
	@ApiModelProperty(value = "Description in a form of a HTML fragment converted from markdown.", example = "This is a <i>cool</i> task.", position = 3)
	public String getDescriptionHtml() { return null; }

	@ApiModelProperty(value = "The status of this task. "
		+ "Its value must be between 0 and 100. If 100, it means completed.",
		example = "0", position = 4)
	public int getStatus() { return 0; }

	@ApiModelProperty(value = "The priority of this task. "
		+ "Its value must be between -1 (lowest) and 2 (highest). "
		+ "Default: 0.",
		example = "0", position = 4)
	public int getPriority() { return 0; }

	@ApiModelProperty(value = "OID of tags that are tagged to this task.", position = 5)
	public List<String> getTags() { return null; }

	@ApiModelProperty(value = "When to start this task."
		+ "Note: if time is specified, the millisecond will be `001`. "
		+ "Otherwise, it is `000` (so are the hour, minute and second fields).",
		example = "2018-12-20T00:00:00.000Z", position = 4)
	public String getStart() { return null; }
	@ApiModelProperty(value = "When to complete this task. "
		+ "Note: if time is specified, the millisecond will be 001. "
		+ "Otherwise, it is 000 (so are the hour, minute and second fields).",
		example = "2018-12-22T00:00:00.000Z", position = 4)
	public String getDue() { return null; }

	@ApiModelProperty(value = "The recurring information of this task. "
		+ "It is null if it is not a recurring task.",
		position = 4)
	public Recurring getRecurring() { return null; }

	@ApiModelProperty(value = "OID of users who are assigned to this task.", position = 5)
	public List<String> getAssignees() { return null; }
	@ApiModelProperty(value = "OID of users who have assigned this tasks to a user. "
		+ "For example, the first item of assignees is assigned by the first item of "
		+ "assignors.", position = 5)
	public List<String> getAssignors() { return null; }

	@ApiModelProperty(value = "OID of the external team that this task belongs to. "
		+ "It is null if this task doesn't belong to any external team.",
		example = "rcBHBYXZSiyDRrHrWPutatfF", position = 5)
	public String getPartner() { return null; }
	@ApiModelProperty(value = "OID of the user who assigned this task to an external team. "
		+ "It is null if this task doesn't belong to any external team.",
		example = "rcBHBYXZSiyDRrHrWPutatfF", position = 5)
	public String getPartnerBy() { return null; }

	@ApiModelProperty(value = "OID of the board that this task was added to. "
		+ "It is null if this task doesn't belong to any board.",
		example = "rcBHBYXZSiyDRrHrWPutatfF", position = 6)
	public String getBoard() { return null; }
	@ApiModelProperty(value = "The order of this task shown on the board. "
		+ "The smaller the number is, the ealier the task is shown. "
		+ "It is meaningless if it doesn't belong to any board.",
		example = "99", position = 6)
	public int getOrder() { return 0; }

	@ApiModelProperty(value = "The attachments of this task.", position = 8)
	public List<Attachment> getAttachments() { return null; }
	@ApiModelProperty(value = "The name of the attachment that is used "
		+ "as a cover of this task.", position = 8)
	public String getCover() { return null; }

	@ApiModelProperty(value = "Number of subtasks of this task. "
		+ "To retrieve these subtasks, make the GET request to \"/task/list/{oid}\".", position = 10)
	public int getChildCount() { return 0; }

	@ApiModelProperty(value = "A list of referrers that refer this task. "
		+ "Note: some of them might no longer exist.", position = 20)
	public List<Referrer> getReferrers() { return null; }

	@ApiModelProperty(value = "When this task's state was changed last time.", example = "2018-12-22T02:06:58.158Z", position = 50)
	public String getToggledAt() { return null; }
	@ApiModelProperty(value = "OID of the user who changed this task's state. ",
		example = "rcBHBYXZSiyDRrHrWPutatfF", position = 50)
	public String getToggledBy() { return null; }

	@ApiModelProperty(value = "OID of users who follow this task.", position = 60)
	public List<String> getFollowers() { return null; }
	@ApiModelProperty(value = "OID of users who favorite this task.", position = 60)
	public List<String> getFavorites() { return null; }

	@ApiModelProperty(value = "When this record was edited last time.", example = "2018-12-22T02:06:58.158Z", position = 50)
	public String getEditedAt() { return null; }

	@ApiModelProperty(value = "When this task was peekabooed. "
		+ "It is null if not peekabooed.",
		example = "2018-12-22T02:06:58.158Z", position = 50)
	public String getPeekabooAt() { return null; }

	@ApiModelProperty(value = "Url of this task on Quire website.",
		example = "https://quire.io/w/my_project/123", position = 50)
	public String getUrl() { return null; }

	@ApiModelProperty(value="OID of the project this task belongs to.",
		example = "Dyh2YkFcu9uLgLFIeN1kB4Ld", position = 99)
	public String getProject() { return null; }
}
