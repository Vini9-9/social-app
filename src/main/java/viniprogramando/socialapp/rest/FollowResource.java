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
import viniprogramando.socialapp.domain.repository.FollowRepository;
import viniprogramando.socialapp.domain.repository.UserRepository;
import viniprogramando.socialapp.rest.dto.FollowDtoResponse;
import viniprogramando.socialapp.rest.dto.FollowRequest;
import viniprogramando.socialapp.rest.dto.PostDtoResponse;
import viniprogramando.socialapp.rest.dto.ResponseError;
import viniprogramando.socialapp.service.FollowService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/users/{userId}/follower")
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
      return Response.status(Status.ACCEPTED).build();
    }
    return ResponseError.createNotFound("id")
            .withStatusCode(Status.NOT_FOUND.getStatusCode());
  }

  @DELETE
  @Transactional
  public Response unfollow (@PathParam("userId") Long userId, @QueryParam("followerId") Long followerId) {
    Set<ConstraintViolation<FollowRequest>> violations = validator.validate(new FollowRequest(followerId));
    if(!violations.isEmpty()){
      return ResponseError.createFromValidation(violations)
              .withStatusCode(ResponseError.UNPROCESSABLE_ENTITY);
    }
      if(followService.unfollow(userId, followerId)){
        return Response.noContent().build();
      }
      return ResponseError.createNotFound("userId follows followerId")
              .withStatusCode(Response.Status.NOT_FOUND.getStatusCode());
  }
  @GET
  public Response getAllFollowers (@PathParam("userId") Long userId) {
      List<Follower> followers = followService.getAllFollowers(userId);
      if(followers != null){
        List<FollowDtoResponse> followersDto = followers.stream()
                .map(FollowDtoResponse::new)
                .collect(Collectors.toList());
        return Response.ok(followersDto).build();
      }
      return ResponseError.createNotFound("userId")
              .withStatusCode(Response.Status.NOT_FOUND.getStatusCode());
  }

}
