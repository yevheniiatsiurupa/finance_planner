package planner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planner.entity.month.ExpensePlanned;

@Repository
public interface ExpensePlannedRepository extends JpaRepository<ExpensePlanned, Integer> {
}
