package io.quire.api.model.task;

import io.quire.api.model.*;
import io.swagger.annotations.*;

import java.util.List;

public class Task extends SimpleTask {

	@ApiModelProperty(value = "Contains the recurrence details of this task. "
		+ "If `null`, the task does not repeat.",
		position = 4)
	public RecurrenceX getRecurrence() { return null; }

	@ApiModelProperty(value = "The estimated time to complete the task, expressed in seconds. "
		+ "If null, no estimate has been specified.",
		example = "500", position = 4)
	public int getEtc() { return 0; }
	@ApiModelProperty(value = "The time log entries associated with this task.",
		position = 4)
	public List<Timelog> getTimelogs() { return null; }

	@ApiModelProperty(value = "A list of users who assigned this task. "
		+ "Each item in assignors corresponds by index to the matching item in assignees "
		+ "(e.g., the first assignee was assigned by the first assignor).", position = 5)
	public List<SimpleIdentity> getAssignors() { return null; }

	@ApiModelProperty(value = "The external team to which this task belongs. "
		+ "If `null`, this task is not associated with any external team.",
		position = 5)
	public SimpleTaggingEntity getPartner() { return null; }

	@ApiModelProperty(value = "The user who assigned this task to the external team. "
		+ "If `null`, the task is not associated with any external team.",
		position = 5)
	public SimpleIdentity getPartnerBy() { return null; }

	@ApiModelProperty(value = "Indicates the display order of this task in the board view. "
		+ "A smaller value means the task appears earlier. "
		+ "This field is only relevant in board view and has no meaning elsewhere.",
		example = "99", position = 6)
	public int getOrder() { return 0; }

	@ApiModelProperty(value = "The list of files attached to this task.", position = 8)
	public List<Attachment> getAttachments() { return null; }
	@ApiModelProperty(value = "The ID of the attachment used as the cover image for this task.",
		example = "qfqVmUtC",position = 8)
	public String getCover() { return null; }

	@ApiModelProperty(value = "The number of subtasks belonging to this task. "
		+ "To retrieve the subtasks, send a GET request to \"/task/list/{oid}\".",
		example = "5", position = 10)
	public int getChildCount() { return 0; }

	@ApiModelProperty(value = "A list of items that reference this task. "
		+ "Note: Some referrers may no longer exist.", position = 20)
	public List<Referrer> getReferrers() { return null; }

	@ApiModelProperty(value = "The timestamp of the most recent status change for this task.",
		example = "2018-12-22T02:06:58.158Z", position = 70)
	public String getToggledAt() { return null; }
	@ApiModelProperty(value = "The user who last changed the status of this task.",
		position = 71)
	public SimpleIdentity getToggledBy() { return null; }

	@ApiModelProperty(value = "The list of users who are following this task.", position = 60)
	public List<SimpleIdentity> getFollowers() { return null; }
	@ApiModelProperty(value = "The list of users who have muted this task and will not receive notifications about it, even if they are assigned.", position = 60)
	public List<SimpleIdentity> getMutes() { return null; }
	@ApiModelProperty(value = "The list of users who have marked this task as a favorite.", position = 60)
	public List<SimpleIdentity> getFavorites() { return null; }

	@ApiModelProperty(value = "Represents the timestamp of the most recent edit to this record.", example = "2018-12-22T02:06:58.158Z", position = 72)
	public String getEditedAt() { return null; }

	@ApiModelProperty(value = "Indicates the timestamp of the most recent comment posted on this record.\n\n"
		+ "If `null`, the record has never had a comment.\n\n"
		+ "Since comments can be removed, this value may not always match the current set of comments.",
		example = "2023-12-22T09:06:28.253Z", position = 73)
	public String getCommentedAt() { return null; }
}
