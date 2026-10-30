package io.quire.api.model.task;

import io.quire.api.model.SimpleEntityWithId;
import io.swagger.annotations.*;

/**
 * A user as task responses give it: {@code {oid, id, name}}, e.g. in
 * {@code assignees} and {@code createdBy}.
 */
@ApiModel(description = "A user as a task gives it: `{oid, id, name}`.")
public class TaskUser extends SimpleEntityWithId {

    @ApiModelProperty(
        value = "User ID.",
        example = "john_doe"
    )
    @Override
    public String getId() { return null; }

    @ApiModelProperty(
        value = "Display name of the user.",
        example = "John Doe"
    )
    public String getName() { return null; }
}
