package words.com.wordservice.domain.services;

import org.springframework.data.domain.Page;
import words.com.wordservice.domain.models.filters.WordPlayListCountFilter;
import words.com.wordservice.domain.models.filters.WordPlayListFilter;
import words.com.wordservice.domain.models.playlist.*;

import java.util.Collection;
import java.util.Set;


public interface WordPlayListService {

    Page<WordPlayList> findBy(WordPlayListFilter filter);

    Page<WordPlayListCount> findBy(WordPlayListCountFilter filter);

    void saveAll(Collection<ModifyPlayList> playLists);

    void updateAll(Collection<ModifyPlayList> playLists);

    Set<String> assignPlaylists(Collection<String> playListIds, String userId);

    Set<AssignedPlaylist> getAssignedPlaylists(String userId);

    void updateGrades(Collection<PlayListGrade> grades);

    void delete(DeletePlayList deletePlayList);
}
