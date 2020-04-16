package planner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planner.entity.basic.Authority;
import planner.entity.basic.supplementary.AuthorityType;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Authority findByName(AuthorityType type);
}
