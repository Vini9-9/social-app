package viniprogramando.socialapp.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import viniprogramando.socialapp.domain.model.Post;
import viniprogramando.socialapp.domain.model.User;
import viniprogramando.socialapp.domain.repository.PostRepository;
import viniprogramando.socialapp.rest.dto.CreatePostRequest;

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
        int remainingChars = user.getRemainingCharacters() - request.getMessage().length();
        return remainingChars;
    }


}
