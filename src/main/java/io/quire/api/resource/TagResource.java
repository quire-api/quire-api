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

    @POST
    @Path("/{projectOid}")
    @ApiOperation(
        value = "Create a tag",
        notes = "Creates a new tag in the specified project (by OID).",
        response = Tag.class
    )
    public Response createTag(
        @ApiParam(
            value = "OID of the project to add the new tag to.\n"
                  + "Specify \"-\" to add it to personal tasks (My Tasks, not in a specific project).",
            required = true
        )
        @PathParam("projectOid") String projectOid,
        @ApiParam(value = "Tag to create", required = true)
        CreateTagBody data
    ) { return null; }

    @POST
    @Path("/id/{projectId}")
    @ApiOperation(
        value = "Create a tag",
        notes = "Creates a new tag in the specified project (by ID).",
        response = Tag.class
    )
    public Response createTagToProject(
        @ApiParam(
            value = "ID of the project to add the new tag to.\n"
                  + "Specify \"-\" to add it to personal tasks (My Tasks, not in a specific project).",
            required = true
        )
        @PathParam("projectId") String projectId,
        @ApiParam(value = "Tag to create", required = true)
        CreateTagBody data
    ) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get a tag",
        notes = "Returns the complete tag record for the given OID.",
        response = Tag.class
    )
    public Response getTag(
        @ApiParam(value = "OID of the tag to fetch.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @GET
    @Path("/list/{projectOid}")
    @ApiOperation(
        value = "List tags by project OID",
        notes = "Returns all tags in the specified project (by OID).",
        response = Tag.class,
        responseContainer = "List"
    )
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
        value = "List tags by project ID",
        notes = "Returns all tags in the specified project (by ID).",
        response = Tag.class,
        responseContainer = "List"
    )
    public Response getTagsByProjectId(
        @ApiParam(
            value = "ID of the project.\n"
                  + "Specify \"-\" to list tags used in personal tasks (My Tasks, not in a specific project).",
            required = true
        )
        @PathParam("projectId") String projectId
    ) { return null; }

    @PUT
    @Path("/{oid}")
    @ApiOperation(
        value = "Update a tag",
        notes = "Updates an existing tag and returns the complete updated record.",
        response = Tag.class
    )
    public Response updateTag(
        @ApiParam(value = "OID of the tag to update.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Tag updates", required = true)
        UpdateTagBody data
    ) { return null; }

    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete a tag",
        notes = "Deletes the tag with the given OID."
    )
    @ApiResponses({
        @ApiResponse(
            code = 200,
            message = "ok",
            examples = @Example({
                @ExampleProperty(mediaType = "application/json", value = "{'success': true}")
            })
        )
    })
    public Response deleteTag(
        @ApiParam(value = "OID of the tag to delete.", required = true)
        @PathParam("oid") String oid
    ) { return null; }
}
