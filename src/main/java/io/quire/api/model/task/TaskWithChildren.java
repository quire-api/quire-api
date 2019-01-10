package io.quire.api.model.task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel()
public class TaskWithChildren extends Task {
    @ApiModelProperty(position = 8)
    public List<TaskWithChildren> getTasks() { return null; }
}
