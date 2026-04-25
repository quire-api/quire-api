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
    @Path("/list")
    @ApiOperation(
        value = "List all stored entries.",
        notes =
            "Returns all stored entries (up to 20) for the current access token.",
        response = StorageList.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of stored entries (may be empty).",
            response = StorageList.class)
    })
    public Response getAllValues() { return null; }

    @GET
    @Path("/list/{prefix}")
    @ApiOperation(
        value = "List stored entries by prefix.",
        notes =
            "Returns up to 20 entries whose keys start with the given prefix.\n"
          + "Use this to page or group application-specific values by a common key prefix.",
        response = StorageList.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of matching entries (may be empty).",
            response = StorageList.class)
    })
    public Response getValues(
        @ApiParam(
            value = "Key prefix to match.",
            example = "foo",
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
          + "Note: values are scoped per access token.",
        response = StorageMap.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — stored value.", response = StorageMap.class),
        @ApiResponse(code = 404, message = "Not Found — no value stored under this key for the current access token.")
    })
    public Response getValue(
        @ApiParam(value = "The key name.", example = "latest", required = true)
        @PathParam("name") String name
    ) { return null; }

    @PUT
    @Path("/{name}")
    @ApiOperation(
        value = "Create or update a stored value.",
        notes =
            "Creates or replaces the value stored under the given name.\n"
          + "If `null` is provided as the value, the key will be deleted.\n"
          + "Note: values are scoped per access token."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content — value stored (or deleted if null)."),
        @ApiResponse(code = 400, message = "Bad Request — payload too large, or malformed JSON.")
    })
    public Response updateValue(
        @ApiParam(value = "The key name.", example = "latest", required = true)
        @PathParam("name") String name,
        @ApiParam(
            value = "The value to store. Any JSON-serializable object is accepted. "
                  + "Send `null` to delete the key."
        )
        StorageMap data
    ) { return null; }

    @DELETE
    @Path("/{name}")
    @ApiOperation(
        value = "Delete a stored value.",
        notes =
            "Deletes the value stored under the given name.\n\n"
            + "> Note: Returns `204 No Content` regardless of whether the value exists.\n"
            + "> Also note: values are scoped per access token."
    )
    @ApiResponses({
        @ApiResponse(
            code = 204,
            message = "No Content"
        )
    })
    public Response deleteValue(
        @ApiParam(value = "The key name.", example = "latest", required = true)
        @PathParam("name") String name
    ) { return null; }
}
