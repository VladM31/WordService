package words.com.wordservice.db.daos.impls;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import words.com.wordservice.db.entities.UserWordEntity;

import java.time.OffsetDateTime;

interface UserWordRepository extends ListCrudRepository<UserWordEntity, String>, JpaSpecificationExecutor<UserWordEntity> {

    // Custom query methods can be defined here if needed

    @Query("UPDATE UserWordEntity u " +
           "SET u.learningGrade = u.learningGrade + :value, u.lastReadDate = :lastReadDate " +
           "WHERE u.id = :id AND u.userId = :userId")
    @Modifying
    void updateGrade(long value, OffsetDateTime lastReadDate, String id, String userId);


    @Modifying
    @Query(value = """
            INSERT INTO user_words(
                id, created_at, custom_image_file_name, custom_sound_file_name, 
                last_read_date, learning_grade, user_id, word_id
            ) VALUES (
                :id, :createdAt, :customImageFileName, :customSoundFileName, 
                :lastReadDate, :learningGrade, :userId, :wordId
            )
            """, nativeQuery = true)
    void upsert(
            @Param("id") String id,
            @Param("createdAt") OffsetDateTime createdAt,
            @Param("customImageFileName") String customImageFileName,
            @Param("customSoundFileName") String customSoundFileName,
            @Param("lastReadDate") OffsetDateTime lastReadDate,
            @Param("learningGrade") long learningGrade,
            @Param("userId") String userId,
            @Param("userWordId") String wordId
    );
}
