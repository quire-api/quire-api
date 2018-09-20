package io.quire.api.resource;

import io.swagger.annotations.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import io.quire.api.model.Project;

@Path("/projects")
@Api(value = "/projects", description = "Operations about projects", authorizations = {
  @Authorization(value = "petstore_auth",
  scopes = {
    @AuthorizationScope(scope = "write:projects", description = "modify projects in your account"),
    @AuthorizationScope(scope = "read:projects", description = "read your projects")
  })
})
@Produces({"application/json", "application/xml"})
public class ProjectResource {

    @GET
    @Path("/{projectId}")
    @ApiOperation(value = "Find project by ID",
      notes = "Returns a project",
      response = Project.class,
      authorizations = @Authorization(value = "api_key")
    )
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid ID supplied"),
        @ApiResponse(code = 404, message = "Project not found") })
    public Response getProjectById(
        @ApiParam(value = "ID of project that needs to be fetched", required = true)
        @PathParam("projectId") String projectId)
        throws io.quire.api.exception.NotFoundException {
        return null;
    }
}
