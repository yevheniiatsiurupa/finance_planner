package planner.entity.month;

import lombok.Data;
import planner.entity.basic.IncomeCategory;

@Data
public class Income {
    private int id;
    private int value;
    private String comment;
    private IncomeCategory category;
    private MonthRecord monthRecord;
}
