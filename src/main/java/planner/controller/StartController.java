package planner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import planner.entity.basic.UserAccount;
import planner.entity.filters.ExpenseIncomeFilter;
import planner.entity.month.Expense;
import planner.entity.month.Income;
import planner.services.ExpenseService;
import planner.services.IncomeService;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class StartController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private IncomeService incomeService;

    @GetMapping("/")
    public String start(@PageableDefault(sort = {"created", "id"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable,
                        Model model,
                        HttpSession session) {
        ExpenseIncomeFilter filterObject = new ExpenseIncomeFilter();
        UserAccount userAccount = (UserAccount) session.getAttribute("userAccount");
        filterObject.setUserAccountId(userAccount.getId());

        Page<Expense> expenses = expenseService.findAllFiltered(filterObject, pageable);
        Page<Income> incomes = incomeService.findAllFiltered(filterObject, pageable);
        Calendar currDate = Calendar.getInstance();
        currDate.setTime(new Date());

        currDate.set(Calendar.DATE, currDate.getActualMinimum(Calendar.DATE));
        Date firstDate = currDate.getTime();

        currDate.set(Calendar.DATE, currDate.getActualMaximum(Calendar.DATE));
        Date lastDate = currDate.getTime();
        filterObject.setCreatedMin(firstDate);
        filterObject.setCreatedMax(lastDate);

        List<Expense> monthExp = expenseService.findAllFiltered(filterObject);
        List<Income> monthInc = incomeService.findAllFiltered(filterObject);

        int monthExpSum = monthExp.stream().mapToInt(Expense::getAmount).sum();
        int monthIncSum = monthInc.stream().mapToInt(Income::getAmount).sum();

        model.addAttribute("expenses", expenses);
        model.addAttribute("incomes", incomes);
        model.addAttribute("monthExpSum", monthExpSum);
        model.addAttribute("monthIncSum", monthIncSum);
        model.addAttribute("expenseStats", getExpenseStats(monthExp));
        return "welcome-page";
    }

    @GetMapping("/expense-stats")
    @ResponseBody
    public Map<String, Integer> getStats(HttpSession session) {
        Calendar currDate = Calendar.getInstance();
        currDate.setTime(new Date());

        currDate.set(Calendar.DATE, currDate.getActualMinimum(Calendar.DATE));
        Date firstDate = currDate.getTime();

        currDate.set(Calendar.DATE, currDate.getActualMaximum(Calendar.DATE));
        Date lastDate = currDate.getTime();

        ExpenseIncomeFilter filterObject = new ExpenseIncomeFilter();
        UserAccount userAccount = (UserAccount) session.getAttribute("userAccount");
        filterObject.setUserAccountId(userAccount.getId());
        filterObject.setCreatedMin(firstDate);
        filterObject.setCreatedMax(lastDate);

        List<Expense> monthExp = expenseService.findAllFiltered(filterObject);
        return getExpenseStats(monthExp);
    }

    private Map<String, Integer> getExpenseStats(List<Expense> expenses) {
        return expenses.stream()
                .collect(Collectors.groupingBy(Expense::getSubCategoryName,
                        Collectors.summingInt(Expense::getAmount)));
    }
}
