package io.quire.api.model.field;

import io.swagger.annotations.ApiModelProperty;

/**
 * Response shape for per-field endpoints
 * (`/add-field`, `/update-field`, `/rename-field`, `/move-field`).
 *
 * Identical to {@link FieldDefinition} but with an extra `name` key.
 * (When a field definition appears as a value in `Project.fields` or
 * `Insight.fields`, the map key is the name, so {@link FieldDefinition}
 * alone is used there.)
 */
public class FieldDefinitionWithName extends FieldDefinition {

    @ApiModelProperty(
        value = "Field name (equals the path `fieldName` for update/rename/move, "
              + "or the `name` supplied in the body for add).",
        example = "Priority",
        required = true
    )
    public String getName() { return null; }
}
