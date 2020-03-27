package planner.entity.month;

import lombok.Data;
import planner.entity.basic.UserAccount;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Class represents short-term plan of expenses and incomes.
 */
@Entity
@Table(name = "short_term_plan")
@Data
public class ShortTermPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @OneToMany(mappedBy = "shortTermPlan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExpensePlanned> expenses;

    @OneToMany(mappedBy = "shortTermPlan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IncomePlanned> incomes;
}
