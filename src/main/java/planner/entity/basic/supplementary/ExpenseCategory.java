package planner.entity.basic.supplementary;

import lombok.Data;

/**
 * Class represents single expense category.
 * These categories then combined using category groups.
 * Each user account has its own block of expense categories.
 *
 * Example: "gym card", "gas expenses" etc.
 */

@Data
public class ExpenseCategory {
    private int categoryNumber;
    private String categoryName;
}
