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
 * Enum-like string values are emitted in the canonical casing shown on
 * each field below (casing varies per field — e.g. `type` is lowercase,
 * `lookupType` is PascalCase) and accepted case-insensitively on input.
 */
public class FieldDefinition {

    @ApiModelProperty(
        value = "Field type.",
        example = "number",
        allowableValues = "text, number, money, date, duration, select, "
                        + "checkbox, user, task, hyperlink, email, formula, "
                        + "file, lookup",
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
              + "`-1` means no rounding.",
        example = "2",
        allowableValues = "-1, 0, 1, 2, 3"
    )
    public Integer getNDecimal() { return null; }

    @ApiModelProperty(
        value = "(Optional, `money` or `formula` only) Currency symbol. "
              + "When omitted on a money field, defaults to `$`.",
        example = "USD"
    )
    public String getCurrency() { return null; }

    @ApiModelProperty(
        value = "(Optional, `duration` or `formula` only) Duration display format.",
        example = "hh:mm",
        allowableValues = "hh:mm:ss, hh:mm, 1h1m, 1h, 1d1h, dd:hh:mm:ss, "
                        + "dd:hh:mm, dd:hh"
    )
    public String getDurationFormat() { return null; }

    @ApiModelProperty(
        value = "(Required for `formula`) Formula expression.",
        example = "SUM(Subtask.Amount)"
    )
    public String getFormula() { return null; }

    @ApiModelProperty(
        value = "(Optional, `formula` only) Expected result type.",
        example = "number",
        allowableValues = "text, number, money, date, duration, checkbox"
    )
    public String getResultType() { return null; }

    @ApiModelProperty(
        value = "(Required for `select`) Option list."
    )
    public List<FieldOption> getOptions() { return null; }

    @ApiModelProperty(
        value = "(Required for `lookup`) Map from lookup key to numeric value. "
              + "Keys are OIDs of the configured `lookupType` entity "
              + "(e.g. user OIDs when `lookupType=User`). Values are numbers. "
              + "Example: `{\"abc123\": 1, \"def456\": 2}`."
    )
    public Map<String, Number> getLookup() { return null; }

    @ApiModelProperty(
        value = "(Optional, `lookup` only) Source type for lookup keys. Default: `User`.",
        example = "User",
        allowableValues = "User, Task, Project, Organization"
    )
    public String getLookupType() { return null; }

    @ApiModelProperty(
        value = "(Optional) Conditional-format rules. "
              + "Applicable to `date` (date rules use `when`) and to "
              + "`number`/`money`/`duration`/`lookup` (value rules use `op`/`first`/`second`). "
              + "A `formula` is resolved by its `resultType`: "
              + "`date` → date rules, `number`/`money`/`duration` → value rules."
    )
    public List<FieldConditionFormat> getConditionFormat() { return null; }
}
