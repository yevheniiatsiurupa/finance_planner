package planner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import planner.entity.basic.Currency;
import planner.entity.basic.UserAccount;
import planner.entity.basic.UserAccountConfig;
import planner.services.CurrencyService;
import planner.services.UserAccountConfigService;
import planner.services.UserAccountService;
import planner.services.UserAccountValidator;

import javax.validation.Valid;

import static planner.controller.UserConfigsController.KEY_DEFAULT;

@Controller
public class AuthController {

    @Autowired
    private UserAccountService accountService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private UserAccountValidator validator;

    @Autowired
    private UserAccountConfigService configService;

    @GetMapping("/sign-up")
    public String getSignUp(Model model) {
        model.addAttribute("userAccount", new UserAccount());
        model.addAttribute("currencies", currencyService.findAll());
        return "auth-sign-up";
    }

    @PostMapping("/sign-up")
    @Transactional
    public String signUp(@ModelAttribute @Valid UserAccount userAccount,
                         Integer currId,
                         BindingResult bindingResult,
                         Model model) {
        validator.validate(userAccount, bindingResult);
        if (bindingResult.hasErrors() || currId == null) {
            if (currId == null) {
                //TODO
                bindingResult.addError(new ObjectError("currency", "Currency is not chosen."));
            }
            model.addAttribute("result", bindingResult);
            model.addAttribute("userAccount", userAccount);
            model.addAttribute("currencies", currencyService.findAll());
            return "auth-sign-up";
        }

        accountService.save(userAccount);

        Currency currency = currencyService.findById(currId);
        UserAccountConfig config = getDefaultConfig();

        UserAccountConfig createdConfig = new UserAccountConfig();
        createdConfig.setId(userAccount.getId());
        createdConfig.setCurrency(currency);
        createdConfig.setUserAccount(userAccount);
        createdConfig.setExpenseCategoriesJSON(config.getExpenseCategoriesJSON());
        createdConfig.setIncomeCategoriesJSON(config.getIncomeCategoriesJSON());

        configService.save(createdConfig);
        return "redirect:/login";
    }

    private UserAccountConfig getDefaultConfig() {
        UserAccount account = accountService.findByUsername(KEY_DEFAULT);
        return configService.findById(account.getId());
    }

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) Boolean error, Model model) {
        if (Boolean.TRUE.equals(error)) {
            //TODO
            model.addAttribute("error", "Wrong password or login");
        }
        return "auth-login";
    }
}
