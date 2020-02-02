package planner.entity.month;

import lombok.Data;
import planner.entity.basic.UserAccount;

import java.util.List;

@Data
public class MonthRecord {
    private int id;
    private int month;
    private int year;
    private UserAccount userAccount;
    private List<Expense> expensesPlanned;
    private List<Expense> expenses;
    private List<Income> incomes;

}
