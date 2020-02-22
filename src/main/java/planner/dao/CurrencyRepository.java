package planner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planner.entity.basic.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
}