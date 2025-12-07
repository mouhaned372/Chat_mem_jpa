package tn.usousse.eniso.tp4_fwk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.usousse.eniso.tp4_fwk.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}