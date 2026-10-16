package io.quire.api.model.sublist;

import io.quire.api.model.work.*;
import io.swagger.annotations.*;

public class Sublist extends Work {

    @Override
    @ApiModelProperty(
        value = "URL of this sublist on the Quire website.",
        example = "https://quire.io/w/my_project?sublist=Highlight101"
    )
    public String getUrl() { return null; }

    @Override
    @ApiModelProperty(
        value = "Project this sublist belongs to.",
        position = 99
    )
    public SublistOwner getOwner() { return null; }

    @ApiModelProperty(
        value = "Target start date for this record, or null if not specified.",
        example = "2024-01-02",
        position = 50
    )
    public String getStart() { return null; }

    @ApiModelProperty(
        value = "Target due date for this record, or null if not specified.",
        example = "2024-05-25",
        position = 50
    )
    public String getDue() { return null; }
}
