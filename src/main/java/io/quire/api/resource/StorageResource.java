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
        "A simple key–value storage for application-specific data.\n\n"
      + "**Lifetime:** data is scoped to the access token that wrote it. "
      + "It is deleted automatically when the token is revoked or expires, "
      + "or when the owning user is deleted (whichever happens first).\n\n"
      + "**Limits:**\n"
      + "- **Records per access token** depend on the calling user's best plan:\n"
      + "  - Free: **15** records.\n"
      + "  - Professional: **100** records.\n"
      + "  - Premium: **250** records.\n"
      + "  - Enterprise: **1,000** records.\n"
      + "- **Per write**: each `PUT` value is capped at **4 KiB** "
      + "(JSON-serialized request body). Uniform across plans.\n"
      + "- **Rate limit**: every storage call counts against the calling "
      + "user's standard per-organization rate limit (see [Rate Limits]"
      + "(#section/Rate-Limits)) — same bucket and plan-tier as the rest "
      + "of the API. No separate per-OApp ceiling.\n\n"
      + "Exceeding any of these returns:\n"
      + "- `402 Payment Required` (`ecQuotaExceeded`) — record cap reached "
      + "for the current plan; upgrade or delete keys to free space.\n"
      + "- `413 Payload Too Large` (`ecTooLarge`) — value exceeds 4 KiB.\n"
      + "- `429 Too Many Requests` (with `Retry-After` header in seconds) — "
      + "organization's per-minute or per-hour rate limit hit."
)
@Produces({"application/json"})
public class StorageResource {

    @GET
    @Path("/list")
    @ApiOperation(
        value = "List all stored entries.",
        notes =
            "Returns up to 20 stored entries for the current access token, "
          + "ordered by key ascending.",
        response = StorageList.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of stored entries (may be empty).",
            response = StorageList.class),
        @ApiResponse(code = 429, message = "Too Many Requests — organization rate limit reached. Honor `Retry-After`.")
    })
    public Response getAllValues() { return null; }

    @GET
    @Path("/list/{prefix}")
    @ApiOperation(
        value = "List stored entries by prefix.",
        notes =
            "Returns up to 20 entries whose keys start with the given prefix, "
          + "ordered by key ascending.\n"
          + "Use this to group application-specific values by a common key prefix.",
        response = StorageList.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of matching entries (may be empty).",
            response = StorageList.class),
        @ApiResponse(code = 429, message = "Too Many Requests — organization rate limit reached. Honor `Retry-After`.")
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
        @ApiResponse(code = 404, message = "Not Found — no value stored under this key for the current access token."),
        @ApiResponse(code = 429, message = "Too Many Requests — organization rate limit reached. Honor `Retry-After`.")
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
        @ApiResponse(code = 400, message = "Bad Request — malformed JSON."),
        @ApiResponse(code = 402, message = "Payment Required — record cap reached for the current plan. Delete keys or upgrade."),
        @ApiResponse(code = 413, message = "Payload Too Large — value exceeds the 4 KiB per-write cap."),
        @ApiResponse(code = 429, message = "Too Many Requests — organization rate limit reached. Honor `Retry-After`.")
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
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 429, message = "Too Many Requests — organization rate limit reached. Honor `Retry-After`.")
    })
    public Response deleteValue(
        @ApiParam(value = "The key name.", example = "latest", required = true)
        @PathParam("name") String name
    ) { return null; }
}
