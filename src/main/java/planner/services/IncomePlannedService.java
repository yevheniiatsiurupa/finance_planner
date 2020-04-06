package planner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import planner.dao.IncomePlannedRepository;
import planner.entity.month.IncomePlanned;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class IncomePlannedService {
    private IncomePlannedRepository repository;

    @Autowired
    public IncomePlannedService(IncomePlannedRepository repository) {
        this.repository = repository;
    }

    public List<IncomePlanned> findAll() {
        return repository.findAll();
    }

    public Page<IncomePlanned> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public void save(IncomePlanned income) {
        repository.save(income);
    }

    public void saveAll(List<IncomePlanned> expenses) {
        repository.saveAll(expenses);
    }

    public IncomePlanned findById(Integer id) {
        Optional<IncomePlanned> income = repository.findById(id);
        if (income.isEmpty()) {
            throw new EntityNotFoundException("IncomePlanned is not found.");
        }
        return income.get();
    }

    public void delete(IncomePlanned income) {
        repository.delete(income);
    }
}
