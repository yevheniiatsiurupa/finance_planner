package planner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import planner.dao.IncomeRepository;
import planner.dao.specifications.IncomeSpecification;
import planner.entity.filters.ExpenseIncomeFilter;
import planner.entity.month.Income;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {
    private IncomeRepository repository;

    @Autowired
    public IncomeService(IncomeRepository repository) {
        this.repository = repository;
    }

    public List<Income> findAll() {
        return repository.findAll();
    }

    public Page<Income> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Income> findAllFiltered(ExpenseIncomeFilter filterObj) {
        IncomeSpecification IncomeSpec = new IncomeSpecification(filterObj);
        return repository.findAll(IncomeSpec);
    }

    public Page<Income> findAllFiltered(ExpenseIncomeFilter filterObj, Pageable pageable) {
        IncomeSpecification IncomeSpec = new IncomeSpecification(filterObj);
        return repository.findAll(IncomeSpec, pageable);
    }

    public void save(Income Income) {
        repository.save(Income);
    }

    public Income findById(Integer id) {
        Optional<Income> Income = repository.findById(id);
        if (Income.isEmpty()) {
            throw new EntityNotFoundException("Income is not found.");
        }
        return Income.get();
    }

    public void delete(Income Income) {
        repository.delete(Income);
    }
}
