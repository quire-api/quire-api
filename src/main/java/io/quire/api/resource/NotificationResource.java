package io.quire.api.resource;

import io.quire.api.model.notification.CreateNotificationBody;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/notification")
@Api(value = "notification", description =
	"Sends a notification the user who granted the access.\n"
	+ "It is usually to report an error to the user.")
@Produces({"application/json"})
public class NotificationResource {
	@POST
	@Path("/")
	@ApiOperation(value = "Send a notification.",
		notes = "Send a notification to the user.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json",
				value =	"{'success': true}")}))})
	public Response createNotification(
		@ApiParam(value = "Message to send", required = true)
				CreateNotificationBody data) { return null; }
}
