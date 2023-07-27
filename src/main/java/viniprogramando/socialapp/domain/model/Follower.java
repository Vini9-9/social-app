package viniprogramando.socialapp.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "followers")
@Data
public class Follower extends PanacheEntityBase {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "follower_id")
  private User follower;


  public Follower() {
  }
  public Follower(User user, User userFollower) {
    this.user = user;
    this.follower = userFollower;
  }
}

