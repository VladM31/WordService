package words.com.wordservice.db.daos.impls;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import words.com.wordservice.db.actions.DeleteWordAction;
import words.com.wordservice.db.daos.WordDao;
import words.com.wordservice.db.entities.WordEntity;
import words.com.wordservice.db.searches.WordSearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
class WordDaoImpl implements WordDao {
    private final WordRepository repository;

    @Override
    public Page<WordEntity> findBy(WordSearch search, Pageable pageable) {
        return repository.findAll(search, pageable);
    }

    @Override
    public List<WordEntity> findBy(WordSearch search) {
        return repository.findAll(search);
    }

    @Override
    public void save(WordEntity entity) {
        repository.save(entity);
    }

    @Override
    public void saveAll(Collection<WordEntity> entities) {
        repository.saveAll(entities);
    }

    @Override
    public void update(WordEntity entity) {
        repository.save(entity);
    }

    @Override
    public void updateAll(Collection<WordEntity> entities) {
        repository.saveAll(entities);
    }

    @Override
    @Transactional
    public void delete(Collection<DeleteWordAction> actions) {
        ArrayList<Specification<WordEntity>> searches = new ArrayList<>();

        for (DeleteWordAction action : actions) {
            searches.add(WordSearch.builder()
                    .wordId(action.id())
                    .type(action.type())
                    .build());
        }
        if (searches.isEmpty()) {
            return;
        }
        repository.delete(Specification.anyOf(searches));
    }
}
