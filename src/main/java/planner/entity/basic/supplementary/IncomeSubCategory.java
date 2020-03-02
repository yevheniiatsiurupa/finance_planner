package planner.entity.basic.supplementary;

import lombok.Data;

/**
 * Class represents single income category.
 * These subCategories then combined using category groups.
 * Each user account has its own block of income subCategories.
 *
 * Example: "salary", "freelance job" etc.
 */

@Data
public class IncomeSubCategory {
    private int subCategoryNumber;
    private String subCategoryName;
}
