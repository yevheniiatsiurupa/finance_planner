package planner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import planner.dao.ShortTermPlanRepository;
import planner.dao.specifications.ShortTermPlanSpecification;
import planner.entity.basic.UserAccount;
import planner.entity.month.ShortTermPlan;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ShortTermPlanService {
    private ShortTermPlanRepository repository;

    @Autowired
    public ShortTermPlanService(ShortTermPlanRepository repository) {
        this.repository = repository;
    }

    public List<ShortTermPlan> findAll() {
        return repository.findAll();
    }

    public Page<ShortTermPlan> findAll(Pageable pageable, UserAccount userAccount) {
        ShortTermPlanSpecification spec = new ShortTermPlanSpecification(userAccount.getId());
        return repository.findAll(spec, pageable);
    }

    public void save(ShortTermPlan plan) {
        repository.save(plan);
    }

    public ShortTermPlan findById(Integer id) {
        Optional<ShortTermPlan> plan = repository.findById(id);
        if (plan.isEmpty()) {
            throw new EntityNotFoundException("ShortTermPlan is not found.");
        }
        return plan.get();
    }

    public void delete(ShortTermPlan plan) {
        repository.delete(plan);
    }
}
