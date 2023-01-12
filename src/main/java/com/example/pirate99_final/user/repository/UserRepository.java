package com.example.pirate99_final.user.repository;

import com.example.pirate99_final.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

}
