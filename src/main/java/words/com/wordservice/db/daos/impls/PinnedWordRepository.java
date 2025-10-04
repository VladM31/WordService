package words.com.wordservice.db.daos.impls;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import words.com.wordservice.db.entities.PinnedWordEntity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

interface PinnedWordRepository extends ListCrudRepository<PinnedWordEntity, PinnedWordEntity.PinnedWordId>,
        JpaSpecificationExecutor<PinnedWordEntity> {

    @Modifying
    @Query("""
                UPDATE PinnedWordEntity pw
                SET pw.learningGrade = pw.learningGrade + :delta,
                    pw.lastReadDate = :date
                WHERE pw.id.userWordId = :userWordId
            """)
    void updateGrade(@Param("delta") long delta,
                          @Param("date") OffsetDateTime date,
                          @Param("userWordId") String userWordId);
}
