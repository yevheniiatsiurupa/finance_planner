package planner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import planner.entity.basic.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
}