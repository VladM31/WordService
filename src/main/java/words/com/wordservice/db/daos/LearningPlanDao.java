package words.com.wordservice.db.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import words.com.wordservice.db.entities.LearningPlanEntity;
import words.com.wordservice.db.searches.LearningPlanSearch;

import java.util.List;

public interface LearningPlanDao {

    Page<LearningPlanEntity> findBy(LearningPlanSearch search, Pageable pageable);

    List<LearningPlanEntity> findBy(LearningPlanSearch search);

    void save(LearningPlanEntity entity);

    void update(LearningPlanEntity entity);
}
