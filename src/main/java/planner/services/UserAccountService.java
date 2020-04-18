package planner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    public PasswordEncoder passwordEncoder;

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

    public UserAccount saveWithEncode(UserAccount userAccount) {
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        return repository.save(userAccount);
    }

    public UserAccount save(UserAccount userAccount) {
        return repository.save(userAccount);
    }

    public boolean checkPassword(String rawPass, String storedPass) {
        return passwordEncoder.matches(rawPass, storedPass);
    }

    public void delete(UserAccount userAccount) {
        repository.delete(userAccount);
    }
}
