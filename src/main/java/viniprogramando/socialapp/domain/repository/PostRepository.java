package viniprogramando.socialapp.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import viniprogramando.socialapp.domain.model.Post;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post> {
    public Post findByIdAndUserId(Long id, Long userId) {
        return find("id = :id and user.id = :userId", Parameters.with("id", id).and("userId", userId)).firstResult();
    }
}
