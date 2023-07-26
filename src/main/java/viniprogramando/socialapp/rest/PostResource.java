package viniprogramando.socialapp.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import viniprogramando.socialapp.domain.model.Post;
import viniprogramando.socialapp.domain.model.User;
import viniprogramando.socialapp.domain.repository.PostRepository;
import viniprogramando.socialapp.domain.repository.UserRepository;
import viniprogramando.socialapp.rest.dto.*;
import viniprogramando.socialapp.service.PostService;
import viniprogramando.socialapp.service.UserService;

import java.util.Set;

@Path("/users/{userId}/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostResource {

  @Inject
  private UserRepository userRepository;
  @Inject
  private PostRepository postRepository;

  @Inject
  UserService userService;
  @Inject
  PostService postService;

  @Inject
  Validator validator;

  @POST
  @Transactional
  public Response createPost(@PathParam("userId") Long userId, CreatePostRequest request) {
    Set<ConstraintViolation<CreatePostRequest>> violations = validator.validate(request);
    if(!violations.isEmpty()){
      return ResponseError.createFromValidation(violations)
              .withStatusCode(ResponseError.UNPROCESSABLE_ENTITY);
    }
    User user = userRepository.findById(userId);
    if(user != null){
      int remainingChars = postService.getNewRemainingChars(request, user);
      if(remainingChars > 0){
        user.setRemainingCharacters(remainingChars);
        userRepository.persist(user);
        Post post = postService.createPost(request, user);

        return Response.status(Status.CREATED).entity(new PostDtoResponse(post)).build();
      } else {
        return ResponseError
                .createNotAllowed(
                        "remainingCharacters",
                        "You have reached your daily limit of characters")
                .withStatusCode(Status.METHOD_NOT_ALLOWED.getStatusCode());
      }
    }
    return ResponseError.createNotFound("id")
            .withStatusCode(Status.NOT_FOUND.getStatusCode());
  }
}
