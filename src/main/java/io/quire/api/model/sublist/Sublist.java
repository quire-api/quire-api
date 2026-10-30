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
        value = "Owner this sublist belongs to: a project, organization, folder, or smart folder.",
        position = 99
    )
    public SublistOwner getOwner() { return null; }

    @ApiModelProperty(
        value = "Target start date for this record, `yyyy-MM-dd`. Omitted if not set.",
        example = "2024-01-02",
        position = 50
    )
    public String getStart() { return null; }

    @ApiModelProperty(
        value = "Target due date for this record, `yyyy-MM-dd`. Omitted if not set.",
        example = "2024-05-25",
        position = 50
    )
    public String getDue() { return null; }

    @ApiModelProperty(
        value = "Whether every task belongs to this sublist by default. If "
              + "`true`, every task of the owner is in it except the excluded "
              + "ones (with their subtasks); if `false`, only the included tasks "
              + "are, with their subtasks.",
        example = "false",
        position = 50
    )
    public boolean getAll() { return false; }
}
