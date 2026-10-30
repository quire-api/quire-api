package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Represents an error response.")
public class ErrorResponse {

    @ApiModelProperty(
        value = "The error code: the HTTP status, echoed — such as `400`, "
              + "`404`, `408` or `409` — except these, which tell apart cases "
              + "that share a status:\n\n"
              + "- `469`: a plan quota or limit has been reached (HTTP `402`).\n"
              + "- `470`: the subscription has expired (HTTP `402`).\n"
              + "- `471`: temporarily blocked; try again later (HTTP `403`).\n"
              + "- `100`: the `Authorization` header is missing or malformed "
              + "(HTTP `403`).",
        example = "429",
        required = true
    )
    public int getCode() {
        return 0;
    }

    @ApiModelProperty(
        value = "Human-readable error message.",
        example = "Something went wrong.",
        required = true
    )
    public String getMessage() {
        return null;
    }
}
