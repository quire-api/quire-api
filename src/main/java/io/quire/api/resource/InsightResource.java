package io.quire.api.resource;

import io.quire.api.model.*;
import io.quire.api.model.field.*;
import io.quire.api.model.insight.*;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/insight")
@Api(
    value = "insight",
    description = "Insight views — configurable task views with filters and columns."
)
@Produces({"application/json"})
public class InsightResource {

    // -------- Create --------

    @POST
    @Path("/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "Create an insight view by owner OID.",
        notes = "Adds a new insight view to the specified owner (`project`, `organization`, `folder`, or `smart-folder`).",
        response = Insight.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created insight record.", response = Insight.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist."),
        @ApiResponse(code = 429, message = "Too Many Requests — insight quota reached.")
    })
    public Response createInsight(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/abc123` is equivalent to `/project/abc123`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner OID.", required = true)
        @PathParam("ownerOid") String ownerOid,
        @ApiParam(value = "Insight view to create.", required = true)
        CreateInsightBody data
    ) { return null; }

    @POST
    @Path("/id/{ownerType}/{ownerId}")
    @ApiOperation(
        value = "Create an insight view by owner ID.",
        notes = "Adds a new insight view to the specified owner by ID (`project`, `organization`, `folder`, or `smart-folder`).",
        response = Insight.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created insight record.", response = Insight.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist."),
        @ApiResponse(code = 429, message = "Too Many Requests — insight quota reached.")
    })
    public Response createInsightById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight view to create.", required = true)
        CreateInsightBody data
    ) { return null; }

    // -------- Read --------

    @GET
    @Path("/{oid}")
    @ApiOperation(
        value = "Get an insight view by OID.",
        notes = "Returns the full insight record.",
        response = Insight.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — insight record.", response = Insight.class),
        @ApiResponse(code = 404, message = "Not Found — insight does not exist.")
    })
    public Response getInsight(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @GET
    @Path("/id/{ownerType}/{ownerId}/{insightId}")
    @ApiOperation(
        value = "Get an insight view by ID.",
        notes = "Returns the full insight record for the given owner and insight ID.",
        response = Insight.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — insight record.", response = Insight.class),
        @ApiResponse(code = 404, message = "Not Found — insight or owner does not exist.")
    })
    public Response getInsightById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true, example = "insight1")
        @PathParam("insightId") String insightId
    ) { return null; }

    @GET
    @Path("/list/{ownerType}/{ownerOid}")
    @ApiOperation(
        value = "List insight views by owner OID.",
        notes = "Returns all insight views for the specified owner.",
        response = Insight.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of insight records (may be empty).", response = Insight.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist.")
    })
    public Response getInsightsByOwnerOid(
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
        value = "List insight views by owner ID.",
        notes = "Returns all insight views for the specified owner.",
        response = Insight.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — list of insight records (may be empty).", response = Insight.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Not Found — owner does not exist.")
    })
    public Response getInsightsByOwnerId(
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
        value = "Update an insight view by OID.",
        notes = "Updates an existing insight view and returns the updated record.",
        response = Insight.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated insight record.", response = Insight.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight does not exist.")
    })
    public Response updateInsightByOid(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateInsightBody data
    ) { return null; }

    @PUT
    @Path("/id/{ownerType}/{ownerId}/{insightId}")
    @ApiOperation(
        value = "Update an insight view by ID.",
        notes = "Updates an existing insight view and returns the updated record.",
        response = Insight.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated insight record.", response = Insight.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight or owner does not exist.")
    })
    public Response updateInsightById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true, example = "insight1")
        @PathParam("insightId") String insightId,
        @ApiParam(value = "Fields to update.", required = true)
        UpdateInsightBody data
    ) { return null; }

    // -------- Undo remove --------

    @PUT
    @Path("/undo-remove/{oid}")
    @ApiOperation(
        value = "Undo the removal of an insight view by OID.",
        notes = "Restores a previously-removed insight view. "
            + "Idempotent: if the insight is not currently removed, this is "
            + "a no-op and returns the current insight record.\n\n"
            + "Subject to the insight-per-owner quota: may return "
            + "`429 Too Many Requests` if the plan's insight limit is "
            + "already reached.",
        response = Insight.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — restored insight record.", response = Insight.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight does not exist."),
        @ApiResponse(code = 429, message = "Too Many Requests — insight quota reached.")
    })
    public Response undoRemoveInsightByOid(
        @ApiParam(value = "OID of the insight to restore.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @PUT
    @Path("/undo-remove/id/{ownerType}/{ownerId}/{insightId}")
    @ApiOperation(
        value = "Undo the removal of an insight view by ID.",
        notes = "Restores a previously-removed insight view. "
            + "Idempotent: if the insight is not currently removed, this is "
            + "a no-op and returns the current insight record.\n\n"
            + "Subject to the insight-per-owner quota: may return "
            + "`429 Too Many Requests` if the plan's insight limit is "
            + "already reached.",
        response = Insight.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — restored insight record.", response = Insight.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight or owner does not exist."),
        @ApiResponse(code = 429, message = "Too Many Requests — insight quota reached.")
    })
    public Response undoRemoveInsightById(
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
        @ApiParam(value = "Insight ID.", required = true, example = "insight1")
        @PathParam("insightId") String insightId
    ) { return null; }

    // -------- Delete --------

    @DELETE
    @Path("/{oid}")
    @ApiOperation(
        value = "Delete an insight view by OID.",
        notes = "Deletes the specified insight view.\n\n"
            + "> Note: Returns `204 No Content` regardless of whether the insight exists."
    )
    @ApiResponses({
        @ApiResponse(
            code = 204,
            message = "No Content"
        )
    })
    public Response deleteInsightByOid(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid
    ) { return null; }

    @DELETE
    @Path("/id/{ownerType}/{ownerId}/{insightId}")
    @ApiOperation(
        value = "Delete an insight view by ID.",
        notes = "Deletes the specified insight view.\n\n"
            + "> Note: Returns `204 No Content` regardless of whether the insight exists."
    )
    @ApiResponses({
        @ApiResponse(
            code = 204,
            message = "No Content"
        )
    })
    public Response deleteInsightById(
        @ApiParam(
            value = "Owner type. One of `project`, `organization`, `folder`, or `smart-folder`. "
                  + "If omitted, `project` is assumed; e.g., `/id/foo` is equivalent to `/id/project/foo`.",
            required = false
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true)
        @PathParam("insightId") String insightId
    ) { return null; }

    // -------- Custom fields --------
    //
    // Each field-extension endpoint is offered in two URL forms:
    //   by-OID: /insight/<ext>/{insightOid}[/{fieldName}[/{newName}]]
    //   by-ID:  /insight/<ext>/id/{ownerType}/{ownerId}/{insightId}[/...]
    // Both dispatch through the same handler; pick whichever is more
    // convenient for the caller.

    @POST
    @Path("/add-field/{oid}")
    @ApiOperation(
        value = "Add a custom-field definition to an insight view.",
        notes = "Adds a new [custom-field definition](#definition-FieldDefinition) "
              + "to the insight view (by OID). The response is the created "
              + "field in public form (same shape as entries in "
              + "`Insight.fields`, with an extra `name` key).\n\n"
              + "Only `formula` and `lookup` types are allowed on insight "
              + "views; all other field types are rejected with `400`.\n\n"
              + "Requires the `Admin` scope to invoke.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight does not exist."),
        @ApiResponse(code = 429, message = "Too Many Requests — custom-field quota reached.")
    })
    public Response addInsightField(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Field definition to add.", required = true)
        AddFieldBody data
    ) { return null; }

    @PUT
    @Path("/update-field/{oid}/{fieldName}")
    @ApiOperation(
        value = "Update a custom-field definition on an insight view.",
        notes = "Updates the content of an existing custom field. "
              + "`type` is immutable — if supplied, it must match the existing "
              + "type. Keys that are omitted leave their current values intact "
              + "(including individual flag bits — flags are merged, not replaced).\n\n"
              + "Requires the `Admin` scope to invoke.\n\n"
              + "To rename a field, use `/insight/rename-field/id/{ownerType}/{ownerId}/{insightId}/{fieldName}/{newName}`; "
              + "to reorder, use `/insight/move-field/id/{ownerType}/{ownerId}/{insightId}/{fieldName}`. "
              + "(The `/{oid}/...` URL form is also accepted.)\n\n"
              + "Response body is a `FieldDefinition` with an extra `name` key "
              + "(equal to the field's name), or an empty object if the field does not exist.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight does not exist.")
    })
    public Response updateInsightField(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Name of the field to update.", required = true, example = "Priority")
        @PathParam("fieldName") String fieldName,
        @ApiParam(value = "New field content.", required = true)
        UpdateFieldBody data
    ) { return null; }

    @DELETE
    @Path("/remove-field/{oid}/{fieldName}")
    @ApiOperation(
        value = "Remove a custom-field definition from an insight view.",
        notes = "Removes the named custom field from the insight view.\n\n"
              + "Requires the `Admin` scope to invoke.\n\n"
              + "> Note: Returns `204 No Content` regardless of whether the field exists."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content")
    })
    public Response removeInsightField(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Name of the field to remove.", required = true)
        @PathParam("fieldName") String fieldName
    ) { return null; }

    @PUT
    @Path("/rename-field/{oid}/{fieldName}/{newName}")
    @ApiOperation(
        value = "Rename a custom-field definition on an insight view.",
        notes = "Renames the field in place and returns the renamed field. "
              + "The field's content is preserved.\n\n"
              + "Requires the `Admin` scope to invoke.\n\n"
              + "Response body is a `FieldDefinition` with an extra `name` key "
              + "(equal to the new name), or an empty object if the source "
              + "field is missing or the target name is already in use.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — renamed field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight does not exist.")
    })
    public Response renameInsightField(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Current field name.", required = true, example = "Priority")
        @PathParam("fieldName") String fieldName,
        @ApiParam(value = "New field name.", required = true, example = "Importance")
        @PathParam("newName") String newName
    ) { return null; }

    @PUT
    @Path("/move-field/{oid}/{fieldName}")
    @ApiOperation(
        value = "Reorder a custom-field definition on an insight view.",
        notes = "Moves the named field to a new position. By default the "
              + "field is moved to the end; pass `?before={otherName}` to "
              + "place it immediately before another field.\n\n"
              + "Requires the `Admin` scope to invoke.\n\n"
              + "Response body is a `FieldDefinition` with an extra `name` key "
              + "(equal to the field's name), or an empty object if the field "
              + "does not exist or `?before={otherName}` refers to a missing field.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — moved field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight does not exist.")
    })
    public Response moveInsightField(
        @ApiParam(value = "Insight OID.", required = true)
        @PathParam("oid") String oid,
        @ApiParam(value = "Name of the field to move.", required = true, example = "Priority")
        @PathParam("fieldName") String fieldName,
        @ApiParam(
            value = "(Optional) Name of the field to insert before. "
                  + "If omitted, the field is moved to the end.",
            example = "before=Priority",
            required = false
        )
        @QueryParam("before") String before
    ) { return null; }

    // -------- Custom fields — by-id variants (#24535) --------

    @POST
    @Path("/add-field/id/{ownerType}/{ownerId}/{insightId}")
    @ApiOperation(
        value = "Add a custom-field definition to an insight view by ID.",
        notes = "Adds a custom-field definition to an insight view, "
              + "identifying the insight by owner-type + owner ID + insight ID. "
              + "Only `formula` and `lookup` field types are allowed on "
              + "insight views. See also `/insight/add-field/{oid}` for the OID form.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — created field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight or owner does not exist."),
        @ApiResponse(code = 429, message = "Too Many Requests — custom-field quota reached.")
    })
    public Response addInsightFieldById(
        @ApiParam(
            value = "Owner type.",
            allowableValues = "project, organization, folder, smart-folder",
            required = true,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true, example = "insight1")
        @PathParam("insightId") String insightId,
        @ApiParam(value = "Field definition to add.", required = true)
        AddFieldBody data
    ) { return null; }

    @PUT
    @Path("/update-field/id/{ownerType}/{ownerId}/{insightId}/{fieldName}")
    @ApiOperation(
        value = "Update a custom-field definition on an insight view by ID.",
        notes = "Updates an existing custom-field definition on an insight "
              + "view, identifying the insight by owner-type + owner ID + "
              + "insight ID. `type` is immutable; omitted keys preserve "
              + "current values. See also `/insight/update-field/{oid}/{fieldName}` "
              + "for the OID form.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — updated field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 400, message = "Bad Request — body validation failed."),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight or owner does not exist.")
    })
    public Response updateInsightFieldById(
        @ApiParam(
            value = "Owner type.",
            allowableValues = "project, organization, folder, smart-folder",
            required = true,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true, example = "insight1")
        @PathParam("insightId") String insightId,
        @ApiParam(value = "Name of the field to update.", required = true, example = "Priority")
        @PathParam("fieldName") String fieldName,
        @ApiParam(value = "New field content.", required = true)
        UpdateFieldBody data
    ) { return null; }

    @DELETE
    @Path("/remove-field/id/{ownerType}/{ownerId}/{insightId}/{fieldName}")
    @ApiOperation(
        value = "Remove a custom-field definition from an insight view by ID.",
        notes = "Removes the named custom field from an insight view, "
              + "identifying the insight by owner-type + owner ID + insight "
              + "ID. Returns `204 No Content` regardless of whether the "
              + "field exists. See also `/insight/remove-field/{oid}/{fieldName}` "
              + "for the OID form."
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content")
    })
    public Response removeInsightFieldById(
        @ApiParam(
            value = "Owner type.",
            allowableValues = "project, organization, folder, smart-folder",
            required = true
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true)
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true)
        @PathParam("insightId") String insightId,
        @ApiParam(value = "Name of the field to remove.", required = true)
        @PathParam("fieldName") String fieldName
    ) { return null; }

    @PUT
    @Path("/rename-field/id/{ownerType}/{ownerId}/{insightId}/{fieldName}/{newName}")
    @ApiOperation(
        value = "Rename a custom-field definition on an insight view by ID.",
        notes = "Renames a custom-field definition on an insight view, "
              + "identifying the insight by owner-type + owner ID + insight "
              + "ID. The field's content is preserved. See also "
              + "`/insight/rename-field/{oid}/{fieldName}/{newName}` for the OID form.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — renamed field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight or owner does not exist.")
    })
    public Response renameInsightFieldById(
        @ApiParam(
            value = "Owner type.",
            allowableValues = "project, organization, folder, smart-folder",
            required = true,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true, example = "insight1")
        @PathParam("insightId") String insightId,
        @ApiParam(value = "Current field name.", required = true, example = "Priority")
        @PathParam("fieldName") String fieldName,
        @ApiParam(value = "New field name.", required = true, example = "Importance")
        @PathParam("newName") String newName
    ) { return null; }

    @PUT
    @Path("/move-field/id/{ownerType}/{ownerId}/{insightId}/{fieldName}")
    @ApiOperation(
        value = "Reorder a custom-field definition on an insight view by ID.",
        notes = "Reorders a custom-field definition on an insight view, "
              + "identifying the insight by owner-type + owner ID + insight "
              + "ID. Pass `?before={otherName}` to place the field before "
              + "another; omit to move it to the end. See also "
              + "`/insight/move-field/{oid}/{fieldName}` for the OID form.",
        response = FieldDefinitionWithName.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK — moved field definition.", response = FieldDefinitionWithName.class),
        @ApiResponse(code = 403, message = "Forbidden — caller lacks permission (or Admin scope required)."),
        @ApiResponse(code = 404, message = "Not Found — insight or owner does not exist.")
    })
    public Response moveInsightFieldById(
        @ApiParam(
            value = "Owner type.",
            allowableValues = "project, organization, folder, smart-folder",
            required = true,
            example = "project"
        )
        @PathParam("ownerType") String ownerType,
        @ApiParam(value = "Owner ID.", required = true, example = "my_project")
        @PathParam("ownerId") String ownerId,
        @ApiParam(value = "Insight ID.", required = true, example = "insight1")
        @PathParam("insightId") String insightId,
        @ApiParam(value = "Name of the field to move.", required = true, example = "Priority")
        @PathParam("fieldName") String fieldName,
        @ApiParam(
            value = "(Optional) Name of the field to insert before. "
                  + "If omitted, the field is moved to the end.",
            example = "before=Priority",
            required = false
        )
        @QueryParam("before") String before
    ) { return null; }
}
