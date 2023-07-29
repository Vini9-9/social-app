package viniprogramando.socialapp.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import viniprogramando.socialapp.domain.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class UserDtoResponse {

  private Long id;
  private String username;
  private String email;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private String birthDate;
  private Integer remainingCharacters;
  private boolean blocked;

  public UserDtoResponse(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.birthDate = localDateToString(user.getBirthDate());
    this.remainingCharacters = user.getRemainingCharacters();
    this.blocked = user.isBlocked();
  }

  public String localDateToString(LocalDate birthDateLD){
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return birthDateLD.format(dtf).toString();
  }

}
