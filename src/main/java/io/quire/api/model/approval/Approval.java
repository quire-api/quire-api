package io.quire.api.model.approval;

import io.quire.api.model.SimpleEntityWithId;
import io.swagger.annotations.ApiModelProperty;

/**
 * The approval state attached to a task.
 *
 * Returned as the {@code approval} field on a task response and as the
 * response body of {@code POST /task/approve/id/{projectId}/{taskId}}
 * (or the OID form {@code POST /task/approve/{taskOid}}).
 */
public class Approval {

    @ApiModelProperty(
        value = "Identifier of the approval category this approval "
              + "belongs to. The default category has id `\"\"`.",
        example = "legal"
    )
    public String getCategory() { return null; }

    @ApiModelProperty(
        value = "Current approval state. Derived from the last POST to "
              + "`/task/approve/id/{projectId}/{taskId}` (or the OID form):\n"
              + "- `awaiting` — `request` was posted (initial or rolled-back).\n"
              + "- `approved` — `approve` was posted.\n"
              + "- `rejected` — `reject` was posted.\n"
              + "- `changes` — `change` (request-for-changes) was posted.",
        example = "awaiting",
        allowableValues = "awaiting, approved, rejected, changes"
    )
    public String getState() { return null; }

    @ApiModelProperty(
        value = "OID of the user who originally requested approval. "
              + "Preserved across subsequent approve / reject / change "
              + "transitions.\n\n"
              + "For ID-based access, prefer `requesterRef`, which "
              + "returns `{oid, id}`."
    )
    public String getRequester() { return null; }

    @ApiModelProperty(
        value = "OID of the user who most recently approved / rejected / "
              + "requested changes. Null while `state` is `awaiting`.\n\n"
              + "For ID-based access, prefer `approverRef`, which "
              + "returns `{oid, id}`."
    )
    public String getApprover() { return null; }

    @ApiModelProperty(
        value = "Requester as `{oid, id}` — same shape as "
              + "`?return=compact`. Recommended over `requester` for "
              + "callers using ID-based access."
    )
    public SimpleEntityWithId getRequesterRef() { return null; }

    @ApiModelProperty(
        value = "Approver as `{oid, id}` — same shape as "
              + "`?return=compact`. Recommended over `approver` for "
              + "callers using ID-based access. Null while `state` is "
              + "`awaiting`."
    )
    public SimpleEntityWithId getApproverRef() { return null; }

    @ApiModelProperty(
        value = "Timestamp (UTC, ISO 8601) of the last state transition.",
        example = "2026-04-24T10:05:00.000Z"
    )
    public String getToggledAt() { return null; }
}
