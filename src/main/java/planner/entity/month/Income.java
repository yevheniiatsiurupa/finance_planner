package planner.entity.month;

import lombok.Data;
import planner.entity.basic.Currency;
import planner.entity.basic.UserAccount;

import javax.persistence.*;
import java.util.Date;

/**
 * Class represents income record for user.
 */
@Entity
@Table(name = "incomes")
@Data
public class Income {
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
}
