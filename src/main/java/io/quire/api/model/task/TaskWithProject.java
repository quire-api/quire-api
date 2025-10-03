package io.quire.api.model.task;

import io.quire.api.model.*;
import io.swagger.annotations.*;

public class TaskWithProject extends Task {

  @ApiModelProperty(
      value = "The project to which this task belongs.",
      position = 10
  )
  public SimpleEntityWithId getProject() { return null; }
}
