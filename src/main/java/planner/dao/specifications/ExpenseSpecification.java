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
        if (criteria.getCategoryName() != null) {
            predicates.add(cb.equal(categoryName, criteria.getCategoryName()));
        }
        if (criteria.getSubCategoryName() != null) {
            predicates.add(cb.equal(subCategoryName, criteria.getSubCategoryName()));
        }
        if (criteria.getCreatedMin() != null) {
            predicates.add(cb.greaterThanOrEqualTo(created, criteria.getCreatedMin()));
        }
        if (criteria.getCreatedMax() != null) {
            predicates.add(cb.lessThanOrEqualTo(created, criteria.getCreatedMax()));
        }
        if (criteria.getComment() != null) {
            if (criteria.getComment()) {
                predicates.add(cb.isNotNull(comment));
                predicates.add(cb.notEqual(comment, ""));
            } else {
                predicates.add(cb.or(cb.isNull(comment), cb.equal(comment, "")));
            }
        }
        if (criteria.getCache() != null) {
            if (criteria.getCache()) {
                predicates.add(cb.isTrue(cache));
            } else {
                predicates.add(cb.isFalse(cache));
            }
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
