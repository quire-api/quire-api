package io.quire.api.model.tag;

import io.swagger.annotations.ApiModelProperty;

public class UpdateTagBody {

    @ApiModelProperty(
        value = "(Optional) New display name for the tag.",
        example = "Later",
        required = false
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "(Optional) Tag color index from Quire's predefined palette. "
              + "Two-digit code `[0-5][0-7]`: first digit 0-5, second digit 0-7 "
              + "(e.g. `00`, `35`, `57`). NOT a CSS hex color.",
        example = "35",
        required = false
    )
    public String getColor() { return null; }

    @ApiModelProperty(
        value = "(Optional) Whether the tag is global (available across projects). "
              + "If set to false, you must also provide `project`.",
        example = "true",
        required = false
    )
    public boolean getGlobal() { return false; }

    @ApiModelProperty(
        value = "(Optional) Project OID that this tag is limited to. "
              + "Used only when `global` is explicitly set to false; ignored otherwise.",
        example = "Dyh2YkFcu9uLgLFIeN1kB4Ld",
        required = false
    )
    public String getProject() { return null; }
}
