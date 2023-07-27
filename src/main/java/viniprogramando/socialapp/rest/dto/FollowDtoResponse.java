package viniprogramando.socialapp.rest.dto;

import lombok.Data;
import viniprogramando.socialapp.domain.model.Follower;

@Data
public class FollowDtoResponse {

    private Long id;
    private String name;

    public FollowDtoResponse() {
    }

    public FollowDtoResponse(Follower follower){
        this(follower.getId(), follower.getFollower().getUsername());
    }

    public FollowDtoResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
