package viniprogramando.socialapp.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CreateUserRequest {

  private String username;
  private String email;
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
