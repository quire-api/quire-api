package io.quire.api.model.task;

import io.quire.api.model.*;
import io.swagger.annotations.*;

public class TaskInfo extends Entity {

    // TaskWithParentInfo shares this.
    static final String PARENT_CHAIN =
        "The parent task as `{oid, id, parent}`, nested up to 10 ancestors. "
      + "On the task itself, `null` if it has no parent; in the chain, the "
      + "top-level ancestor has no `parent` key.\n\n"
      + "Past 10 ancestors, the 10th ancestor's `parent` is "
      + "`{\"parent\": \"<OID>\"}`, holding the OID of the 11th, "
      + "with no `oid` or `id`, to mark the cut-off.";

    @ApiModelProperty(
        value = "Task id.",
        example = "12"
    )
    public int getId() { return 0; }

    @ApiModelProperty(
        value = PARENT_CHAIN,
        position = 10
    )
    public TaskInfo getParent() { return null; }
}
