package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class Subscription {

    @ApiModelProperty(
        value = "Plan name (e.g., Free, Professional, Premium, Enterprise).",
        example = "Professional"
    )
    public String getPlan() { return null; }

    @ApiModelProperty(
        value = "Subscription due/renewal timestamp in UTC (ISO 8601).",
        example = "2038-02-22T02:06:58Z"
    )
    public String getDue() { return null; }

    @ApiModelProperty(
        value = "Whether the subscription is expired. Available only when a due date is present.",
        example = "false"
    )
    public boolean getExpired() { return false; }
}
