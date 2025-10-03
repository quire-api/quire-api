package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class Timelog {

    @ApiModelProperty(
        value = "Start timestamp (UTC) for this time log.",
        example = "2023-02-20T00:00:00.000Z",
        position = 1
    )
    public String getStart() { return null; }

    @ApiModelProperty(
        value = "End timestamp (UTC) for this time log.",
        example = "2023-02-20T00:05:35.000Z",
        position = 2
    )
    public String getEnd() { return null; }

    @ApiModelProperty(
        value = "User who recorded this time log.",
        position = 3
    )
    public SimpleIdentity getUser() { return null; }

    @ApiModelProperty(
        value = "Whether this time log is billable. May be omitted in responses when false.",
        example = "true",
        position = 4
    )
    public boolean getBillable() { return false; }

    @ApiModelProperty(
        value = "Optional note for this time log.",
        example = "A piece of cake",
        position = 5
    )
    public String getNote() { return null; }
}
