package io.quire.api.model.sublist;

import io.swagger.annotations.*;

public class Change {

    @ApiModelProperty(
        value = "OID of the task to include or exclude. A task is skipped if "
              + "it doesn't exist, if it isn't under the sublist's owner, or, "
              + "for a sublist shared with an external team, if it isn't "
              + "shared with that team.",
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
              + "Default: false — the task and all its descendants are included or "
              + "excluded, and earlier includes or excludes of its descendants are "
              + "cleared. With true, only the task itself changes; its subtasks "
              + "keep their current state.",
        example = "false"
    )
    public boolean getSingle() { return false; }
}
