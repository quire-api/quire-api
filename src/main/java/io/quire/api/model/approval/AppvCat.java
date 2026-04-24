package io.quire.api.model.approval;

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
              + "- List of user OIDs: those specific users.",
        position = 20
    )
    public List<String> getClaimers() { return null; }

    @ApiModelProperty(
        value = "Users allowed to approve, reject, or request changes in "
              + "this category.\n\n"
              + "- Omitted from the response: anyone can respond.\n"
              + "- `[]` (empty list): admins only.\n"
              + "- List of user OIDs: those specific users.",
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
