package io.quire.api.model.project;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class UpdateProjectBody {

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
