package planner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import planner.entity.month.Expense;
import planner.services.ExpenseService;

import java.util.List;

@Controller
@RequestMapping("/expense")
@SessionAttributes({"userAccount", "userAccountConfig"})
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
}
