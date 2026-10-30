package io.quire.api.model.field;

import io.swagger.annotations.ApiModelProperty;

/**
 * An option entry for updating a `select` field definition: a
 * {@link FieldOption} that can rename a current option with `from`.
 */
public class UpdateFieldOption extends FieldOption {

    @ApiModelProperty(
        value = "(Optional) The current name of the option this entry renames "
              + "to `name`; tasks set to that option get `name` instead. "
              + "Without it, the entry is the current option of the same "
              + "`name`, or a new option if there is none. It must name a "
              + "current option, and no two entries may match the same "
              + "option (by `name` or `from`); otherwise `400`.",
        example = "High"
    )
    public String getFrom() { return null; }
}
