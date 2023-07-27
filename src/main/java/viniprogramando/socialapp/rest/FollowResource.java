package viniprogramando.socialapp.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import viniprogramando.socialapp.domain.model.Follower;
import viniprogramando.socialapp.domain.model.Post;
import viniprogramando.socialapp.domain.model.User;
import viniprogramando.socialapp.domain.repository.FollowRepository;
import viniprogramando.socialapp.domain.repository.UserRepository;
import viniprogramando.socialapp.rest.dto.FollowDtoResponse;
import viniprogramando.socialapp.rest.dto.FollowRequest;
import viniprogramando.socialapp.rest.dto.PostDtoResponse;
import viniprogramando.socialapp.rest.dto.ResponseError;
import viniprogramando.socialapp.service.FollowService;
import viniprogramando.socialapp.service.PostService;
import viniprogramando.socialapp.service.UserService;

import java.util.Set;

@Path("/users/{userId}/follow")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FollowResource {

  @Inject
  private UserRepository userRepository;
  @Inject
  private FollowRepository followRepository;

  @Inject
  FollowService followService;

  @Inject
  Validator validator;

  @PUT
  @Transactional
  public Response follow (@PathParam("userId") Long userId, FollowRequest request) {
    Set<ConstraintViolation<FollowRequest>> violations = validator.validate(request);
    if(!violations.isEmpty()){
      return ResponseError.createFromValidation(violations)
              .withStatusCode(ResponseError.UNPROCESSABLE_ENTITY);
    }
    Follower follower = followService.follow(userId, request.getFollowerId());
    if(follower != null){
      return Response.status(Status.ACCEPTED).entity(new FollowDtoResponse(follower)).build();
    }
    return ResponseError.createNotFound("id")
            .withStatusCode(Status.NOT_FOUND.getStatusCode());
  }

}
