package planner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import planner.dao.ExpenseRepository;
import planner.dao.UserAccountConfigRepository;
import planner.entity.basic.UserAccountConfig;
import planner.entity.month.Expense;

import java.util.List;
import java.util.Optional;

@Service
public class UserAccountConfigService {
    private UserAccountConfigRepository repository;

    @Autowired
    public UserAccountConfigService(UserAccountConfigRepository repository) {
        this.repository = repository;
    }

    public UserAccountConfig findById(Integer id) {
        Optional<UserAccountConfig> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("Id is not found");
        }
        return optional.get();
    }
}
