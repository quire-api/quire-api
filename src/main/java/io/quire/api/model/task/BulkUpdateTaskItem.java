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

    @ApiModelProperty(
        value = "Target task OID. Exactly one of `oid` / `id` must be "
              + "supplied per item.",
        example = "iuRRiKoyrxdBFhFTTo"
    )
    public String getOid() { return null; }

    @ApiModelProperty(
        value = "Target task ID. Exactly one of `oid` / `id` must be "
              + "supplied per item.",
        example = "42"
    )
    public Integer getId() { return null; }
}
