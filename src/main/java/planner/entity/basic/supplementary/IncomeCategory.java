package planner.entity.basic.supplementary;

import lombok.Data;

import java.util.List;

/**
 * Class represents group of income subCategories.
 */
@Data
public class IncomeCategory {
    private int categoryNumber;
    private String categoryName;
    private List<IncomeSubCategory> subCategories;
}
