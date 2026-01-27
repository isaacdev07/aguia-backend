package aguia.history.drakes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import aguia.history.drakes.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // buscar usuario pelo email
    UserDetails findByEmail(String email);
}