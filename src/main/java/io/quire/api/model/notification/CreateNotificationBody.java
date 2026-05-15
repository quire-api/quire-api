package io.quire.api.model.notification;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

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
              + "Rate-limit cost: every 10 recipients counts as 1 unit (rounded up; "
              + "minimum 1 per call). A call to 1–10 recipients costs 1 unit — the "
              + "same as a single self-only call; 11–20 costs 2, and so on. A call "
              + "that would exceed the caller's per-minute or per-hour API budget is "
              + "rejected with `429 Too Many Requests` and no notification is sent.",
        example = "[\"bob@example.com\"]"
    )
    public List<String> getRecipients() { return null; }
}
