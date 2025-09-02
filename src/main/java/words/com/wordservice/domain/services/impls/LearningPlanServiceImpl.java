package words.com.wordservice.domain.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import words.com.wordservice.db.daos.LearningPlanDao;
import words.com.wordservice.domain.mappers.LearningPlanDomainMapper;
import words.com.wordservice.domain.mappers.LearningPlanSearchMapper;
import words.com.wordservice.domain.models.LearningPlan;
import words.com.wordservice.domain.models.filters.LearningPlanFilter;
import words.com.wordservice.domain.services.LearningPlanService;

import java.util.Optional;

@RequiredArgsConstructor
class LearningPlanServiceImpl implements LearningPlanService {
    private final LearningPlanDao learningPlanDao;
    private final LearningPlanSearchMapper learningPlanSearchMapper;
    private final LearningPlanDomainMapper learningPlanDomainMapper;

    @Override
    public Page<LearningPlan> findBy(LearningPlanFilter filter) {
        var search = learningPlanSearchMapper.toSearch(filter);
        var pageable = learningPlanSearchMapper.toPageable(filter);
        return learningPlanDao.findBy(search, pageable)
                .map(learningPlanDomainMapper::toModel);
    }

    @Override
    public LearningPlan create(LearningPlan learningPlan) {
        return Optional.ofNullable(learningPlan)
                .map(learningPlanDomainMapper::toEntity)
                .map(learningPlanDao::save)
                .map(learningPlanDomainMapper::toModel)
                .orElse(null);
    }

    @Override
    public void update(LearningPlan learningPlan) {
        learningPlanDao.update(learningPlanDomainMapper.toEntity(learningPlan));
    }
}
