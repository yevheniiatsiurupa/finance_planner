package planner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import planner.dao.CurrencyRepository;
import planner.entity.basic.Currency;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository repository;

    public List<Currency> findAll() {
        return repository.findAll();
    }

    public Currency findById(Integer id) {
        Optional<Currency> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Id is not found");
        }
        return optional.get();
    }
}
