package planner.dao.specifications;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import planner.entity.basic.UserAccount_;
import planner.entity.month.ShortTermPlan;
import planner.entity.month.ShortTermPlan_;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ShortTermPlanSpecification implements Specification<ShortTermPlan> {

    private Integer userAccountId;

    public ShortTermPlanSpecification(Integer userAccountId) {
        this.userAccountId = userAccountId;
    }

    @Override
    public Predicate toPredicate(Root<ShortTermPlan> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        Path<Integer> userId = root.get(ShortTermPlan_.userAccount).get(UserAccount_.id);

        final List<Predicate> predicates = new ArrayList<>();
        if (userAccountId != null) {
            predicates.add(cb.equal(userId, userAccountId));
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
