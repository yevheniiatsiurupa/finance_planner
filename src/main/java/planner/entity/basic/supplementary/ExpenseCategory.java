package planner.entity.basic.supplementary;

import lombok.Data;

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
}
