package words.com.wordservice.db.daos.impls;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import words.com.wordservice.db.entities.LearningPlanEntity;

 interface LearningPlanRepository extends ListCrudRepository<LearningPlanEntity,String>,
        JpaSpecificationExecutor<LearningPlanEntity> {
}
