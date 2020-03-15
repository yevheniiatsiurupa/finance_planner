package planner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import planner.entity.basic.UserAccount;
import planner.entity.basic.UserAccountConfig;
import planner.entity.month.Expense;
import planner.services.ExpenseService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/expense")
public class ExpensesController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpensesController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }


    @GetMapping("/all")
    public String getExpenses(Model model) {
        List<Expense> expenses = expenseService.findAll();
        model.addAttribute("expenses", expenses);
        return "expenses";
    }

    @GetMapping("/add")
    public String addExpense(Model model) {
        model.addAttribute("expense", new Expense());
        return "expense-add";
    }

    @PostMapping("/add")
    public String addExpensePost(Model model,
                                 @ModelAttribute("expense") Expense expense,
                                 HttpSession session) {
        UserAccount userAccount = (UserAccount) session.getAttribute("userAccount");
        UserAccountConfig config = (UserAccountConfig) session.getAttribute("userAccountConfig");
        expense.setUserAccount(userAccount);
        expense.setCurrency(config.getCurrency());

        expenseService.save(expense);
        String message = "Ok";
        model.addAttribute("message", message);
        return "expense-add-post";
    }

    @GetMapping("/update/{id}")
    public String updateExpense(@PathVariable Integer id,
                                Model model,
                                HttpSession session) {
        Expense expense = expenseService.findById(id);
        model.addAttribute("expense", expense);

        UserAccountConfig accountConfig = (UserAccountConfig) session.getAttribute("userAccountConfig");
        model.addAttribute("categories", accountConfig.getExpenseCategories());

        String expenseCategory = expense.getCategoryName();
        model.addAttribute("subcategories", accountConfig.getExpenseSubCategories(expenseCategory));
        return "expense-update";
    }

    @PostMapping("/update")
    public String updateExpensePost(Model model,
                                 @ModelAttribute("expense") Expense expense) {
        expenseService.save(expense);
        String message = "Ok";
        model.addAttribute("message", message);
        return "expense-update-post";
    }
}
