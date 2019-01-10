package io.quire.api.resource;

import io.quire.api.model.tag.UpdateTagBody;
import io.quire.api.model.tag.Tag;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/tags")
@Api(value = "tags", description =
    "A tag is a label that can be attached to any task in Quire.")
@Produces({"application/json"})
public class TagResource {
    @GET
    @Path("/{oid}")
    @ApiOperation(value = "Get a tag",
        notes = "returns the full tag record.",
        response = Tag.class)
    public Response getTag(
        @ApiParam(value = "Oid of tag that needs to be fetched.", required = true)
        @PathParam("oid") String oid) { return null; }

    @PUT
    @Path("/{oid}")
    @ApiOperation(value = "Update a tag",
        notes = "A specific, existing tag can be updated by making a PUT request on the URL for that tag.\n" +
                "Returns the complete updated tag record.",
        response = Tag.class)
    public Response updateTag(
        @ApiParam(value = "Oid of tag that needs to be updated", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "User to update", required = true)
        UpdateTagBody data) { return null; }

    @DELETE
    @Path("/{oid}")
    @ApiOperation(value = "Delete a tag",
        notes = "A specific, existing tag can be deleted by making a DELETE request " +
                "on the URL for that tag.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok",
            examples = @Example({@ExampleProperty(mediaType = "application/json", value =
                "{'Success': 'true'}")}))})
    public Response deleteTag(
        @ApiParam(value = "Oid of tag that needs to be deleted", required = true)
        @PathParam("oid") String oid) { return null; }

}
