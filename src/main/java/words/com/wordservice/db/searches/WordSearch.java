package words.com.wordservice.db.searches;

import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import words.com.wordservice.db.entities.UserWordEntity;
import words.com.wordservice.db.entities.WordEntity;
import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;
import words.com.wordservice.domain.models.enums.WordType;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordSearch implements Specification<WordEntity> {
    @Singular(ignoreNullCollections = true)
    private Collection<String> wordIds;
    @Singular(ignoreNullCollections = true)
    private Collection<Language> languages;
    @Singular(ignoreNullCollections = true)
    private Collection<Language> translateLanguages;
    @Singular(ignoreNullCollections = true)
    private Collection<String> categories;
    @Singular(ignoreNullCollections = true)
    private Collection<CEFR> cefrs;
    @Singular(ignoreNullCollections = true)
    private Collection<WordType> types;

    private String translate;
    private String original;

    private UserId userId;

    private Boolean hasSound;
    private Boolean hasImage;

    @Nullable
    @Override
    public Predicate toPredicate(@NonNull Root<WordEntity> root, @Nullable CriteriaQuery<?> query,@NonNull CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (!CollectionUtils.isEmpty(wordIds)) {
            predicates.add(root.get("id").in(wordIds));
        }
        if (!CollectionUtils.isEmpty(languages)) {
            predicates.add(root.get("lang").in(languages));
        }
        if (!CollectionUtils.isEmpty(translateLanguages)) {
            predicates.add(root.get("translateLang").in(translateLanguages));
        }
        if (!CollectionUtils.isEmpty(categories)) {
            predicates.add(root.get("category").in(categories));
        }
        if (!CollectionUtils.isEmpty(cefrs)) {
            predicates.add(root.get("cefr").in(cefrs));
        }
        if (!CollectionUtils.isEmpty(types)) {
            predicates.add(root.get("type").in(types));
        }

        if (StringUtils.hasText(translate)) {
            predicates.add(cb.like(cb.lower(root.get("translate")), "%" + translate.toLowerCase() + "%"));
        }
        if (StringUtils.hasText(original)) {
            predicates.add(cb.like(cb.lower(root.get("original")), "%" + original.toLowerCase() + "%"));
        }

        if (Boolean.TRUE.equals(hasSound)) {
            predicates.add(cb.isNotNull(root.get("soundLink")));
        }
        if (Boolean.TRUE.equals(hasImage)) {
            predicates.add(cb.isNotNull(root.get("imageLink")));
        }
        if (Boolean.FALSE.equals(hasSound)) {
            predicates.add(cb.isNull(root.get("soundLink")));
        }
        if (Boolean.FALSE.equals(hasImage)) {
            predicates.add(cb.isNull(root.get("imageLink")));
        }

        if (hasUserId()) {
            Join<WordEntity, UserWordEntity> userWordJoin = root.join("word", JoinType.LEFT);
            if (userId.isIn()) {
                predicates.add(cb.equal(userWordJoin.get("userId"), userId.getId()));
            } else {
                predicates.add(cb.or(
                        cb.notEqual(userWordJoin.get("userId"), userId.getId()),
                        cb.isNull(userWordJoin.get("userId"))
                ));
            }
        }

        return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserId{
        private boolean isIn;
        @NonNull
        private String id;
    }

    public boolean hasUserId(){
        return userId != null;
    }
}
