package words.com.wordservice.domain.services;

import org.springframework.data.domain.Page;
import words.com.wordservice.domain.models.filters.WordPlayListCountFilter;
import words.com.wordservice.domain.models.filters.WordPlayListFilter;
import words.com.wordservice.domain.models.playlist.*;

import java.util.Collection;


public interface WordPlayListService {

    Page<WordPlayList> findBy(WordPlayListFilter filter);

    Page<WordPlayListCount> findBy(WordPlayListCountFilter filter);

    void saveAll(Collection<ModifyPlayList> playLists);

    void updateAll(Collection<ModifyPlayList> playLists);

    void updateGrades(Collection<PlayListGrade> grades);

    void delete(DeletePlayList deletePlayList);
}
