package io.quire.api.model.task;

import io.swagger.annotations.*;

class Priority {

    @ApiModelProperty(
        value = "Display name of the priority.",
        example = "Urgent"
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "Numeric priority level. Integer from -1 (lowest) to 2 (highest). A higher value indicates a higher priority. Default: 0.",
        example = "0",
        position = 4
    )
    public int getValue() { return 0; }
}
