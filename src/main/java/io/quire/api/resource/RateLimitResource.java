package io.quire.api.resource;

import io.quire.api.model.ratelimit.RateLimit;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/rate-limit")
@Api(
    value = "rate-limit",
    description = "Inspect the current API rate-limit usage for an organization."
)
@Produces({"application/json"})
public class RateLimitResource {

    // The rate-limit endpoints share this.
    private static final String NOTES =
        "`plan` and the limits are those enforced. A trial doesn't change "
      + "them: during one, they stay those of the plan before the trial.\n\n"
      + "For organizations in a paid master group, counters are shared "
      + "across all organizations in the group.\n\n"
      + "This call is free: it never counts against the rate limit, so you "
      + "can make it even while throttled.";

    @GET
    @Path("/id/{id}")
    @ApiOperation(
        value = "Get rate-limit usage by organization ID.",
        notes =
            "Returns the current per-hour and per-minute API usage for the "
          + "organization with the given ID.\n\n"
          + NOTES,
        response = RateLimit.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — current rate-limit counters.",
            response = RateLimit.class),
        @ApiResponse(code = 403, message = "Forbidden — caller can't access this organization."),
        @ApiResponse(code = 404, message = "Not Found — organization does not exist.")
    })
    public Response getRateLimitById(
        @ApiParam(value = "Organization ID.", example = "my_org", required = true)
        @PathParam("id") String id
    ) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get rate-limit usage by organization OID.",
        notes =
            "Returns the current per-hour and per-minute API usage for the "
          + "organization with the given OID.\n\n"
          + NOTES,
        response = RateLimit.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — current rate-limit counters.",
            response = RateLimit.class),
        @ApiResponse(code = 403, message = "Forbidden — caller can't access this organization."),
        @ApiResponse(code = 404, message = "Not Found — organization does not exist.")
    })
    public Response getRateLimitByOid(
        @ApiParam(value = "Organization OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }
}
