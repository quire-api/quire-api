package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Represents an error.")
public class ErrorResponse {
    @ApiModelProperty(value = "The error code.",
        example = "429", required = true)
    public int getCode() {
        return 0;
    }

    @ApiModelProperty(value = "The error message.",
        example = "Something went wrong.", required = true)
    public String getMessage() {
        return null;
    }
}
