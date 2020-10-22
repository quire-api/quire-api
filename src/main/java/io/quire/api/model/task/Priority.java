package io.quire.api.model.task;

import io.swagger.annotations.*;

@ApiModel()
class Priority {
    @ApiModelProperty(value="Name.", example = "Urgent")
    public String getName() { return null; }

	@ApiModelProperty(value = "The priority. "
		+ "Its value is between -1 and 2. "
		+ "The higher the value, the higher the priority.\n"
		+ "Default: 0",
		example = "0", position = 4)
	public int getValue() { return 0; }
}
