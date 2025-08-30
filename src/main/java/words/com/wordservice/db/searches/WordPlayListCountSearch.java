package words.com.wordservice.db.searches;

import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import words.com.wordservice.db.entities.PinnedWordEntity;
import words.com.wordservice.db.entities.WordPlayListEntity;
import words.com.wordservice.db.projections.WordPlaylistCountProjection;
import words.com.wordservice.utils.Range;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordPlayListCountSearch implements Specification<WordPlayListEntity> {
    @Singular(ignoreNullCollections = true)
    private Collection<String> ids;
    @Singular(ignoreNullCollections = true)
    private Collection<String> userIds;
    private String name;
    private Range<Long> count;


    @Override
    public Predicate toPredicate(Root<WordPlayListEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Subquery<Long> countSub = query.subquery(Long.class);
        Root<PinnedWordEntity> itemRoot = countSub.from(PinnedWordEntity.class);
        countSub.select(cb.count(itemRoot));
        countSub.where(cb.equal(itemRoot.get("id").get("playListId"), root.get("id")));

        // Use constructor expression for the projection
        query.multiselect(
                root.get("id"),
                root.get("userId"),
                root.get("name"),
                root.get("createdAt"),
                countSub
        ).where(getPredicates(root, query, cb).toArray(new Predicate[0]));

        // Ensure distinct results to avoid duplicates
        query.distinct(true);

        return cb.and(getPredicates(root, query, cb).toArray(new Predicate[0]));
    }

    private List<Predicate> getPredicates(Root<WordPlayListEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (!CollectionUtils.isEmpty(ids)) {
            predicates.add(root.get("id").in(ids));
        }
        if (!CollectionUtils.isEmpty(userIds)) {
            predicates.add(root.get("userId").in(userIds));
        }
        if (StringUtils.hasText(name)) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (Range.hasAny(count)) {
            Subquery<Long> countSub = query.subquery(Long.class);
            Root<PinnedWordEntity> itemRoot = countSub.from(PinnedWordEntity.class);
            countSub.select(cb.count(itemRoot));
            countSub.where(cb.equal(itemRoot.get("id").get("playListId"), root.get("id")));

            if (Range.hasFrom(count)) {
                predicates.add(cb.greaterThanOrEqualTo(countSub, count.getFrom()));
            }
            if (Range.hasTo(count)) {
                predicates.add(cb.lessThanOrEqualTo(countSub, count.getTo()));
            }
        }


        return predicates;
    }
}