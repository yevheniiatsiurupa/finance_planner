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
}
