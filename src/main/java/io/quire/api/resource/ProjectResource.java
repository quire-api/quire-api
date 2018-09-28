package io.quire.api.resource;

import io.quire.api.annotation.CodeSample;
import io.quire.api.annotation.CodeSampleLangs;
import io.quire.api.model.CreateProjectBody;
import io.quire.api.model.UpdateProjectBody;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import io.quire.api.model.Project;

@Path("/projects")
@Api(value = "/projects", description = "Operations about projects", authorizations = {
  @Authorization(value = "quire_auth",
  scopes = {
    @AuthorizationScope(scope = "write:projects", description = "modify projects in your account"),
    @AuthorizationScope(scope = "read:projects", description = "read your projects")
  })
})
@Produces({"application/json"})
public class ProjectResource {

    @POST
    @ApiOperation(value = "Create a new project.",
      notes = "Creates a new project in an organization.",
      response = Project.class
    )
    @ApiResponses(value = {
      @ApiResponse(code = 400, message = "Invalid ID supplied"),
      @ApiResponse(code = 403, message = "Not allow")})
    public Response create(
        @ApiParam(value = "Project to create", required = true)
        CreateProjectBody data) {
        return null;
    }

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Get a specific project",
      notes = "returns the complete project record for a single project.",
      response = Project.class
    )
    @ApiResponses(value = {
      @ApiResponse(code = 400, message = "Invalid ID supplied"),
      @ApiResponse(code = 404, message = "Project not found")})
    public Response getProject(
        @ApiParam(value = "ID of project that needs to be fetched", required = true)
        @PathParam("id") String id) {
        return null;
    }

    @PUT
    @Path("/{id}")
    @ApiOperation(value = "Update a specific project",
      notes = "A specific, existing project can be updated by making a PUT request on the URL for that project.\n" +
              "Returns the complete updated project record.",
      response = Project.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied"),
        @ApiResponse(code = 403, message = "Not allow"),
        @ApiResponse(code = 404, message = "Project not found")})
    public Response update(
        @ApiParam(value = "ID of project that needs to be updated", required = true)
        @PathParam("id") String id,
        @ApiParam(value = "Project to create", required = true)
        UpdateProjectBody data) {
        return null;
    }
}
