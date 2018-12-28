package io.quire.api.model.task;

import io.swagger.annotations.*;

public class CreateTaskBody {

    @ApiModelProperty(example = "Marketing_Project", required = true,
        value = "The project id")
    public String getProject() { return null; }

    @ApiModelProperty(example = "10",
        value = "(optional) The parent task id")
    public String getParent() { return null; }

    @ApiModelProperty(example = "13",
        value = "(optional) The previous sibling task id" )
    public String getAfter() {
        return null;
    }

    @ApiModelProperty(example = "New Task")
    public String getName() { return null; }

    @ApiModelProperty(example = "john",
        value = "User id to assign")
    public String getAssignee() { return null; }

    @ApiModelProperty(example = "2018-12-20T02:06:58.158Z")
    public String getStart() { return null; }

    @ApiModelProperty(example = "2018-12-22T02:06:58.158Z")
    public String getDue() { return null; }

}
