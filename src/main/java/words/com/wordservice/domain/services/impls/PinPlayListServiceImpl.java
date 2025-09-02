package words.com.wordservice.domain.services.impls;

import lombok.RequiredArgsConstructor;
import words.com.wordservice.db.daos.PinnedWordDao;
import words.com.wordservice.db.searches.PinnedWordSearch;
import words.com.wordservice.domain.mappers.PinPlayListDomainMapper;
import words.com.wordservice.domain.models.pins.PinOptions;
import words.com.wordservice.domain.models.pins.UnpinOptions;
import words.com.wordservice.domain.services.PinPlayListService;

@RequiredArgsConstructor
class PinPlayListServiceImpl implements PinPlayListService {
    private final PinnedWordDao pinnedWordDao;
    private final PinPlayListDomainMapper pinPlayListDomainMapper;

    @Override
    public void pin(PinOptions options) {
        var entities = pinPlayListDomainMapper.toEntities(options);
        pinnedWordDao.saveAll(entities);
    }

    @Override
    public void unpin(UnpinOptions options) {
        var ids = options.pins().stream().map(pinPlayListDomainMapper::toEntity).toList();
        if (ids.isEmpty()) {
            return;
        }
        var search = PinnedWordSearch.builder().pinnedIds(ids).userId(options.userId()).build();
        pinnedWordDao.delete(search);
    }
}
