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
        value = "Parent task information, if this task has a parent.\n\n"
            + "At most 10 levels of ancestors are returned. "
            + "If exceeded, the 10th ancestor contains only the OID to indicate this case.",
        position = 10
    )
    public TaskInfo getParent() { return null; }
}
