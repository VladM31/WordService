package words.com.wordservice.db.searches.history;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import words.com.wordservice.db.entities.history.LearningHistoryEntity;
import words.com.wordservice.utils.Range;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningHistorySearch implements Specification<LearningHistoryEntity> {
    @Singular(ignoreNullCollections = true)
    private Collection<String> userIds;
    @Singular(ignoreNullCollections = true)
    private Collection<String> wordIds;
    private Range<LocalDate> date;

    @Override
    public Predicate toPredicate(@NonNull Root<LearningHistoryEntity> root, CriteriaQuery<?> query,@NonNull CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (!CollectionUtils.isEmpty(userIds)) {
            predicates.add(root.get("userId").in(userIds));
        }
        if (!CollectionUtils.isEmpty(wordIds)) {
            predicates.add(root.get("wordId").in(wordIds));
        }

        if (Range.hasFrom(date)) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("date"), date.getFrom()));
        }
        if (Range.hasTo(date)) {
            predicates.add(cb.lessThanOrEqualTo(root.get("date"), date.getTo()));
        }

        return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
    }
}
