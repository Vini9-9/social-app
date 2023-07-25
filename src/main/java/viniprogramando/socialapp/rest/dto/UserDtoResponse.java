package viniprogramando.socialapp.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import viniprogramando.socialapp.domain.model.User;

import java.time.LocalDate;

public class UserDtoResponse {

  private String username;
  private String email;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate birthDate;
  private Integer remainingCharacters = 1000;

  public UserDtoResponse(User user) {
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.birthDate = user.getBirthDate();
    this.remainingCharacters = user.getRemainingCharacters();
  }

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

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public Integer getRemainingCharacters() {
    return remainingCharacters;
  }

  public void setRemainingCharacters(Integer remainingCharacters) {
    this.remainingCharacters = remainingCharacters;
  }
}
