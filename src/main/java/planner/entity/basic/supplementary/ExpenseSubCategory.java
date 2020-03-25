package planner.entity.basic.supplementary;

import lombok.Data;

import javax.persistence.EntityNotFoundException;
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


    public static ExpenseSubCategory getSubcategoryByNumber(List<ExpenseSubCategory> list, Integer subCategoryNumber) {
        ExpenseSubCategory subCategory = null;
        if (subCategoryNumber != null) {
            subCategory = list.stream()
                    .filter(cat -> subCategoryNumber == cat.getSubCategoryNumber())
                    .findFirst()
                    .orElse(null);
        }
        if (subCategory == null) {
            throw new EntityNotFoundException("Category was not found.");
        }
        return subCategory;
    }


    public static ExpenseSubCategory getSubcategoryByName(List<ExpenseSubCategory> list, String subCategoryName) {
        ExpenseSubCategory subCategory = null;
        if (subCategoryName != null) {
            subCategory = list.stream()
                    .filter(cat -> subCategoryName.equals(cat.getSubCategoryNumber()))
                    .findFirst()
                    .orElse(null);
        }
        if (subCategory == null) {
            throw new EntityNotFoundException("Category was not found.");
        }
        return subCategory;
    }
}
