package io.quire.api.model.project;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class UpdateProjectBody {

    // The follower fields share this; it is the same text everywhere.
    private static final String FOLLOW_RIGHT =
        "With read access only, `addFollowers` and `removeFollowers` may "
      + "name only yourself: `\"me\"`, or `\"app\"` to add the app on your "
      + "behalf. Adding or removing anyone else returns `403`, even along "
      + "with yourself. `followers` replaces every follower, so it needs "
      + "edit access even as `[\"me\"]`.";

    @ApiModelProperty(
        value = "(Optional) New display name for this project (Markdown supported).",
        example = "My Project"
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "(Optional) New description for this project (Markdown supported). An embedded `data:` image is rejected — upload the image as an attachment instead.",
        example = "**Great** project to start with."
    )
    public String getDescription() { return null; }

    @ApiModelProperty(
        value = "(Optional) Target start date for this project: a date only, "
              + "e.g. `2024-01-02`; a value with a time of day returns `400`. "
              + "`null` clears it. Needs a project admin.",
        example = "2024-01-02"
    )
    public String getStart() { return null; }

    @ApiModelProperty(
        value = "(Optional) Target due date for this project: a date only, "
              + "e.g. `2024-05-25`; a value with a time of day returns `400`. "
              + "`null` clears it. Needs a project admin.",
        example = "2024-05-25"
    )
    public String getDue() { return null; }

    @ApiModelProperty(
        value = "(Optional) Archive toggle. Specify true to archive this project; specify false to unarchive.",
        example = "true"
    )
    public Boolean getArchived() { return null; }

    @ApiModelProperty(
        value = "(Optional) Public toggle. Specify true to make this project public; specify false to make it private. "
              + "Making it public needs a plan with public projects; otherwise `402` (`code` `469`).",
        example = "true"
    )
    public Boolean getPublic() { return null; }

    @ApiModelProperty(
        value = "(Optional) Followers to replace the current followers of this project (OID, ID, or email).\n"
              + "This replaces all existing followers. To modify incrementally, use `addFollowers` or `removeFollowers`.\n\n"
              + "Accepts the same special values as `addFollowers`: "
              + "`\"me\"` (the current user) and `\"app\"` / `\"app|team\"` / "
              + "`\"app|team|channel\"` / `\"app|/path\"` (the application).\n\n"
              + FOLLOW_RIGHT
    )
    public List<String> getFollowers() { return null; }

    @ApiModelProperty(
        value = "(Optional) Followers to add to this project (OID, ID, or email).\n"
              + "Special values:\n"
              + "- \"me\": the current user follows the project\n"
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
        value = "(Optional) Followers to remove from this project (OID, ID, or email).\n"
              + "Accepts the same special values as `addFollowers`: "
              + "`\"me\"` (the current user) and `\"app\"` / `\"app|team\"` / "
              + "`\"app|team|channel\"` / `\"app|/path\"` (the application).\n\n"
              + FOLLOW_RIGHT
    )
    public List<String> getRemoveFollowers() { return null; }
}
