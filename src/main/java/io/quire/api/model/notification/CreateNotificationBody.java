package io.quire.api.model.notification;

import io.swagger.annotations.ApiModelProperty;

public class CreateNotificationBody {

    @ApiModelProperty(
        value = "Notification message.",
        example = "Unable to synchronize",
        required = true
    )
    public String getMessage() { return null; }

    @ApiModelProperty(
        value = "(Optional) URL associated with the message. When provided, the client may render the message as a hyperlink.",
        example = "https://superheros.com/sync",
        required = false
    )
    public String getUrl() { return null; }
}
