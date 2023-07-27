package viniprogramando.socialapp.rest.dto;

import lombok.Data;
import viniprogramando.socialapp.domain.model.Follower;

@Data
public class FollowDtoResponse {

    private Long id;
    private String username;

    public FollowDtoResponse() {
    }

    public FollowDtoResponse(Follower follower){
        this(follower.getUser().getId(), follower.getUser().getUsername());
    }

    public FollowDtoResponse(Long id, String username) {
        this.id = id;
        this.username = username;
    }

}
