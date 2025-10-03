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
    description = "A sublist is a collection of tasks, representing a subset of tasks from a larger scope."
)
@Produces({"application/json"})
public class SublistResource {

    @POST
    @Path("/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "Create a sublist by owner OID",
        notes = "Creates a new sublist under the specified owner (by OID).",
        response = Sublist.class
    )
    public Response createSublist(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`.\n"
                  + "If omitted, `project` is assumed. For example, `/abc123` is equivalent to `/project/abc123`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "OID of the owner the new sublist will belong to.", required = true)
        @PathParam("ownerOid") String ownerOid,
        @ApiParam(value = "Sublist to create", required = true)
        CreateSublistBody data
    ) { return null; }

    @POST
    @Path("/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "Create a sublist by owner ID",
        notes = "Creates a new sublist under the specified owner (by ID).",
        response = Sublist.class
    )
    public Response createSublistToProject(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`.\n"
                  + "If omitted, `project` is assumed. For example, `/id/foo` is equivalent to `/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "ID of the owner the new sublist will belong to.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Sublist to create", required = true)
        CreateSublistBody data
    ) { return null; }

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get a sublist by OID",
        notes = "Returns the complete sublist record for the given OID.",
        response = Sublist.class
    )
    public Response getSublist(
        @ApiParam(value = "OID of the sublist.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @GET
    @Path("/id/{ownerType}/{ownerId}/{id}")
    @ApiOperation(
        value = "Get a sublist by ID",
        notes = "Returns the complete sublist record for the given ID.",
        response = Sublist.class
    )
    public Response getSublistById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`.\n"
                  + "If omitted, `project` is assumed. For example, `/id/foo` is equivalent to `/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "ID of the owner the sublist belongs to.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "ID of the sublist.", required = true)
        @PathParam("id") String id
    ) { return null; }

    @GET
    @Path("/list/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "List sublists by owner OID",
        notes = "Returns all sublists under the specified owner (by OID).",
        response = Sublist.class,
        responseContainer = "List"
    )
    public Response getSublistsByProjectOid(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`.\n"
                  + "If omitted, `project` is assumed. For example, `/list/abc123` is equivalent to `/list/project/abc123`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "OID of the owner.", required = true)
        @PathParam("ownerOid") String ownerOid
    ) { return null; }

    @GET
    @Path("/list/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "List sublists by owner ID",
        notes = "Returns all sublists under the specified owner (by ID).",
        response = Sublist.class,
        responseContainer = "List"
    )
    public Response getSublistsByProjectId(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`.\n"
                  + "If omitted, `project` is assumed. For example, `/list/id/foo` is equivalent to `/list/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "ID of the owner.", required = true)
        @PathParam("ownerId") String ownerId
    ) { return null; }

    @PUT
    @Path("/{oid}")
    @ApiOperation(
        value = "Update a sublist by OID",
        notes = "Updates an existing sublist and returns the complete updated record.",
        response = Sublist.class
    )
    public Response updateSublist(
        @ApiParam(value = "OID of the sublist to update.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Sublist updates", required = true)
        UpdateSublistBody data
    ) { return null; }

    @PUT
    @Path("/id/{ownerType}/{ownerId}/{id}")
    @ApiOperation(
        value = "Update a sublist by ID",
        notes = "Updates an existing sublist and returns the complete updated record.",
        response = Sublist.class
    )
    public Response updateSublist(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`.\n"
                  + "If omitted, `project` is assumed. For example, `/id/foo` is equivalent to `/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "ID of the owner.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Sublist updates", required = true)
        UpdateSublistBody data,
        @ApiParam(value = "ID of the sublist to update.", required = true)
        @PathParam("id") String id
    ) { return null; }

    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete a sublist by OID",
        notes = "Deletes the sublist with the given OID."
    )
    @ApiResponses({
        @ApiResponse(
            code = 200,
            message = "ok",
            examples = @Example({
                @ExampleProperty(
                    mediaType = "application/json",
                    value = "{'Success': 'true'}"
                )
            })
        )
    })
    public Response deleteSublist(
        @ApiParam(value = "OID of the sublist to delete.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @DELETE
    @Path("/id/{ownerType}/{ownerId}/{id}")
    @ApiOperation(
        value = "Delete a sublist by ID",
        notes = "Deletes the sublist with the given ID."
    )
    @ApiResponses({
        @ApiResponse(
            code = 200,
            message = "ok",
            examples = @Example({
                @ExampleProperty(
                    mediaType = "application/json",
                    value = "{'Success': 'true'}"
                )
            })
        )
    })
    public Response deleteSublist(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`.\n"
                  + "If omitted, `project` is assumed. For example, `/id/foo` is equivalent to `/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "ID of the owner.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "ID of the sublist to delete.", required = true)
        @PathParam("id") String id
    ) { return null; }
}
