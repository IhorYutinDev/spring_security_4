package ua.ihor.SpringBootSecurity4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.ihor.SpringBootSecurity4.models.MyUser;

import java.util.Optional;


public interface UsersRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByName(String username);
}
