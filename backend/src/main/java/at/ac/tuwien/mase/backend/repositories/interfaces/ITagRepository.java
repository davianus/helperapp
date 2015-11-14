package at.ac.tuwien.mase.backend.repositories.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by xvinci on 11/14/15.
 */
public interface ITagRepository extends MongoRepository<String, String> {
}
