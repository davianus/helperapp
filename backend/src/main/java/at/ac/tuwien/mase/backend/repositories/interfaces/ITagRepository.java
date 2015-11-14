package at.ac.tuwien.mase.backend.repositories.interfaces;

import at.ac.tuwien.mase.backend.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by xvinci on 11/14/15.
 */
public interface ITagRepository extends JpaRepository<Tag, String> {
    @Query("SELECT t FROM Tag t WHERE t.name LIKE :name")
    public Tag findByName(@Param("name") String name);
}
