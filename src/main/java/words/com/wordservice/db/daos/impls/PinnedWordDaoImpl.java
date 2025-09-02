package words.com.wordservice.db.daos.impls;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import words.com.wordservice.db.daos.PinnedWordDao;
import words.com.wordservice.db.entities.PinnedWordEntity;
import words.com.wordservice.db.searches.PinnedWordSearch;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
class PinnedWordDaoImpl implements PinnedWordDao {
    private final PinnedWordRepository repository;
    @Override
    public Page<PinnedWordEntity> findBy(PinnedWordSearch search, Pageable pageable) {
        return repository.findAll(search, pageable);
    }

    @Override
    public List<PinnedWordEntity> findBy(PinnedWordSearch search) {
        return repository.findAll(search);
    }

    @Override
    public void saveAll(Collection<PinnedWordEntity> entities) {
        repository.saveAll(entities);
    }

    @Override
    @Transactional
    public void delete(PinnedWordSearch search) {
        repository.delete(search);
    }
}
