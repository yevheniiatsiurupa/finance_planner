package planner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import planner.entity.basic.UserAccountConfig;

public interface UserAccountConfigRepository extends JpaRepository<UserAccountConfig, Integer> {
}
