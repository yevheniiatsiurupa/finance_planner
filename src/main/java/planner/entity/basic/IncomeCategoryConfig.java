package planner.entity.basic;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class IncomeCategoryConfig {
    private Map<String, List<IncomeCategory>> config;
}
