package viniprogramando.socialapp.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Data
public class Post extends PanacheEntityBase {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 250, nullable = false)
  private String text;

  @Column(name = "dateTime")
  private LocalDateTime dateTime;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @PrePersist
  public void prePersist(){
    setDateTime(LocalDateTime.now());
  }

}

