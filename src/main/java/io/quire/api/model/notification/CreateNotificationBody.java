package io.quire.api.model.notification;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class CreateNotificationBody {

    @ApiModelProperty(
        value = "Notification message. Required; an empty one is rejected "
              + "with `400`.",
        example = "Unable to synchronize",
        required = true
    )
    public String getMessage() { return null; }

    @ApiModelProperty(
        value = "(Optional) URL associated with the message. When provided, the client may render the message as a hyperlink.\n\n"
              + "A `javascript:` URL is rejected with `400`. Without a scheme, "
              + "such as `example.com/sync`, `https://` is assumed; a path "
              + "starting with `/` points into Quire.",
        example = "https://superheros.com/sync",
        required = false
    )
    public String getUrl() { return null; }

    @ApiModelProperty(
        value = "(Optional) Recipients of the notification (OID, ID, or email of a user). "
              + "Omit or send an empty list to notify only the authorizing user (default).\n\n"
              + "Each entry must be the authorizing user or a colleague visible to "
              + "this app — exactly the set returned by `GET /user/list`. Entries that "
              + "do not exist or are not visible are rejected with `404 Not Found`; the "
              + "response is identical regardless of the reason, so this endpoint "
              + "cannot be used to probe whether a user exists.\n\n"
              + "Special value:\n"
              + "- `[\"*\"]` — broadcast to **every user visible to this app** "
              + "(same set as `GET /user/list`, including the authorizing user). "
              + "`*` must be the sole entry; mixing it with other entries returns "
              + "`400 Bad Request`.\n\n"
              + "Rate-limit cost: one unit per 10 recipients, rounded up (at "
              + "least 1), counted after `*` is expanded. A call made once the "
              + "caller's per-minute or per-hour API budget is used up is "
              + "rejected with `429 Too Many Requests` and no notification is sent.",
        example = "[\"bob@example.com\"]"
    )
    public List<String> getRecipients() { return null; }
}
