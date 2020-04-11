package planner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import planner.dao.ExpenseRepository;
import planner.dao.specifications.ExpenseSpecification;
import planner.entity.filters.ExpenseIncomeFilter;
import planner.entity.month.Expense;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
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

    public Page<Expense> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Expense> findAllFiltered(ExpenseIncomeFilter filterObj) {
        ExpenseSpecification expenseSpec = new ExpenseSpecification(filterObj);
        return repository.findAll(expenseSpec);
    }

    public Page<Expense> findAllFiltered(ExpenseIncomeFilter filterObj, Pageable pageable) {
        ExpenseSpecification expenseSpec = new ExpenseSpecification(filterObj);
        return repository.findAll(expenseSpec, pageable);
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

    public void delete(Expense expense) {
        repository.delete(expense);
    }

    public List<Expense> findByDateRange(Date start, Date end) {
        return repository.findAllByCreatedBetween(start, end);
    }
}
