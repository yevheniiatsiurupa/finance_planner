package planner.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import planner.entity.basic.Currency;
import planner.entity.basic.UserAccount;
import planner.entity.basic.UserAccountConfig;
import planner.entity.basic.supplementary.*;
import planner.services.CurrencyService;
import planner.services.UserAccountConfigService;
import planner.services.UserAccountService;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/configs")
public class UserConfigsController {

    public static final String KEY_DEFAULT = "default";

    @Autowired
    private UserAccountService accountService;

    @Autowired
    private UserAccountConfigService configService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private MessageSource messageSource;


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

    @GetMapping("/expense-categories/default")
    @ResponseBody
    public List<ExpenseCategory> getDefaultExpenseCategories() {
        UserAccount account = accountService.findByUsername(KEY_DEFAULT);
        UserAccountConfig config = configService.findById(account.getId());
        config.setExpenseConfig();
        List<ExpenseCategory> result = config.getExpenseCategories();
        if (result == null) {
            return new ArrayList<>();
        }
        return result;
    }

    @GetMapping("/income-categories/default")
    @ResponseBody
    public List<IncomeCategory> getDefaultIncomeCategories() {
        UserAccount account = accountService.findByUsername(KEY_DEFAULT);
        UserAccountConfig config = configService.findById(account.getId());
        config.setIncomeConfig();
        List<IncomeCategory> result = config.getIncomeCategories();
        if (result == null) {
            return new ArrayList<>();
        }
        return result;
    }

    @GetMapping("/categories/update")
    public String updateCategories(HttpSession session, Model model) {
        UserAccountConfig accountConfig = (UserAccountConfig) session.getAttribute("userAccountConfig");
        List<ExpenseCategory> expenseCategories = accountConfig.getExpenseCategories();
        List<IncomeCategory> incomeCategories = accountConfig.getIncomeCategories();

        model.addAttribute("expenseCategories", expenseCategories);
        model.addAttribute("incomeCategories", incomeCategories);
        return "user-settings-categories";
    }

    @PostMapping(value = "/expense-categories/save", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String saveExpenseCategories(Principal principal,
                                        HttpSession session,
                                        Locale locale,
                                        @RequestBody ExpenseCategoryConfig categoryConfig) throws JsonProcessingException {
        String currentUsername = principal.getName();
        UserAccount user = accountService.findByUsername(currentUsername);

        UserAccountConfig userConfig = configService.findById(user.getId());
        userConfig.setExpenseCategoriesJSON(categoryConfig.getJsonString());
        configService.save(userConfig);

        userConfig.setExpenseConfig();
        userConfig.setIncomeConfig();
        session.setAttribute("userAccountConfig", userConfig);
        return messageSource.getMessage("message.success.save.exp.categories", null, locale);
    }

    @PostMapping(value = "/income-categories/save", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String saveIncomeCategories(Principal principal,
                                       HttpSession session,
                                       Locale locale,
                                       @RequestBody IncomeCategoryConfig categoryConfig) throws JsonProcessingException {
        String currentUsername = principal.getName();
        UserAccount user = accountService.findByUsername(currentUsername);

        UserAccountConfig userConfig = configService.findById(user.getId());
        userConfig.setIncomeCategoriesJSON(categoryConfig.getJsonString());
        configService.save(userConfig);

        userConfig.setExpenseConfig();
        userConfig.setIncomeConfig();
        session.setAttribute("userAccountConfig", userConfig);
        return messageSource.getMessage("message.success.save.inc.categories", null, locale);
    }

    @GetMapping("/all")
    public String getSettingsPage() {
        return "user-settings";
    }

    @GetMapping("/basic")
    public String getBasicSettingsPage(Model model, HttpSession session) {
        model.addAttribute("currencies", currencyService.findAll());
        return "user-settings-basic";
    }

    @PostMapping("/basic")
    public String setBasicSettings(Model model,
                                   Currency currency,
                                   HttpSession session) {
        UserAccountConfig config = (UserAccountConfig) session.getAttribute("userAccountConfig");
        config.setCurrency(currency);
        configService.save(config);

        session.setAttribute("userAccountConfig", config);
        model.addAttribute("currencies", currencyService.findAll());
        model.addAttribute("message", "ok");
        return "user-settings-basic";
    }
}
