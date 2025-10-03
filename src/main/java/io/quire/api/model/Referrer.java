package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class Referrer {

    @ApiModelProperty(
        value = "OID of the task that references another task (the referrer task).",
        example = "wrSpgghWFCzPHBqiShSurDeD"
    )
    public String getTask() { return null; }

    @ApiModelProperty(
        value = "OID of the user who created this reference.",
        example = "wrSpgghWFCzPHBqiShSurDeD"
    )
    public String getUser() { return null; }

    @ApiModelProperty(
        value = "Timestamp (UTC, ISO 8601) when this reference was created.",
        example = "2018-12-22T02:06:58.158Z"
    )
    public String getWhen() { return null; }
}
