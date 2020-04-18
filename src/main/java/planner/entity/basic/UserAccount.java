package planner.entity.basic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import planner.entity.month.Expense;
import planner.entity.month.Income;
import planner.entity.month.ShortTermPlan;

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
@Getter
@Setter
@NoArgsConstructor
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 3, max = 20, message = "message.error.bad.login")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Size(min = 3, message = "message.error.bad.password")
    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Size(min = 3, max = 20, message = "message.error.bad.name")
    @Column(name = "name")
    private String name;

    @Email(message = "message.error.bad.email")
    @Column(name = "email")
    private String email;

    @Column(name = "created")
    private Date created;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities;

    @JsonIgnore
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Expense> expenses;

    @JsonIgnore
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Income> incomes;

    @JsonIgnore
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ShortTermPlan> shortTermPlans;

}
