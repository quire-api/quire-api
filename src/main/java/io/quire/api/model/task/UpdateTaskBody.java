package io.quire.api.model.task;

import io.swagger.annotations.ApiModelProperty;

public class UpdateTaskBody {
    @ApiModelProperty(example = "New name of task")
    public String getName() {
        return null;
    }

    @ApiModelProperty(example = "Detail about this task")
    public String getDescription() { return null; }

    @ApiModelProperty(example = "john")
    public String getAssignee() { return null; }

    @ApiModelProperty(example = "2018-12-20T02:06:58.158Z")
    public String getStart() { return null; }

    @ApiModelProperty(example = "2018-12-22T02:06:58.158Z")
    public String getDue() { return null; }
}
