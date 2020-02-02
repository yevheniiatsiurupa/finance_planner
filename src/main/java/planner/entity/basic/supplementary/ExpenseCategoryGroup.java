package planner.entity.basic.supplementary;

import lombok.Data;

import java.util.List;

/**
 * Class represents group of expense categories.
 */
@Data
public class ExpenseCategoryGroup {
    private int groupNumber;
    private String groupName;
    private List<ExpenseCategory> categories;
}
