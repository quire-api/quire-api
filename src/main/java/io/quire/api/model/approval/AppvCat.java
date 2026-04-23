package io.quire.api.model.approval;

import io.quire.api.model.SimpleIdentity;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * An approval category defined on a project.
 *
 * Projects carry an implicit default category (id `""`) that is always
 * available; additional categories can be added via
 * {@code POST /project/add-appv-cat/{oid}}.
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
        value = "Users allowed to request approval in this category. "
              + "Omitted means `anyone` can claim; an empty list means "
              + "`admins only`; otherwise, the listed user OIDs.",
        position = 20
    )
    public List<String> getClaimers() { return null; }

    @ApiModelProperty(
        value = "Users allowed to approve / reject / request-change in "
              + "this category. Same conventions as `claimers`.",
        position = 20
    )
    public List<String> getApprovers() { return null; }

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
