package viniprogramando.socialapp.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FollowRequest {

  @NotNull(message = "followerId is required")
  private Long followerId;

}
