package planner.entity.basic.supplementary;

import lombok.Data;

import java.util.List;

/**
 * Class represents group of income categories.
 */
@Data
public class IncomeCategoryGroup {
    private int groupNumber;
    private String groupName;
    private List<IncomeCategory> categories;
}
