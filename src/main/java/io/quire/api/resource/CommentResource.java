package io.quire.api.resource;

import io.quire.api.model.*;
import io.quire.api.model.comment.*;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/comment")
@Api(value = "comment", description =
	"A comment that an user can put to a task or project.")
@Produces({"application/json"})
public class CommentResource {

	@POST
	@Path("/{chatOid}")
	@ApiOperation(value = "Add a new comment to a chat channel.",
		notes = "Add a new comment to a chat channel.",
		response = Comment.class)
	public Response createCommentToChatByOid(
		@ApiParam(value = "OID of the chat channel that new comment will be added to",
			required = true)
		@PathParam("chatOid") String chatOid,
		@ApiParam(value = "Comment to create", required = true)
		CreateCommentBody data) { return null; }

	@POST
	@Path("/{taskOid}")
	@ApiOperation(value = "Add a new comment to a task.",
		notes = "Add a new comment to a task.",
		response = Comment.class)
	public Response createCommentToTaskByOid(
		@ApiParam(value = "OID of the task that new comment will be added to",
			required = true)
		@PathParam("taskOid") String taskOid,
		@ApiParam(value = "Comment to create", required = true)
		CreateCommentBody data) { return null; }

	@POST
	@Path("/id/{projectId}/{chatId}")
	@ApiOperation(value = "Add a new comment to a chat channel.",
		notes = "Add a new comment to a chat channel.",
		response = Comment.class)
	public Response createCommentToChat(
		@ApiParam(value = "ID of the project that the chat channel is in", required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "ID of the chat channel that new comment will be added to", required = true)
		@PathParam("chatId") String chatId,
		@ApiParam(value = "Comment to create", required = true)
		CreateCommentBody data) { return null; }

	@POST
	@Path("/id/{projectId}/{taskId}")
	@ApiOperation(value = "Add a new comment to a task.",
		notes = "Add a new comment to a task.",
		response = Comment.class)
	public Response createCommentToTask(
		@ApiParam(value = "ID of the project that the task is in", required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "ID of the task that new comment will be added to", required = true)
		@PathParam("taskId") int taskId,
		@ApiParam(value = "Comment to create", required = true)
		CreateCommentBody data) { return null; }

	@GET
	@Path("/list/{chatOid}")
	@ApiOperation(value = "Get all comments of the chat channel.",
		notes = "Returns all comment records of the chat channel.",
		response = Comment.class,
		responseContainer = "List")
	public Response getCommentsOfChatByOid(
		@ApiParam(value = "OID of the chat channel that comments will be be fetched from.", required = true)
		@PathParam("chatOid") String chatOid) { return null; }

	@GET
	@Path("/list/{taskOid}")
	@ApiOperation(value = "Get all comments of the task.",
		notes = "Returns all comment records of the task.",
		response = Comment.class,
		responseContainer = "List")
	public Response getCommentsOfTaskByOid(
		@ApiParam(value = "OID of the task that comments will be be fetched from.", required = true)
		@PathParam("taskOid") String taskOid) { return null; }

	@GET
	@Path("/list/id/{projectId}/{chatId}")
	@ApiOperation(value = "Get all comments of the chat channel.",
		notes = "Returns all comment records of the chat channel by its ID.",
		response = Comment.class,
        responseContainer = "List")
	public Response getCommentsOfChat(
		@ApiParam(value = "ID of the project.", required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "ID of the chat channel.", required = true)
		@PathParam("chatId") String chatId) { return null; }

	@GET
	@Path("/list/id/{projectId}/{taskId}")
	@ApiOperation(value = "Get all comments of the task. ",
		notes = "Returns all comment records of the task by its ID.",
		response = Comment.class,
        responseContainer = "List")
	public Response getCommentsOfTask(
		@ApiParam(value = "ID of the project.", required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "ID of the task.", required = true)
		@PathParam("taskId") int taskId) { return null; }

	@PUT
	@Path("/{commentOid}")
	@ApiOperation(value = "Update an existing comment",
		notes = "Updates an existing comment, and returns the complete updated comment record.",
		response = Comment.class)
	public Response updateCommentByOid(
		@ApiParam(value = "OID of the comment that needs to be updated.", required = true)
		@PathParam("commentOid") String commentOid,
		@ApiParam(value = "The new content of the comment to update to", required = true)
		UpdateCommentBody data) { return null; }

	@POST
	@Path("/attach/{commentOid}/{filename}")
	@ApiOperation(value = "Uploads an attachment to an existing comment",
		notes = "Uploads an attachment to an existing comment.",
		response = SimpleAttachment.class)
	public Response attachCommentByOid(
		@ApiParam(value = "OID of the comment to upload an attachment.", required = true)
		@PathParam("commentOid") String commentOid,
		@ApiParam(value = "The attachment's name, such as `readme.txt`.\n\n"
			+ "It is better to provide a meaningful extension so browser can "
			+ "recognize the mime type properly. "
			+ "For example, `revenue.pdf` and `contacts.json`.",
			required = true)
		String filename) { return null; }

	@DELETE
	@Path("/{commentOid}")
	@ApiOperation(value = "Delete an existing comment",
		notes = "Delete an existing comment.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json", value =
				"{'success': true}")}))})
	public Response deleteCommentByOid(
		@ApiParam(value = "OID of comment that needs to be deleted", required = true)
		@PathParam("commentOid") String commentOid) { return null; }
}
