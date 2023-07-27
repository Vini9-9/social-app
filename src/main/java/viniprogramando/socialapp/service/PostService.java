package viniprogramando.socialapp.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import viniprogramando.socialapp.domain.model.Post;
import viniprogramando.socialapp.domain.model.User;
import viniprogramando.socialapp.domain.repository.PostRepository;
import viniprogramando.socialapp.rest.dto.CreatePostRequest;
import viniprogramando.socialapp.rest.dto.ResponseError;

@ApplicationScoped
public class PostService {

    @Inject
    private PostRepository postRepository;

    @Transactional
    public Post createPost(CreatePostRequest request, User user) {
        Post post = new Post();
        post.setText(request.getMessage());
        post.setUser(user);
        postRepository.persist(post);
        return post;
    }

    public int getNewRemainingChars(CreatePostRequest request, User user) {
        if(user.isBlocked()){
            return -1;
        }
        return user.getRemainingCharacters() - request.getMessage().length();
    }
    public int getNewRemainingChars(CreatePostRequest request, User user, int lengthOldPost) {
        return user.getRemainingCharacters() + lengthOldPost - request.getMessage().length();
    }

    @Transactional
    public void deletePost(Post post, User user) {
        user.setRemainingCharacters(user.getRemainingCharacters() + post.getText().length());
        postRepository.delete(post);
    }

    public Response createNotFoundResponse(Long postId) {
        if(postRepository.findByIdOptional(postId).isEmpty()){
            return ResponseError.createNotFound("userId")
                    .withStatusCode(Response.Status.NOT_FOUND.getStatusCode());
        }
        return ResponseError.createNotFound("postId")
                .withStatusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}
