package viniprogramando.socialapp.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import viniprogramando.socialapp.domain.model.Post;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post> {

}
