package io.quire.api.model.task;

import io.quire.api.model.*;
import io.swagger.annotations.*;

import java.util.List;

@ApiModel()
public class Task extends SimpleTask {

	@ApiModelProperty(value = "The recurring information of this task. "
		+ "It is null if it is not a recurring task.",
		position = 4)
	public Recurring getRecurring() { return null; }

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

	@ApiModelProperty(value = "The board that this task was added to. "
		+ "It is null if this task doesn't belong to any board.",
		position = 6)
	public SimpleIdentity getBoard() { return null; }
	@ApiModelProperty(value = "The order of this task shown on the board. "
		+ "The smaller the number is, the ealier the task is shown. "
		+ "It is meaningless if it doesn't belong to any board.",
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

	@ApiModelProperty(value = "When this task's state was changed last time.", example = "2018-12-22T02:06:58.158Z", position = 50)
	public String getToggledAt() { return null; }
	@ApiModelProperty(value = "The user who changed this task's state. ",
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

	@ApiModelProperty(value="The project this task belongs to.",
		position = 99)
	public SimpleIdentity getProject() { return null; }
}
