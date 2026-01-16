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
public class UserWordSearch implements Specification<UserWordEntity> {
    @Singular(ignoreNullCollections = true)
    private Collection<String> userWordIds;
    @Singular(ignoreNullCollections = true)
    private Collection<String> wordIds;
    @Singular(ignoreNullCollections = true)
    private Collection<String> userIds;
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

    private Boolean hasSound;
    private Boolean hasImage;
    private Operations operation;

    @Nullable
    @Override
    public Predicate toPredicate(@NonNull Root<UserWordEntity> root, @Nullable CriteriaQuery<?> query,@NonNull CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (!CollectionUtils.isEmpty(userWordIds)) {
            predicates.add(root.get("id").in(userWordIds));
        }
        if (!CollectionUtils.isEmpty(wordIds)) {
            predicates.add(root.get("word").get("id").in(wordIds));
        }
        if (!CollectionUtils.isEmpty(userIds)) {
            predicates.add(root.get("userId").in(userIds));
        }

        Join<UserWordEntity, WordEntity> wordJoin = root.join("word", JoinType.INNER);

        if (!CollectionUtils.isEmpty(languages)) {
            predicates.add(wordJoin.get("lang").in(languages));
        }
        if (!CollectionUtils.isEmpty(translateLanguages)) {
            predicates.add(wordJoin.get("translateLang").in(translateLanguages));
        }
        if (!CollectionUtils.isEmpty(categories)) {
            predicates.add(wordJoin.get("category").in(categories));
        }
        if (!CollectionUtils.isEmpty(cefrs)) {
            predicates.add(wordJoin.get("cefr").in(cefrs));
        }
        if (!CollectionUtils.isEmpty(types)) {
            predicates.add(wordJoin.get("type").in(types));
        }

        if (StringUtils.hasText(translate)) {
            predicates.add(cb.like(cb.lower(wordJoin.get("translate")), "%" + translate.toLowerCase() + "%"));
        }
        if (StringUtils.hasText(original)) {
            predicates.add(cb.like(cb.lower(wordJoin.get("original")), "%" + original.toLowerCase() + "%"));
        }

        if (Boolean.TRUE.equals(hasSound)) {
            predicates.add(cb.or(
                    cb.isNotNull(wordJoin.get("soundFileName")),
                    cb.isNotNull(root.get("customSoundFileName"))
            ));
        }
        if (Boolean.TRUE.equals(hasImage)) {
            predicates.add(cb.or(
                    cb.isNotNull(wordJoin.get("imageFileName")),
                    cb.isNotNull(root.get("customImageFileName"))
            ));
        }
        if (Boolean.FALSE.equals(hasSound)) {
            predicates.add(cb.and(
                    cb.isNull(wordJoin.get("soundFileName")),
                    cb.isNull(root.get("customSoundFileName"))
            ));
        }
        if (Boolean.FALSE.equals(hasImage)) {
            predicates.add(cb.and(
                    cb.isNull(wordJoin.get("imageFileName")),
                    cb.isNull(root.get("customImageFileName"))
            ));
        }

        if (predicates.isEmpty()) {
            return null;
        }
        if (operation == Operations.OR) {
            return cb.or(predicates.toArray(new Predicate[0]));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}