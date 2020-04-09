package planner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import planner.dao.ExpensePlannedRepository;
import planner.entity.basic.supplementary.ExpenseCategory;
import planner.entity.basic.supplementary.ExpenseSubCategory;
import planner.entity.month.ExpensePlanned;
import planner.entity.month.ShortTermPlan;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpensePlannedService {
    private ExpensePlannedRepository repository;

    @Autowired
    public ExpensePlannedService(ExpensePlannedRepository repository) {
        this.repository = repository;
    }

    public List<ExpensePlanned> findAll() {
        return repository.findAll();
    }

    public Page<ExpensePlanned> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public void save(ExpensePlanned expense) {
        repository.save(expense);
    }

    public void saveAll(List<ExpensePlanned> expenses) {
        repository.saveAll(expenses);
    }

    public ExpensePlanned findById(Integer id) {
        Optional<ExpensePlanned> expense = repository.findById(id);
        if (expense.isEmpty()) {
            throw new EntityNotFoundException("ExpensePlanned is not found.");
        }
        return expense.get();
    }

    public void delete(ExpensePlanned expense) {
        repository.delete(expense);
    }

    public List<ExpensePlanned> findByPlan(ShortTermPlan plan) {
        return repository.findByShortTermPlan(plan);
    }

    public List<List<ExpensePlanned>> getGroupedList(List<ExpenseCategory> categories,
                                                            List<ExpensePlanned> expenses,
                                                            boolean needEmptyEntities) {
        List<List<ExpensePlanned>> resultList = new ArrayList<>();
        if (expenses == null || expenses.size() == 0) {
            return resultList;
        }
        categories.forEach(category -> {
            String categoryName = category.getCategoryName();
            List<ExpenseSubCategory> subCategories = category.getSubCategories();
            List<ExpensePlanned> categoryList = new ArrayList<>();

            subCategories.forEach(subcategory -> {
                String subcategoryName = subcategory.getSubCategoryName();
                List<ExpensePlanned> filtered = getExpensesByParams(categoryName, subcategoryName, expenses);
                if (needEmptyEntities && filtered.size() == 0) {
                    ExpensePlanned emptyExp = new ExpensePlanned();
                    emptyExp.setCategoryName(categoryName);
                    emptyExp.setSubCategoryName(subcategoryName);
                    emptyExp.setComment("");
                    filtered.add(emptyExp);
                }
                filtered.forEach(expense -> {
                    expense.setCategoryNumber(String.valueOf(category.getCategoryNumber()));
                    expense.setSubCategoryNumber(String.valueOf(subcategory.getSubCategoryNumber()));
                });
                categoryList.addAll(filtered);
            });
            if (categoryList.size() != 0) {
                resultList.add(categoryList);
            }
        });
        return resultList;
    }

    private List<ExpensePlanned> getExpensesByParams(String categoryName, String subcategoryName,
                                                     List<ExpensePlanned> expenses) {
        if (categoryName == null || subcategoryName == null) {
            return new ArrayList<>();
        }
        return expenses.stream()
                .filter(expense -> categoryName.equals(expense.getCategoryName()) &&
                        subcategoryName.equals(expense.getSubCategoryName()))
                .collect(Collectors.toList());
    }
}
