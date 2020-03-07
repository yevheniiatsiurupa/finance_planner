package planner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import planner.entity.basic.UserAccount;
import planner.entity.basic.UserAccountConfig;
import planner.services.UserAccountConfigService;
import planner.services.UserAccountService;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class StartController {

    private final UserAccountConfigService userAccountConfigService;
    private final UserAccountService userAccountService;

    @Autowired
    public StartController(UserAccountConfigService userAccountConfigService,
                                 UserAccountService userAccountService) {
        this.userAccountConfigService = userAccountConfigService;
        this.userAccountService = userAccountService;
    }

    @GetMapping("/")
    public String start(Principal principal, HttpSession session) {
        String currentUsername = principal.getName();
        UserAccount user = userAccountService.findByUsername(currentUsername);
        UserAccountConfig userConfig = userAccountConfigService.findById(user.getId());
        userConfig.setExpenseConfig();
        userConfig.setIncomeConfig();

        session.setAttribute("userAccount", user);
        session.setAttribute("userAccountConfig", userConfig);
        return "welcome-page";
    }
}
