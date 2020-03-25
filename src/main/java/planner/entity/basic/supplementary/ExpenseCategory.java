package planner.entity.basic.supplementary;

import lombok.Data;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Class represents group of expense subCategories.
 */
@Data
public class ExpenseCategory {
    private int categoryNumber;
    private String categoryName;
    private List<ExpenseSubCategory> subCategories;

    public static String getNameByNumber(List<ExpenseCategory> list, Integer categoryNumber) {
        if (categoryNumber != null) {
            return list.stream()
                    .filter(cat -> categoryNumber == cat.getCategoryNumber())
                    .map(ExpenseCategory::getCategoryName)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public static ExpenseCategory getCategoryByNumber(List<ExpenseCategory> list, Integer categoryNumber) {
        ExpenseCategory category = null;
        if (categoryNumber != null) {
            category = list.stream()
                    .filter(cat -> categoryNumber == cat.getCategoryNumber())
                    .findFirst()
                    .orElse(null);
        }
        if (category == null) {
            throw new EntityNotFoundException("Category was not found.");
        }
        return category;
    }


    public static ExpenseCategory getCategoryByName(List<ExpenseCategory> list, String categoryName) {
        ExpenseCategory category = null;
        if (categoryName != null) {
            category = list.stream()
                    .filter(cat -> categoryName.equals(cat.getCategoryName()))
                    .findFirst()
                    .orElse(null);
        }
        if (category == null) {
            throw new EntityNotFoundException("Category was not found.");
        }
        return category;
    }
}
