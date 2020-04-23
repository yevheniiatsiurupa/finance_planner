package planner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import planner.dao.IncomePlannedRepository;
import planner.entity.basic.supplementary.IncomeCategory;
import planner.entity.basic.supplementary.IncomeSubCategory;
import planner.entity.month.ExpenseIncomeComparison;
import planner.entity.month.Income;
import planner.entity.month.IncomePlanned;
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
public class IncomePlannedService {
    private IncomePlannedRepository repository;

    @Autowired
    public IncomePlannedService(IncomePlannedRepository repository) {
        this.repository = repository;
    }

    public List<IncomePlanned> findAll() {
        return repository.findAll();
    }

    public Page<IncomePlanned> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public void save(IncomePlanned income) {
        repository.save(income);
    }

    public void saveAll(List<IncomePlanned> expenses) {
        repository.saveAll(expenses);
    }

    public IncomePlanned findById(Integer id) {
        Optional<IncomePlanned> income = repository.findById(id);
        if (income.isEmpty()) {
            throw new EntityNotFoundException("IncomePlanned is not found.");
        }
        return income.get();
    }

    public void delete(IncomePlanned income) {
        repository.delete(income);
    }

    public List<IncomePlanned> findByPlan(ShortTermPlan plan) {
        return repository.findByShortTermPlan(plan);
    }

    public List<List<IncomePlanned>> getGroupedList(List<IncomeCategory> categories,
                                                     List<IncomePlanned> incomes,
                                                     boolean needEmptyEntities) {
        List<List<IncomePlanned>> resultList = new ArrayList<>();
        if (incomes == null || incomes.size() == 0) {
            return resultList;
        }
        categories.forEach(category -> {
            String categoryName = category.getCategoryName();
            List<IncomeSubCategory> subCategories = category.getSubCategories();
            List<IncomePlanned> categoryList = new ArrayList<>();

            subCategories.forEach(subcategory -> {
                String subcategoryName = subcategory.getSubCategoryName();
                List<IncomePlanned> filtered = getExpensesByParams(categoryName, subcategoryName, incomes);
                if (needEmptyEntities && filtered.size() == 0) {
                    IncomePlanned emptyExp = new IncomePlanned();
                    emptyExp.setCategoryName(categoryName);
                    emptyExp.setSubCategoryName(subcategoryName);
                    emptyExp.setComment("");
                    filtered.add(emptyExp);
                }
                filtered.forEach(income -> {
                    income.setCategoryNumber(String.valueOf(category.getCategoryNumber()));
                    income.setSubCategoryNumber(String.valueOf(subcategory.getSubCategoryNumber()));
                });
                categoryList.addAll(filtered);
            });
            if (categoryList.size() != 0) {
                resultList.add(categoryList);
            }
        });
        return resultList;
    }

    private List<IncomePlanned> getExpensesByParams(String categoryName, String subcategoryName,
                                                     List<IncomePlanned> incomes) {
        if (categoryName == null || subcategoryName == null) {
            return new ArrayList<>();
        }
        return incomes.stream()
                .filter(income -> categoryName.equals(income.getCategoryName())
                        && subcategoryName.equals(income.getSubCategoryName()))
                .collect(Collectors.toList());
    }

    public List<ExpenseIncomeComparison> getComparison(List<IncomePlanned> listPlanned, List<Income> listActual, List<IncomeCategory> categories) {
        Map<String, Map<String, Integer>> groupedPlanned = listPlanned.stream()
                .collect(groupingBy(IncomePlanned::getCategoryName,
                        groupingBy(IncomePlanned::getSubCategoryName, summingInt(IncomePlanned::getAmount))));
        Map<String, Map<String, Integer>> groupedActual = listActual.stream()
                .collect(groupingBy(Income::getCategoryName,
                        groupingBy(Income::getSubCategoryName, summingInt(Income::getAmount))));
        List<ExpenseIncomeComparison> result = new ArrayList<>();

        categories.forEach(category -> {
            String catName = category.getCategoryName();
            ExpenseIncomeComparison comparison = new ExpenseIncomeComparison();
            comparison.setCategoryName(catName);

            List<IncomeSubCategory> subCategories = category.getSubCategories();
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
