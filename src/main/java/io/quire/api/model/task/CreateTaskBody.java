package io.quire.api.model.task;

import io.quire.api.model.Recurring;
import io.swagger.annotations.*;

import java.util.List;

public class CreateTaskBody {

	@ApiModelProperty(example = "Task 101",
		value = "The name of the task.", required = true)
	public String getName() { return null; }

	@ApiModelProperty(example = "**Great** task to start with.",
		value = "(Optional) An optional description about this task.")
	public String getDescription() { return null; }

	@ApiModelProperty(value = "(Optiona) An optional priority. "
		+ "Its value must be between -1 (lowest) and 2 (highest). "
		+ "Default: 0.",
		example = "0", position = 4)
	public int getPriority() { return 0; }

	@ApiModelProperty(value = "(Optiona) An optional status. "
		+ "Its value must be between 0 and 100. "
		+ "Default: 0.",
		example = "0", position = 4)
	public int getStatus() { return 0; }

	@ApiModelProperty(
		value = "(Optional) OID of the tags to be added to the new created task.")
	public List<String> getTags() { return null; }

	@ApiModelProperty(
		value = "(Optional) OID of the users that this task is assigned to.")
	public List<String> getAssignees() { return null; }

	@ApiModelProperty(example = "2018-12-20T00:00:00.000Z",
		value = "(Optional) An optional start time. "
		+ "Note: if time is specified, the millisecond must be `001`. "
		+ "Otherwise, it is `000` (so are the hour, minute and second fields).")
	public String getStart() { return null; }

	@ApiModelProperty(example = "2018-12-22T00:00:00.000Z",
		value = "(Optional) An optional start time. "
		+ "Note: if time is specified, the millisecond must be `001`. "
		+ "Otherwise, it is `000` (so are the hour, minute and second fields).")
	public String getDue() { return null; }

	@ApiModelProperty(value = "(Optional) The recurring information of this task. "
		+ "It is null if it is not a recurring task.")
	public Recurring getRecurring() { return null; }

	@ApiModelProperty(example = "true",
		value = "(Optional) Specify true to peekaboo this task and its subtasks, "
		+ "if any. Default: false.")
	public boolean getPeekaboo() { return false; }

	@ApiModelProperty(example = "true",
		value = "(Optional) Specify true if you'd like to make "
		+ "this new task as created by the app.\n"
		+ "Default: false -- the task is marked as created by the user authorizing "
		+ "the app.")
	public boolean getAsUser() { return false; }

	@ApiModelProperty(value = "(Optional) OID of users who follow this task."
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
	public List<String> getFollowers() { return null; }

//	@ApiModelProperty(value = "(Optional) A list of subtasks to create.")
//	public List<CreateTaskBody> getTasks() { return null; }
}
