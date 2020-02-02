package planner.entity.basic;

import lombok.Data;

@Data
public class UserAccountConfig {
    //shared primary key with user account
    private int id;
    private UserAccount userAccount;
    private Currency currency;
    private String expenseCategoriesJSON;
    private String incomeCategoriesJSON;
}
