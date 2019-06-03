package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Recurring {
	@ApiModelProperty(example = "2048",
		value = "The type of this recurring."
		+ "It is 0 if it is weekly. "
		+ "It is 1 if it is monthly. "
		+ "It is 2 if it is yearly. "
		+ "It is 3 if it is custom. ")
    public int getType() { return 0; }

	@ApiModelProperty(example = "2048",
		value = "How often this recurring shall occur. "
		+ "If the rate is 2 and the type is weekly, it means it shall "
		+ "occur every two week. "
		+ "If the type is custom, it means number of days to repeat.")
	public int getRate() { return 0; }

	@ApiModelProperty(value = "When this recurring shall end. "
		+ "If not specified, it means it is never end.",
		example = "2020-12-22T00:00:00.000Z", position = 4)
	public String getEnd() { return null; }

	@ApiModelProperty(
		value = "It depends on the type of this recurring. "
		+ "If weekly, bit 0 is Sunday, bit 1 is Monday and so on. "
   		+ "For example, if the data is 6, it means every Monday and Tuesday.")
	public int getData() { return 0; }
}
