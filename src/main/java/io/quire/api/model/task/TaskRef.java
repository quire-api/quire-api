package io.quire.api.model.task;

import io.quire.api.model.Entity;
import io.swagger.annotations.ApiModelProperty;

/**
 * Compact task reference: {@code {oid, id}}. Same shape as the
 * {@code ?return=compact} response. Used by fields like
 * {@link SimpleTask#getSuccessorRefs()} that surface task references
 * by both OID and ID.
 */
public class TaskRef extends Entity {

    @ApiModelProperty(
        value = "Task id.",
        example = "12"
    )
    public int getId() { return 0; }
}
