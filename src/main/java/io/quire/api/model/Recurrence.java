package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel()
public class Recurrence {
	@ApiModelProperty(example = "weekly",
		value = "The frequency of this recurring.")
    public String getFreq() { return null; }

	@ApiModelProperty(example = "1",
		value = "How often this recurring shall occur. "
		+ "If the rate is 2 and the type is weekly, it means it shall "
		+ "occur every two week. "
		+ "If the type is custom, it means number of days to repeat.")
	public int getInterval() { return 0; }

	@ApiModelProperty(value = "When this recurring shall end. "
		+ "If not specified, it means it is never end.",
		example = "2020-12-22", position = 4)
	public String getUntil() { return null; }

	@ApiModelProperty(
		value = "It must be an integer or array: 0 for Monday, 1 for Tuesday, and so on. "
		+ "For weekly, it is a list of integers, such as [1] and [0, 3]. "
		+ "When given, these values will define the weekdays where the recurrence will be applied. "
   		+ "For example, if the data is [0,1], it means every Monday and Tuesday.")
	public List<Integer> getByweekday() { return null; }

	@ApiModelProperty(
		value = "If given, it must be an integer, meaning the week number, "
		+ "or `last` meaning the last week. The value starts with 1. "
		+ "It is supported only for `monthly` and `yearly`.",
		example = "2")
	public int getByweekno() { return 0; }

	@ApiModelProperty(
		value = " If given, it must be an integer, starting from 1, "
		+ "meaning the day to apply to. It is supported only for `monthly` and `yearly`."
		+ "Note: `byweekday` and `bydayno` can not be specified at the same time.",
		example = "25")
	public int getBydayno() { return 0; }

	@ApiModelProperty(
		value = "If given, it must be an integer, starting from 1, "
		+ "meaning the month to apply to. It is supported only if `freq` is `yearly`. "
		+ "Default: 1 meaning January.",
		example = "10")
	public int getBymonth() { return 0; }

	@ApiModelProperty(
		value = "Whether to duplicate the subtasks when the task is completed. Default: true",
		example = "false")
	public boolean getDupsubtasks() { return true; }
	
	@ApiModelProperty(
		value = "Whether to repeat it since the last date the task is completed. "
		+ "It is available only for `daily`. Default: false",
		example = "true")
	public boolean getSincelatest() { return false; }
}
