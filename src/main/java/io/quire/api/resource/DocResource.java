package io.quire.api.resource;

import io.quire.api.model.*;
import io.quire.api.model.doc.*;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/doc")
@Api(value = "doc", description = "Documents.")
@Produces({"application/json"})
public class DocResource {

    // The read endpoints share this.
    private static final String READ_403 =
        "Forbidden — the user can't access it, or didn't authorize it for this app.";

    // The create endpoints share this.
    private static final String CREATE_NOTES =
        "Adds a new document to the specified owner (`project`, `organization`, `folder`, or `smart-folder`) "
      + "and returns it.\n\n"
      + "Example — `POST /doc/id/project/my_project`:\n\n"
      + "```json\n"
      + "{\n"
      + "  \"name\": \"Release notes\",\n"
      + "  \"description\": \"What changed in **v2.0**.\",\n"
      + "  \"followers\": [\"me\"]\n"
      + "}\n"
      + "```\n\n"
      + "`followers` is allowed only when the owner is a project.\n\n"
      + "Returns `402` if the plan's document limit is reached.";

    // The list endpoints share this.
    private static final String LIST_NOTES =
        "Returns the owner's documents that the caller can see; one shown to "
      + "selected members only is left out for the others. Archived documents "
      + "are included (they carry `archivedAt`); removed ones are not.";

    // The delete endpoints share this.
    private static final String DELETE_NOTES =
        "Removes the document. Restore it with "
      + "`PUT /doc/undo-remove/id/{ownerType}/{ownerId}/{docId}` "
      + "(or `/doc/undo-remove/{oid}`).\n\n"
      + "Returns `204 No Content`, also when the document does not exist.";

    // The by-ID get, update and delete endpoints share this.
    private static final String OWNER_TYPE_BY_ID =
        "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
      + "If omitted, `project` is assumed; e.g., `/id/my_project/1234` "
      + "is equivalent to `/id/project/my_project/1234`.";

    // The create, update and undo-remove endpoints share this.
    private static final String RETURN_MODE =
        "(Optional) Response shape: `full` (default) returns the full document "
      + "record; `compact` returns only `{\"oid\": \"…\", \"id\": \"…\"}`, "
      + "which is cheaper — use it when you don't need the record back. "
      + "Any other value returns `400 Bad Request`.";

    // -------- Create --------

    @POST
    @Path("/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "Create a document by owner OID.",
        notes = CREATE_NOTES,
        response = Doc.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created doc record.", response = Doc.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed, for example a `partner` that isn't an external team of the project (or any, when the owner isn't a project), or none from an external team member sharing with every member."),
        @ApiResponse(code = 402, message = "Payment Required — document quota reached."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to create a doc in this owner."),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist.")
    })
    public Response createDoc(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/abc123` is equivalent to `/project/abc123`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner OID.", required = true)
        @PathParam("ownerOid") String ownerOid,
        @ApiParam(value = "Document to create.", required = true)
        CreateDocBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @POST
    @Path("/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "Create a document by owner ID.",
        notes = CREATE_NOTES,
        response = Doc.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created doc record.", response = Doc.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed, for example a `partner` that isn't an external team of the project (or any, when the owner isn't a project), or none from an external team member sharing with every member."),
        @ApiResponse(code = 402, message = "Payment Required — document quota reached."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to create a doc in this owner."),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist.")
    })
    public Response createDocById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Document to create.", required = true)
        CreateDocBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    // -------- Read --------

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get a document by OID.",
        notes = "Returns the full document record.",
        response = Doc.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — doc record.", response = Doc.class),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — doc does not exist.")
    })
    public Response getDoc(
        @ApiParam(value = "Document OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @GET
    @Path("/id/{ownerType}/{ownerId}/{docId}")
    @ApiOperation(
        value = "Get a document by ID.",
        notes = "Returns the full document record for the given owner and document ID.",
        response = Doc.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — doc record.", response = Doc.class),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — doc does not exist.")
    })
    public Response getDocById(
        @ApiParam(value = OWNER_TYPE_BY_ID, required = false, example = "project")
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Document ID.", required = true, example = "1234")
        @PathParam("docId") String docId
    ) { return null; }

    @GET
    @Path("/list/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "List documents by owner OID.",
        notes = LIST_NOTES,
        response = Doc.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of doc records (may be empty).", response = Doc.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist.")
    })
    public Response getDocsByOwnerOid(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/list/abc123` is equivalent to `/list/project/abc123`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner OID.", required = true)
        @PathParam("ownerOid") String ownerOid
    ) { return null; }

    @GET
    @Path("/list/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "List documents by owner ID.",
        notes = LIST_NOTES,
        response = Doc.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of doc records (may be empty).", response = Doc.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = READ_403),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist.")
    })
    public Response getDocsByOwnerId(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/list/id/foo` is equivalent to `/list/id/project/foo`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId
    ) { return null; }

    // -------- Update --------

    @PUT
    @Path("/{oid}")
    @ApiOperation(
        value = "Update a document by OID.",
        notes = "Updates an existing document and returns the updated record.\n\n"
              + "To archive or unarchive, set `archived: true` or `archived: false` in the body.",
        response = Doc.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated doc record.", response = Doc.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to modify this doc."),
        @ApiResponse(code = 404, message = "Not Found — doc does not exist.")
    })
    public Response updateDocByOid(
        @ApiParam(value = "Document OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateDocBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/id/{ownerType}/{ownerId}/{docId}")
    @ApiOperation(
        value = "Update a document by ID.",
        notes = "Updates an existing document and returns the updated record.\n\n"
              + "To archive or unarchive, set `archived: true` or `archived: false` in the body.",
        response = Doc.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated doc record.", response = Doc.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to modify this doc."),
        @ApiResponse(code = 404, message = "Not Found — doc does not exist.")
    })
    public Response updateDocById(
        @ApiParam(value = OWNER_TYPE_BY_ID, required = false, example = "project")
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Document ID.", required = true, example = "1234")
        @PathParam("docId") String docId,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateDocBody data,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    // -------- Undo remove --------

    @PUT
    @Path("/undo-remove/{oid}")
    @ApiOperation(
        value = "Undo the removal of a document by OID.",
        notes = "Restores a previously-removed document. "
            + "Idempotent: if the document is not currently removed, this is "
            + "a no-op and returns the current document record.\n\n"
            + "Subject to the document-per-owner quota: may return "
            + "`402 Payment Required` if the plan's document limit is "
            + "already reached.",
        response = Doc.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — restored doc record.", response = Doc.class),
        @ApiResponse(code = 402, message = "Payment Required — document quota reached."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to restore this doc."),
        @ApiResponse(code = 404, message = "Not Found — doc does not exist.")
    })
    public Response undoRemoveDocByOid(
        @ApiParam(value = "OID of the document to restore.", required = true)
        @PathParam("oid") String oid,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    @PUT
    @Path("/undo-remove/id/{ownerType}/{ownerId}/{docId}")
    @ApiOperation(
        value = "Undo the removal of a document by ID.",
        notes = "Restores a previously-removed document. "
            + "Idempotent: if the document is not currently removed, this is "
            + "a no-op and returns the current document record.\n\n"
            + "Subject to the document-per-owner quota: may return "
            + "`402 Payment Required` if the plan's document limit is "
            + "already reached.",
        response = Doc.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — restored doc record.", response = Doc.class),
        @ApiResponse(code = 402, message = "Payment Required — document quota reached."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to restore this doc."),
        @ApiResponse(code = 404, message = "Not Found — doc does not exist.")
    })
    public Response undoRemoveDocById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/undo-remove/id/foo/bar` "
                  + "is equivalent to `/undo-remove/id/project/foo/bar`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Document ID.", required = true, example = "1234")
        @PathParam("docId") String docId,

        @ApiParam(value = RETURN_MODE, example = "compact", allowableValues = "full, compact")
        @QueryParam("return") String returnMode
    ) { return null; }

    // -------- Delete --------

    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete a document by OID.",
        notes = DELETE_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to remove this doc.")
    })
    public Response deleteDocByOid(
        @ApiParam(value = "Document OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @DELETE
    @Path("/id/{ownerType}/{ownerId}/{docId}")
    @ApiOperation(
        value = "Delete a document by ID.",
        notes = DELETE_NOTES
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission to remove this doc.")
    })
    public Response deleteDocById(
        @ApiParam(value = OWNER_TYPE_BY_ID, required = false, example = "project")
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Document ID.", required = true, example = "1234")
        @PathParam("docId") String docId
    ) { return null; }
}
