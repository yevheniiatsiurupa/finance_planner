package planner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import planner.entity.month.ShortTermPlan;
import planner.services.ExpensePlannedService;
import planner.services.IncomePlannedService;
import planner.services.ShortTermPlanService;

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

    public ShortTermPlanController() {
    }
//
//    @ModelAttribute("expenseCategories")
//    public ArrayList<ExpenseCategory> getExpenseCategories(HttpSession session) {
//        UserAccountConfig accountConfig = (UserAccountConfig) session.getAttribute("userAccountConfig");
//        return (ArrayList<ExpenseCategory>) accountConfig.getExpenseCategories();
//    }
//
//    @ModelAttribute("incomeCategories")
//    public ArrayList<IncomeCategory> getIncomeCategories(HttpSession session) {
//        UserAccountConfig accountConfig = (UserAccountConfig) session.getAttribute("userAccountConfig");
//        return (ArrayList<IncomeCategory>) accountConfig.getIncomeCategories();
//    }

    @GetMapping("/add")
    public String addPlanStep1(Model model) {
        model.addAttribute("plan", new ShortTermPlan());
        return "short-plan-add";
    }

    @GetMapping("/add/plan-page")
    public String addPlanStep2(Model model) {
        model.addAttribute("plan", new ShortTermPlan());
        return "short-plan-add-page";
    }

//    @PostMapping("/add")
//    public String addExpensePost(Model model, HttpSession session,
//                                 @ModelAttribute("expense") Expense expense,
//                                 @ModelAttribute("categories") List<ExpenseCategory> categories) {
//        UserAccount userAccount = (UserAccount) session.getAttribute("userAccount");
//        UserAccountConfig config = (UserAccountConfig) session.getAttribute("userAccountConfig");
//        expense.setUserAccount(userAccount);
//        expense.setCurrency(config.getCurrency());
//
//        fillCategoryNames(expense, categories);
//
//        expenseService.save(expense);
//        String message = "Ok";
//        model.addAttribute("message", message);
//        return "expense-add-post";
//    }
}
