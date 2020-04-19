package planner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import planner.entity.month.ShortTermPlan;

@Repository
public interface ShortTermPlanRepository extends JpaRepository<ShortTermPlan, Integer>, JpaSpecificationExecutor<ShortTermPlan> {
}
