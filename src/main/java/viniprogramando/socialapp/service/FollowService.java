package viniprogramando.socialapp.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import viniprogramando.socialapp.domain.model.Follower;
import viniprogramando.socialapp.domain.model.User;
import viniprogramando.socialapp.domain.repository.FollowRepository;
import viniprogramando.socialapp.domain.repository.UserRepository;

import java.util.List;

@ApplicationScoped
public class FollowService {

    @Inject
    private FollowRepository followRepository;
    @Inject
    private UserRepository userRepository;

    @Transactional
    public Follower follow (Long userId, Long followerId) {
        User user = userRepository.findById(userId);
        User userFollower = userRepository.findById(followerId);
        if(isValidFollow(user, userFollower)){
            Follower follower = new Follower(user, userFollower);
            followRepository.persist(follower);
            return follower;
        }
        return null;
    }

    public boolean isValidFollow(User user, User userFollower){
        return  user != null &&
                userFollower != null &&
                !user.isBlocked() &&
                !userFollower.isBlocked();
    }


    public boolean unfollow(Long userId, Long followerId) {
        return followRepository.deleteUserFollowsUser(userId, followerId) > 0;
    }

    public List<Follower> getAllFollowers(Long userId) {
        return followRepository.findAllFollowers(userId);
    }

    public List<Follower> getAllFollowing(Long userId) {
        return followRepository.findAllFollowing(userId);
    }
}
