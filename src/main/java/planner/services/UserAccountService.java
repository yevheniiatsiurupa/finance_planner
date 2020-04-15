package planner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import planner.dao.UserAccountRepository;
import planner.entity.basic.UserAccount;

import java.util.Optional;

@Service
public class UserAccountService {
    private UserAccountRepository repository;

    @Autowired
    public UserAccountService(UserAccountRepository repository) {
        this.repository = repository;
    }

    public UserAccount findById(Integer id) {
        Optional<UserAccount> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("Id is not found");
        }
        return optional.get();
    }

    public UserAccount findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public UserAccount save(UserAccount userAccount) {
        return repository.save(userAccount);
    }
}
