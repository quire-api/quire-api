package io.quire.api.resource;

import io.quire.api.model.sublist.Sublist;
import io.quire.api.model.sublist.CreateSublistBody;
import io.quire.api.model.sublist.UpdateSublistBody;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/sublist")
@Api(
    value = "sublist",
    description = "A sublist is a collection of tasks, representing a subset of tasks from a larger scope. "
        + "To list the tasks of a project's sublist, use `GET /task/search/id/{projectId}?sublist={sublistId}`."
)
@Produces({"application/json"})
public class SublistResource {

    // The read endpoints share this.
    private static final String READ_403 =
        "Forbidden — the user can't access it, or didn't authorize it for this app.";

    // The create endpoints share this.
    private static final String CREATE_NOTES =
        "Creates a sublist under the given owner — a `project`, `organization`, "
      + "`folder`, or `smart-folder` — and returns it.\n\n"
      + "Example — `POST /sublist/id/project/my_project`:\n\n"
      + "```json\n"
      + "{\n"
      + "  \"name\": \"Sprint 12\",\n"
      + "  \"includes\": [\"2MmYOpJH_ZLeehIjjytH1Rwr\"],\n"
      + "  \"due\": \"2026-11-30\"\n"
      + "}\n"
      + "```\n\n"
      + "`includes` adds those tasks with all their subtasks. To start with "
      + "every task instead, pass `\"all\": true`, then leave tasks out by "
      + "updating the sublist with `changes`.\n\n"
      + "Returns `402` if the plan's sublist limit is reached.";

    // The get endpoints share this.
    private static final String GET_NOTES =
        "Returns the sublist record.\n\n"
      + "To list its tasks, call `GET /task/search/id/{projectId}?sublist={sublistId}` "
      + "(`sublist` also takes the OID). It works for a project's sublist only, "
      + "returns 30 tasks unless you pass `limit`, and gives no `cursor`.";

    // The list endpoints share this.
    private static final String LIST_NOTES =
        "Returns the owner's sublists that the caller can see; one shown to "
      + "selected members only is left out for the others. Archived sublists "
      + "are included (they carry `archivedAt`); removed ones are not.";

    // The delete endpoints share this.
    private static final String DELETE_NOTES =
        "Removes the sublist. Restore it with "
      + "`PUT /sublist/undo-remove/id/{ownerType}/{ownerId}/{sublistId}` "
      + "(or `/sublist/undo-remove/{oid}`).\n\n"
      + "Returns `204 No Content`, also when the sublist does not exist.";

    // The undo-remove endpoints share this.
    private static final String UNDO_REMOVE_NOTES =
        "Restores a previously-removed sublist. "
      + "Idempotent: if the sublist is not currently removed, this is "
      + "a no-op and returns the current sublist record.\n\n"
      + "Subject to the sublist-per-owner quota: may return "
      + "`402 Payment Required` if the plan's sublist limit is "
      + "already reached.";

    // The by-ID get, update and delete endpoints share this.
    private static final String OWNER_TYPE_BY_ID =
        "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`.\n"
      + "If omitted, `project` is assumed. For example, `/id/my_project/1234` "
      + "is equivalent to `/id/project/my_project/1234`.";

    // The create, update and undo-remove endpoints share this.
    private static final String RETURN_MODE =
        "(Optional) Response shape: `full` (default) returns the full sublist "
      + "record; `compact` returns only `{\"oid\": \"…\", \"id\": \"…\"}`, "
      + "which is cheaper — use it when you don't need the record back. "
      + "Any other value returns `400 Bad Request`.";

    @POST
    @Path("/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "Create a sublist by owner OID.",
        notes = CREATE_NOTES,
        response = Sublist.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created sublist record.", response = Sublist.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed, for example a `partner` that isn't an external team of the project (or any, when the owner isn't a project), or none from an external team member sharing with every member."),
        @ApiResponse(code = 402, message = "Payment Required — sublist quota reached."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to create a sublist under this owner."),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist.")
    })
    public Response createSublist(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`.\n"
                  + "If omitted, `project` is assumed. For example, `/abc123` is equivalent to `/project/abc123`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "OID of the owner the new sublist will belong to.", required = true)
        @PathParam("ownerOid") String ownerOid,
        @ApiParam(value = "Sublist to create", required = true)
        CreateSublistBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @POST
    @Path("/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "Create a sublist by owner ID.",
        notes = CREATE_NOTES,
        response = Sublist.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created sublist record.", response = Sublist.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed, for example a `partner` that isn't an external team of the project (or any, when the owner isn't a project), or none from an external team member sharing with every member."),
        @ApiResponse(code = 402, message = "Payment Required — sublist quota reached."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to create a sublist under this owner."),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist.")
    })
    public Response createSublistToProject(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`.\n"
                  + "If omitted, `project` is assumed. For example, `/id/foo` is equivalent to `/id/project/foo`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "ID of the owner the new sublist will belong to.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Sublist to create", required = true)
        CreateSublistBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get a sublist by OID.",
        notes = GET_NOTES,
        response = Sublist.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — sublist record.", response = Sublist.class),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — sublist does not exist.")
    })
    public Response getSublist(
        @ApiParam(value = "OID of the sublist.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @GET
    @Path("/id/{ownerType}/{ownerId}/{sublistId}")
    @ApiOperation(
        value = "Get a sublist by ID.",
        notes = GET_NOTES,
        response = Sublist.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — sublist record.", response = Sublist.class),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — sublist does not exist.")
    })
    public Response getSublistById(
        @ApiParam(value = OWNER_TYPE_BY_ID, required = false, example = "project")
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "ID of the owner the sublist belongs to.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "ID of the sublist.", required = true, example = "1234")
        @PathParam("sublistId") String sublistId
    ) { return null; }

    @GET
    @Path("/list/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "List sublists by owner OID.",
        notes = LIST_NOTES,
        response = Sublist.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of sublist records (may be empty).", response = Sublist.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist.")
    })
    public Response getSublistsByProjectOid(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`.\n"
                  + "If omitted, `project` is assumed. For example, `/list/abc123` is equivalent to `/list/project/abc123`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "OID of the owner.", required = true)
        @PathParam("ownerOid") String ownerOid
    ) { return null; }

    @GET
    @Path("/list/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "List sublists by owner ID.",
        notes = LIST_NOTES,
        response = Sublist.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of sublist records (may be empty).", response = Sublist.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist.")
    })
    public Response getSublistsByProjectId(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`.\n"
                  + "If omitted, `project` is assumed. For example, `/list/id/foo` is equivalent to `/list/id/project/foo`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "ID of the owner.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId
    ) { return null; }

    @PUT
    @Path("/{oid}")
    @ApiOperation(
        value = "Update a sublist by OID.",
        notes = "Updates an existing sublist and returns the complete updated record.\n\n"
              + "To archive or unarchive, set `archived: true` or `archived: false` in the body.",
        response = Sublist.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated sublist record.", response = Sublist.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to modify this sublist."),
        @ApiResponse(code = 404, message = "Not Found — sublist does not exist.")
    })
    public Response updateSublistByOid(
        @ApiParam(value = "OID of the sublist to update.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Sublist updates", required = true)
        UpdateSublistBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/id/{ownerType}/{ownerId}/{sublistId}")
    @ApiOperation(
        value = "Update a sublist by ID.",
        notes = "Updates an existing sublist and returns the complete updated record.\n\n"
              + "To archive or unarchive, set `archived: true` or `archived: false` in the body.",
        response = Sublist.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated sublist record.", response = Sublist.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to modify this sublist."),
        @ApiResponse(code = 404, message = "Not Found — sublist does not exist.")
    })
    public Response updateSublistById(
        @ApiParam(value = OWNER_TYPE_BY_ID, required = false, example = "project")
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "ID of the owner.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Sublist updates", required = true)
        UpdateSublistBody data,
        @ApiParam(value = "ID of the sublist to update.", required = true, example = "1234")
        @PathParam("sublistId") String sublistId,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/undo-remove/{oid}")
    @ApiOperation(
        value = "Undo the removal of a sublist by OID.",
        notes = UNDO_REMOVE_NOTES,
        response = Sublist.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — restored sublist record.", response = Sublist.class),
        @ApiResponse(code = 402, message = "Payment Required — sublist quota reached."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to restore this sublist."),
        @ApiResponse(code = 404, message = "Not Found — sublist does not exist.")
    })
    public Response undoRemoveSublistByOid(
        @ApiParam(value = "OID of the sublist to restore.", required = true)
        @PathParam("oid") String oid,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/undo-remove/id/{ownerType}/{ownerId}/{sublistId}")
    @ApiOperation(
        value = "Undo the removal of a sublist by ID.",
        notes = UNDO_REMOVE_NOTES,
        response = Sublist.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — restored sublist record.", response = Sublist.class),
        @ApiResponse(code = 402, message = "Payment Required — sublist quota reached."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to restore this sublist."),
        @ApiResponse(code = 404, message = "Not Found — sublist does not exist.")
    })
    public Response undoRemoveSublistById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`.\n"
                  + "If omitted, `project` is assumed. For example, `/undo-remove/id/foo/bar` "
                  + "is equivalent to `/undo-remove/id/project/foo/bar`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "ID of the owner.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "ID of the sublist to restore.", required = true, example = "1234")
        @PathParam("sublistId") String sublistId,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete a sublist by OID.",
        notes = DELETE_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to remove this sublist.")
    })
    public Response deleteSublistByOid(
        @ApiParam(value = "OID of the sublist to delete.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @DELETE
    @Path("/id/{ownerType}/{ownerId}/{sublistId}")
    @ApiOperation(
        value = "Delete a sublist by ID.",
        notes = DELETE_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to remove this sublist.")
    })
    public Response deleteSublistById(
        @ApiParam(value = OWNER_TYPE_BY_ID, required = false, example = "project")
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "ID of the owner.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "ID of the sublist to delete.", required = true, example = "1234")
        @PathParam("sublistId") String sublistId
    ) { return null; }
}
