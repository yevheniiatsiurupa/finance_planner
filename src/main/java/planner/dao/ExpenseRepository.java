package planner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import planner.entity.month.Expense;

import java.util.Date;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer>, JpaSpecificationExecutor<Expense> {
    List<Expense> findAllByCreatedBetween(Date start, Date end);
}
