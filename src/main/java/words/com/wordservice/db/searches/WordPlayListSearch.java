package words.com.wordservice.db.searches;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import words.com.wordservice.db.entities.WordPlayListEntity;
import words.com.wordservice.domain.models.enums.PlayListVisibility;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordPlayListSearch implements Specification<WordPlayListEntity> {
    @Singular(ignoreNullCollections = true)
    private Collection<String> ids;
    @Singular(ignoreNullCollections = true)
    private Collection<String> userIds;

    private String name;
    private PlayListVisibility visibility;

    @Override
    public Predicate toPredicate(Root<WordPlayListEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
        if (visibility != null) {
            predicates.add(cb.equal(root.get("visibility"), visibility));
        }

        if (predicates.isEmpty()) {
            return null;
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
