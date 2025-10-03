package io.quire.api.model.sublist;

import io.swagger.annotations.*;

public class Change {

    @ApiModelProperty(
        value = "OID of the task to include or exclude.",
        required = true,
        example = "2MmYOpJH_ZLeehIjjytH1Rwr"
    )
    public String getTask() { return null; }

    @ApiModelProperty(
        value = "Whether to exclude the task. Default: false (include).",
        example = "false"
    )
    public boolean getExclude() { return false; }

    @ApiModelProperty(
        value = "Whether the operation applies only to the specified task. "
              + "Default: false — all descendant tasks are also included or excluded. "
              + "Note: this does not change descendants that were explicitly included or excluded in prior operations.",
        example = "false"
    )
    public boolean getSingle() { return false; }
}
