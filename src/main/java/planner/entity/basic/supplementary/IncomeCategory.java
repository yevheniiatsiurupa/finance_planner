package planner.entity.basic.supplementary;

import lombok.Data;

/**
 * Class represents single income category.
 * These categories then combined using category groups.
 * Each user account has its own block of income categories.
 *
 * Example: "salary", "freelance job" etc.
 */

@Data
public class IncomeCategory {
    private int categoryNumber;
    private String categoryName;
}
