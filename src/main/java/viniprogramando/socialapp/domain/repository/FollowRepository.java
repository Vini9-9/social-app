package viniprogramando.socialapp.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import viniprogramando.socialapp.domain.model.Follower;
import viniprogramando.socialapp.domain.model.Post;

import java.util.List;

@ApplicationScoped
public class FollowRepository implements PanacheRepository<Follower> {
    public long deleteUserFollowsUser(Long userId, Long followerId) {
        return delete("user.id = :userId and follower.id = :followerId",
                Parameters.with("userId", userId).and("followerId", followerId));
    }

    public List<Follower> findAllByUserId(Long userId) {
        return find("user.id", userId).list();
    }

}
