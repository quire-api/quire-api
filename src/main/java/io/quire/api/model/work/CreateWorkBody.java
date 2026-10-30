package io.quire.api.model.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class CreateWorkBody {

    // The create and update bodies share this: every icon the server accepts.
    static final String IMAGES =
        "icon-view-list, icon-view-kanban, icon-grid, icon-view-timeline, "
      + "icon-calendar, icon-timer, icon-briefcase-o, icon-rocket-o, "
      + "icon-bug-o, icon-user-o, icon-colleagues-o, icon-lightbulb-o, "
      + "icon-leaf-o, icon-clapperboard, icon-sitemap-o, icon-list, "
      + "icon-flash-on-o, icon-piggy-bank-o, icon-graduation-cap-o, "
      + "icon-paper-plane-o, icon-globe-o, icon-music-o, icon-detail, "
      + "icon-beach-o, icon-paper, icon-home-o, icon-building, "
      + "icon-database-o, icon-microscope-o, icon-hamburger-o, "
      + "icon-trophy-o, icon-thumbs-o-up, icon-thumbs-o-down, icon-smile-o, "
      + "icon-frown-o, icon-meh-o, icon-bullseye, icon-incomplete, "
      + "icon-filter-o, icon-comment, icon-view-insight, "
      + "icon-view-dashboard, icon-square-dotted-o";

    @ApiModelProperty(
        value = "(Optional) ID for this record, unique among the owner's records "
              + "of the same kind (e.g. a project's sublists). If omitted or "
              + "invalid, Quire derives one from `name`; if already taken, Quire "
              + "changes it by appending characters. Neither is an error, so read "
              + "the `id` back from the response.",
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
        value = "(Optional) Icon image identifier, one of the listed values.",
        example = "icon-view-kanban",
        allowableValues = IMAGES
    )
    public String getImage() { return null; }

    @ApiModelProperty(
        value = "(Optional) OID of the external team to share this record "
              + "with; it is then shared with the members of the project and "
              + "of that team, and is always `private`.\n\n"
              + "Only a project's record can carry one — an organization, a "
              + "folder or a smart folder has no external team — and it must "
              + "be one of the project's external teams; any other value is "
              + "`400`. It cannot be combined with `members` either (`400`).\n\n"
              + "An external team member shares a record with every member "
              + "only through their own team: with `members` omitted or null, "
              + "they must set `partner` to one of their teams. Otherwise "
              + "`400` names their teams, as it does for a team of the project "
              + "that isn't theirs.\n\n"
              + "Settable only when creating the record.",
        example = "rcBHBYXZSiyDRrHrWPutatfF",
        position = 15
    )
    public String getPartner() { return null; }

    @ApiModelProperty(
        value = "(Optional) Hide this record from guests and from the visitors "
              + "of a public project or of a share link, like a custom field's "
              + "`private`. A choice only when `members` is null and `partner` "
              + "is null; defaults to false (everyone). Otherwise it is always "
              + "true: a record shared with an external team, with selected "
              + "members or with the admins only is hidden from them, so "
              + "`false` is rejected with `400`.",
        example = "false",
        position = 16
    )
    public Boolean getPrivate() { return null; }

    @ApiModelProperty(
        value = "(Optional) Users who can see this record, and the only ones "
              + "it is shown to. Each entry is a user ID, OID, or email, or "
              + "`\"me\"` for the authenticated user.\n\n"
              + "- Omit the field, or pass null — every member of the owner can "
              + "see it, and so can guests and the visitors of a public project "
              + "or of a share link unless `private`. This is the default. (A "
              + "reminder differs: omitting its "
              + "`members` means only the authenticated user.) An external "
              + "team member must also set `partner` to one of their teams.\n"
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
}
