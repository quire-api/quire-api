package io.quire.api.model.field;

import io.swagger.annotations.ApiModelProperty;

/**
 * An option entry for a `select` field definition.
 */
public class FieldOption {

    @ApiModelProperty(
        value = "Option name (unique within the field).",
        example = "High",
        required = true
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "(Optional) Palette color index for the option's chip. "
              + "Format: two digits `[0-5][0-7]` (first = row 0-5, "
              + "second = column 0-7). Examples: `00`, `13`, `57`. "
              + "NOT a CSS hex color. If omitted on creation, a color is auto-assigned.",
        example = "13"
    )
    public String getColor() { return null; }
}
