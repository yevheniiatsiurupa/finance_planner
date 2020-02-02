package planner.entity.month;

import lombok.Data;
import planner.entity.basic.ExpenseCategory;

@Data
public class Expense {
    private int id;
    private int value;
    private String comment;
    private ExpenseCategory category;
    private MonthRecord monthRecord;
}
