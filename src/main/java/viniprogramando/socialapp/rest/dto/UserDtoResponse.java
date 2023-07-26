package viniprogramando.socialapp.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import viniprogramando.socialapp.domain.model.User;

import java.time.LocalDate;

@Data
public class UserDtoResponse {

  private String username;
  private String email;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate birthDate;
  private Integer remainingCharacters;
  private boolean blocked;

  public UserDtoResponse(User user) {
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.birthDate = user.getBirthDate();
    this.remainingCharacters = user.getRemainingCharacters();
    this.blocked = user.isBlocked();
  }

}
