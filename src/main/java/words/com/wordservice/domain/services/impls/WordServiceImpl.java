package words.com.wordservice.domain.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import words.com.wordservice.db.daos.WordDao;
import words.com.wordservice.db.searches.Operations;
import words.com.wordservice.db.searches.WordSearch;
import words.com.wordservice.domain.mappers.WordDomainMapper;
import words.com.wordservice.domain.mappers.WordSearchMapper;
import words.com.wordservice.domain.models.filters.WordFilter;
import words.com.wordservice.domain.models.words.DeleteWordOptions;
import words.com.wordservice.domain.models.words.ModifyWord;
import words.com.wordservice.domain.models.words.Word;
import words.com.wordservice.domain.services.WordService;

import java.util.Collection;
import java.util.stream.Collectors;

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
        if (CollectionUtils.isEmpty(words)) {
            return;
        }
        var originals = words.stream()
                .map(ModifyWord::original)
                .filter(StringUtils::hasText)
                .distinct()
                .toList();
        if (CollectionUtils.isEmpty(originals)) {
            return;
        }
        var searchBuilder = WordSearch.builder()
                .operation(Operations.OR);
        originals.stream()
                .map(it -> WordSearch.builder().originalEq(it).build())
                .forEach(searchBuilder::search);
        var search = searchBuilder.build();


        var existsWords = wordDao.findBy(search).stream()
                .map(it -> it.getOriginal().toLowerCase().trim())
                .collect(Collectors.toSet());

        var entities = words.stream()
                .filter(it -> !existsWords.contains(it.original().toLowerCase().trim()))
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
