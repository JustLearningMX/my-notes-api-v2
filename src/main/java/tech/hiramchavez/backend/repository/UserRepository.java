package tech.hiramchavez.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import tech.hiramchavez.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailAndActiveTrue(String email);

    UserDetails findByEmailAndActiveTrue(String email);

}
