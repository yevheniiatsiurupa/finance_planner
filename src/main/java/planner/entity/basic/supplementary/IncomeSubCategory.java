package planner.entity.basic.supplementary;

import lombok.Data;

import javax.persistence.EntityNotFoundException;
import java.util.List;

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

    public static String getNameByNumber(List<IncomeSubCategory> list, Integer subCategoryNumber) {
        if (subCategoryNumber != null) {
            return list.stream()
                    .filter(cat -> subCategoryNumber == cat.getSubCategoryNumber())
                    .map(IncomeSubCategory::getSubCategoryName)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }


    public static IncomeSubCategory getSubcategoryByNumber(List<IncomeSubCategory> list, Integer subCategoryNumber) {
        IncomeSubCategory subCategory = null;
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


    public static IncomeSubCategory getSubcategoryByName(List<IncomeSubCategory> list, String subCategoryName) {
        IncomeSubCategory subCategory = null;
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
