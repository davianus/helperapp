package at.ac.tuwien.mase.backend.repositories.interfaces;

import at.ac.tuwien.mase.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by lobmaier on 14.11.2015.
 */
public interface IUserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUsername(@Param("username")String username);
}
