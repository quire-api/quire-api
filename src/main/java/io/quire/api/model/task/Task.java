package io.quire.api.model.task;

import io.quire.api.model.StampedEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class Task extends StampedEntity {

    @ApiModelProperty(example = "7DQI5OVhLyg2eWaQ7oNvClst")
    public String getOid() {
        return null;
    }

    @ApiModelProperty(example = "12", position = 1)
    public Integer getId() {
        return null;
    }

    @ApiModelProperty(example = "Design new logo", position = 2)
    public String getName() {
        return null;
    }

    @ApiModelProperty(example = "Detail about this task", position = 2)
    public String getDescription() { return null; }

    @ApiModelProperty(example = "0", position = 2)
    public Integer getState() { return null; }

    @ApiModelProperty(position = 2)
    public List<String> getAssignees() { return null; }

    @ApiModelProperty(position = 2)
    public List<String> getTags() { return null; }

    @ApiModelProperty(example = "rcBHBYXZSiyDRrHrWPutatfF", position = 2)
    public String getBoard() { return null; }

    @ApiModelProperty(example = "2018-12-20T02:06:58.158Z", position = 2)
    public String getStart() { return null; }

    @ApiModelProperty(example = "2018-12-22T02:06:58.158Z", position = 2)
    public String getDue() { return null; }

}
