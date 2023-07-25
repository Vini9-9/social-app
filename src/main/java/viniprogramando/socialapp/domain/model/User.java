package viniprogramando.socialapp.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import viniprogramando.socialapp.exception.AlreadyExistsException;
import viniprogramando.socialapp.exception.NotAllowedException;
import viniprogramando.socialapp.rest.dto.CreateUserRequest;

@Entity
@Table(name = "users")
public class User extends PanacheEntityBase {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 30, nullable = false)
  private String username;

  @Column(length = 50, nullable = false)
  private String email;

  @Column(name = "birth_date")
  private LocalDate birthDate;

  @Column(name = "remaining_characters")
  private Integer remainingCharacters = 1000;
  private boolean blocked = false;

  public boolean isBlocked() {
    return blocked;
  }

  public void setBlocked(boolean blocked) {
    this.blocked = blocked;
    if (blocked) {
      this.remainingCharacters = -1;
    }
  }

  public User() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

