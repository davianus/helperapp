package at.ac.tuwien.mase.backend.repositories.interfaces;

import at.ac.tuwien.mase.backend.models.Request;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by xvinci on 11/14/15.
 */
public interface IRequestRepository extends MongoRepository<Request, String> {
}
