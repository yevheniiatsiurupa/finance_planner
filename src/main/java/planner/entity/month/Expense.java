package planner.entity.month;

import lombok.Data;
import planner.entity.basic.Currency;
import planner.entity.basic.supplementary.ExpenseCategory;

@Data
public class Expense {
    private int id;
    private int value;
    private String comment;
    private String categoryName;
    private String groupName;
    private MonthRecord monthRecord;
    private Currency currency;
}
