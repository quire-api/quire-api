package io.quire.api.model.approval;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Request body for adding an approval category to a project
 * ({@code POST /project/add-appv-cat/{oid}}).
 */
public class AddAppvCatBody {

    @ApiModelProperty(
        value = "Category identifier (URL-safe). Must be unique within "
              + "the project. The identifier `\"\"` is reserved for the "
              + "implicit default category.",
        example = "legal",
        required = true
    )
    public String getId() { return null; }

    @ApiModelProperty(
        value = "Display name.",
        example = "Legal Review",
        required = true
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "(Optional) Users allowed to request approval in this "
              + "category. Omit or pass `null` for `anyone`; pass `[]` "
              + "for `admins only`; otherwise, a list of user OIDs."
    )
    public List<String> getClaimers() { return null; }

    @ApiModelProperty(
        value = "(Optional) Users allowed to approve / reject / "
              + "request-change in this category. Same conventions "
              + "as `claimers`."
    )
    public List<String> getApprovers() { return null; }
}
