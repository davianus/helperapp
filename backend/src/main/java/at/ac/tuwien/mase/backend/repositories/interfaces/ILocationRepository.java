package at.ac.tuwien.mase.backend.repositories.interfaces;

import at.ac.tuwien.mase.backend.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by capta on 14.11.2015.
 */
public interface ILocationRepository extends JpaRepository<Location, Long> {
    @Query("SELECT l FROM Location l WHERE l.name LIKE %:name%")
    public List<Location> autocompleteLocation(@Param("name") String name);
}
