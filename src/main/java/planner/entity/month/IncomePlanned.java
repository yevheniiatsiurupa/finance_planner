package planner.entity.month;

import lombok.Data;
import planner.entity.basic.Currency;
import planner.entity.basic.UserAccount;

import javax.persistence.*;
import java.util.Date;

/**
 * Class represents planned expense record for user.
 */
@Entity
@Table(name = "incomes_planned")
@Data
public class IncomePlanned {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "amount")
    private int amount;

    @Column(name = "comment")
    private String comment;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Transient
    private String categoryNumber;

    @Column(name = "sub_category_name", nullable = false)
    private String subCategoryName;

    @Transient
    private String subCategoryNumber;

    @Column(name = "created")
    private Date created;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @ManyToOne
    @JoinColumn(name = "short_term_plan_id")
    private ShortTermPlan shortTermPlan;

}
