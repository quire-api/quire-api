package io.quire.api.model.field;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;

/**
 * Request body for updating a custom-field definition
 * (`PUT /project/update-field/{oid}/{fieldName}` or
 * `PUT /insight/update-field/{oid}/{fieldName}`).
 *
 * All keys are optional; any key omitted preserves the existing value
 * (flag keys are merged, not replaced). Use
 * `/rename-field/{oid}/{fieldName}/{newName}` to rename.
 *
 * Enum-like string values (`type`, `resultType`, `durationFormat`,
 * `lookupType`, and `when`/`op` within `conditionFormat`) are accepted
 * case-insensitively on input.
 */
public class UpdateFieldBody {

    @ApiModelProperty(
        value = "(Optional) Field type. Immutable on update — if supplied, "
              + "must match the existing field's type. Usually omitted; "
              + "include only to verify the stored type.",
        example = "number",
        allowableValues = "text, number, money, date, duration, select, "
                        + "checkbox, user, task, hyperlink, email, formula, "
                        + "file, lookup"
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
        value = "(Optional, `money`/`formula` only) Currency symbol. "
              + "Pass `$` to reset a money field to its default.",
        example = "USD"
    )
    public String getCurrency() { return null; }

    @ApiModelProperty(
        value = "(Optional, `duration`/`formula` only) Duration display format.",
        example = "hh:mm",
        allowableValues = "hh:mm:ss, hh:mm, 1h1m, 1h, 1d1h, dd:hh:mm:ss, "
                        + "dd:hh:mm, dd:hh"
    )
    public String getDurationFormat() { return null; }

    @ApiModelProperty(
        value = "(Optional, `formula` only) Formula expression.",
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
        value = "(Optional, `select` only) Replacement option list "
              + "(replaces the entire set)."
    )
    public List<FieldOption> getOptions() { return null; }

    @ApiModelProperty(
        value = "(Optional, `lookup` only) Map from lookup key to numeric value. "
              + "Keys are OIDs of the configured `lookupType` entity. "
              + "Replaces the entire map."
    )
    public Map<String, Number> getLookup() { return null; }

    @ApiModelProperty(
        value = "(Optional, `lookup` only) Source type for lookup keys.",
        example = "User",
        allowableValues = "User, Task, Project, Organization"
    )
    public String getLookupType() { return null; }

    @ApiModelProperty(
        value = "(Optional) Replacement conditional-format rules "
              + "(replaces the entire list)."
    )
    public List<FieldConditionFormat> getConditionFormat() { return null; }
}
