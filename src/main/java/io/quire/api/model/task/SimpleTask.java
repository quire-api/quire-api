package io.quire.api.model.task;

import io.quire.api.model.*;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class SimpleTask {

    @ApiModelProperty(value="OID, aka. UUID.", example = "Dyh2YkFcu9uLgLFIeN1kB4Ld")
    public String getOid() { return null; }

	@ApiModelProperty(example = "12", position = 1)
	public int getId() { return 0; }

	@ApiModelProperty(value = "This task's name.", example = "Design new <b>logo</b>", position = 2)
	public String getName() { return null; }

	@ApiModelProperty(value = "The status of this task. "
		+ "Its value must be between 0 and 100. If 100, it means completed.",
		example = "0", position = 4)
	public int getStatus() { return 0; }

	@ApiModelProperty(value = "The priority of this task. "
		+ "Its value must be between -1 (lowest) and 2 (highest). "
		+ "Default: 0.",
		example = "0", position = 4)
	public int getPriority() { return 0; }

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

	@ApiModelProperty(value = "Url of this task on Quire website.",
		example = "https://quire.io/w/my_project/123", position = 50)
	public String getUrl() { return null; }
}
