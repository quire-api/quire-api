package io.quire.api.model.task;

import io.quire.api.model.*;
import io.swagger.annotations.*;

@ApiModel()
public class SimpleTaskWithProject extends SimpleTask {
  @ApiModelProperty(value = "The project that this task belongs to.", position = 10)
  public SimpleEntityWithId getProject() { return null; }
}
