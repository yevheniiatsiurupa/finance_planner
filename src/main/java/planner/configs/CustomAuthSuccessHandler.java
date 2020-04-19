package planner.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import planner.entity.basic.UserAccount;
import planner.entity.basic.UserAccountConfig;
import planner.services.UserAccountConfigService;
import planner.services.UserAccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserAccountConfigService userAccountConfigService;

    @Autowired
    private UserAccountService userAccountService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();

        String currentUsername = authentication.getName();
        UserAccount user = userAccountService.findByUsername(currentUsername);
        UserAccountConfig userConfig = userAccountConfigService.findById(user.getId());
        userConfig.setExpenseConfig();
        userConfig.setIncomeConfig();

        session.setAttribute("userAccount", user);
        session.setAttribute("userAccountConfig", userConfig);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
