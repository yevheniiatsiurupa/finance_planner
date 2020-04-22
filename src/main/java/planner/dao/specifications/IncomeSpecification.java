package planner.dao.specifications;

import org.springframework.data.jpa.domain.Specification;
import planner.entity.basic.UserAccount_;
import planner.entity.filters.ExpenseIncomeFilter;
import planner.entity.month.Income;
import planner.entity.month.Income_;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IncomeSpecification implements Specification<Income> {
    private static final String TEXT_TRUE = "true";
    public static final String TEXT_FALSE = "false";
    private ExpenseIncomeFilter criteria;

    public IncomeSpecification(ExpenseIncomeFilter criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Income> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        Path<Integer> amount = root.get(Income_.amount);
        Path<String> categoryName = root.get(Income_.categoryName);
        Path<String> subCategoryName = root.get(Income_.subCategoryName);
        Path<Date> created = root.get(Income_.created);
        Path<String> comment = root.get(Income_.comment);
        Path<Integer> userId = root.get(Income_.userAccount).get(UserAccount_.id);

        final List<Predicate> predicates = new ArrayList<>();
        if (criteria.getUserAccountId() != null) {
            predicates.add(cb.equal(userId, criteria.getUserAccountId()));
        }
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
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
