package words.com.wordservice.db.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import words.com.wordservice.db.actions.DeleteUserWordAction;
import words.com.wordservice.db.actions.UpdateUserWordGradeAction;
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


    void addGrades(Collection<UpdateUserWordGradeAction> entities);

    void delete(Collection<DeleteUserWordAction> actions);

    void delete(List<UserWordEntity> entities);

    long count(UserWordSearch s);
}
