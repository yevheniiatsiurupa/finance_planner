package planner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import planner.entity.basic.UserAccount;

@Component
public class UserAccountValidator implements Validator {

    @Autowired
    private UserAccountService accountService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserAccount.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserAccount userAccount = (UserAccount) target;
        if (accountService.findByUsername(userAccount.getUsername()) != null) {
            errors.rejectValue("username", "", "message.error.bad.login.used");
        }
    }
}
