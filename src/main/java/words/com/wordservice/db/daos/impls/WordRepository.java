package words.com.wordservice.db.daos.impls;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.lang.Nullable;
import words.com.wordservice.db.entities.WordEntity;
import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;

interface WordRepository extends ListCrudRepository<WordEntity, String>, JpaSpecificationExecutor<WordEntity> {


    @Query("""
            UPDATE WordEntity w
            SET w.original = :original,
                w.lang = :lang,
                w.translate = :translate,
                w.translateLang = :translateLang,
                w.category = :category,
                w.description = :description,
                w.cefr = :cefr
            WHERE w.id = :id
            """)
    @Modifying
    void update(
            String id,
            String original,
            Language lang,
            String translate,
            Language translateLang,
            @Nullable
            String category,
            @Nullable
            String description,
            CEFR cefr
    );

}
