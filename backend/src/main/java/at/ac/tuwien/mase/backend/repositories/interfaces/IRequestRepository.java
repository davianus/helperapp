package at.ac.tuwien.mase.backend.repositories.interfaces;

import at.ac.tuwien.mase.backend.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by xvinci on 11/14/15.
 */
public interface IRequestRepository extends JpaRepository<Request, Long> {
	@Query("SELECT r FROM Request r WHERE r.endDate >= :start AND r.startDate < :end ORDER BY r.id")
	List<Request> findByDate(@Param("start") Date start, @Param("end") Date end);
}
