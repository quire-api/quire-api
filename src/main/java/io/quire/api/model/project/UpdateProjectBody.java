package io.quire.api.model.project;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class UpdateProjectBody {

    @ApiModelProperty(
        value = "(Optional) New display name for this project (Markdown supported).",
        example = "My Project"
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "(Optional) New description for this project (Markdown supported).",
        example = "**Great** project to start with."
    )
    public String getDescription() { return null; }

    @ApiModelProperty(
        value = "(Optional) Target start date for this project.",
        example = "2024-01-02"
    )
    public String getStart() { return null; }

    @ApiModelProperty(
        value = "(Optional) Target due date for this project.",
        example = "2024-05-25"
    )
    public String getDue() { return null; }

    @ApiModelProperty(
        value = "(Optional) Archive toggle. Specify true to archive this project; specify false to unarchive.",
        example = "true"
    )
    public Boolean getArchived() { return null; }

    @ApiModelProperty(
        value = "(Optional) Public toggle. Specify true to make this project public; specify false to make it private.",
        example = "true"
    )
    public Boolean getPublic() { return null; }

    @ApiModelProperty(
        value = "(Optional) Followers to replace the current followers of this project (user OIDs).\n"
              + "This replaces all existing followers. To modify incrementally, use `getAddFollowers()` or `getRemoveFollowers()`."
    )
    public List<String> getFollowers() { return null; }

    @ApiModelProperty(
        value = "(Optional) Followers to add to this project (user OIDs).\n"
              + "Special values:\n"
              + "- \"me\": the current user follows the project\n"
              + "- \"app\": the application follows (receives notifications)\n\n"
              + "App follower syntax:\n"
              + "- `app|team` or `app|team|channel`\n"
              + "- `app|/path` → appended to the hook URL when invoking the registered hook.\n"
              + "  Example: hook `https://super.app/hooks/standard` + follower `app|/soc1/33456/a7`\n"
              + "  → `https://super.app/hooks/standard/soc1/33456/a7`"
    )
    public List<String> getAddFollowers() { return null; }

    @ApiModelProperty(
        value = "(Optional) Followers to remove from this project (user OIDs).\n"
              + "See `getAddFollowers()` for details on special values."
    )
    public List<String> getRemoveFollowers() { return null; }
}
