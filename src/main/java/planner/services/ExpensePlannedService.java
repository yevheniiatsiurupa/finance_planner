package planner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import planner.dao.ExpensePlannedRepository;
import planner.entity.month.ExpensePlanned;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ExpensePlannedService {
    private ExpensePlannedRepository repository;

    @Autowired
    public ExpensePlannedService(ExpensePlannedRepository repository) {
        this.repository = repository;
    }

    public List<ExpensePlanned> findAll() {
        return repository.findAll();
    }

    public Page<ExpensePlanned> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public void save(ExpensePlanned expense) {
        repository.save(expense);
    }

    public void saveAll(List<ExpensePlanned> expenses) {
        repository.saveAll(expenses);
    }

    public ExpensePlanned findById(Integer id) {
        Optional<ExpensePlanned> expense = repository.findById(id);
        if (expense.isEmpty()) {
            throw new EntityNotFoundException("ExpensePlanned is not found.");
        }
        return expense.get();
    }

    public void delete(ExpensePlanned expense) {
        repository.delete(expense);
    }
}
