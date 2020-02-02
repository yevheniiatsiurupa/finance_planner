package planner.entity.basic;

import lombok.Data;

import javax.persistence.*;
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

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "categoryName")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "created")
    private Date created;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities;

    @OneToOne(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private UserAccountConfig config;

}
