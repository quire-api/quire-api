package io.quire.api.model.task;

import io.quire.api.model.*;
import io.swagger.annotations.*;

public class TaskWithParentInfo extends Task {
  @ApiModelProperty(
      value = "The parent information, if this task has a parent. "
      + "If this task has no parent, this field will be null.\n\n"
      + "Note: It returns at most 10 levels of parent tasks. "
      + "If exceeded, the 11th item will contain only the id.\n\n";
      position = 80
  )
  public TaskInfo getParent() { return null; }
}
