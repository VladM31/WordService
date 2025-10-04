package words.com.wordservice.db.daos.impls;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import words.com.wordservice.db.entities.history.CountLearningHistoryProjection;
import words.com.wordservice.db.entities.history.LearningHistoryEntity;
import words.com.wordservice.db.entities.history.StatisticsLearningHistoryProjection;

import java.time.LocalDate;

interface LearningHistoryRepository extends ListCrudRepository<LearningHistoryEntity, String>,
        JpaSpecificationExecutor<LearningHistoryEntity> {

    String SELECT_STATISTICS_LEARNING_HISTORY =
            "SELECT count(lh.id) as count, sum(lh.grade) as grades, lh.type, lh.date from learning_history lh" +
                    " WHERE " +
                    " (:isEmptyUserIds OR lh.user_id in(:userIds)) " +
                    " AND (:startDate IS NULL OR lh.date >= :startDate)" +
                    " AND (:endDate IS NULL OR lh.date <= :endDate)" +
                    " group by lh.date, lh.type" +
                    " order by lh.date, lh.type ";

    String SELECT_COUNT_LEARNING_HISTORY =
            "SELECT count(lh.id) as count, lh.type from learning_history lh" +
                    " WHERE " +
                    " (:isEmptyUserIds OR lh.user_id in(:userIds)) " +
                    " group by lh.type";

    @Query(value = SELECT_STATISTICS_LEARNING_HISTORY, nativeQuery = true)
    Page<StatisticsLearningHistoryProjection> findStatisticsBy(
            boolean isEmptyUserIds,
            java.util.Collection<String> userIds,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );

    @Query(value = SELECT_COUNT_LEARNING_HISTORY, nativeQuery = true)
    Page<CountLearningHistoryProjection> findCountBy(
            boolean isEmptyUserIds,
            java.util.Collection<String> userIds,
            Pageable pageable
    );
}
