package planner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planner.entity.month.IncomePlanned;

@Repository
public interface IncomePlannedRepository extends JpaRepository<IncomePlanned, Integer> {
}
