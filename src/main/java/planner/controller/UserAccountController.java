package planner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import planner.entity.basic.UserAccount;
import planner.entity.basic.UserAccountConfig;
import planner.services.CurrencyService;
import planner.services.UserAccountConfigService;
import planner.services.UserAccountService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserAccountController {

    @Autowired
    private UserAccountService accountService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private UserAccountConfigService configService;

    @GetMapping("/my-account")
    public String showMyAccount() {
        return "user-my-account";
    }

    @GetMapping("/edit")
    public String editUser(HttpSession session, Model model) {
        model.addAttribute("userAccount", session.getAttribute("userAccount"));
        return "user-edit";
    }

    @PostMapping("/edit")
    public String editUserPost(@ModelAttribute @Valid UserAccount userAccount,
                               BindingResult bindingResult,
                               Model model,
                               Principal principal,
                               HttpSession session) {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                model.addAttribute(error.getField(), error.getDefaultMessage());
            }
            model.addAttribute("userAccount", userAccount);
            model.addAttribute("currencies", currencyService.findAll());
            return "user-edit";
        }
        String currentUsername = principal.getName();
        UserAccount prevUser = accountService.findByUsername(currentUsername);
        prevUser.setName(userAccount.getName());
        prevUser.setUsername(userAccount.getUsername());
        prevUser.setEmail(userAccount.getEmail());

        accountService.save(prevUser);

        session.setAttribute("userAccount", prevUser);
        model.addAttribute("message", "ok");
        return "user-edit";
    }

    private void setInfoFromPrevUser(UserAccount userAccount, UserAccount prevAccount) {
        userAccount.setCreated(prevAccount.getCreated());
        userAccount.setEnabled(prevAccount.isEnabled());
        userAccount.setPassword(prevAccount.getPassword());
        userAccount.setAuthorities(prevAccount.getAuthorities());
        userAccount.setId(prevAccount.getId());
    }

    @GetMapping("/change-pass")
    public String changePass() {
        return "user-change-pass";
    }

    @PostMapping("/change-pass")
    public String changePassPost(String oldPass, String newPass,  String confirmPass,
                                 HttpSession session, Model model) {
        UserAccount storedUser = (UserAccount) session.getAttribute("userAccount");
        boolean matchStored = accountService.checkPassword(oldPass, storedUser.getPassword());
        if (!matchStored) {
            model.addAttribute("oldPass", "message.error.wrong.old.pass");
        }

        boolean newPassOk = newPass.length() > 2;
        if (!newPassOk) {
            model.addAttribute("newPass", "message.error.bad.password");
        }

        boolean confirmPassOk = confirmPass.length() > 2;
        if (!confirmPassOk) {
            model.addAttribute("confirmPass", "message.error.bad.password");
        }

        boolean matchNewConfirm = newPass.equals(confirmPass);
        if (!matchNewConfirm) {
            model.addAttribute("confirmPass", "message.error.pass.not.confirmed");
        }

        if (matchStored && newPassOk && confirmPassOk && matchNewConfirm) {
            storedUser.setPassword(newPass);
            accountService.saveWithEncode(storedUser);
            session.setAttribute("userAccount", storedUser);
            model.addAttribute("message", "ok");
        } else {
            model.addAttribute("oldPassValue", oldPass);
            model.addAttribute("newPassValue", newPass);
            model.addAttribute("confirmPassValue", confirmPass);
        }
        return "user-change-pass";
    }

    @GetMapping("/delete")
    public String deleteAccount(Model model) {
        model.addAttribute("message", "error");
        return "user-my-account";
    }

    @PostMapping("/delete")
    public String deleteAccountPost(HttpSession session) {
//        UserAccount userAccount = (UserAccount) session.getAttribute("userAccount");
//        userAccount.setEnabled(false);
//        accountService.save(userAccount);

        UserAccountConfig config = (UserAccountConfig) session.getAttribute("userAccountConfig");
        configService.delete(config);

        UserAccount userAccount = (UserAccount) session.getAttribute("userAccount");
        accountService.delete(userAccount);
        return "redirect:/logout";
    }
}
