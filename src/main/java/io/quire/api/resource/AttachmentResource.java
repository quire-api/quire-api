package io.quire.api.resource;

import io.quire.api.model.Attachment;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Api(value = "attachments", description =
    "An attachment object represents any file attached to a task or project in Quire.")
@Produces({"application/json"})
public class AttachmentResource {

    @GET
    @Path("/projects/{oid}/attachments")
    @ApiOperation(value = "Get project attachments",
        notes = "Returns the complete attachment record for a single project.",
        response = Attachment.class,
        responseContainer = "List")
    public Response getProjectAttachments(
        @ApiParam(value = "Oid of project", required = true)
        @PathParam("oid") String oid) { return null; }

    @GET
    @Path("/tasks/{oid}/attachments")
    @ApiOperation(value = "Get task attachments",
        notes = "Returns the complete attachment record for a single task.",
        response = Attachment.class,
        responseContainer = "List")
    public Response getTaskAttachments(
        @ApiParam(value = "Oid of task", required = true)
        @PathParam("oid") String oid) { return null; }

    @DELETE
    @Path("/attachments/{url}")
    @ApiOperation(value = "Delete an attachment",
        notes = "A specific, existing attachment can be deleted by making a DELETE request " +
                "on the URL for that attachment.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok",
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'Success': 'true'}")}))})
    public Response deleteAttachment(
        @ApiParam(value = "Url of attachment that needs to be deleted", required = true)
        @PathParam("url") String url) { return null; }
}
