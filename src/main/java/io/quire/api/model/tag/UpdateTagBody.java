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
        value = "(Optional) Tag color (palette index or hex, depending on your client).",
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
