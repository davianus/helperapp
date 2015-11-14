package at.ac.tuwien.mase.backend.repositories.interfaces;

import at.ac.tuwien.mase.backend.models.Fulfillment;
import at.ac.tuwien.mase.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by lobmaier on 14.11.2015.
 */
public interface IFulfillmentRepository extends JpaRepository<Fulfillment, Long> {
    List<Fulfillment> findByUser(User user);
}
