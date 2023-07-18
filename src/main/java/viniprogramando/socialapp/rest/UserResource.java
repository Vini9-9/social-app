package viniprogramando.socialapp.rest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import viniprogramando.socialapp.rest.dto.CreateUserRequest;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

  @POST
  public Response createUser(CreateUserRequest request) {
    return Response.ok("user created successfully").build();
  }

}
