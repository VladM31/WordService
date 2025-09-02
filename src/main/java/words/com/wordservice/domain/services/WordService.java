package words.com.wordservice.domain.services;

import org.springframework.data.domain.Page;
import words.com.wordservice.domain.models.filters.WordFilter;
import words.com.wordservice.domain.models.words.DeleteWordOptions;
import words.com.wordservice.domain.models.words.ModifyWord;
import words.com.wordservice.domain.models.words.Word;

import java.util.Collection;

public interface WordService {
    Page<Word> findBy(WordFilter filter);

    void saveAll(Collection<ModifyWord> words);

    void updateAll(Collection<ModifyWord> words);

    void delete(DeleteWordOptions options);
}
