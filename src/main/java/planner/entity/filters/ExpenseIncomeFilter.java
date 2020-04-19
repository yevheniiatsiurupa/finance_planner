package planner.entity.filters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import planner.entity.basic.Currency;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseIncomeFilter {
    private Integer amountMin;
    private Integer amountMax;
    private String categoryName = "";
    private Integer categoryNumber;
    private String subCategoryName = "";
    private Integer subCategoryNumber;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createdMin;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createdMax;
    private Currency currency;
    private String comment = "";
    private String cache = "";
    private Integer userAccountId;
}
