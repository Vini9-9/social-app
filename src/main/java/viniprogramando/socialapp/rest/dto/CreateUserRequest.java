package viniprogramando.socialapp.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

public class CreateUserRequest {

  @NotBlank(message = "username is required")
  private String username;
  @NotBlank(message = "email is required")
  private String email;
  @NotBlank(message = "birthDate is required")
  @JsonFormat(pattern = "dd/MM/yyyy")
  private String birthDate;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }
}
