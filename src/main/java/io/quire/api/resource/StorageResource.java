package io.quire.api.resource;

import io.quire.api.model.*;
import io.quire.api.model.storage.StorageList;
import io.quire.api.model.storage.StorageMap;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/storage")
@Api(
    value = "storage",
    description =
        "A simple key–value storage for application-specific data.\n"
      + "Data is scoped to the current access token and will be deleted when the token is revoked or expires."
)
@Produces({"application/json"})
public class StorageResource {

    @GET
    @Path("/list/{prefix}")
    @ApiOperation(
        value = "List stored entries by prefix.",
        notes =
            "Returns up to 20 entries whose keys start with the given prefix.\n"
          + "Use this to page or group application-specific values by a common key prefix.",
        response = StorageList.class
    )
    public Response getValues(
        @ApiParam(
            value = "Key prefix to match. Example: \"foo\".",
            required = true
        )
        @PathParam("prefix") String prefix
    ) { return null; }

    @GET
    @Path("/{name}")
    @ApiOperation(
        value = "Get a stored value.",
        notes =
            "Returns the application-specific value stored under the given name.\n"
          + "If the key is not found, a 404 status is returned.\n"
          + "Note: values are scoped per access token.",
        response = StorageMap.class
    )
    public Response getValue(
        @ApiParam(value = "The key name. Example: \"latest\".", required = true)
        @PathParam("name") String name
    ) { return null; }

    @PUT
    @Path("/{name}")
    @ApiOperation(
        value = "Create or update a stored value.",
        notes =
            "Creates or replaces the value stored under the given name.\n"
          + "If `null` is provided as the value, the key will be deleted.\n"
          + "Returns `{ \"success\": true }` when the operation succeeds.\n"
          + "Note: values are scoped per access token."
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
    public Response updateValue(
        @ApiParam(value = "The key name. Example: \"latest\".", required = true)
        @PathParam("name") String name,
        @ApiParam(
            value = "The value to store. Any JSON-serializable object is accepted."
        )
        StorageMap data
    ) { return null; }

    @DELETE
    @Path("/{name}")
    @ApiOperation(
        value = "Delete a stored value.",
        notes =
            "Deletes the value stored under the given name.\n"
          + "Returns `{ \"success\": true }` if the key existed and was deleted,\n"
          + "or `{ \"success\": false }` if the key did not exist.\n"
          + "Note: values are scoped per access token."
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
    public Response deleteValue(
        @ApiParam(value = "The key name. Example: \"latest\".", required = true)
        @PathParam("name") String name
    ) { return null; }
}
