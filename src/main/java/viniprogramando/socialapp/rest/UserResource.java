package viniprogramando.socialapp.rest;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import viniprogramando.socialapp.domain.model.User;
import viniprogramando.socialapp.rest.dto.CreateUserRequest;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

  @POST
  @Transactional
  public Response createUser(CreateUserRequest request) {
      User user = new User(request);
      user.persist();
      return Response.status(Status.CREATED).entity(user).build();
  }
  @GET
  public Response listAllUsers() {
    PanacheQuery<PanacheEntityBase> query = User.findAll();
    return Response.ok(query.list()).build();
  }

}
