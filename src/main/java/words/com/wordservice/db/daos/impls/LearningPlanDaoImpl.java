package words.com.wordservice.db.daos.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import words.com.wordservice.db.daos.LearningPlanDao;
import words.com.wordservice.db.entities.LearningPlanEntity;
import words.com.wordservice.db.searches.LearningPlanSearch;

import java.util.List;

@RequiredArgsConstructor
class LearningPlanDaoImpl implements LearningPlanDao {
    private final LearningPlanRepository repository;
    @Override
    public Page<LearningPlanEntity> findBy(LearningPlanSearch search, Pageable pageable) {
        return repository.findAll(search, pageable);
    }

    @Override
    public List<LearningPlanEntity> findBy(LearningPlanSearch search) {
        return repository.findAll(search);
    }

    @Override
    public void save(LearningPlanEntity entity) {
        repository.save(entity);
    }

    @Override
    public void update(LearningPlanEntity entity) {
        repository.save(entity);
    }
}
