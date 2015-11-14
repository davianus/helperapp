package at.ac.tuwien.mase.backend.repositories.interfaces;

import at.ac.tuwien.mase.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lobmaier on 14.11.2015.
 */
public interface IUserRepository extends JpaRepository<User, String> {
}
