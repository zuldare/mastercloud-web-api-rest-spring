package mastercloud.jh.books.repository;

import mastercloud.jh.books.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNick(String nick);
    boolean existsByNick(String nick);
}
