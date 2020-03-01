package planner.entity.basic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import planner.entity.basic.supplementary.ExpenseCategoryConfig;
import planner.entity.basic.supplementary.IncomeCategoryConfig;

import javax.persistence.*;

@Entity
@Table(name = "user_config")
@Data
public class UserAccountConfig {
    @Id
    @Column(name = "id")
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private UserAccount userAccount;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @Column(name = "expense_categories")
    private String expenseCategoriesJSON;

    @Column(name = "income_categories")
    private String incomeCategoriesJSON;

    public ExpenseCategoryConfig getExpenseCategoryConfig() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(expenseCategoriesJSON, ExpenseCategoryConfig.class);
    }

    public IncomeCategoryConfig getIncomeCategoryConfig() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(incomeCategoriesJSON, IncomeCategoryConfig.class);
    }

}
