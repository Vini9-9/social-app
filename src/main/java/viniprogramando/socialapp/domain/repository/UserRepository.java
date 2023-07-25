package viniprogramando.socialapp.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import viniprogramando.socialapp.domain.model.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public User findByUsername(String username){
        return User.find("username", username).firstResult();
    }

    public User findByEmail(String email) {
        return User.find("email", email).firstResult();
    }
}
