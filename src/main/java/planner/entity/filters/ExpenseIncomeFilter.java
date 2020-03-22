package planner.entity.filters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import planner.entity.basic.Currency;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseIncomeFilter {
    private Integer amountMin;
    private Integer amountMax;
    private String categoryName = "";
    private String subCategoryName = "";
    private Date createdMin;
    private Date createdMax;
    private Currency currency;
    private String comment = "";
    private String cache = "";
}
