package io.quire.api.model.approval;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Request body for updating an approval category
 * ({@code PUT /project/update-appv-cat/id/{projectId}/{catId}}, or the
 * OID form {@code PUT /project/update-appv-cat/{oid}/{catId}}).
 *
 * All fields are optional; omitted keys preserve their current values.
 * At least one of {@code name}, {@code claimers}, or {@code approvers}
 * must be supplied.
 */
public class UpdateAppvCatBody {

    @ApiModelProperty(
        value = "(Optional) New display name.",
        example = "Legal Review"
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "(Optional) New claimers list — users allowed to request "
              + "approval in this category.\n\n"
              + "- `null`: anyone can request.\n"
              + "- `[]` (empty list): admins only.\n"
              + "- List of user OIDs: those specific users."
    )
    public List<String> getClaimers() { return null; }

    @ApiModelProperty(
        value = "(Optional) New approvers list — users allowed to approve, "
              + "reject, or request changes in this category.\n\n"
              + "- `null`: anyone can respond.\n"
              + "- `[]` (empty list): admins only.\n"
              + "- List of user OIDs: those specific users."
    )
    public List<String> getApprovers() { return null; }
}
