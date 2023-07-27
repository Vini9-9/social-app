package viniprogramando.socialapp.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import viniprogramando.socialapp.domain.model.Follower;
import viniprogramando.socialapp.rest.dto.FollowDtoResponse;
import viniprogramando.socialapp.rest.dto.ResponseError;
import viniprogramando.socialapp.service.FollowService;

import java.util.List;
import java.util.stream.Collectors;

@Path("/users/{userId}/following")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FollowingResource {

  @Inject
  FollowService followService;


  @GET
  public Response getAllFollowing (@PathParam("userId") Long userId) {
      List<Follower> followings = followService.getAllFollowing(userId);
      if(followings != null){
        List<FollowDtoResponse> followingsDto = followings.stream()
                .map(FollowDtoResponse::new)
                .collect(Collectors.toList());
        return Response.ok(followingsDto).build();
      }
      return ResponseError.createNotFound("userId")
              .withStatusCode(Status.NOT_FOUND.getStatusCode());
  }

}
