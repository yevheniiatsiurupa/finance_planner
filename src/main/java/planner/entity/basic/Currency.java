package planner.entity.basic;

import lombok.Data;

import javax.persistence.*;

/**
 * Class which represents currency.
 * Used in user account configs (as default currency value),
 * in incomes and expenses.
 *
 * Example: dollar, euro, ruble etc.
 */

@Entity
@Table(name = "currencies")
@Data
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "categoryName")
    private String name;
}
