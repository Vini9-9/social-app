package viniprogramando.socialapp.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserRequest {

  @NotBlank(message = "username is required")
  private String username;
  @NotBlank(message = "email is required")
  private String email;
  @NotBlank(message = "birthDate is required")
  @JsonFormat(pattern = "dd/MM/yyyy")
  private String birthDate;

}
