package viniprogramando.socialapp.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import viniprogramando.socialapp.exception.NotAllowedException;
import viniprogramando.socialapp.rest.dto.CreateUserRequest;

@Entity
@Table(name = "users")
public class User extends PanacheEntityBase {
  @Id
  @Column(length = 30, nullable = false)
  private String username;

  @Column(length = 50, nullable = false)
  private String email;

  @Column(name = "birth_date")
  private LocalDate birthDate;

  @Column(name = "remaining_caracteres")
  private Integer remainingCharacters = 1000;

  public User() {
  }

  public User(CreateUserRequest request) throws NotAllowedException {
    this.setUsername(request.getUsername());
    this.setEmail(request.getEmail());
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    this.setBirthDate(LocalDate.parse(request.getBirthDate(), dtf));
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

  public void setBirthDate(LocalDate birthDate) throws NotAllowedException {
    if(isOfLegalAge(birthDate)){
      this.birthDate = birthDate;
    } else {
      throw new NotAllowedException("user is not old enough");
    }
  }

  public Integer getRemainingCharacters() {
    return remainingCharacters;
  }

  public void setRemainingCharacters(Integer remainingCharacters) {
    this.remainingCharacters = remainingCharacters;
  }

  public boolean isOfLegalAge(LocalDate dateUser){
    int legalAge = 16;
    int age = Period.between(dateUser, LocalDate.now()).getYears();
    return age >= legalAge;
  }

  @Override
  public String toString() {
    return "User{" +
        "username='" + username + '\'' +
        ", email='" + email + '\'' +
        ", birthDate=" + birthDate +
        ", remainingCharacters=" + remainingCharacters +
        '}';
  }
}

