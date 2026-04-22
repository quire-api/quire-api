package io.quire.api.model.field;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;

/**
 * Request body for updating a custom-field definition
 * (`PUT /project/update-field/{oid}/{fieldName}` or
 * `PUT /insight/update-field/{oid}/{fieldName}`).
 *
 * `type` is required and must match the existing field's type (it is
 * immutable via update). Other keys are applied only when present;
 * flag keys that are omitted preserve the existing value. Use
 * `/rename-field/{oid}/{fieldName}/{newName}` to rename.
 */
public class UpdateFieldBody {

    @ApiModelProperty(
        value = "Field type. Must match the existing field's type "
              + "(type is immutable; use a separate field for a different type).",
        example = "number",
        required = true
    )
    public String getType() { return null; }

    @ApiModelProperty(value = "(Optional) See `FieldDefinition.multiple`.")
    public Boolean getMultiple() { return null; }

    @ApiModelProperty(value = "(Optional) See `FieldDefinition.hidden`.")
    public Boolean getHidden() { return null; }

    @ApiModelProperty(value = "(Optional) See `FieldDefinition.clearOnDup`.")
    public Boolean getClearOnDup() { return null; }

    @ApiModelProperty(value = "(Optional) See `FieldDefinition.private`.")
    public Boolean getPrivate() { return null; }

    @ApiModelProperty(value = "(Optional, `date` only) See `FieldDefinition.withTime`.")
    public Boolean getWithTime() { return null; }

    @ApiModelProperty(value = "(Optional, `number` only) See `FieldDefinition.percent`.")
    public Boolean getPercent() { return null; }

    @ApiModelProperty(value = "(Optional, `number`/`money` only) See `FieldDefinition.nDecimal`.")
    public Integer getNDecimal() { return null; }

    @ApiModelProperty(value = "(Optional, `money`/`formula` only) See `FieldDefinition.currency`.")
    public String getCurrency() { return null; }

    @ApiModelProperty(value = "(Optional, `duration`/`formula` only) See `FieldDefinition.durationFormat`.")
    public String getDurationFormat() { return null; }

    @ApiModelProperty(value = "(Optional, `formula` only) See `FieldDefinition.formula`.")
    public String getFormula() { return null; }

    @ApiModelProperty(value = "(Optional, `formula` only) See `FieldDefinition.resultType`.")
    public String getResultType() { return null; }

    @ApiModelProperty(
        value = "(Optional, `select` only) Replacement option list. "
              + "Providing this list replaces the entire set of options."
    )
    public List<FieldOption> getOptions() { return null; }

    @ApiModelProperty(value = "(Optional, `lookup` only) See `FieldDefinition.lookup`. Replaces the entire map.")
    public Map<String, Number> getLookup() { return null; }

    @ApiModelProperty(value = "(Optional, `lookup` only) See `FieldDefinition.lookupType`.")
    public String getLookupType() { return null; }

    @ApiModelProperty(value = "(Optional) Replacement conditional-format rules. Replaces the entire list.")
    public List<FieldConditionFormat> getConditionFormats() { return null; }
}
