package planner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import planner.entity.basic.Currency;
import planner.entity.basic.UserAccount;
import planner.entity.basic.UserAccountConfig;
import planner.entity.basic.supplementary.ExpenseCategory;
import planner.entity.basic.supplementary.IncomeCategory;
import planner.entity.filters.ExpenseIncomeFilter;
import planner.entity.month.*;
import planner.services.*;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/short-plan")
public class ShortTermPlanController {

    @Autowired
    private IncomePlannedService incomePlannedService;

    @Autowired
    private ExpensePlannedService expensePlannedService;

    @Autowired
    private ShortTermPlanService planService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private IncomeService incomeService;

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
    @PostMapping(value = "/add/plan-page")
    @ResponseBody
    public Map<String, String> addPlanPost(HttpSession session,
                                           Locale locale,
                                           @RequestBody ShortTermPlan plan) {
        savePlan(session, locale, plan, new Date());
        return getMessage(locale, plan.getStartDate(), plan.getEndDate(), "add");
    }

    private void savePlan(HttpSession session, Locale locale, @RequestBody ShortTermPlan plan, Date created) {
        UserAccountConfig accountConfig = (UserAccountConfig) session.getAttribute("userAccountConfig");
        UserAccount userAccount = accountConfig.getUserAccount();
        Currency currency = accountConfig.getCurrency();

        List<ExpensePlanned> expenses = plan.getExpenses();
        List<IncomePlanned> incomes = plan.getIncomes();

        plan.setUserAccount(userAccount);
        plan.setCreated(created);
        plan.setExpenses(null);
        plan.setIncomes(null);
        plan.setCurrency(currency);
        fillPlanName(locale, plan);

        planService.save(plan);

        expenses.forEach(expense -> {
            expense.setCurrency(currency);
            expense.setCreated(created);
            expense.setUserAccount(userAccount);
            expense.setShortTermPlan(plan);
        });
        incomes.forEach(income -> {
            income.setCurrency(currency);
            income.setCreated(created);
            income.setUserAccount(userAccount);
            income.setShortTermPlan(plan);
        });

        expensePlannedService.saveAll(expenses);
        incomePlannedService.saveAll(incomes);
    }

    private void fillPlanName(Locale locale, ShortTermPlan plan) {
        if (plan.getName() == null || plan.getName().isEmpty()) {
            String planString = messageSource.getMessage("label.page.plan.short", null, locale);
            DateFormat df = new SimpleDateFormat("-yyyy-MM", locale);
            String dateString = df.format(plan.getStartDate());
            plan.setName(planString + dateString);
        }
    }

    private Map<String, String> getMessage(Locale locale, Date start, Date end, String keyEnd) {
        Map<String, String> result = new HashMap<>();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        String startString = df.format(start);
        String endString = df.format(end);
        String fullKey = String.format("message.success.%s", keyEnd);
        String message = messageSource.getMessage(fullKey, null, locale);
        String plan = messageSource.getMessage("label.page.plan.short", null, locale);
        String from = messageSource.getMessage("label.from", null, locale);
        String to = messageSource.getMessage("label.to", null, locale);

        String returnMessage =  String.format("%s %s %s %s %s %s", message, plan, from, startString, to, endString);
        String button1 = messageSource.getMessage("label.page.plan.month", null, locale);
        String button2 = messageSource.getMessage("label.page.home.to", null, locale);

        result.put("message", returnMessage);
        result.put("button1", button1);
        result.put("button2", button2);
        return result;
    }

    @GetMapping("/all")
    public String getPlans(Model model,
                           HttpSession session,
                           @PageableDefault(sort = "created", direction = Sort.Direction.DESC, size = 15) Pageable pageable) {
        UserAccount userAccount = (UserAccount) session.getAttribute("userAccount");

        Page<ShortTermPlan> plans = planService.findAll(pageable, userAccount);
        model.addAttribute("plansPaged", plans);
        return "short-plan-all";
    }

    @GetMapping("/delete/{id}")
    public String deletePlan(@PathVariable Integer id) {
        ShortTermPlan plan = planService.findById(id);
        planService.delete(plan);
        return "redirect:/short-plan/all";
    }

    @GetMapping("/update/{id}")
    public String updatePlan(@PathVariable Integer id, Model model) {
        ShortTermPlan plan = planService.findById(id);
        model.addAttribute("plan", plan);
        return "short-plan-update-page";
    }

    @Transactional
    @PostMapping(value = "/update/{id}")
    @ResponseBody
    public Map<String, String> updatePlanPost(@PathVariable Integer id,
                                              HttpSession session,
                                              Locale locale,
                                              @RequestBody ShortTermPlan plan) {
        ShortTermPlan planDelete = planService.findById(id);
        Date created = planDelete.getCreated();
        planService.delete(planDelete);
        savePlan(session, locale, plan, created);
        return getMessage(locale, plan.getStartDate(), plan.getEndDate(), "update");
    }

    @GetMapping("/{id}")
    public String showPlan(@PathVariable int id, Model model) {
        ShortTermPlan plan = planService.findById(id);
        model.addAttribute("plan", plan);
        return "short-plan";
    }

    @PostMapping(value = "/show/expenses", consumes = "text/plain")
    @ResponseBody
    public List<List<ExpensePlanned>> getExpensesByPlanId(@RequestBody String id, HttpSession session) {
        int parsedId = Integer.parseInt(id);
        ShortTermPlan plan = new ShortTermPlan();
        plan.setId(parsedId);
        List<ExpensePlanned> expensePlanned = expensePlannedService.findByPlan(plan);

        UserAccountConfig accountConfig = (UserAccountConfig) session.getAttribute("userAccountConfig");
        List<ExpenseCategory> categories = accountConfig.getExpenseCategories();
        return expensePlannedService.getGroupedList(categories, expensePlanned, false);
    }

    @PostMapping(value = "/show/expenses-update", consumes = "text/plain")
    @ResponseBody
    public List<List<ExpensePlanned>> getExpensesByPlanIdForUpdate(@RequestBody String id, HttpSession session) {
        int parsedId = Integer.parseInt(id);
        ShortTermPlan plan = new ShortTermPlan();
        plan.setId(parsedId);
        List<ExpensePlanned> expensePlanned = expensePlannedService.findByPlan(plan);

        UserAccountConfig accountConfig = (UserAccountConfig) session.getAttribute("userAccountConfig");
        List<ExpenseCategory> categories = accountConfig.getExpenseCategories();
        return expensePlannedService.getGroupedList(categories, expensePlanned, true);
    }

    @PostMapping(value = "/show/incomes", consumes = "text/plain")
    @ResponseBody
    public List<List<IncomePlanned>> getIncomesByPlanId(@RequestBody String id, HttpSession session) {
        int parsedId = Integer.parseInt(id);
        ShortTermPlan plan = new ShortTermPlan();
        plan.setId(parsedId);
        List<IncomePlanned> incomePlanned = incomePlannedService.findByPlan(plan);

        UserAccountConfig accountConfig = (UserAccountConfig) session.getAttribute("userAccountConfig");
        List<IncomeCategory> categories = accountConfig.getIncomeCategories();
        return incomePlannedService.getGroupedList(categories, incomePlanned, false);
    }

    @PostMapping(value = "/show/incomes-update", consumes = "text/plain")
    @ResponseBody
    public List<List<IncomePlanned>> getIncomesByPlanIdForUpdate(@RequestBody String id, HttpSession session) {
        int parsedId = Integer.parseInt(id);
        ShortTermPlan plan = new ShortTermPlan();
        plan.setId(parsedId);
        List<IncomePlanned> incomePlanned = incomePlannedService.findByPlan(plan);

        UserAccountConfig accountConfig = (UserAccountConfig) session.getAttribute("userAccountConfig");
        List<IncomeCategory> categories = accountConfig.getIncomeCategories();
        return incomePlannedService.getGroupedList(categories, incomePlanned, true);
    }

    @GetMapping("/compare")
    public String comparePlansMain(Model model,
                                   HttpSession session,
                                   @PageableDefault(sort = "created", direction = Sort.Direction.DESC, size = 15) Pageable pageable) {
        UserAccount userAccount = (UserAccount) session.getAttribute("userAccount");

        Page<ShortTermPlan> plans = planService.findAll(pageable, userAccount);
        model.addAttribute("plansPaged", plans);
        return "short-plan-compare-main";
    }

    @GetMapping("/compare/{id}")
    public String comparePlan(@PathVariable Integer id, Model model) {
        ShortTermPlan plan = planService.findById(id);
        model.addAttribute("plan", plan);
        return "short-plan-compare-page";
    }

    @PostMapping(value = "/compare", consumes = "text/plain")
    @ResponseBody
    public Map<String, Object> comparePlanPost(@RequestBody String id, HttpSession session) {
        int parsedId = Integer.parseInt(id);
        ShortTermPlan plan = planService.findById(parsedId);

        List<ExpensePlanned> expensePlanned = expensePlannedService.findByPlan(plan);
        List<IncomePlanned> incomePlanned = incomePlannedService.findByPlan(plan);

        Date start = plan.getStartDate();
        Date end = plan.getEndDate();

        ExpenseIncomeFilter filterObject = new ExpenseIncomeFilter();
        UserAccount userAccount = (UserAccount) session.getAttribute("userAccount");
        filterObject.setUserAccountId(userAccount.getId());
        filterObject.setCreatedMin(start);
        filterObject.setCreatedMax(end);

        List<Expense> expenseActual = expenseService.findAllFiltered(filterObject);
        List<Income> incomeActual = incomeService.findAllFiltered(filterObject);

        UserAccountConfig accountConfig = (UserAccountConfig) session.getAttribute("userAccountConfig");
        List<ExpenseCategory> expCategories = accountConfig.getExpenseCategories();
        List<IncomeCategory> incCategories = accountConfig.getIncomeCategories();

        List<ExpenseIncomeComparison> expensesCompare =
                expensePlannedService.getComparison(expensePlanned, expenseActual, expCategories);
        List<ExpenseIncomeComparison> incomesCompare =
                incomePlannedService.getComparison(incomePlanned, incomeActual, incCategories);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("expensesCompare", expensesCompare);
        resultMap.put("incomesCompare", incomesCompare);

        resultMap.put("expensesPlannedSum", expensePlanned.stream().mapToInt(ExpensePlanned::getAmount).sum());
        resultMap.put("expensesActualSum", expenseActual.stream().mapToInt(Expense::getAmount).sum());
        resultMap.put("incomesPlannedSum", incomePlanned.stream().mapToInt(IncomePlanned::getAmount).sum());
        resultMap.put("incomesActualSum", incomeActual.stream().mapToInt(Income::getAmount).sum());
        return resultMap;
    }
}
