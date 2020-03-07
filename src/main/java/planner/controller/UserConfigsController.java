package planner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import planner.entity.basic.UserAccountConfig;
import planner.entity.basic.supplementary.ExpenseCategory;
import planner.entity.basic.supplementary.ExpenseSubCategory;
import planner.entity.basic.supplementary.IncomeCategory;
import planner.entity.basic.supplementary.IncomeSubCategory;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/configs")
public class UserConfigsController {

    @GetMapping("/expense-categories")
    @ResponseBody
    public List<ExpenseCategory> getExpenseCategories(HttpSession session) {
        UserAccountConfig accountConfig = (UserAccountConfig) session.getAttribute("userAccountConfig");
        return accountConfig.getExpenseCategories();
    }

    @GetMapping("/income-categories")
    @ResponseBody
    public List<IncomeCategory> getIncomeCategories(HttpSession session) {
        UserAccountConfig accountConfig = (UserAccountConfig) session.getAttribute("userAccountConfig");
        return accountConfig.getIncomeCategories();
    }

    @PostMapping("/expense-subcategories")
    @ResponseBody
    public List<ExpenseSubCategory> getExpenseSubCategories(@RequestBody String categoryName,
                                                            HttpSession session) {
        UserAccountConfig accountConfig = (UserAccountConfig) session.getAttribute("userAccountConfig");
        return accountConfig.getExpenseSubCategories(categoryName);
    }

    @PostMapping("/income-subcategories")
    @ResponseBody
    public List<IncomeSubCategory> getIncomeSubCategories(@RequestBody String categoryName,
                                                          HttpSession session) {
        UserAccountConfig accountConfig = (UserAccountConfig) session.getAttribute("userAccountConfig");
        return accountConfig.getIncomeSubCategories(categoryName);
    }
}
