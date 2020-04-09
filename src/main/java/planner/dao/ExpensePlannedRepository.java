package planner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planner.entity.month.ExpensePlanned;
import planner.entity.month.ShortTermPlan;

import java.util.List;

@Repository
public interface ExpensePlannedRepository extends JpaRepository<ExpensePlanned, Integer> {
    List<ExpensePlanned> findByShortTermPlan(ShortTermPlan plan);
}
