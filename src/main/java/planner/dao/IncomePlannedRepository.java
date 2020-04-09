package planner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planner.entity.month.IncomePlanned;
import planner.entity.month.ShortTermPlan;

import java.util.List;

@Repository
public interface IncomePlannedRepository extends JpaRepository<IncomePlanned, Integer> {
    List<IncomePlanned> findByShortTermPlan(ShortTermPlan plan);
}
