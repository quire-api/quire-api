package io.quire.api.model.chat;

import io.quire.api.model.work.*;
import io.swagger.annotations.*;

import java.util.List;

/**
 * Request body for {@code PUT /chat/{oid}} (and the by-ID form).
 *
 * Inherits all fields from [UpdateWorkBody](#definition-UpdateWorkBody) —
 * {@code id}, {@code name}, {@code description}, {@code iconColor},
 * {@code image}, {@code archived} — and adds
 * follower management ({@code followers} / {@code addFollowers} /
 * {@code removeFollowers}). Any field omitted from the request leaves its
 * current value unchanged.
 */
@ApiModel(parent = UpdateWorkBody.class,
    description = "Request body for updating a chat channel. "
                + "Extends UpdateWorkBody and adds follower management; "
                + "omitted fields preserve existing values.")
public class UpdateChatBody extends UpdateWorkBody {

    // The follower fields share these; FOLLOW_RIGHT is the same everywhere.
    private static final String FOLLOW_RIGHT =
        "With read access only, `addFollowers` and `removeFollowers` may "
      + "name only yourself: `\"me\"`, or `\"app\"` to add the app on your "
      + "behalf. Adding or removing anyone else returns `403`, even along "
      + "with yourself. `followers` replaces every follower, so it needs "
      + "edit access even as `[\"me\"]`.";

    private static final String FOLLOW_EDITED =
        "An update that only follows or unfollows doesn't change `editedAt`.";

    @ApiModelProperty(
        value = "(Optional) Followers (OID, ID, or email) to replace the current "
              + "followers.\n\n"
              + "- `\"me\"` — the current user.\n"
              + "- `\"app\"` — the application follows (receives notifications).\n\n"
              + "App follower syntax: `app|team`, `app|team|channel`, or `app|/path`.\n\n"
              + FOLLOW_RIGHT + " " + FOLLOW_EDITED,
        example = "[\"me\"]"
    )
    public List<String> getFollowers() { return null; }

    @ApiModelProperty(
        value = "(Optional) Followers (OID, ID, or email) to add. Adding a user "
              + "also takes them off `mutes`.\n\n"
              + "- `\"me\"` — the current user.\n"
              + "- `\"app\"` — the application follows.\n\n"
              + "App follower syntax: `app|team`, `app|team|channel`, or `app|/path`.\n\n"
              + FOLLOW_RIGHT + " " + FOLLOW_EDITED,
        example = "[\"me\"]"
    )
    public List<String> getAddFollowers() { return null; }

    @ApiModelProperty(
        value = "(Optional) Followers (OID, ID, or email) to remove. A removed "
              + "user who follows the project is also muted (added to `mutes`), "
              + "so they stop getting notifications about it.\n\n"
              + "- `\"me\"` — the current user.\n"
              + "- `\"app\"` — the application.\n\n"
              + "App follower syntax: `app|team`, `app|team|channel`, or `app|/path`.\n\n"
              + FOLLOW_RIGHT + " " + FOLLOW_EDITED,
        example = "[\"bob@example.com\"]"
    )
    public List<String> getRemoveFollowers() { return null; }
}
