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
        value = "(Date fields) Date condition. One of:\n"
              + "`past`, `yesterday`, `today`, `tomorrow`, `upcoming`, "
              + "`last7d`, `next7d`, `lastWeek`, `thisWeek`, `nextWeek`.",
        example = "today"
    )
    public String getWhen() { return null; }

    @ApiModelProperty(
        value = "(Value fields) Comparison operator. One of:\n"
              + "`>=`, `>`, `<=`, `<`, `=`, `!=`, `between`, `notBetween`.",
        example = ">="
    )
    public String getOp() { return null; }

    @ApiModelProperty(
        value = "(Value fields) First operand. For `duration`, must be an integer number of seconds.",
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
        value = "Icon color (hex) used to highlight matching values.",
        example = "#ff0000",
        required = true
    )
    public String getColor() { return null; }
}
