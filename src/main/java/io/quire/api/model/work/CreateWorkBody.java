package io.quire.api.model.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class CreateWorkBody {

    @ApiModelProperty(
        value = "(Optional) ID for this record. If omitted, Quire generates one automatically. Must be unique within the project.",
        example = "Highlight101"
    )
    public String getId() { return null; }

    @ApiModelProperty(
        value = "Display name of the record (Markdown supported).",
        example = "Highlight 101",
        required = true
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "(Optional) Description of the record (Markdown supported). An embedded `data:` image is rejected — upload the image as an attachment instead.",
        example = "**Great** record to start with."
    )
    public String getDescription() { return null; }

    @ApiModelProperty(
        value = "(Optional) Icon color index from Quire's predefined palette. "
              + "Two-digit code `[0-5][0-7]`: first digit 0-5, second digit 0-7 "
              + "(e.g. `00`, `37`, `57`). NOT a CSS hex color.",
        example = "37"
    )
    public String getIconColor() { return null; }

    @ApiModelProperty(
        value = "(Optional) Icon image identifier.",
        example = "icon-view-kanban",
        allowableValues = "icon-view-list, icon-view-kanban, icon-briefcase-o, "
                        + "icon-rocket-o, icon-bug-o, icon-leaf-o, icon-clapperboard, "
                        + "icon-sitemap-o, icon-flash-on-o, icon-piggy-bank-o, "
                        + "icon-graduation-cap-o, icon-paper-plane-o, icon-globe-o, "
                        + "icon-music-o, icon-detail, icon-beach-o, icon-paper, "
                        + "icon-home-o, icon-building, icon-database-o, "
                        + "icon-microscope-o, icon-hamburger-o, icon-trophy-o, "
                        + "icon-thumbs-o-up, icon-thumbs-o-down, icon-smile-o, "
                        + "icon-frown-o, icon-meh-o, icon-bullseye, icon-square-dotted-o"
    )
    public String getImage() { return null; }

    @ApiModelProperty(
        value = "(Optional) OID of the external team this record belongs to.",
        example = "rcBHBYXZSiyDRrHrWPutatfF",
        position = 15
    )
    public String getPartner() { return null; }

    @ApiModelProperty(
        value = "(Optional) Users who can see this record, and the only ones "
              + "it is shown to. Each entry is a user ID, OID, or email, or "
              + "`\"me\"` for the authenticated user.\n\n"
              + "- Omit the field, or pass null — every member of the owner can "
              + "see it. This is the default. (A reminder differs: omitting its "
              + "`members` means only the authenticated user.)\n"
              + "- Pass `[]` — only the owner's admins can see it.\n"
              + "- Pass a list — only those users can see it.\n\n"
              + "Rejected with `400` when:\n"
              + "- a listed user is not a member of the owner. Unlike a task's "
              + "`assignees`, which silently drops a non-member, this is an "
              + "error: dropping the last one would leave `[]`, which means "
              + "something else entirely.\n"
              + "- the list is non-empty and does not include the authenticated "
              + "user — you would not be able to see what you just created.\n"
              + "- `partner` is also set, since an external-team record is "
              + "already shared with all members of the project and of the team.\n\n"
              + "Settable only when creating the record. It cannot be changed "
              + "afterwards, so recreate the record to change who can see it.",
        example = "[\"me\", \"john\"]",
        position = 60
    )
    public List<String> getMembers() { return null; }

    @ApiModelProperty(
        value = "(Optional) Target start date.",
        example = "2024-01-02",
        position = 50
    )
    public String getStart() { return null; }

    @ApiModelProperty(
        value = "(Optional) Target due date.",
        example = "2024-05-25",
        position = 50
    )
    public String getDue() { return null; }
}
