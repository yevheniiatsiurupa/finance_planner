package planner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import planner.entity.basic.UserAccount;
import planner.entity.basic.UserAccountConfig;
import planner.entity.basic.supplementary.ExpenseCategory;
import planner.entity.basic.supplementary.ExpenseSubCategory;
import planner.entity.filters.ExpenseIncomeFilter;
import planner.entity.month.Expense;
import planner.services.ExpenseService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
    public String getExpenses(Model model, HttpSession session,
                              ExpenseIncomeFilter filterObject,
                              @PageableDefault(sort = "created", direction = Sort.Direction.DESC, size = 15) Pageable pageable) {
        UserAccountConfig accountConfig = (UserAccountConfig) session.getAttribute("userAccountConfig");
        List<ExpenseCategory> categories = accountConfig.getExpenseCategories();
        model.addAttribute("categories", categories);
        List<ExpenseSubCategory> subCategories;
        if (filterObject.getCategoryNumber() != null) {
            subCategories = accountConfig.getExpenseSubCategories(filterObject.getCategoryNumber());
        } else {
            subCategories = new ArrayList<>();
        }
        model.addAttribute("subcategories", subCategories);

        String categoryName = ExpenseCategory.getNameByNumber(categories, filterObject.getCategoryNumber());
        String subCategoryName = ExpenseSubCategory.getNameByNumber(subCategories, filterObject.getSubCategoryNumber());
        filterObject.setCategoryName(categoryName);
        filterObject.setSubCategoryName(subCategoryName);

        Page<Expense> expenses = expenseService.findAllFiltered(filterObject, pageable);
        model.addAttribute("expensesPaged", expenses);
        model.addAttribute("filterObject", filterObject);
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

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Integer id) {
        Expense expense = expenseService.findById(id);
        expenseService.delete(expense);
        return "redirect:/expense/all";
    }
}
