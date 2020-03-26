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
@SessionAttributes({"categories"})
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @ModelAttribute("categories")
    public ArrayList<ExpenseCategory> getCategories(HttpSession session) {
        UserAccountConfig accountConfig = (UserAccountConfig) session.getAttribute("userAccountConfig");
        return (ArrayList<ExpenseCategory>) accountConfig.getExpenseCategories();
    }


    @GetMapping("/all")
    public String getExpenses(Model model,
                              ExpenseIncomeFilter filterObject,
                              @PageableDefault(sort = "created", direction = Sort.Direction.DESC, size = 15) Pageable pageable,
                              @ModelAttribute("categories") ArrayList<ExpenseCategory> categories) {
        List<ExpenseSubCategory> subCategories;
        if (filterObject.getCategoryNumber() != null) {
            ExpenseCategory category = ExpenseCategory.getCategoryByNumber(categories, filterObject.getCategoryNumber());
            subCategories = category.getSubCategories();
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
    public String addExpensePost(Model model, HttpSession session,
                                 @ModelAttribute("expense") Expense expense,
                                 @ModelAttribute("categories") List<ExpenseCategory> categories) {
        UserAccount userAccount = (UserAccount) session.getAttribute("userAccount");
        UserAccountConfig config = (UserAccountConfig) session.getAttribute("userAccountConfig");
        expense.setUserAccount(userAccount);
        expense.setCurrency(config.getCurrency());

        fillCategoryNames(expense, categories);

        expenseService.save(expense);
        String message = "Ok";
        model.addAttribute("message", message);
        return "expense-add-post";
    }

    @GetMapping("/update/{id}")
    public String updateExpense(@PathVariable Integer id,
                                @ModelAttribute("categories") List<ExpenseCategory> categories,
                                Model model) {
        Expense expense = expenseService.findById(id);
        model.addAttribute("expense", expense);

        String expenseCategory = expense.getCategoryName();
        ExpenseCategory category = ExpenseCategory.getCategoryByName(categories, expenseCategory);
        model.addAttribute("subcategories", category.getSubCategories());
        return "expense-update";
    }

    @PostMapping("/update")
    public String updateExpensePost(Model model,
                                    @ModelAttribute("expense") Expense expense,
                                    @ModelAttribute("categories") List<ExpenseCategory> categories) {
        fillCategoryNames(expense, categories);

        expenseService.save(expense);
        String message = "Ok";
        model.addAttribute("message", message);
        return "expense-update-post";
    }

    private void fillCategoryNames(Expense expense, List<ExpenseCategory> categories) {
        ExpenseCategory category = ExpenseCategory.getCategoryByNumber(
                categories, Integer.parseInt(expense.getCategoryNumber()));
        ExpenseSubCategory subCategory = ExpenseSubCategory.getSubcategoryByNumber(
                category.getSubCategories(), Integer.parseInt(expense.getSubCategoryNumber()));

        expense.setCategoryName(category.getCategoryName());
        expense.setSubCategoryName(subCategory.getSubCategoryName());
    }

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Integer id) {
        Expense expense = expenseService.findById(id);
        expenseService.delete(expense);
        return "redirect:/expense/all";
    }
}
