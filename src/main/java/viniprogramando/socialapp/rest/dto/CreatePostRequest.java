package viniprogramando.socialapp.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePostRequest {

  @NotBlank(message = "message is required")
  private String message;

}
