package planner.entity.basic.supplementary;

import lombok.Data;

/**
 * Class represents single expense category.
 * These subCategories then combined using category groups.
 * Each user account has its own block of expense subCategories.
 *
 * Example: "gym card", "gas expenses" etc.
 */

@Data
public class ExpenseSubCategory {
    private int subCategoryNumber;
    private String subCategoryName;
}
