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

    // -------- Create --------

    @POST
    @Path("/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "Create a document by owner OID.",
        notes = "Adds a new document to the specified owner (`project`, `organization`, `folder`, or `smart-folder`).",
        response = Doc.class
    )
    public Response createDoc(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/abc123` is equivalent to `/project/abc123`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner OID.", required = true)
        @PathParam("ownerOid") String ownerOid,
        @ApiParam(value = "Document to create.", required = true)
        CreateDocBody data
    ) { return null; }

    @POST
    @Path("/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "Create a document by owner ID.",
        notes = "Adds a new document to the specified owner by ID (`project`, `organization`, `folder`, or `smart-folder`).",
        response = Doc.class
    )
    public Response createDocById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Document to create.", required = true)
        CreateDocBody data
    ) { return null; }

    // -------- Read --------

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get a document by OID.",
        notes = "Returns the full document record.",
        response = Doc.class
    )
    public Response getDoc(
        @ApiParam(value = "Document OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @GET
    @Path("/id/{ownerType}/{ownerId}/{id}")
    @ApiOperation(
        value = "Get a document by ID.",
        notes = "Returns the full document record for the given owner and document ID.",
        response = Doc.class
    )
    public Response getDocById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Document ID.", required = true)
        @PathParam("id") String id
    ) { return null; }

    @GET
    @Path("/list/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "List documents by owner OID.",
        notes = "Returns all documents for the specified owner.",
        response = Doc.class,
        responseContainer = "List"
    )
    public Response getDocsByOwnerOid(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/list/abc123` is equivalent to `/list/project/abc123`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner OID.", required = true)
        @PathParam("ownerOid") String ownerOid
    ) { return null; }

    @GET
    @Path("/list/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "List documents by owner ID.",
        notes = "Returns all documents for the specified owner.",
        response = Doc.class,
        responseContainer = "List"
    )
    public Response getDocsByOwnerId(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/list/id/foo` is equivalent to `/list/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true)
        @PathParam("ownerId") String ownerId
    ) { return null; }

    // -------- Update --------

    @PUT
    @Path("/{oid}")
    @ApiOperation(
        value = "Update a document by OID.",
        notes = "Updates an existing document and returns the updated record.",
        response = Doc.class
    )
    public Response updateDocByOid(
        @ApiParam(value = "Document OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateDocBody data
    ) { return null; }

    @PUT
    @Path("/id/{ownerType}/{ownerId}/{id}")
    @ApiOperation(
        value = "Update a document by ID.",
        notes = "Updates an existing document and returns the updated record.",
        response = Doc.class
    )
    public Response updateDocById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Document ID.", required = true)
        @PathParam("id") String id,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateDocBody data
    ) { return null; }

    // -------- Delete --------

    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete a document by OID.",
        notes = "Deletes the specified document."
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ok",
            examples = @Example({
                @ExampleProperty(mediaType = "application/json", value = "{'success': true}")
            }))
    })
    public Response deleteDocByOid(
        @ApiParam(value = "Document OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @DELETE
    @Path("/id/{ownerType}/{ownerId}/{id}")
    @ApiOperation(
        value = "Delete a document by ID.",
        notes = "Deletes the specified document."
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ok",
            examples = @Example({
                @ExampleProperty(mediaType = "application/json", value = "{'success': true}")
            }))
    })
    public Response deleteDocById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Document ID.", required = true)
        @PathParam("id") String id
    ) { return null; }
}
