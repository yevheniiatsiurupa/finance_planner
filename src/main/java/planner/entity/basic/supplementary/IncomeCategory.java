package planner.entity.basic.supplementary;

import lombok.Data;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Class represents group of income subCategories.
 */
@Data
public class IncomeCategory {
    private int categoryNumber;
    private String categoryName;
    private List<IncomeSubCategory> subCategories;

    public static String getNameByNumber(List<IncomeCategory> list, Integer categoryNumber) {
        if (categoryNumber != null) {
            return list.stream()
                    .filter(cat -> categoryNumber == cat.getCategoryNumber())
                    .map(IncomeCategory::getCategoryName)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public static IncomeCategory getCategoryByNumber(List<IncomeCategory> list, Integer categoryNumber) {
        IncomeCategory category = null;
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


    public static IncomeCategory getCategoryByName(List<IncomeCategory> list, String categoryName) {
        IncomeCategory category = null;
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
