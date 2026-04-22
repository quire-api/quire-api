package io.quire.api.model.field;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;

/**
 * Request body for adding a custom-field definition
 * (`POST /project/add-field/{oid}` or `POST /insight/add-field/{oid}`).
 *
 * All the type-specific keys of {@link FieldDefinition} are accepted;
 * see that class for the per-type semantics. The extra `name` field
 * carries the field's identifier (supplied in the body, not the URL).
 */
public class AddFieldBody {

    @ApiModelProperty(
        value = "Unique name for this field (cannot contain `}`, `\"`, or `\\`).",
        example = "Priority",
        required = true
    )
    public String getName() { return null; }

    @ApiModelProperty(
        value = "Field type. See `FieldDefinition.type`.",
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

    @ApiModelProperty(value = "(Required for `formula`) See `FieldDefinition.formula`.")
    public String getFormula() { return null; }

    @ApiModelProperty(value = "(Required for `formula`) See `FieldDefinition.resultType`.")
    public String getResultType() { return null; }

    @ApiModelProperty(value = "(Required for `select`) See `FieldDefinition.options`.")
    public List<FieldOption> getOptions() { return null; }

    @ApiModelProperty(value = "(Required for `lookup`) See `FieldDefinition.lookup`.")
    public Map<String, Number> getLookup() { return null; }

    @ApiModelProperty(value = "(Optional, `lookup` only) See `FieldDefinition.lookupType`.")
    public String getLookupType() { return null; }

    @ApiModelProperty(value = "(Optional) See `FieldDefinition.conditionFormats`.")
    public List<FieldConditionFormat> getConditionFormats() { return null; }
}
