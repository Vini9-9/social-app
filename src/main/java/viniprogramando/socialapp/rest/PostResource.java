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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

  @DELETE
  @Path("{postId}")
  @Transactional
  public Response deletePost (@PathParam("userId") Long userId, @PathParam("postId") Long postId){
    Post post = postRepository.findByIdAndUserId(postId, userId);
    if(post != null){
      User user = userRepository.findById(userId);
      postService.deletePost(post, user);
    }
    return ResponseError.createNotFound("id")
            .withStatusCode(Status.NOT_FOUND.getStatusCode());
  }
  @GET
  @Path("{postId}")
  public Response getPostsByUser (@PathParam("userId") Long userId, @PathParam("postId") Long postId){
    Post post = postRepository.findByIdAndUserId(postId, userId);
    if(post != null){
      return Response.ok(new PostDtoResponse(post)).build();
    }
    return ResponseError.createNotFound("id")
            .withStatusCode(Status.NOT_FOUND.getStatusCode());
  }
  @GET
  public Response getAllPostsByUser (@PathParam("userId") Long userId){
    List<Post> posts = postRepository.findAllByUserId(userId);
    if(!posts.isEmpty()){
      List<PostDtoResponse> postsDto = posts.stream().map(PostDtoResponse::new).collect(Collectors.toList());
      return Response.ok(postsDto).build();
    }
    return ResponseError.createNotFound("id")
            .withStatusCode(Status.NOT_FOUND.getStatusCode());
  }
  @PUT
  @Path("{postId}")
  @Transactional
  public Response updatePost (@PathParam("userId") Long userId,
                              @PathParam("postId") Long postId,
                              CreatePostRequest request){
    User user = userRepository.findById(userId);
    if(user != null){
        Post post = postRepository.findByIdAndUserId(postId, userId);
      int remainingChars = postService.getNewRemainingChars(request, user, post.getText().length());
      if(remainingChars > 0){
        post.setText(request.getMessage());
        user.setRemainingCharacters(remainingChars);
        userRepository.persist(user);
        postRepository.persist(post);
      return Response.ok(new PostDtoResponse(post)).build();
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
