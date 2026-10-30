package io.quire.api.model.task;

import io.quire.api.model.*;
import io.swagger.annotations.*;

public class TaskWithParentInfo extends Task {
  @ApiModelProperty(
      value = TaskInfo.PARENT_CHAIN,
      position = 80
  )
  public TaskInfo getParent() { return null; }
}
