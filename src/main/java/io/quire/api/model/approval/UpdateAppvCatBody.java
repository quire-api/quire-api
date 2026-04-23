package io.quire.api.model.approval;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Request body for updating an approval category
 * ({@code PUT /project/update-appv-cat/{oid}/{catId}}).
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
        value = "(Optional) New claimers list. Pass `null` for `anyone`, "
              + "`[]` for `admins only`, or a list of user OIDs."
    )
    public List<String> getClaimers() { return null; }

    @ApiModelProperty(
        value = "(Optional) New approvers list. Same conventions as `claimers`."
    )
    public List<String> getApprovers() { return null; }
}
