package io.quire.api.resource;

import io.quire.api.model.comment.Comment;
import io.quire.api.model.comment.CreateCommentBody;
import io.quire.api.model.comment.UpdateCommentBody;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/comment")
@Api(value = "comment", description =
	"A comment that an user can put to a task or project.")
@Produces({"application/json"})
public class CommentResource {

	@POST
	@Path("/{oid}")
	@ApiOperation(value = "Add a new comment to a task or a project.",
		notes = "Add a new comment to a task or a project. "
		+ "If the given OID is a project, the comment will be added to "
		+ "a project. If a task, the comment will be added to a task.",
		response = Comment.class)
	public Response createComment(
		@ApiParam(value = "OID of the project or task that new comment will be added to",
			required = true)
		@PathParam("oid") String oid,
		@ApiParam(value = "Comment to create", required = true)
		CreateCommentBody data) { return null; }

	@POST
	@Path("/id/{projectId}")
	@ApiOperation(value = "Add a new comment to a project.",
		notes = "Add a new comment to a project.",
		response = Comment.class)
	public Response createCommentToProject(
		@ApiParam(value = "ID of the project that new comment will be added to",
			required = true)
		@PathParam("projectId") String projectId,
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
	@Path("/id/{projectId}/{oid}")
	@ApiOperation(value = "Get a comment with project's ID",
		notes = "Returns the full comment record of the given OID.",
		response = Comment.class)
	public Response getCommentById(
		@ApiParam(value = "ID of the project that the comment is in.", required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "OID of comment that needs to be feteched.", required = true)
		@PathParam("oid") String oid) { return null; }

	@GET
	@Path("/{projectOid}/{oid}")
	@ApiOperation(value = "Get a comment with project's OID",
		notes = "Returns the full comment record of the given OID.",
		response = Comment.class)
	public Response getCommentByOid(
		@ApiParam(value = "OID of the project that the comment is in.", required = true)
		@PathParam("projectOid") String projectOid,
		@ApiParam(value = "OID of comment that needs to be feteched.", required = true)
		@PathParam("oid") String oid) { return null; }

	@GET
	@Path("/list/{oid}")
	@ApiOperation(value = "Get comments added to the given object.",
		notes = "Returns all comment records of the given object that "
		+ "can be a project or a task.",
		response = Comment.class,
		responseContainer = "List")
	public Response getComments(
		@ApiParam(value = "OID of project or task that comments will be be fetched from.", required = true)
		@PathParam("oid") String oid) { return null; }

	@GET
	@Path("/list/id/{projectId}")
	@ApiOperation(value = "Get all comments of the given project.",
		notes = "Returns all comment records of the given project by its ID.",
		response = Comment.class,
        responseContainer = "List")
	public Response getProjectComments(
		@ApiParam(value = "ID of project.", required = true)
		@PathParam("projectId") String projectId) { return null; }

	@GET
	@Path("/list/id/{projectId}/{taskId}")
	@ApiOperation(value = "Get all comments of the given task. ",
		notes = "Returns all comment records of the given task by its ID.",
		response = Comment.class,
        responseContainer = "List")
	public Response getTaskComments(
		@ApiParam(value = "ID of the project.", required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "ID of the task", required = true)
		@PathParam("taskId") int taskId) { return null; }

	@PUT
	@Path("/id/{projectId}/{oid}")
	@ApiOperation(value = "Update an existing comment with project's ID",
		notes = "Updates an existing comment, and returns the complete updated comment record.",
		response = Comment.class)
	public Response updateCommentById(
		@ApiParam(value = "ID of the project that the comment is in.", required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "OID of the comment that needs to be updated.", required = true)
		@PathParam("oid") String oid,
		@ApiParam(value = "The new content of the comment to update to", required = true)
		UpdateCommentBody data) { return null; }

	@PUT
	@Path("/{projectOid}/{oid}")
	@ApiOperation(value = "Update an existing comment with project's OID",
		notes = "Updates an existing comment, and returns the complete updated comment record.",
		response = Comment.class)
	public Response updateCommentByOid(
		@ApiParam(value = "OID of the project that the comment is in.", required = true)
		@PathParam("projectOid") String projectOid,
		@ApiParam(value = "OID of the comment that needs to be updated.", required = true)
		@PathParam("oid") String oid,
		@ApiParam(value = "The new content of the comment to update to", required = true)
		UpdateCommentBody data) { return null; }

	@DELETE
	@Path("/id/{projectId}/{oid}")
	@ApiOperation(value = "Delete an existing comment with project's ID",
		notes = "Delete an existing comment.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json", value =
				"{'success': true}")}))})
	public Response deleteCommentById(
		@ApiParam(value = "ID of the project that the comment is in.", required = true)
		@PathParam("projectId") String projectId,
		@ApiParam(value = "OID of comment that needs to be deleted", required = true)
		@PathParam("oid") String oid) { return null; }

	@DELETE
	@Path("/{projectOid}/{oid}")
	@ApiOperation(value = "Delete an existing comment with project's OID",
		notes = "Delete an existing comment.")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok",
			examples = @Example({@ExampleProperty(mediaType = "application/json", value =
				"{'success': true}")}))})
	public Response deleteCommentByOid(
		@ApiParam(value = "OID of the project that the comment is in.", required = true)
		@PathParam("projectOid") String projectOid,
		@ApiParam(value = "OID of comment that needs to be deleted", required = true)
		@PathParam("oid") String oid) { return null; }
}
