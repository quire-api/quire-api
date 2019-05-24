package io.quire.api.model.board;

import io.quire.api.model.NamedIconEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class Board extends NamedIconEntity {

    @ApiModelProperty(example = "rcBHBYXZSiyDRrHrWPutatfF")
    public String getOid() { return null; }

    @ApiModelProperty(example = "Marketing_Week", position = 1)
    public String getId() { return null; }

    @ApiModelProperty(example = "Marketing Week 1", position = 2)
    public String getName() { return null; }

    @ApiModelProperty(example = "MW", position = 2)
    public String getInitials() {
        return null;
    }

    @ApiModelProperty(example = "Detail about this board", position = 2)
    public String getDescription() { return null; }
}
