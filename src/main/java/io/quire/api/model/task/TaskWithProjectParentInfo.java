package io.quire.api.model.task;

import io.quire.api.model.*;
import io.swagger.annotations.*;

public class TaskWithProjectParentInfo extends TaskWithParentInfo {

  @ApiModelProperty(
      value = "The project to which this task belongs.",
      position = 81
  )
  public SimpleEntityWithId getProject() { return null; }
}
