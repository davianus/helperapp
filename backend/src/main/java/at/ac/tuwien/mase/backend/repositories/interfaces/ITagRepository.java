package at.ac.tuwien.mase.backend.repositories.interfaces;

import at.ac.tuwien.mase.backend.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xvinci on 11/14/15.
 */
public interface ITagRepository extends JpaRepository<Tag, String> {
}
