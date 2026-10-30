package io.quire.api.model.ratelimit;

import io.swagger.annotations.ApiModelProperty;

public class RateLimit {

    @ApiModelProperty(
        value = "OID of the organization these limits apply to.",
        example = "YxjapXXRCOYxoaiCT4tT3OQm"
    )
    public String getOrganization() { return null; }

    @ApiModelProperty(
        value = "The plan whose limits are enforced: `Free`, `Professional`, "
              + "`Premium`, or `Enterprise`. During a trial, it is the plan "
              + "before the trial, not the trial's: a trial doesn't change "
              + "the rate limits.",
        example = "Enterprise"
    )
    public String getPlan() { return null; }

    @ApiModelProperty(
        value = "Hourly rate-limit bucket."
    )
    public RateLimitBucket getHour() { return null; }

    @ApiModelProperty(
        value = "Per-minute rate-limit bucket."
    )
    public RateLimitBucket getMinute() { return null; }
}
