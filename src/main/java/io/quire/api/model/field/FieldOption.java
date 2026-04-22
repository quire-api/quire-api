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
        value = "Color code (hex) for the option's chip. "
              + "If omitted on creation, a color is auto-assigned.",
        example = "#ff0000"
    )
    public String getColor() { return null; }
}
