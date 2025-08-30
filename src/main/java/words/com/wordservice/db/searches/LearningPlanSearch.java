package words.com.wordservice.db.searches;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import words.com.wordservice.db.entities.LearningPlanEntity;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningPlanSearch implements Specification<LearningPlanEntity> {
    @Singular(ignoreNullCollections = true)
    private Collection<String> userIds;

    @Override
    public Predicate toPredicate(Root<LearningPlanEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (!CollectionUtils.isEmpty(userIds)) {
            predicates.add(root.get("userId").in(userIds));
        }

        if (predicates.isEmpty()) {
            return null;
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
