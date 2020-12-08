package io.quire.api.model.task;

import io.quire.api.model.*;
import io.swagger.annotations.*;

import java.util.List;

@ApiModel()
public class Task extends StampedEntity {

	@ApiModelProperty(example = "12", position = 1)
	public int getId() { return 0; }

	@ApiModelProperty(value = "This task's name.",
		example = "Design new **logo**", position = 2)
	public String getName() { return null; }
	@ApiModelProperty(value = "This task's name but excluding markdown characters.",
		example = "Design new logo", position = 2)
	public String getNameText() { return null; }
	@ApiModelProperty(value = "This task's name in a form of a HTML fragment converted from markdown.",
		example = "Design new <b>logo</b>", position = 2)
	public String getNameHtml() { return null; }

	@ApiModelProperty(value = "Description about this task.", example = "This is a *cool* task.", position = 3)
	public String getDescription() { return null; }
	@ApiModelProperty(value = "Description but excluding markdown characters.", example = "This is a cool task.", position = 3)
	public String getDescriptionText() { return null; }
	@ApiModelProperty(value = "Description in a form of a HTML fragment converted from markdown.", example = "This is a <i>cool</i> task.", position = 3)
	public String getDescriptionHtml() { return null; }

	@ApiModelProperty(value = "The status of this task.", position = 4)
	public Status getStatus() { return null; }

	@ApiModelProperty(value = "The priority of this task. "
		+ "Its value must be between -1 (lowest) and 2 (highest). "
		+ "Default: 0.", position = 4)
	public Priority getPriority() { return null; }

	@ApiModelProperty(value = "Tags that are tagged to this task.", position = 5)
	public List<SimpleTaggingEntity> getTags() { return null; }

	@ApiModelProperty(value = "When to start this task."
		+ "If the time is also set, it returns in the format of "
		+ "`yyyy-mm-ddThh:mmZ`, e.g., `2020-10-30T09:30Z`.\n"
		+ "If not, it returns in the format of `yyyy-mm-dd`, e.g., `2020-10-30`."
		+ "Note: the are both in UTC time.",
		example = "2020-10-30", position = 4)
	public String getStart() { return null; }
	@ApiModelProperty(value = "When to complete this task. "
		+ "If the time is also set, it returns in the format of "
		+ "`yyyy-mm-ddThh:mmZ`, e.g., `2020-10-30T09:30Z`.\n"
		+ "If not, it returns in the format of `yyyy-mm-dd`, e.g., `2020-10-30`."
		+ "Note: the are both in UTC time.",
		example = "2020-10-30", position = 4)
	public String getDue() { return null; }

	@ApiModelProperty(value = "The recurring information of this task. "
		+ "It is null if it is not a recurring task.",
		position = 4)
	public Recurring getRecurring() { return null; }

	@ApiModelProperty(value = "Users who are assigned to this task.", position = 5)
	public List<SimpleIdentity> getAssignees() { return null; }
	@ApiModelProperty(value = "Users who have assigned this tasks to a user. "
		+ "For example, the first item of assignees is assigned by the first item of "
		+ "assignors.", position = 5)
	public List<SimpleIdentity> getAssignors() { return null; }

	@ApiModelProperty(value = "The external team that this task belongs to. "
		+ "It is null if this task doesn't belong to any external team.",
		position = 5)
	public SimpleTaggingEntity getPartner() { return null; }
	@ApiModelProperty(value = "The user who assigned this task to an external team. "
		+ "It is null if this task doesn't belong to any external team.",
		position = 5)
	public SimpleIdentity getPartnerBy() { return null; }

	@ApiModelProperty(value = "The order of this task shown on the board view. "
		+ "The smaller the number is, the ealier the task is shown. "
		+ "It is meaningless if not in a board view.",
		example = "99", position = 6)
	public int getOrder() { return 0; }

	@ApiModelProperty(value = "The attachments of this task.", position = 8)
	public List<Attachment> getAttachments() { return null; }
	@ApiModelProperty(value = "The id of the attachment that is used "
		+ "as a cover of this task.",
		example = "qfqVmUtC",position = 8)
	public String getCover() { return null; }

	@ApiModelProperty(value = "Number of subtasks of this task. "
		+ "To retrieve these subtasks, make the GET request to \"/task/list/{oid}\".",
		example = "5", position = 10)
	public int getChildCount() { return 0; }

	@ApiModelProperty(value = "A list of referrers that refer this task. "
		+ "Note: some of them might no longer exist.", position = 20)
	public List<Referrer> getReferrers() { return null; }

	@ApiModelProperty(value = "When this task's status was changed last time.", example = "2018-12-22T02:06:58.158Z", position = 50)
	public String getToggledAt() { return null; }
	@ApiModelProperty(value = "The user who changed this task's status.",
		position = 50)
	public SimpleIdentity getToggledBy() { return null; }

	@ApiModelProperty(value = "Users who follow this task.", position = 60)
	public List<SimpleIdentity> getFollowers() { return null; }
	@ApiModelProperty(value = "Users who don't want any nofications of this task even they're assinged to it.", position = 60)
	public List<SimpleIdentity> getMutes() { return null; }
	@ApiModelProperty(value = "Users who favorite this task.", position = 60)
	public List<SimpleIdentity> getFavorites() { return null; }

	@ApiModelProperty(value = "When this record was edited last time.", example = "2018-12-22T02:06:58.158Z", position = 50)
	public String getEditedAt() { return null; }

	@ApiModelProperty(value = "Whether this task was peekabooed. "
		+ "It is null if not peekabooed.",
		example = "true", position = 50)
	public boolean getPeekaboo() { return true; }

	@ApiModelProperty(value = "Url of this task on Quire website.",
		example = "https://quire.io/w/my_project/123", position = 50)
	public String getUrl() { return null; }

	@ApiModelProperty(value="The project this task belongs to.",
		position = 99)
	public SimpleIdentity getProject() { return null; }
}
