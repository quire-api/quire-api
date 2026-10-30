package io.quire.api.resource;

import io.quire.api.model.notification.CreateNotificationBody;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/notification")
@Api(
    value = "notification",
    description =
        "Send a notification to the user who authorized this app, "
      + "or to their colleagues. Typically used to surface errors, "
      + "alerts, or events worth flagging in Quire's notification panel."
)
@Produces({"application/json"})
public class NotificationResource {

    @POST
    @Path("/")
    @ApiOperation(
        value = "Send a notification.",
        notes =
            "Sends a notification to the authorizing user (default) or to a "
          + "list of recipients — the authorizing user and/or their colleagues "
          + "— specified via `recipients`.\n\n"
          + "Eligible recipients are the authorizing user plus the colleagues "
          + "this app can see — the same set returned by `GET /user/list`: "
          + "when the user granted the app access to contacts, all colleagues "
          + "are visible; otherwise only colleagues who also authorized this "
          + "app.\n\n"
          + "An entry that does not exist or is not visible is rejected with "
          + "`404 Not Found`. The response is identical for every such case "
          + "so the endpoint cannot be used to probe user existence or "
          + "colleague relationships.\n\n"
          + "The app needs the *Manage notifications* permission, granted "
          + "when the user authorized it; otherwise `403`.\n\n"
          + "Rate-limit cost: one unit per 10 recipients, rounded up (at "
          + "least 1), counted from the resolved list — so `[\"*\"]` costs "
          + "one unit per 10 users visible to the app. It is charged only "
          + "once the recipients resolve, so a call rejected with `404` costs "
          + "nothing. It is counted against the organization of the user's "
          + "best plan (a trial doesn't count); a user in no organization has "
          + "a bucket of their own, at the Free plan's limits.\n\n"
          + "Returns `204 No Content`."
    )
    @ApiResponses({
        @ApiResponse(
            code = 204,
            message = "No Content — notification accepted and queued for delivery."
        ),
        @ApiResponse(
            code = 400,
            message = "Bad Request — `message` is missing or empty, `url` is a `javascript:` URL, `recipients` is not a list, or `*` was combined with other entries."
        ),
        @ApiResponse(
            code = 403,
            message = "Forbidden — the app wasn't granted the *Manage notifications* permission."
        ),
        @ApiResponse(
            code = 404,
            message = "Not Found — one or more entries in `recipients` do not exist or are not visible to this app."
        ),
        @ApiResponse(
            code = 429,
            message = "Too Many Requests — the caller's per-minute or per-hour API budget is used up. No notification is sent."
        )
    })
    public Response createNotification(
        @ApiParam(value = "Notification payload.", required = true)
        CreateNotificationBody data
    ) { return null; }
}
