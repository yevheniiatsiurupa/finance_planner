package planner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import planner.entity.basic.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
