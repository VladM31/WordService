package words.com.wordservice.db.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import words.com.wordservice.db.actions.WordDeleteAction;
import words.com.wordservice.db.actions.WordUpdateAction;
import words.com.wordservice.db.entities.WordEntity;
import words.com.wordservice.db.searches.WordSearch;

import java.util.Collection;
import java.util.List;

public interface WordDao {

    Page<WordEntity> findBy(WordSearch search, Pageable pageable);

    List<WordEntity> findBy(WordSearch search);

    void save(WordEntity entity);

    void saveAll(Collection<WordEntity> entities);

    void update(WordEntity entity);

    void update(WordUpdateAction action);

    void updateAll(Collection<WordEntity> entities);

    void delete(Collection<WordDeleteAction> actions);
}
