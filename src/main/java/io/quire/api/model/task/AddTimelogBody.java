package io.quire.api.model.task;

import io.swagger.annotations.ApiModelProperty;

/**
 * Request body for {@code POST /task/add-timelog/id/{projectId}/{taskId}}
 * (or the OID form {@code POST /task/add-timelog/{taskOid}}).
 *
 * Identity of a timelog is the triple {@code (user, start, end)}.
 * Re-adding an existing triple returns {@code 409 Conflict} — use
 * {@code PUT /task/update-timelog/...} to amend an existing log.
 */
public class AddTimelogBody {

    @ApiModelProperty(
        value = "Start timestamp (ISO 8601, UTC). Sub-second precision is "
              + "truncated to whole seconds.",
        example = "2026-04-26T09:00:00Z",
        required = true
    )
    public String getStart() { return null; }

    @ApiModelProperty(
        value = "End timestamp (ISO 8601, UTC). Must be on or after `start`. "
              + "Sub-second precision is truncated to whole seconds.",
        example = "2026-04-26T10:30:00Z",
        required = true
    )
    public String getEnd() { return null; }

    @ApiModelProperty(
        value = "(Optional) User identifier (OID, ID, or email) the log is "
              + "credited to. Omitted, `null`, or empty defaults to the "
              + "authenticated caller. Unknown users return `400 Bad Request`.",
        example = "john.doe@example.com"
    )
    public String getUser() { return null; }

    @ApiModelProperty(
        value = "(Optional) Whether this log is billable. Omitted or `null` "
              + "defaults to `true`.",
        example = "true"
    )
    public Boolean getBillable() { return null; }

    @ApiModelProperty(
        value = "(Optional) Free-form note for the log. Omitted, `null`, "
              + "or empty stores no note.",
        example = "Pair-programming session"
    )
    public String getNote() { return null; }
}
