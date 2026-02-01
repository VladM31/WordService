package words.com.wordservice.db.searches;

import jakarta.persistence.criteria.*;
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
    @Singular(ignoreNullCollections = true)
    private Collection<PinnedWordEntity.PinnedWordId> pinnedIds;

    @Override
    public Predicate toPredicate(@NonNull Root<PinnedWordEntity> root, CriteriaQuery<?> query,@NonNull CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        // Add fetch joins only for non-count queries to avoid affecting count projections
        Class<?> resultType = query.getResultType();
        if (!Long.class.equals(resultType)) {
            // fetch UserWordEntity (root.word) and nested WordEntity (root.word.word)
            try {
                root.fetch("word", JoinType.LEFT).fetch("word", JoinType.LEFT);
                query.distinct(true);
            } catch (Exception ignore) {
                // ignore fetch failures to keep search robust
            }
        }

        if (!CollectionUtils.isEmpty(pinnedIds)) {
            predicates.add(root.get("id").in(pinnedIds));
        }
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
