package planner.entity.month;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import planner.entity.basic.Currency;
import planner.entity.basic.UserAccount;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Class represents short-term plan of expenses and incomes.
 */
@Entity
@Table(name = "short_term_plan")
@Getter
@Setter
@NoArgsConstructor
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

    @Column(name = "created")
    private Date created;

    @Column(name = "total_expenses")
    private int totalExpenses;

    @Column(name = "total_incomes")
    private int totalIncomes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @OneToMany(mappedBy = "shortTermPlan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExpensePlanned> expenses;

    @OneToMany(mappedBy = "shortTermPlan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IncomePlanned> incomes;
}
