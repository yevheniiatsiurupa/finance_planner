package planner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import planner.dao.ExpenseRepository;
import planner.entity.month.Expense;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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

    public Expense findById(Integer id) {
        Optional<Expense> expense = repository.findById(id);
        if (expense.isEmpty()) {
            throw new EntityNotFoundException("Expense is not found.");
        }
        return expense.get();
    }
}
