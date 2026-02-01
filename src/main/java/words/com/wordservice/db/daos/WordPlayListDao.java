package words.com.wordservice.db.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import words.com.wordservice.db.actions.UpdateUserWordGradeAction;
import words.com.wordservice.db.entities.WordPlayListEntity;
import words.com.wordservice.db.projections.WordPlaylistCountProjection;
import words.com.wordservice.db.searches.WordPlayListCountSearch;
import words.com.wordservice.db.searches.WordPlayListSearch;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface WordPlayListDao {

    Page<WordPlayListEntity> findBy(WordPlayListSearch search, Pageable pageable);

    List<WordPlayListEntity> findBy(WordPlayListSearch search);

    Page<WordPlaylistCountProjection> findBy(WordPlayListCountSearch search, Pageable pageable);

    List<WordPlaylistCountProjection> findBy(WordPlayListCountSearch search);

    Set<String> getAssignedPlaylistIds(String userId);

    void saveAll(Collection<WordPlayListEntity> entities);

    void updateAll(Collection<WordPlayListEntity> entities);

    void updateGrades(Collection<UpdateUserWordGradeAction> actions);

    void delete(WordPlayListSearch search);
}
