package planner.entity.basic;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ExpenseCategoryConfig {
    private Map<String, List<ExpenseCategory>> config;
}
