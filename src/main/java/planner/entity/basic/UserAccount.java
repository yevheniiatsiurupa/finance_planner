package planner.entity.basic;

import lombok.Data;

@Data
public class UserAccount {
    private int id;
    private String username;
    private String password;
    private String name;
    private Role role;
    private String email;
    private UserAccountConfig config;

}
