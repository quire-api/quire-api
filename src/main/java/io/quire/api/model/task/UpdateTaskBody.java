package io.quire.api.model.task;

import java.util.List;

import io.quire.api.model.Recurring;
import io.swagger.annotations.ApiModelProperty;

public class UpdateTaskBody {
	@ApiModelProperty(example = "New idea",
		value = "(Optional) New name of the task.")
	public String getName() { return null; }

	@ApiModelProperty(value = "(Optional) Change the description about this task.",
	example = "This is a **cool** task.")
	public String getDescription() { return null; }

	@ApiModelProperty(value = "(Optional) Change the priority of this task. "
		+ "Its value must be between -1 (lowest) and 2 (highest). ",
		example = "0", position = 4)
	public int getPriority() { return 0; }

	@ApiModelProperty(value = "(Optional) An optional status to change to. "
		+ "Its value must be between 0 and 100. "
		+ "To complete a task, specify 100.",
		example = "100", position = 4)
	public int getStatus() { return 0; }

	@ApiModelProperty(value = "(Optional) The estimated time to complete. "
		+ "If specified, it must be non-negative or null. "
		+ "If null is specified, it means to clear the value."
		+ "Unit: seconds.",
		example = "0", position = 4)
	public int getEtc() { return 0; }

	@ApiModelProperty(
		value = "(Optional) OID or names of the tags to replace existing tags of to this task. "
		+ "If specified, it will replace any existing tag(s). "
		+ "Instead of replacment, you can use \"addTags\" or \"removeTags\".\n"
		+ "Note: if tag's name is specified, it is case-insensitive.")
	public List<String> getTags() { return null; }
	@ApiModelProperty(
		value = "(Optional) OID or names of the tags to be added to this task.\n"
		+ "Note: if tag's name is specified, it is case-insensitive.")
	public List<String> getAddTags() { return null; }
	@ApiModelProperty(
		value = "(Optional) OID or names of the tags to be removed from this task.\n"
		+ "Note: if tag's name is specified, it is case-insensitive.")
	public List<String> getRemoveTags() { return null; }

	@ApiModelProperty(
		value = "(Optional) OID, ID emails of the users that this task is assigned to. "
		+ "If specified, it will replace any existing assignee(s). "
		+ "Instead of replacment, you can use \"addAssignees\" or \"removeAssignees\".")
	public List<String> getAssignees() { return null; }
	@ApiModelProperty(
		value = "(Optional) OID or ID of the assignees to be added to this task.")
	public List<String> getAddAssignees() { return null; }
	@ApiModelProperty(
		value = "(Optional) OID or ID of the assignees to be removed from this task.")
	public List<String> getRemoveAssignees() { return null; }

	@ApiModelProperty(example = "2018-12-20T00:00:00.000Z",
		value = "(Optional) An optional start time. "
		+ "If you'd like to specify time, use `yyyy-mm-ddThh:mmZ`, "
		+ "e.g., `2020-10-30T09:30Z`.\n"
		+ "If you don't like to set time, use `yyyy-mm-dd`, e.g., `2020-10-30`."
		+ "Note: they must be in UTC time.")
	public String getStart() { return null; }

	@ApiModelProperty(example = "2018-12-22T00:00:00.000Z",
		value = "(Optional) An optional due time. "
		+ "If you'd like to specify time, use `yyyy-mm-ddThh:mmZ`, "
		+ "e.g., `2020-10-30T09:30Z`.\n"
		+ "If you don't like to set time, use `yyyy-mm-dd`, e.g., `2020-10-30`."
		+ "Note: they must be in UTC time.")
	public String getDue() { return null; }

	@ApiModelProperty(value = "(Optional) The recurring information of this task. "
		+ "It is null if it is not a recurring task.")
	public Recurring getRecurring() { return null; }

	@ApiModelProperty(example = "true",
		value = "(Optional) Specify true or a positive integer to peekaboo "
		+ "this task and its subtasks. "
		+ "Or, specify false to undo the previous peekaboo if any.\n"
		+ "If a positive integer is specified, it is the number of days to peekaboo a task. "
		+ "If true, the default number of days will be used (depending on \n"
		+ "the project's setting).")
	public Object getPeekaboo() { return false; }

	@ApiModelProperty(example = "true",
		value = "(Optional) Specify whether this task is a section or a normal task.")
	public boolean getSection() { return false; }

	@ApiModelProperty(
		value = "(Optional) OID, ID or emails of the users to replace the followers of this task. "
		+ "Please refer to `addFollowers()` for more details.")
	public List<String> getFollowers() { return null; }
	@ApiModelProperty(
		value = "(Optional) OID or ID of the followers to be added to this task."
		+ "If \"me\" is specified, it means the current user will follow this task.\n"
		+ "If the application would like to follow (i.e., receive notifications), "
		+ "it can pass \"app\" as one of OIDs.\n"
		+ "In additions, it can pass additional information in one of the following syntaxes.\n\n"
		+ "Syntax 1:\n"
		+ "\"app|team\" or \"app|team|channel\"\n"
		+ "where team and channel can be any value.\n\n"
		+ "Syntax 2:\n"
		+ "\"app|/path\"\n"
		+ "where \"/path\" can be any URL path. It will be appended to the hook's URL "
		+ "when calling the registered hook. For example, if the hook URL is "
		+ "\"https://super.app/hooks/standard\" and the follower is "
		+ "\"app|/soc1/33456/a7\", then the notification will be sent to "
		+ "\"https://super.app/hooks/standard/soc1/33456/a7\".")
	public List<String> getAddFollowers() { return null; }
	@ApiModelProperty(
		value = "(Optional) OID or ID of the followers to be removed from this task."
		+ "Please refer to `addFollowers()` for more details.")
	public List<String> getRemoveFollowers() { return null; }

	@ApiModelProperty(example = "true",
		value = "(Optional) Specify true if you'd like to make "
		+ "this task as updated by the app.\n"
		+ "Default: false -- the task is marked as created by the user authorizing "
		+ "the app.")
	public boolean getAsUser() { return false; }
}
