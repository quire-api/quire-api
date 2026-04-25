package io.quire.api.model.task;

import io.swagger.annotations.ApiModelProperty;

/**
 * Request body for {@code PUT /task/update-timelog/id/{projectId}/{taskId}}
 * (or the OID form {@code PUT /task/update-timelog/{taskOid}}).
 *
 * The existing log is identified by the {@code start}, {@code end},
 * and (optionally) {@code user} query parameters; this body carries
 * the new values. <strong>Any field omitted, or set to {@code null},
 * preserves the existing value</strong> — sending {@code null} for a
 * field never resets it to the default. To clear {@code note},
 * send an empty string.
 */
public class UpdateTimelogBody {

    @ApiModelProperty(
        value = "(Optional) New start timestamp (ISO 8601, UTC). "
              + "Sub-second precision is truncated to whole seconds. "
              + "Omit or set `null` to preserve.",
        example = "2026-04-26T09:15:00Z"
    )
    public String getStart() { return null; }

    @ApiModelProperty(
        value = "(Optional) New end timestamp (ISO 8601, UTC). Must be on "
              + "or after the (new or existing) start. Sub-second precision "
              + "is truncated to whole seconds. Omit or set `null` to preserve.",
        example = "2026-04-26T10:45:00Z"
    )
    public String getEnd() { return null; }

    @ApiModelProperty(
        value = "(Optional) New user the log is credited to (OID, ID, or "
              + "email). Omit or set `null` to preserve. Send empty string "
              + "to reset to the authenticated caller.",
        example = "john.doe@example.com"
    )
    public String getUser() { return null; }

    @ApiModelProperty(
        value = "(Optional) New billable flag. Omit or set `null` to preserve.",
        example = "false"
    )
    public Boolean getBillable() { return null; }

    @ApiModelProperty(
        value = "(Optional) New note. Omit or set `null` to preserve. "
              + "Send empty string to clear.",
        example = "Updated note"
    )
    public String getNote() { return null; }
}
