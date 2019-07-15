package io.quire.api.model.task;

import io.quire.api.model.*;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class Task {

    @ApiModelProperty(value="OID, aka. UUID.", example = "Dyh2YkFcu9uLgLFIeN1kB4Ld")
    public String getOid() { return null; }

	@ApiModelProperty(example = "12", position = 1)
	public int getId() { return 0; }

	@ApiModelProperty(value = "This task's name.", example = "Design new logo", position = 2)
	public String getName() { return null; }
}
