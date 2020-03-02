package planner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import planner.entity.basic.UserAccount;
import planner.entity.basic.UserAccountConfig;
import planner.entity.basic.supplementary.ExpenseCategory;
import planner.entity.basic.supplementary.ExpenseSubCategory;
import planner.entity.basic.supplementary.IncomeCategory;
import planner.entity.basic.supplementary.IncomeSubCategory;
import planner.services.UserAccountConfigService;
import planner.services.UserAccountService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/configs")
@SessionAttributes({"userAccount", "userAccountConfig"})
public class UserConfigsController {

    private final UserAccountConfigService userAccountConfigService;
    private final UserAccountService userAccountService;

    @Autowired
    public UserConfigsController(UserAccountConfigService userAccountConfigService,
                                 UserAccountService userAccountService) {
        this.userAccountConfigService = userAccountConfigService;
        this.userAccountService = userAccountService;
    }


    @ModelAttribute
    public void addUserAccountConfig(Principal principal, Model model) {
        String currentUsername = principal.getName();
        UserAccount user = userAccountService.findByUsername(currentUsername);
        UserAccountConfig userConfig = userAccountConfigService.findById(user.getId());
        userConfig.setExpenseConfig();
        userConfig.setIncomeConfig();

        model.addAttribute("userAccount", user);
        model.addAttribute("userAccountConfig", userConfig);
    }

    @GetMapping("/expense-categories")
    @ResponseBody
    public List<ExpenseCategory> getExpenseCategories(@ModelAttribute("userAccountConfig") UserAccountConfig accountConfig) {
        return accountConfig.getExpenseCategories();
    }

    @GetMapping("/income-categories")
    @ResponseBody
    public List<IncomeCategory> getIncomeCategories(@ModelAttribute("userAccountConfig") UserAccountConfig accountConfig) {
        return accountConfig.getIncomeCategories();
    }

    @PostMapping("/expense-subcategories")
    @ResponseBody
    public List<ExpenseSubCategory> getExpenseSubCategories(@RequestBody String categoryName,
                                                            @ModelAttribute("userAccountConfig") UserAccountConfig accountConfig) {
        return accountConfig.getExpenseSubCategories(categoryName);
    }

    @PostMapping("/income-subcategories")
    @ResponseBody
    public List<IncomeSubCategory> getIncomeSubCategories(@RequestBody String categoryName,
                                                           @ModelAttribute("userAccountConfig") UserAccountConfig accountConfig) {
        return accountConfig.getIncomeSubCategories(categoryName);
    }
}
