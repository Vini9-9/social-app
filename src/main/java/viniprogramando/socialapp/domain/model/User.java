package viniprogramando.socialapp.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDate;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
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

}

