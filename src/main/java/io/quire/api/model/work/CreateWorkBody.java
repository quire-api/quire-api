package io.quire.api.model.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
        value = "(Optional) Description of the record (Markdown supported).",
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
