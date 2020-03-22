package planner.dao.specifications;

import org.springframework.data.jpa.domain.Specification;
import planner.entity.filters.ExpenseIncomeFilter;
import planner.entity.month.Expense;
import planner.entity.month.Expense_;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseSpecification implements Specification<Expense> {
    private static final String TEXT_TRUE = "true";
    public static final String TEXT_FALSE = "false";
    private ExpenseIncomeFilter criteria;

    public ExpenseSpecification(ExpenseIncomeFilter criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Expense> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        Path<Integer> amount = root.get(Expense_.amount);
        Path<String> categoryName = root.get(Expense_.categoryName);
        Path<String> subCategoryName = root.get(Expense_.subCategoryName);
        Path<Date> created = root.get(Expense_.created);
        Path<String> comment = root.get(Expense_.comment);
        Path<Boolean> cache = root.get(Expense_.cache);

        final List<Predicate> predicates = new ArrayList<>();
        if (criteria.getAmountMin() != null) {
            predicates.add(cb.greaterThanOrEqualTo(amount, criteria.getAmountMin()));
        }
        if (criteria.getAmountMax() != null) {
            predicates.add(cb.lessThanOrEqualTo(amount, criteria.getAmountMax()));
        }
        if (criteria.getCategoryName() != null && !criteria.getCategoryName().isEmpty()) {
            predicates.add(cb.equal(categoryName, criteria.getCategoryName()));
        }
        if (criteria.getSubCategoryName() != null && !criteria.getSubCategoryName().isEmpty()) {
            predicates.add(cb.equal(subCategoryName, criteria.getSubCategoryName()));
        }
        if (criteria.getCreatedMin() != null) {
            predicates.add(cb.greaterThanOrEqualTo(created, criteria.getCreatedMin()));
        }
        if (criteria.getCreatedMax() != null) {
            predicates.add(cb.lessThanOrEqualTo(created, criteria.getCreatedMax()));
        }
        if (criteria.getComment() != null && !criteria.getComment().isEmpty()) {
            if (TEXT_TRUE.equals(criteria.getComment())) {
                predicates.add(cb.isNotNull(comment));
                predicates.add(cb.notEqual(comment, ""));
            } else if (TEXT_FALSE.equals(criteria.getComment())) {
                predicates.add(cb.or(cb.isNull(comment), cb.equal(comment, "")));
            }
        }
        if (criteria.getCache() != null && !criteria.getCache().isEmpty()) {
            if (TEXT_TRUE.equals(criteria.getCache())) {
                predicates.add(cb.isTrue(cache));
            } else if (TEXT_FALSE.equals(criteria.getCache())){
                predicates.add(cb.isFalse(cache));
            }
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
