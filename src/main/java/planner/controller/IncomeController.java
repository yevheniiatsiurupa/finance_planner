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
import planner.entity.basic.supplementary.IncomeCategory;
import planner.entity.basic.supplementary.IncomeSubCategory;
import planner.entity.filters.ExpenseIncomeFilter;
import planner.entity.month.Income;
import planner.services.IncomeService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/income")
@SessionAttributes({"incomeCategories"})
public class IncomeController {

    private final IncomeService incomeService;

    @Autowired
    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @ModelAttribute("incomeCategories")
    public ArrayList<IncomeCategory> getCategories(HttpSession session) {
        UserAccountConfig accountConfig = (UserAccountConfig) session.getAttribute("userAccountConfig");
        return (ArrayList<IncomeCategory>) accountConfig.getIncomeCategories();
    }


    @GetMapping("/all")
    public String getIncomes(Model model,
                             HttpSession session,
                             ExpenseIncomeFilter filterObject,
                             @PageableDefault(sort = "created", direction = Sort.Direction.DESC, size = 15) Pageable pageable,
                             @ModelAttribute("incomeCategories") ArrayList<IncomeCategory> categories) {
        List<IncomeSubCategory> subCategories;
        if (filterObject.getCategoryNumber() != null) {
            IncomeCategory category = IncomeCategory.getCategoryByNumber(categories, filterObject.getCategoryNumber());
            subCategories = category.getSubCategories();
        } else {
            subCategories = new ArrayList<>();
        }
        model.addAttribute("subcategories", subCategories);

        String categoryName = IncomeCategory.getNameByNumber(categories, filterObject.getCategoryNumber());
        String subCategoryName = IncomeSubCategory.getNameByNumber(subCategories, filterObject.getSubCategoryNumber());
        filterObject.setCategoryName(categoryName);
        filterObject.setSubCategoryName(subCategoryName);

        UserAccount userAccount = (UserAccount) session.getAttribute("userAccount");
        filterObject.setUserAccountId(userAccount.getId());

        Page<Income> incomes = incomeService.findAllFiltered(filterObject, pageable);
        model.addAttribute("incomesPaged", incomes);
        model.addAttribute("filterObject", filterObject);
        return "incomes";
    }

    @GetMapping("/add")
    public String addIncome(Model model) {
        model.addAttribute("income", new Income());
        return "income-add";
    }

    @PostMapping("/add")
    public String addIncomePost(Model model, HttpSession session,
                                @ModelAttribute("income") Income income,
                                @ModelAttribute("incomeCategories") List<IncomeCategory> categories) {
        UserAccount userAccount = (UserAccount) session.getAttribute("userAccount");
        UserAccountConfig config = (UserAccountConfig) session.getAttribute("userAccountConfig");
        income.setUserAccount(userAccount);
        income.setCurrency(config.getCurrency());

        fillCategoryNames(income, categories);

        incomeService.save(income);
        String message = "ok";
        model.addAttribute("message", message);
        model.addAttribute("savedEntity", income);
        model.addAttribute("expense", new Income());
        return "income-add";
    }

    @GetMapping("/update/{id}")
    public String updateIncome(@PathVariable Integer id,
                               @ModelAttribute("incomeCategories") List<IncomeCategory> categories,
                                Model model) {
        Income income = incomeService.findById(id);
        model.addAttribute("income", income);

        String incomeCategory = income.getCategoryName();
        IncomeCategory category = IncomeCategory.getCategoryByName(categories, incomeCategory);
        model.addAttribute("subcategories", category.getSubCategories());
        return "income-update";
    }

    @PostMapping("/update")
    public String updateIncomePost(@ModelAttribute("income") Income income,
                                   @ModelAttribute("incomeCategories") List<IncomeCategory> categories) {
        fillCategoryNames(income, categories);

        incomeService.save(income);
        return "redirect:/income/all";
    }

    private void fillCategoryNames(Income income, List<IncomeCategory> categories) {
        IncomeCategory category = IncomeCategory.getCategoryByNumber(
                categories, Integer.parseInt(income.getCategoryNumber()));
        IncomeSubCategory subCategory = IncomeSubCategory.getSubcategoryByNumber(
                category.getSubCategories(), Integer.parseInt(income.getSubCategoryNumber()));

        income.setCategoryName(category.getCategoryName());
        income.setSubCategoryName(subCategory.getSubCategoryName());
    }

    @GetMapping("/delete/{id}")
    public String deleteIncome(@PathVariable Integer id) {
        Income income = incomeService.findById(id);
        incomeService.delete(income);
        return "redirect:/income/all";
    }
}
