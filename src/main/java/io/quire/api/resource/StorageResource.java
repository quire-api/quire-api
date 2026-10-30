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
        "A simple key–value storage for application-specific data. Each "
      + "value is any JSON value, kept per authorization of your app. A key "
      + "is addressed as `/storage/{name}`: unlike other resources, there "
      + "is no `/storage/id/...` form (`404`)."
)
@Produces({"application/json"})
public class StorageResource {

    // The storage endpoints share these.
    private static final String LIFETIME =
        "Stored data belongs to the authorization — the user's grant to "
      + "the app, behind its refresh token — not to the access token: a "
      + "refreshed or new access token of the same authorization sees the "
      + "same data. It is deleted when the authorization is revoked or "
      + "expires, or when the user is deleted.";

    private static final String LIMITS =
        "Limits:\n\n"
      + "- Keys per authorization, by the user's best plan (a trial doesn't "
      + "count): Free 15, Professional 100, Premium 250, Enterprise 1,000. "
      + "A `PUT` adding a key beyond that returns `402` (`code` `469`); "
      + "delete keys or upgrade.\n"
      + "- A value is at most 4 KiB as JSON; a larger one returns `413`.\n"
      + "- Each call costs 1 rate-limit unit, counted against the "
      + "organization of that best plan; a user in no organization has a "
      + "bucket of their own, at the Free plan's limits. Over the limit, "
      + "`429` with `Retry-After`.";

    private static final String NAME =
        "The key. Any string except `id` and `list`, and one starting with "
      + "`:}>`.";

    private static final String VALUE_EXAMPLE =
        "Example: after `PUT /storage/latest` with the body "
      + "`{\"project\": \"my_project\"}`, `GET /storage/latest` returns "
      + "`{\"project\": \"my_project\"}`.";

    private static final String ENTRIES =
        "The response is one JSON object mapping each key to its value, as "
      + "it was stored — at most 20 entries, by key ascending, and `{}` if "
      + "none. There is no paging: with more than 20 keys, narrow the "
      + "listing with `GET /storage/list/{prefix}`.\n\n"
      + "Example: `{\"latest\": {\"project\": \"my_project\"}, "
      + "\"theme\": \"dark\"}`.";

    private static final String TOO_MANY_REQUESTS =
        "Too Many Requests — rate limit reached. Honor `Retry-After`.";

    @GET
    @Path("/list")
    @ApiOperation(
        value = "List all stored entries.",
        notes =
            "Returns the stored entries of the current authorization.\n\n"
          + ENTRIES + "\n\n"
          + LIFETIME + "\n\n"
          + LIMITS,
        response = StorageList.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — key-to-value object of the stored entries (may be `{}`).",
            response = StorageList.class),
        @ApiResponse(code = 429, message = TOO_MANY_REQUESTS)
    })
    public Response getAllValues() { return null; }

    @GET
    @Path("/list/{prefix}")
    @ApiOperation(
        value = "List stored entries by prefix.",
        notes =
            "Returns the stored entries whose keys start with `prefix`. "
          + "The prefix is matched literally: `%` and `_` are not "
          + "wildcards. Use it to group values under a common key prefix.\n\n"
          + ENTRIES + "\n\n"
          + LIFETIME + "\n\n"
          + LIMITS,
        response = StorageList.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — key-to-value object of the matching entries (may be `{}`).",
            response = StorageList.class),
        @ApiResponse(code = 429, message = TOO_MANY_REQUESTS)
    })
    public Response getValues(
        @ApiParam(
            value = "Key prefix to match.",
            example = "sync.",
            required = true
        )
        @PathParam("prefix") String prefix
    ) { return null; }

    @GET
    @Path("/{name}")
    @ApiOperation(
        value = "Get a stored value.",
        notes =
            "Returns the value stored under `name`, exactly as it was "
          + "stored: any JSON value, such as an object, array, string or "
          + "number. `404` if there is none.\n\n"
          + VALUE_EXAMPLE + "\n\n"
          + LIFETIME + "\n\n"
          + LIMITS,
        response = StorageMap.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — the stored value, as it was stored.", response = StorageMap.class),
        @ApiResponse(code = 404, message = "Not Found — no value is stored under this key."),
        @ApiResponse(code = 429, message = TOO_MANY_REQUESTS)
    })
    public Response getValue(
        @ApiParam(value = NAME, example = "latest", required = true)
        @PathParam("name") String name
    ) { return null; }

    @PUT
    @Path("/{name}")
    @ApiOperation(
        value = "Create or update a stored value.",
        notes =
            "Stores the request body under `name`, replacing any value "
          + "already there. The body is the value itself: any JSON value, "
          + "such as an object, array, string or number. A body of `null`, "
          + "or an empty one, deletes the key instead.\n\n"
          + "Returns `204 No Content`. " + VALUE_EXAMPLE + "\n\n"
          + LIFETIME + "\n\n"
          + LIMITS
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content — value stored (or deleted if the body is `null` or empty)."),
        @ApiResponse(code = 400, message = "Bad Request — the key is `id` or `list`, or starts with `:}>`."),
        @ApiResponse(code = 402, message = "Payment Required — the key limit of the plan is reached (`code` `469`). Delete keys or upgrade."),
        @ApiResponse(code = 413, message = "Payload Too Large — the value exceeds 4 KiB as JSON."),
        @ApiResponse(code = 418, message = "Not Valid JSON Content — the body isn't valid JSON."),
        @ApiResponse(code = 429, message = TOO_MANY_REQUESTS)
    })
    public Response updateValue(
        @ApiParam(value = NAME, example = "latest", required = true)
        @PathParam("name") String name,
        @ApiParam(
            value = "The value to store: any JSON value, such as "
                  + "`{\"project\": \"my_project\"}`. Send `null`, or no "
                  + "body, to delete the key."
        )
        StorageMap data
    ) { return null; }

    @DELETE
    @Path("/{name}")
    @ApiOperation(
        value = "Delete a stored value.",
        notes =
            "Deletes the value stored under `name`.\n\n"
          + "> Note: Returns `204 No Content` regardless of whether the value exists.\n\n"
          + LIFETIME + "\n\n"
          + LIMITS
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 429, message = TOO_MANY_REQUESTS)
    })
    public Response deleteValue(
        @ApiParam(value = NAME, example = "latest", required = true)
        @PathParam("name") String name
    ) { return null; }
}
