package io.quire.api.model.field;

import io.swagger.annotations.ApiModelProperty;

/**
 * A conditional-format rule for a field definition.
 *
 * Date fields use `when` (e.g., `today`, `upcoming`);
 * value fields (`number`, `money`, `duration`, `lookup`, and a `formula`
 * whose `resultType` resolves to one of those) use `op` + `first`
 * (and `second` for `between`/`notBetween`).
 */
public class FieldConditionFormat {

    @ApiModelProperty(
        value = "(Date fields) Date condition.",
        example = "today",
        allowableValues = "past, yesterday, today, tomorrow, upcoming, "
                        + "last7d, next7d, lastWeek, thisWeek, nextWeek"
    )
    public String getWhen() { return null; }

    @ApiModelProperty(
        value = "(Value fields) Comparison operator.",
        example = ">=",
        allowableValues = ">=, >, <=, <, =, !=, between, notBetween"
    )
    public String getOp() { return null; }

    @ApiModelProperty(
        value = "(Value fields) First operand. "
              + "For `duration`, must be an integer number of seconds.",
        example = "100"
    )
    public Number getFirst() { return null; }

    @ApiModelProperty(
        value = "(Value fields, `between`/`notBetween` only) Second operand. "
              + "For `duration`, must be an integer number of seconds.",
        example = "200"
    )
    public Number getSecond() { return null; }

    @ApiModelProperty(
        value = "Palette color index. Format: two digits `[0-5][0-7]` "
              + "(first = row 0-5, second = column 0-7). "
              + "Examples: `00`, `13`, `57`. NOT a CSS hex color.",
        example = "42",
        required = true
    )
    public String getColor() { return null; }
}
