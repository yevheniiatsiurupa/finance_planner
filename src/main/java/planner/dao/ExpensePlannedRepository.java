package planner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import planner.entity.month.ExpensePlanned;

public interface ExpensePlannedRepository extends JpaRepository<ExpensePlanned, Integer> {
}
