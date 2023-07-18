package viniprogramando.socialapp.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Date;

@Entity
public class User {
  @Id
  @Column(length = 30, nullable = false)
  private String username;

  @Column(length = 50, nullable = false)
  private String email;

  @Column(name = "birth_date")
  private Date birthDate;

  @Column(name = "remaining_caracteres")
  private Integer remainingCharacters;


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

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public Integer getRemainingCharacters() {
    return remainingCharacters;
  }

  public void setRemainingCharacters(Integer remainingCharacters) {
    this.remainingCharacters = remainingCharacters;
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

