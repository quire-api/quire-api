package io.quire.api.model.task;

import io.swagger.annotations.ApiModelProperty;

/**
 * One item in a {@code PUT /task/bulk-update/...} request body.
 *
 * Extends {@link UpdateTaskBody} with the target identifier — exactly
 * one of {@code oid} or {@code id} must be supplied. All other fields
 * are interpreted as new values for the target task, identical to the
 * single-item {@code PUT /task/...} body.
 *
 * Supplying both {@code oid} and {@code id}, or neither, returns
 * {@code 400 Bad Request} (and rolls back the whole batch). Supplying
 * an OID or ID that doesn't resolve to a task in the project is NOT
 * an error — the slot is silently skipped (the corresponding response
 * element is {@code null}). See the bulk-update endpoint notes.
 */
public class BulkUpdateTaskItem extends UpdateTaskBody {

    // The oid and id properties share this.
    static final String TARGET_RULE =
        "Exactly one of `oid` / `id` must be supplied per item; both or "
      + "neither is rejected with `400`. If it doesn't match a live task "
      + "in the project, the item is skipped and its response slot is "
      + "`null`.";

    @ApiModelProperty(
        value = "Target task OID. " + TARGET_RULE,
        example = "iuRRiKoyrxdBFhFTTo"
    )
    public String getOid() { return null; }

    @ApiModelProperty(
        value = "Target task ID, an integer; a `\"#42\"` string works "
              + "too. " + TARGET_RULE,
        example = "42"
    )
    public Integer getId() { return null; }
}
