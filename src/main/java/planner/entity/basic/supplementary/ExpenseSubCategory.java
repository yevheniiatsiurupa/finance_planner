package planner.entity.basic.supplementary;

import lombok.Data;

import java.util.List;

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


    public static String getNameByNumber(List<ExpenseSubCategory> list, Integer subCategoryNumber) {
        if (subCategoryNumber != null) {
            return list.stream()
                    .filter(cat -> subCategoryNumber == cat.getSubCategoryNumber())
                    .map(ExpenseSubCategory::getSubCategoryName)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}
