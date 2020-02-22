package planner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planner.entity.basic.UserAccountConfig;

@Repository
public interface UserAccountConfigRepository extends JpaRepository<UserAccountConfig, Integer> {
}
