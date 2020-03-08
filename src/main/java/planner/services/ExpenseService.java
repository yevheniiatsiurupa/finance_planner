package planner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import planner.dao.ExpenseRepository;
import planner.entity.month.Expense;

import java.util.List;

@Service
public class ExpenseService {
    private ExpenseRepository repository;

    @Autowired
    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public List<Expense> findAll() {
        return repository.findAll();
    }

    public void save(Expense expense) {
        repository.save(expense);
    }
}
