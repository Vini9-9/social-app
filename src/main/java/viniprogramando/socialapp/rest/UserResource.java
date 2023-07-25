package viniprogramando.socialapp.rest;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import viniprogramando.socialapp.domain.model.User;
import viniprogramando.socialapp.domain.repository.UserRepository;
import viniprogramando.socialapp.rest.dto.CreateUserRequest;
import viniprogramando.socialapp.rest.dto.UserDtoResponse;
import viniprogramando.socialapp.service.UserService;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

  @Inject
  UserRepository userRepository;

  @Inject
  UserService userService;

  @POST
  @Transactional
  public Response createUser(CreateUserRequest request) {
      if(userService.userIsValid(request)){
        UserDtoResponse userDto = userService.createUser(request);
        return Response.status(Status.CREATED).entity(userDto).build();
      }
        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
  }
  @GET
  public Response listAllUsers() {
    PanacheQuery<User> query = userRepository.findAll();
    return Response.ok(query.list()).build();
  }
  @DELETE
  @Path("{id}")
  @Transactional
  public Response deleteUser(@PathParam("id") Long id) {
    User user = userRepository.findById(id);
    if(user != null){
      userRepository.delete(user);
      return Response.ok().build();
    }
    return Response.status(Status.NOT_FOUND).build();
  }
//  @PUT
//  @Path("{id}")
//  @Transactional
//  public Response updateUser(@PathParam("id") Long id, CreateUserRequest userData) {
//    User user = userRepository.findById(id);
//    if(user != null){
//      if(!user.getUsername().equalsIgnoreCase(userData.getUsername())){
//        user.setUsername(userData.getUsername());
//      }
//      user.setBirthDate(userData.getBirthDate());
//      user.setEmail(userData.getEmail());
//      return Response.ok().build();
//    }
//    return Response.status(Status.NOT_FOUND).build();
//  }

}
