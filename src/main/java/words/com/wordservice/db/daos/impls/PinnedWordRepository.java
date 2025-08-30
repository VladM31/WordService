package words.com.wordservice.db.daos.impls;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import words.com.wordservice.db.entities.PinnedWordEntity;

interface PinnedWordRepository extends ListCrudRepository<PinnedWordEntity, PinnedWordEntity.PinnedWordId>,
        JpaSpecificationExecutor<PinnedWordEntity> {

    // Custom query methods can be defined here if needed
}
