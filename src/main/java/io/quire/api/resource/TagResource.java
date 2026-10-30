package io.quire.api.resource;

import io.quire.api.model.tag.UpdateTagBody;
import io.quire.api.model.tag.CreateTagBody;
import io.quire.api.model.tag.Tag;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/tag")
@Api(
    value = "tag",
    description = "A tag is a label that can be attached to a task in Quire."
)
@Produces({"application/json"})
public class TagResource {

    // The tag write endpoints share these.
    private static final String TAG_RIGHT =
        "Requires a user who can manage the project's tags, and an app with "
      + "the *Manage tags* permission; otherwise `403`.";

    private static final String GLOBAL_TAG_RIGHT =
        "Creating a global tag, or making a tag global, also takes the right "
      + "to manage the organization's tags; updating or deleting a global "
      + "tag takes only that right.";

    // The tag read endpoints share this.
    private static final String READ_403 =
        "Forbidden — the user can't access it, or didn't authorize it for this app.";

    private static final String TAG_403 =
        "Forbidden — the user can't manage the tags, or the app lacks the *Manage tags* permission.";

    private static final String RETURN_TAG =
        "(Optional) Response shape: `full` (default) for the full record, "
      + "or `compact` for just `{\"oid\": …}`. "
      + "Any other value returns `400 Bad Request`.";

    private static final String CREATE_TAG_NOTES =
        "Creates a new tag in the specified project and returns it. With "
      + "`global: true`, the tag belongs to the project's organization and "
      + "is available in all its projects; My Tasks (`-`) can't have one "
      + "(`403`).\n\n"
      + TAG_RIGHT + " " + GLOBAL_TAG_RIGHT;

    // The tag list endpoints share this.
    private static final String LIST_TAG_NOTES =
        "Returns the tags of the specified project, and the global tags of "
      + "its organization.";

    @POST
    @Path("/{projectOid}")
    @ApiOperation(
        value = "Create a tag.",
        notes = CREATE_TAG_NOTES,
        response = Tag.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — newly created tag.", response = Tag.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed (e.g., name missing or too long)."),
        @ApiResponse(code = 403, message = TAG_403),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response createTag(
        @ApiParam(
            value = "OID of the project to add the new tag to.\n"
                  + "Specify \"-\" to add it to personal tasks (My Tasks, not in a specific project).",
            required = true
        )
        @PathParam("projectOid") String projectOid,
        @ApiParam(value = "Tag to create", required = true)
        CreateTagBody data,

        @ApiParam(
            value = RETURN_TAG,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @POST
    @Path("/id/{projectId}")
    @ApiOperation(
        value = "Create a tag.",
        notes = CREATE_TAG_NOTES,
        response = Tag.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — newly created tag.", response = Tag.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = TAG_403),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response createTagToProject(
        @ApiParam(
            value = "ID of the project to add the new tag to.\n"
                  + "Specify \"-\" to add it to personal tasks (My Tasks, not in a specific project).",
            example = "my_project",
            required = true
        )
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Tag to create", required = true)
        CreateTagBody data,

        @ApiParam(
            value = RETURN_TAG,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get a tag.",
        notes = "Returns the complete tag record for the given OID.",
        response = Tag.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — tag record.", response = Tag.class),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — tag does not exist.")
    })
    public Response getTag(
        @ApiParam(value = "OID of the tag to fetch.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @GET
    @Path("/list/{projectOid}")
    @ApiOperation(
        value = "List tags by project OID.",
        notes = LIST_TAG_NOTES,
        response = Tag.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of tags (may be empty).",
            response = Tag.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response getTagsByProjectOid(
        @ApiParam(
            value = "OID of the project.\n"
                  + "Specify \"-\" to list tags used in personal tasks (My Tasks, not in a specific project).",
            required = true
        )
        @PathParam("projectOid") String projectOid
    ) { return null; }

    @GET
    @Path("/list/id/{projectId}")
    @ApiOperation(
        value = "List tags by project ID.",
        notes = LIST_TAG_NOTES,
        response = Tag.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of tags (may be empty).",
            response = Tag.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — project does not exist.")
    })
    public Response getTagsByProjectId(
        @ApiParam(
            value = "ID of the project.\n"
                  + "Specify \"-\" to list tags used in personal tasks (My Tasks, not in a specific project).",
            example = "my_project",
            required = true
        )
        @PathParam("projectId") String projectId
    ) { return null; }

    @PUT
    @Path("/{oid}")
    @ApiOperation(
        value = "Update a tag.",
        notes = "Updates an existing tag and returns the complete updated record. "
              + "Omitted body fields are left unchanged.\n\n"
              + TAG_RIGHT + " " + GLOBAL_TAG_RIGHT,
        response = Tag.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated tag record.", response = Tag.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = TAG_403),
        @ApiResponse(code = 404, message = "Not Found — tag does not exist.")
    })
    public Response updateTag(
        @ApiParam(value = "OID of the tag to update.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Tag updates", required = true)
        UpdateTagBody data,

        @ApiParam(
            value = RETURN_TAG,
            example = "compact",
            allowableValues = "full, compact"
        )
        @QueryParam("return") String returnMode
    ) { return null; }

    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete a tag.",
        notes = "Deletes the tag with the given OID.\n\n"
              + TAG_RIGHT + " " + GLOBAL_TAG_RIGHT + "\n\n"
              + "Returns `204 No Content`, also when the tag doesn't exist."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content — tag deleted (or already absent)."),
        @ApiResponse(code = 403, message = TAG_403)
    })
    public Response deleteTag(
        @ApiParam(value = "OID of the tag to delete.", required = true)
        @PathParam("oid") String oid
    ) { return null; }
}
