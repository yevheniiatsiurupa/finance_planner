package planner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import planner.entity.basic.UserAccountConfig;
import planner.entity.month.Expense;
import planner.services.ExpenseService;

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
    public String addExpense(Model model,
                             @ModelAttribute("userAccountConfig") UserAccountConfig accountConfig) {
        model.addAttribute("expense", new Expense());
        return "expense-add";
    }
}
