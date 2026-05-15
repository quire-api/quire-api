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
          + "Rate-limit cost: every 10 delivered recipients counts as 1 unit "
          + "against the caller's per-minute / per-hour API rate limit "
          + "(rounded up; minimum 1 unit per call)."
    )
    @ApiResponses({
        @ApiResponse(
            code = 204,
            message = "No Content — notification accepted and queued for delivery."
        ),
        @ApiResponse(
            code = 400,
            message = "Bad Request — `recipients` is not a list, or `*` was combined with other entries."
        ),
        @ApiResponse(
            code = 404,
            message = "Not Found — one or more entries in `recipients` do not exist or are not visible to this app."
        ),
        @ApiResponse(
            code = 429,
            message = "Too Many Requests — the rate-limit cost would exceed the caller's per-minute or per-hour API budget. No notification is sent."
        )
    })
    public Response createNotification(
        @ApiParam(value = "Notification payload.", required = true)
        CreateNotificationBody data
    ) { return null; }
}
