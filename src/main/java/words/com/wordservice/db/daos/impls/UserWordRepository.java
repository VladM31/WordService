package words.com.wordservice.db.daos.impls;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import words.com.wordservice.db.entities.UserWordEntity;

import java.time.OffsetDateTime;

interface UserWordRepository extends ListCrudRepository<UserWordEntity, String>, JpaSpecificationExecutor<UserWordEntity> {

    // Custom query methods can be defined here if needed

    @Query("UPDATE UserWordEntity u " +
           "SET u.learningGrade = u.learningGrade + :value, u.lastReadDate = :lastReadDate " +
           "WHERE u.id = :id AND u.userId = :userId")
    @Modifying
    void updateGrade(long value, OffsetDateTime lastReadDate, String id, String userId);
}
