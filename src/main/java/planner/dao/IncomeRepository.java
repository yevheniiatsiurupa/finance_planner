package planner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import planner.entity.month.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer>, JpaSpecificationExecutor<Income> {
}
