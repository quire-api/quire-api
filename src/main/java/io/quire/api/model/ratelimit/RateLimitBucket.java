package io.quire.api.model.ratelimit;

import io.swagger.annotations.ApiModelProperty;

public class RateLimitBucket {

    @ApiModelProperty(
        value = "Maximum number of calls allowed in this window. "
              + "`-1` indicates no limit.",
        example = "1250"
    )
    public int getLimit() { return 0; }

    @ApiModelProperty(
        value = "Number of calls consumed so far in the current window.",
        example = "42"
    )
    public int getUsed() { return 0; }

    @ApiModelProperty(
        value = "Remaining calls: `max(limit - used, 0)`, or `-1` if unlimited.",
        example = "1208"
    )
    public int getRemaining() { return 0; }

    @ApiModelProperty(
        value = "Unix epoch (seconds) of the next wall-clock boundary at which "
              + "the `used` counter will reset.",
        example = "1774863600"
    )
    public long getReset() { return 0; }
}
