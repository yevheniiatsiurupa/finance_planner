package planner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import planner.entity.basic.Authority;
import planner.entity.basic.Currency;
import planner.entity.basic.UserAccount;
import planner.entity.basic.UserAccountConfig;
import planner.services.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    @Autowired
    private AuthorityService authorityService;

    @GetMapping("/sign-up")
    public String getSignUp(Model model) {
        model.addAttribute("userAccount", new UserAccount());
        model.addAttribute("currencies", currencyService.findAll());
        return "auth-sign-up";
    }

    @PostMapping("/sign-up")
    @Transactional
    public String signUp(@ModelAttribute @Valid UserAccount userAccount,
                         BindingResult bindingResult,
                         Integer currId,
                         Model model) {
        validator.validate(userAccount, bindingResult);
        if (bindingResult.hasErrors() || currId == null) {
            if (currId == null) {
                model.addAttribute("currency", "message.error.no.currency");
            }
            for (FieldError error : bindingResult.getFieldErrors()) {
                model.addAttribute(error.getField(), error.getDefaultMessage());
            }
            model.addAttribute("userAccount", userAccount);
            model.addAttribute("currencies", currencyService.findAll());
            model.addAttribute("selectedCurr", currId);
            return "auth-sign-up";
        }

        accountService.saveWithEncode(configureUserAccount(userAccount));
        configService.save(configureUserAccountConfig(userAccount, currId));
        return "redirect:/login";
    }

    private UserAccount configureUserAccount(UserAccount userAccount) {
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authorityService.findUserAuthority());

        userAccount.setAuthorities(authorities);
        userAccount.setEnabled(true);
        userAccount.setCreated(new Date());
        return userAccount;
    }

    private UserAccountConfig configureUserAccountConfig(UserAccount userAccount, Integer currId) {
        Currency currency = currencyService.findById(currId);
        UserAccountConfig config = getDefaultConfig();

        UserAccountConfig createdConfig = new UserAccountConfig();
        createdConfig.setId(userAccount.getId());
        createdConfig.setCurrency(currency);
        createdConfig.setUserAccount(userAccount);
        createdConfig.setExpenseCategoriesJSON(config.getExpenseCategoriesJSON());
        createdConfig.setIncomeCategoriesJSON(config.getIncomeCategoriesJSON());
        return createdConfig;
    }

    private UserAccountConfig getDefaultConfig() {
        UserAccount account = accountService.findByUsername(KEY_DEFAULT);
        return configService.findById(account.getId());
    }

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) Boolean error,
                        Model model) {
        if (Boolean.TRUE.equals(error)) {
            model.addAttribute("error", "message.error.bad.credential");
        }
        return "auth-login";
    }
}
