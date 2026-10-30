package io.quire.api.model.organization;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class UpdateOrganizationBody {

    // The follower fields share this.
    private static final String FOLLOW_RIGHT =
        "Needs edit access to the organization, even for only `\"me\"`: "
      + "unlike a project, an organization can't be followed with read "
      + "access.";

    @ApiModelProperty(
        value = "(Optional) New display name for this organization (Markdown "
              + "supported). Needs an admin of the organization.",
        example = "My Organization"
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "(Optional) New description for this organization (Markdown "
              + "supported). An embedded `data:` image is rejected — upload the "
              + "image as an attachment instead. Needs an admin of the "
              + "organization.",
        example = "**Great** organization to start with."
    )
    public String getDescription() { return null; }

    @ApiModelProperty(
        value = "(Optional) Followers to replace the current followers of this organization (OID, ID, or email).\n"
              + "This replaces all existing followers. To modify incrementally, use `addFollowers` or `removeFollowers`.\n\n"
              + "Accepts the same special values as `addFollowers`: "
              + "`\"me\"` (the current user) and `\"app\"` / `\"app|team\"` / "
              + "`\"app|team|channel\"` / `\"app|/path\"` (the application).\n\n"
              + FOLLOW_RIGHT
    )
    public List<String> getFollowers() { return null; }

    @ApiModelProperty(
        value = "(Optional) Followers to add to this organization (OID, ID, or email).\n"
              + "Special values:\n"
              + "- \"me\": the current user follows the organization\n"
              + "- \"app\": the application follows (receives notifications)\n\n"
              + "App follower syntax:\n"
              + "- `app|team` or `app|team|channel`\n"
              + "- `app|/path` → appended to the hook URL when invoking the registered hook.\n"
              + "  Example: hook `https://super.app/hooks/standard` + follower `app|/soc1/33456/a7`\n"
              + "  → `https://super.app/hooks/standard/soc1/33456/a7`\n\n"
              + FOLLOW_RIGHT
    )
    public List<String> getAddFollowers() { return null; }

    @ApiModelProperty(
        value = "(Optional) Followers to remove from this organization (OID, ID, or email).\n"
              + "Accepts the same special values as `addFollowers`: "
              + "`\"me\"` (the current user) and `\"app\"` / `\"app|team\"` / "
              + "`\"app|team|channel\"` / `\"app|/path\"` (the application).\n\n"
              + FOLLOW_RIGHT
    )
    public List<String> getRemoveFollowers() { return null; }
}
