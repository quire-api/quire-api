package io.quire.api.resource;

import io.quire.api.model.notification.CreateNotificationBody;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/notification")
@Api(
    value = "notification",
    description =
        "Send a notification to the user who authorized this app. "
      + "Typically used to surface errors or important alerts."
)
@Produces({"application/json"})
public class NotificationResource {

    @POST
    @Path("/")
    @ApiOperation(
        value = "Send a notification.",
        notes = "Sends a notification to the current authorized user."
    )
    @ApiResponses({
        @ApiResponse(
            code = 200,
            message = "ok",
            examples = @Example({
                @ExampleProperty(
                    mediaType = "application/json",
                    value = "{'success': true}"
                )
            })
        )
    })
    public Response createNotification(
        @ApiParam(value = "Notification payload.", required = true)
        CreateNotificationBody data
    ) { return null; }
}
