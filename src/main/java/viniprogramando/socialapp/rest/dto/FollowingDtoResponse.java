package viniprogramando.socialapp.rest.dto;

import lombok.Data;
import viniprogramando.socialapp.domain.model.Follower;

@Data
public class FollowingDtoResponse {

    private Long id;
    private String username;

    public FollowingDtoResponse() {
    }

    public FollowingDtoResponse(Follower follower){
        this(follower.getFollower().getId(), follower.getFollower().getUsername());
    }

    public FollowingDtoResponse(Long id, String username) {
        this.id = id;
        this.username = username;
    }

}
