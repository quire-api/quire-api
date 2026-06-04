package io.quire.api.model.doc;

import io.quire.api.model.work.*;
import io.swagger.annotations.*;

import java.util.List;

/**
 * Request body for {@code PUT /doc/{oid}} (and the by-ID form).
 *
 * Inherits all fields from [UpdateWorkBody](#definition-UpdateWorkBody) —
 * {@code id}, {@code name}, {@code description}, {@code iconColor},
 * {@code image}, {@code start}, {@code due}, {@code archived} — and adds
 * follower management ({@code followers} / {@code addFollowers} /
 * {@code removeFollowers}). Any field omitted from the request leaves its
 * current value unchanged.
 */
@ApiModel(parent = UpdateWorkBody.class,
    description = "Request body for updating a doc. "
                + "Extends UpdateWorkBody and adds follower management; "
                + "omitted fields preserve existing values.")
public class UpdateDocBody extends UpdateWorkBody {

    @ApiModelProperty(
        value = "(Optional) Followers (OID, ID, or email) to replace the current "
              + "followers. Applies only when the owner is a project; rejected "
              + "(400) for organization, folder, or smart-folder documents.\n\n"
              + "- `\"me\"` — the current user.\n"
              + "- `\"app\"` — the application follows (receives notifications).\n\n"
              + "App follower syntax: `app|team`, `app|team|channel`, or `app|/path`.",
        example = "[\"me\"]"
    )
    public List<String> getFollowers() { return null; }

    @ApiModelProperty(
        value = "(Optional) Followers (OID, ID, or email) to add.\n\n"
              + "- `\"me\"` — the current user.\n"
              + "- `\"app\"` — the application follows.\n\n"
              + "App follower syntax: `app|team`, `app|team|channel`, or `app|/path`.",
        example = "[\"me\"]"
    )
    public List<String> getAddFollowers() { return null; }

    @ApiModelProperty(
        value = "(Optional) Followers (OID, ID, or email) to remove.\n\n"
              + "- `\"me\"` — the current user.\n"
              + "- `\"app\"` — the application.\n\n"
              + "App follower syntax: `app|team`, `app|team|channel`, or `app|/path`.",
        example = "[\"bob@example.com\"]"
    )
    public List<String> getRemoveFollowers() { return null; }
}
