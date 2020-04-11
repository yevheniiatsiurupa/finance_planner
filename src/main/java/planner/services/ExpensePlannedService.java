package planner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import planner.dao.ExpensePlannedRepository;
import planner.entity.basic.supplementary.ExpenseCategory;
import planner.entity.basic.supplementary.ExpenseSubCategory;
import planner.entity.month.Expense;
import planner.entity.month.ExpenseIncomeComparison;
import planner.entity.month.ExpensePlanned;
import planner.entity.month.ShortTermPlan;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

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

    public List<ExpenseIncomeComparison> getComparison(List<ExpensePlanned> listPlanned, List<Expense> listActual, List<ExpenseCategory> categories) {
        Map<String, Map<String, Integer>> groupedPlanned = listPlanned.stream()
                .collect(groupingBy(ExpensePlanned::getCategoryName,
                        groupingBy(ExpensePlanned::getSubCategoryName, summingInt(ExpensePlanned::getAmount))));
        Map<String, Map<String, Integer>> groupedActual = listActual.stream()
                .collect(groupingBy(Expense::getCategoryName,
                        groupingBy(Expense::getSubCategoryName, summingInt(Expense::getAmount))));
        List<ExpenseIncomeComparison> result = new ArrayList<>();

        categories.forEach(category -> {
            String catName = category.getCategoryName();
            ExpenseIncomeComparison comparison = new ExpenseIncomeComparison();
            comparison.setCategoryName(catName);

            List<ExpenseSubCategory> subCategories = category.getSubCategories();
            subCategories.forEach(subcategory -> {
                String subcatName = subcategory.getSubCategoryName();

                Integer plannedValue = getValueFromMaps(catName, subcatName, groupedPlanned);
                Integer actualValue = getValueFromMaps(catName, subcatName, groupedActual);

                if (plannedValue != null || actualValue != null) {
                    ExpenseIncomeComparison.Item compareItem =
                            ExpenseIncomeComparison.Item.createItem(subcatName, plannedValue, actualValue);
                    comparison.addItem(compareItem);
                }
            });

            if (comparison.getComparisons().size() != 0) {
                result.add(comparison);
            }
        });
        return result;
    }

    private Integer getValueFromMaps(String catName, String subcatName, Map<String, Map<String, Integer>> map) {
        if (catName == null || subcatName == null) {
            return null;
        }
        Map<String, Integer> innerMap = map.get(catName);
        if (innerMap == null) {
            return null;
        }
        return innerMap.get(subcatName);
    }
}
