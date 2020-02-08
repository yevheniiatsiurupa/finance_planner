package planner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import planner.entity.basic.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
}
