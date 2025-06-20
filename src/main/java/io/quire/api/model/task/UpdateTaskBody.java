package io.quire.api.model.task;

import java.util.*;

import io.quire.api.model.Recurrence;
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
		value = "(Optional) A list of OID, ID or emails of the users that this task is assigned to. "
		+ "If specified, it will replace any existing assignee(s). "
		+ "Instead of replacment, you can use \"addAssignees\" or \"removeAssignees\".\n\n"
		+ "Please refer to `addAssignees` for more details.")
	public List<String> getAssignees() { return null; }
	@ApiModelProperty(
		value = "(Optional) A list of OID, ID or emails of the assignees to be added to this task.\n\n"
		+ "You can specify \"me\" instead of OID to indicate the current user.\n"
		+ "You can also specify \"inherit\" to include all assignees of the parent task, if any.\n\n"
		+ "Example: `{\"addAssignees\": [\"me\", \"inherit\", \"foo@domain.com\"]}`")
	public List<String> getAddAssignees() { return null; }
	@ApiModelProperty(
		value = "(Optional) A list of OID, ID or emails of the assignees to be removed from this task.\n\n"
		+ "Please refer to `addAssignees` for more details.")
	public List<String> getRemoveAssignees() { return null; }

	@ApiModelProperty(example = "2018-12-20T00:00:00.000Z",
		value = "(Optional) An optional start time. "
		+ "If you'd like to specify time, use `yyyy-mm-ddThh:mmZ`, "
		+ "e.g., `2020-10-30T09:30Z`.\n"
		+ "Note: they must be in UTC time, and we don't support seconds.\n\n"
		+ "If you don't like to set time, use `yyyy-mm-dd`, e.g., `2020-10-30`.\n\n"
		+ "Also note: for backward compatibility, `2020-10-30T00:00:00` "
		+ "(all zero and up to second) is considered the same as `2020-10-30`, "
		+ "i.e., without time. OTOH, To specify time at midnight in UTC, "
		+ "please specify `2020-10-30T00:00` (no second)")
	public String getStart() { return null; }

	@ApiModelProperty(example = "2018-12-22T00:00:00.000Z",
		value = "(Optional) An optional due time. "
		+ "If you'd like to specify time, use `yyyy-mm-ddThh:mmZ`, "
		+ "e.g., `2020-10-30T09:30Z`.\n"
		+ "Note: they must be in UTC time, and we don't support seconds.\n\n"
		+ "If you don't like to set time, use `yyyy-mm-dd`, e.g., `2020-10-30`.\n\n"
		+ "Also note: for backward compatibility, `2020-10-30T00:00:00` "
		+ "(all zero and up to second) is considered the same as `2020-10-30`, "
		+ "i.e., without time. OTOH, To specify time at midnight in UTC, "
		+ "please specify `2020-10-30T00:00` (no second)")
	public String getDue() { return null; }

	@ApiModelProperty(value = "(Optional) The recurring information of this task. "
		+ "It is null if it is not a recurring task.\n\n"
		+ "- `freq`: `daily`, `weekly`, `monthly`, `yearly`\n"
		+ "- `interval`: The interval between each freq iteration. Default: 1.\n"
		+ "- `until`: The last recurrence is less than or equals to the specified value. Default: never ends.\n"
		+ "- `bymonth`: If given, it must be an integer, starting from 1, meaning the month to apply to. It is supported only if `freq` is `yearly`. Default: 1 meaning January.\n"
		+ "- `byweekno`: If given, it must be an integer, meaning the week number, or `last` meaning the last week. The value starts with 1. It is supported only for `monthly` and `yearly`.\n"
		+ "- `byweekday`:  If given, it must be an integer: 0 for Monday, 1 for Tuesday, and so on. For `weekly`, it is a list of integers, such as `[1]` and `[0, 3]`. When given, these values will define the weekdays where the recurrence will be applied.\n"
		+ "- `bydayno`: If given, it must be an integer, starting from 1, meaning the day to apply to. It is supported only for `monthly` and `yearly`.\n"
		+ "  - Note: `byweekday` and `bydayno` can not be specified at the same time.\n"
		+ "- `dupsubtasks`: Whether to duplicate the subtasks when the task is completed. Default: true.\n"
		+ "- `sincelatest`: Whether to repeat it since the last date the task is completed. It is available only for `daily`. Default: false.")
	public Recurrence getRecurrence() { return null; }

	@ApiModelProperty(example = "true",
		value = "(Optional) Specify true or a positive integer to peekaboo "
		+ "this task and its subtasks, if any.\n"
		+ "Or, specify false to undo the previous peekaboo if any.\n\n"
		+ "If a positive integer is specified, it is the number of days to peekaboo a task.\n"
		+ "If true, it won't be reshowed automatically.\n\n"
		+ "Default: false.")
	public Object getPeekaboo() { return false; }

	@ApiModelProperty(example = "true",
		value = "(Optional) Specify whether this task is a section or a normal task.")
	public boolean getSection() { return false; }

	@ApiModelProperty(
		value = "(Optional) A list of OID, ID or emails of the users to replace the followers of this task.\n\n"
		+ "Please refer to `addFollowers` for more details.")
	public List<String> getFollowers() { return null; }
	@ApiModelProperty(
		value = "(Optional) A list of OID, ID or emails of the followers to be added to this task."
		+ "You can specify \"me\" instead of OID to indicate the current user.\n"
		+ "You can also specify \"inherit\" to include all followers of the parent task, if any.\n\n"
		+ "If the application itself would like to follow (i.e., receive notifications), "
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
		value = "(Optional) A list of OID, ID or emails of the followers to be removed from this task.\n\n"
		+ "Please refer to `addFollowers` for more details.")
	public List<String> getRemoveFollowers() { return null; }

	@ApiModelProperty(value = "(Optional) A list of OID or ID of tasks to replace the successors of this task.\n\n"
		+ "To specify task's ID, you can specify as `#id` or `id`.\n\n"
		+ "Example, `'AMZ0-59R125-35KTK2356G'`, `'#13'`, and `135`.")
	public List<String> getSuccessors() { return null; }
	@ApiModelProperty(value = "(Optional) A list of OID or ID of tasks to be added to the successors of this task.\n\n"
		+ "To specify task's ID, you can specify as `#id` or `id`.\n\n"
		+ "Example, `'AMZ0-59R125-35KTK2356G'`, `'#13'`, and `135`.")
	public List<String> getAddSuccessors() { return null; }
	@ApiModelProperty(value = "(Optional) A list of OID or ID of tasks to be removed from the successors of this task.\n\n"
		+ "To specify task's ID, you can specify as `#id` or `id`.\n\n"
		+ "Example, `'AMZ0-59R125-35KTK2356G'`, `'#13'`, and `135`.")
	public List<String> getRemoveSuccessors() { return null; }

	@ApiModelProperty(example = "true",
		value = "(Optional) Specify true if you'd like to make "
		+ "this task as updated by the app.\n"
		+ "Default: false -- the task is marked as created by the user authorizing "
		+ "the app.")
	public boolean getAsUser() { return false; }

	@ApiModelProperty(
		value = "(Optional) Specify a value to your custom field. "
		+ "The name and value depends on your definition of the custom field.\n\n"
		+ "- For Money type, please specify the value directly (without currency).\n"
		+ "- For User or Task type, please specify the OID.\n"
		+ "- For Duration type, please specify number of seconds.\n"
		+ "- For multiple values, you can specify a list of values.")
	public Object getYourField() { return false; }

	@ApiModelProperty(
		value = "Specify any data you'd like to store into the task. "
		+ "It is so-called source ref, and it'll be available when retrieving "
		+ "the task via API. Thus, you can identify them if necessary.\n\n"
		+ "NOTE: if you put an entry called 'text', it'll be displayed at client side. "
		+ "The syntax of the value is Markdown. "
		+ "It is recommmended to put the source link here, so the end user can find "
		+ "back the source.",
		example = "{'text': 'Source: [Gmail](https://gmail.com/link'}", position = 60)
	public Map<String, Object> getSourceRef() { return null; }
}
