package io.quire.api.model.task;

import io.quire.api.model.Tag;
import io.quire.api.model.User;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class Task {
    @ApiModelProperty(example = "12")
    public Integer getId() {
        return null;
    }

    @ApiModelProperty(example = "Design new logo")
    public String getName() {
        return null;
    }

    @ApiModelProperty(example = "Detail about this task")
    public String getDescription() { return null; }

    @ApiModelProperty(example = "0")
    public Integer getState() { return null; }

    public List<User> getAssignees() { return null; }

    public List<Tag> getTags() { return null; }

    @ApiModelProperty(example = "2018-12-20T02:06:58.158Z")
    public String getStart() { return null; }

    @ApiModelProperty(example = "2018-12-22T02:06:58.158Z")
    public String getDue() { return null; }

    @ApiModelProperty(example = "2018-12-22T02:06:58.158Z")
    public String getCreatedAt() { return null; }

    public User getCreatedBy() { return null; }
}
