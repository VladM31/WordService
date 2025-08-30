package words.com.wordservice.db.searches;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import words.com.wordservice.db.entities.PinnedWordEntity;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PinnedWordSearch implements Specification<PinnedWordEntity> {
    @Singular(ignoreNullCollections = true)
    private Collection<String> playListIds;
    @Singular(ignoreNullCollections = true)
    private Collection<String> userIds;
    @Singular(ignoreNullCollections = true)
    private Collection<String> wordIds;
    @Singular(ignoreNullCollections = true)
    private Collection<String> userWordIds;

    @Override
    public Predicate toPredicate(@NonNull Root<PinnedWordEntity> root, CriteriaQuery<?> query,@NonNull CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (!CollectionUtils.isEmpty(playListIds)) {
            predicates.add(root.get("id").get("playListId").in(playListIds));
        }
        if (!CollectionUtils.isEmpty(userIds)) {
            predicates.add(root.get("word").get("userId").in(userIds));
        }
        if (!CollectionUtils.isEmpty(wordIds)) {
            predicates.add(root.get("word").get("word").get("id").in(wordIds));
        }
        if (!CollectionUtils.isEmpty(userWordIds)) {
            predicates.add(root.get("id").get("userWordId").in(userWordIds));
        }

        return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
    }
}
