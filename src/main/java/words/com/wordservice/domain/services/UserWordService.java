package words.com.wordservice.domain.services;

import org.springframework.data.domain.Page;
import words.com.wordservice.domain.models.filters.UserWordFilter;
import words.com.wordservice.domain.models.words.*;

import java.util.Collection;

public interface UserWordService {

    Page<UserWord> findBy(UserWordFilter filter);

    void save(Collection<ModifyUserWord> userWords);

    void savePins(Collection<PinUserWord> userWords);

    void update(Collection<ModifyUserWord> userWords);

    void addGrades(Collection<GradeUserWord> grades);

    long count(UserWordFilter filter);

    void delete(Collection<DeleteUserWordOptions> userWords);
}
