package planner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import planner.entity.basic.Currency;
import planner.entity.basic.UserAccount;
import planner.entity.basic.UserAccountConfig;
import planner.entity.month.ExpensePlanned;
import planner.entity.month.IncomePlanned;
import planner.entity.month.ShortTermPlan;
import planner.services.ExpensePlannedService;
import planner.services.IncomePlannedService;
import planner.services.ShortTermPlanService;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/short-plan")
//@SessionAttributes({"expenseCategories", "incomeCategories"})
public class ShortTermPlanController {

    @Autowired
    private IncomePlannedService incomeService;

    @Autowired
    private ExpensePlannedService expenseService;

    @Autowired
    private ShortTermPlanService planService;

    @Autowired
    private MessageSource messageSource;

    public ShortTermPlanController() {
    }

    @GetMapping("/add")
    public String addPlanStep1() {
        return "short-plan-add";
    }

    @GetMapping("/add/plan-page")
    public String addPlanStep2() {
        return "short-plan-add-page";
    }

    @Transactional
    @PostMapping(value = "/add/plan-page", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String addExpensePost(HttpSession session,
                                 Locale locale,
                                 @RequestBody ShortTermPlan plan) {
        UserAccountConfig accountConfig = (UserAccountConfig) session.getAttribute("userAccountConfig");
        UserAccount userAccount = accountConfig.getUserAccount();
        Currency currency = accountConfig.getCurrency();

        List<ExpensePlanned> expenses = plan.getExpenses();
        List<IncomePlanned> incomes = plan.getIncomes();

        plan.setUserAccount(userAccount);
        plan.setExpenses(null);
        plan.setIncomes(null);

        planService.save(plan);

        expenses.forEach(expense -> {
            expense.setCurrency(currency);
            expense.setCreated(new Date());
            expense.setUserAccount(userAccount);
            expense.setShortTermPlan(plan);
        });
        incomes.forEach(income -> {
            income.setCurrency(currency);
            income.setCreated(new Date());
            income.setUserAccount(userAccount);
            income.setShortTermPlan(plan);
        });

        expenseService.saveAll(expenses);
        incomeService.saveAll(incomes);

        return getMessage(locale, plan.getStartDate(), plan.getEndDate());
    }

    private String getMessage(Locale locale, Date start, Date end) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        String startString = df.format(start);
        String endString = df.format(end);
        String message = messageSource.getMessage("message.success.add", null, locale);
        String plan = messageSource.getMessage("label.page.plan.short", null, locale);
        String from = messageSource.getMessage("label.from", null, locale);
        String to = messageSource.getMessage("label.to", null, locale);
        return String.format("%s %s %s %s %s %s", message, plan, from, startString, to, endString);
    }
}
