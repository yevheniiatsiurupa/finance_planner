package planner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import planner.entity.month.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
}
