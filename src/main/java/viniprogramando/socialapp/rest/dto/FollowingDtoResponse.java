package viniprogramando.socialapp.rest.dto;

import lombok.Data;
import viniprogramando.socialapp.domain.model.Follower;

@Data
public class FollowingDtoResponse {

    private Long id;
    private String username;
    private boolean followBack;

    public FollowingDtoResponse() {
    }

    public FollowingDtoResponse(Follower follower, boolean userFollowBack){
        this(follower.getFollower().getId(), follower.getFollower().getUsername(), userFollowBack);
    }

    public FollowingDtoResponse(Long id, String username, boolean followBack) {
        this.id = id;
        this.username = username;
        this.followBack = followBack;
    }

}
