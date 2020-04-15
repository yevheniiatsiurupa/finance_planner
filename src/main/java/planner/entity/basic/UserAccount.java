package planner.entity.basic;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

/**
 * Class represent single user account with references to their authorities and config.
 */
@Entity
@Table(name = "users")
@Data
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 3, message = "Login should have from 3 to 20 characters")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Size(min = 3, max = 20, message = "Password should have from 3 to 20 characters")
    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Size(min = 3, max = 20, message = "Name should have from 3 to 20 characters")
    @Column(name = "name")
    private String name;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "created")
    private Date created;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities;

//    @OneToOne(mappedBy = "userAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private UserAccountConfig config;

}
