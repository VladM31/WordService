package words.com.wordservice.db.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import words.com.wordservice.db.actions.UserWordDeleteAction;
import words.com.wordservice.db.actions.UserWordGradeUpdateAction;
import words.com.wordservice.db.actions.UserWordUpdateAction;
import words.com.wordservice.db.actions.UserWordUpsertAction;
import words.com.wordservice.db.entities.UserWordEntity;
import words.com.wordservice.db.searches.UserWordSearch;

import java.util.Collection;
import java.util.List;

public interface UserWordDao {

    Page<UserWordEntity> findBy(UserWordSearch s, Pageable pageable);

    List<UserWordEntity> findBy(UserWordSearch s);


    void save(UserWordEntity entity);

    void saveAll(Collection<UserWordEntity> entities);

    void update(UserWordEntity entity);

    void updateAll(Collection<UserWordEntity> entities);

    void upsertAll(Collection<UserWordUpsertAction> entities);

    void update(UserWordUpdateAction action);

    void addGrades(Collection<UserWordGradeUpdateAction> entities);

    void delete(Collection<UserWordDeleteAction> actions);

    void delete(List<UserWordEntity> entities);

    long count(UserWordSearch s);
}
