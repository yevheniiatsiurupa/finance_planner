package planner.entity.basic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import planner.entity.basic.supplementary.*;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "logged_once")
    private boolean loggedOnce;

    @Transient
    private ExpenseCategoryConfig expenseCategoryConfig;

    @Transient
    private IncomeCategoryConfig incomeCategoryConfig;

    public void setExpenseConfig() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            expenseCategoryConfig = objectMapper.readValue(expenseCategoriesJSON, ExpenseCategoryConfig.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void setIncomeConfig() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            incomeCategoryConfig = objectMapper.readValue(incomeCategoriesJSON, IncomeCategoryConfig.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<ExpenseCategory> getExpenseCategories() {
        return expenseCategoryConfig.getConfig();
    }

    public List<IncomeCategory> getIncomeCategories() {
        return incomeCategoryConfig.getConfig();
    }

    public List<ExpenseSubCategory> getExpenseSubCategories(String categoryName) {
        List<ExpenseCategory> categories = getExpenseCategories();
        ExpenseCategory category =  categories.stream()
                .filter(group -> categoryName.equals(group.getCategoryName()))
                .findFirst()
                .orElse(null);

        if (category == null) {
            throw new IllegalArgumentException(String.format("Category with name %s was not found", categoryName));
        }
        return category.getSubCategories();
    }

    public List<ExpenseSubCategory> getExpenseSubCategories(Integer categoryNumber) {
        List<ExpenseCategory> categories = getExpenseCategories();
        ExpenseCategory category =  categories.stream()
                .filter(group -> categoryNumber.equals(group.getCategoryNumber()))
                .findFirst()
                .orElse(null);

        if (category == null) {
            throw new IllegalArgumentException(String.format("Category with number %s was not found", categoryNumber));
        }
        return category.getSubCategories();
    }

    public List<IncomeSubCategory> getIncomeSubCategories(String categoryName) {
        List<IncomeCategory> categories = getIncomeCategories();
        IncomeCategory category =  categories.stream()
                .filter(group -> categoryName.equals(group.getCategoryName()))
                .findFirst()
                .orElse(null);

        if (category == null) {
            throw new IllegalArgumentException(String.format("Category with name %s was not found", categoryName));
        }
        return category.getSubCategories();
    }

    public List<IncomeSubCategory> getIncomeSubCategories(Integer categoryNumber) {
        List<IncomeCategory> categories = getIncomeCategories();
        IncomeCategory category =  categories.stream()
                .filter(group -> categoryNumber.equals(group.getCategoryNumber()))
                .findFirst()
                .orElse(null);

        if (category == null) {
            throw new IllegalArgumentException(String.format("Category with number %s was not found", categoryNumber));
        }
        return category.getSubCategories();
    }

}
