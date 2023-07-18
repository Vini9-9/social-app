package viniprogramando.socialapp.rest;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/users")
public class UserResource {

  @POST
  public String createUser() {
    return "user created";
  }

}
