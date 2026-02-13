package io.quire.api.model.task;

import io.quire.api.model.*;
import io.swagger.annotations.*;

public class TaskInfo extends Entity {
    @ApiModelProperty(
        value = "Task id.",
        example = "12"
    )
    public int getId() { return 0; }

    @ApiModelProperty(
      value = "The parent information, if this task has a parent.",
      position = 10
    )
    public TaskInfo getParent() { return null; }
}
