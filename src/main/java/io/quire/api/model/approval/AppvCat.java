package io.quire.api.model.approval;

import io.quire.api.model.SimpleEntityWithId;
import io.quire.api.model.SimpleIdentity;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * An approval category defined on a project.
 *
 * Projects carry an implicit default category (id `""`) that is always
 * available; additional categories can be added via
 * {@code POST /project/add-appv-cat/id/{projectId}} (or the OID form).
 */
public class AppvCat {

    @ApiModelProperty(
        value = "Category identifier (URL-safe). The implicit default "
              + "category has an empty id.",
        example = "legal"
    )
    public String getId() { return null; }

    @ApiModelProperty(
        value = "Display name.",
        example = "Legal Review"
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "Users allowed to request approval in this category.\n\n"
              + "- Omitted from the response: anyone can request.\n"
              + "- `[]` (empty list): admins only.\n"
              + "- List of user OIDs: those specific users.\n\n"
              + "For ID-based access, prefer `claimerRefs`, which "
              + "returns `{oid, id}` pairs.",
        position = 20
    )
    public List<String> getClaimers() { return null; }

    @ApiModelProperty(
        value = "Users allowed to approve, reject, or request changes in "
              + "this category.\n\n"
              + "- Omitted from the response: anyone can respond.\n"
              + "- `[]` (empty list): admins only.\n"
              + "- List of user OIDs: those specific users.\n\n"
              + "For ID-based access, prefer `approverRefs`, which "
              + "returns `{oid, id}` pairs.",
        position = 20
    )
    public List<String> getApprovers() { return null; }

    @ApiModelProperty(
        value = "Claimers as `{oid, id}` pairs — same shape as "
              + "`?return=compact`. Recommended over `claimers` for "
              + "callers using ID-based access. Tri-state semantics "
              + "match `claimers`: omitted = anyone; `[]` = admins only; "
              + "list = those specific users.",
        position = 21
    )
    public List<SimpleEntityWithId> getClaimerRefs() { return null; }

    @ApiModelProperty(
        value = "Approvers as `{oid, id}` pairs — same shape as "
              + "`?return=compact`. Recommended over `approvers` for "
              + "callers using ID-based access. Tri-state semantics "
              + "match `approvers`.",
        position = 21
    )
    public List<SimpleEntityWithId> getApproverRefs() { return null; }

    @ApiModelProperty(
        value = "User who created this category.",
        position = 90
    )
    public SimpleIdentity getCreatedBy() { return null; }

    @ApiModelProperty(
        value = "Timestamp (UTC, ISO 8601) when this category was created.",
        example = "2026-04-24T10:00:00.000Z",
        position = 90
    )
    public String getCreatedAt() { return null; }
}
