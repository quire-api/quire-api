package io.quire.api.model.field;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;

/**
 * A custom-field definition on a Project or Insight view.
 *
 * Field-definition shape returned by the Project and Insight APIs and
 * accepted (with a few differences) by the add/update extensions.
 *
 * Enum-like string values are emitted in the canonical casing shown below
 * and accepted case-insensitively on input.
 */
public class FieldDefinition {

    @ApiModelProperty(
        value = "Field type. One of:\n"
              + "`text`, `number`, `money`, `date`, `duration`, `select`,\n"
              + "`checkbox`, `user`, `task`, `hyperlink`, `email`,\n"
              + "`formula`, `file`, `lookup`.\n"
              + "Required on `POST`; immutable on `PUT` (must match the existing type).",
        example = "number",
        required = true
    )
    public String getType() { return null; }

    @ApiModelProperty(
        value = "(Optional) Allow multiple values. "
              + "Applies to `select`, `user`, `task`, and `file` (always true for `file`).",
        example = "true"
    )
    public Boolean getMultiple() { return null; }

    @ApiModelProperty(
        value = "(Optional) Hide this field from the task detail panel.",
        example = "false"
    )
    public Boolean getHidden() { return null; }

    @ApiModelProperty(
        value = "(Optional) Clear this field when duplicating a task.",
        example = "false"
    )
    public Boolean getClearOnDup() { return null; }

    @ApiModelProperty(
        value = "(Optional) Restrict access to non-guest members only.",
        example = "false"
    )
    public Boolean getPrivate() { return null; }

    @ApiModelProperty(
        value = "(Optional, `date` only) Include time-of-day in addition to the date.",
        example = "false"
    )
    public Boolean getWithTime() { return null; }

    @ApiModelProperty(
        value = "(Optional, `number` only) Format the value as a percentage.",
        example = "false"
    )
    public Boolean getPercent() { return null; }

    @ApiModelProperty(
        value = "(Optional, `number`/`money` only) Number of decimal digits. "
              + "Range: `-1` (no rounding) to `3`.",
        example = "2"
    )
    public Integer getNDecimal() { return null; }

    @ApiModelProperty(
        value = "(Optional, `money` or `formula` only) Currency symbol. "
              + "When omitted on a money field, defaults to `$`.",
        example = "USD"
    )
    public String getCurrency() { return null; }

    @ApiModelProperty(
        value = "(Optional, `duration` or `formula` only) Duration display format. One of:\n"
              + "`hh:mm:ss`, `hh:mm`, `1h1m`, `1h`, `1d1h`, `dd:hh:mm:ss`, `dd:hh:mm`, `dd:hh`.",
        example = "hh:mm"
    )
    public String getDurationFormat() { return null; }

    @ApiModelProperty(
        value = "(Required for `formula`) Formula expression.",
        example = "SUM(Subtask.Amount)"
    )
    public String getFormula() { return null; }

    @ApiModelProperty(
        value = "(Required for `formula`) Expected result type. One of:\n"
              + "`text`, `number`, `money`, `date`, `duration`, `checkbox`.",
        example = "number"
    )
    public String getResultType() { return null; }

    @ApiModelProperty(
        value = "(Required for `select`) Option list."
    )
    public List<FieldOption> getOptions() { return null; }

    @ApiModelProperty(
        value = "(Required for `lookup`) Map from lookup key to numeric value.",
        example = "{\"A\":1,\"B\":2,\"C\":3}"
    )
    public Map<String, Number> getLookup() { return null; }

    @ApiModelProperty(
        value = "(Optional, `lookup` only) Source type for lookup keys. One of:\n"
              + "`User`, `Task`, `Project`, `Organization`. Default: `User`.",
        example = "User"
    )
    public String getLookupType() { return null; }

    @ApiModelProperty(
        value = "(Optional) Conditional-format rules.\n"
              + "Applicable to `date` (date rules) and to `number`/`money`/`duration`/`lookup`, "
              + "including a `formula` whose `resultType` resolves to one of those (value rules)."
    )
    public List<FieldConditionFormat> getConditionFormats() { return null; }
}
