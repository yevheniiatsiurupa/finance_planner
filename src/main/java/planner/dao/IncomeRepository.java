package planner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import planner.entity.month.Income;

public interface IncomeRepository extends JpaRepository<Income, Integer> {
}
