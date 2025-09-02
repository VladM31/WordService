package words.com.wordservice.domain.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import words.com.wordservice.db.daos.WordDao;
import words.com.wordservice.domain.mappers.WordDomainMapper;
import words.com.wordservice.domain.mappers.WordSearchMapper;
import words.com.wordservice.domain.models.filters.WordFilter;
import words.com.wordservice.domain.models.words.DeleteWordOptions;
import words.com.wordservice.domain.models.words.ModifyWord;
import words.com.wordservice.domain.models.words.Word;
import words.com.wordservice.domain.services.WordService;

import java.util.Collection;

@RequiredArgsConstructor
class WordServiceImpl implements WordService {
    private final WordDao wordDao;
    private final WordSearchMapper wordSearchMapper;
    private final WordDomainMapper wordDomainMapper;

    @Override
    public Page<Word> findBy(WordFilter filter) {
        var search = wordSearchMapper.toSearch(filter);
        var pageable = wordSearchMapper.toPageable(filter);
        return wordDao.findBy(search, pageable)
                .map(wordDomainMapper::toModel);
    }

    @Override
    public void saveAll(Collection<ModifyWord> words) {
        var entities = words.stream()
                .map(wordDomainMapper::toEntity)
                .toList();
        wordDao.saveAll(entities);
    }

    @Override
    public void updateAll(Collection<ModifyWord> words) {
        var entities = words.stream()
                .map(wordDomainMapper::toEntity)
                .toList();
        wordDao.saveAll(entities);
    }

    @Override
    public void delete(DeleteWordOptions options) {
        var actions = wordDomainMapper.toActions(options);
        wordDao.delete(actions);
    }


}
