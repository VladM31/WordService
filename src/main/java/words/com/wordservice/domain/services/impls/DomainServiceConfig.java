package words.com.wordservice.domain.services.impls;

import org.springframework.context.annotation.Bean;
import words.com.wordservice.db.daos.*;
import words.com.wordservice.domain.mappers.*;
import words.com.wordservice.domain.services.*;

public class DomainServiceConfig {

    @Bean
    WordService wordServiceImpl(
            WordDao wordDao,
            WordSearchMapper wordSearchMapper,
            WordDomainMapper wordDomainMapper
    ) {
        return new WordServiceImpl(
                wordDao,
                wordSearchMapper,
                wordDomainMapper
        );
    }

    @Bean
    UserWordService userWordServiceImpl(
            UserWordDao userWordDao,
            UserWordDomainMapper userWordDomainMapper,
            UserWordSearchMapper userWordSearchMapper,
            LearningHistoryDao learningHistoryDao
    ) {
        return new UserWordServiceImpl(
                userWordDao,
                userWordDomainMapper,
                userWordSearchMapper,
                learningHistoryDao
        );
    }

    @Bean
    LearningHistoryService learningHistoryServiceImpl(
            LearningHistoryDomainMapper learningHistoryMapper,
            LearningHistoryDao learningHistoryDao,
            UserWordDao userWordDao,
            LearningHistorySearchMapper learningHistorySearchMapper
    ) {
        return new LearningHistoryServiceImpl(
                learningHistoryMapper,
                learningHistoryDao,
                userWordDao,
                learningHistorySearchMapper
        );
    }

    @Bean
    LearningPlanService learningPlanServiceImpl(
            LearningPlanDao learningPlanDao,
            LearningPlanSearchMapper learningPlanSearchMapper,
            LearningPlanDomainMapper learningPlanDomainMapper
    ) {
        return new LearningPlanServiceImpl(
                learningPlanDao,
                learningPlanSearchMapper,
                learningPlanDomainMapper
        );
    }

    @Bean
    PinPlayListService pinPlayListServiceImpl(
            PinnedWordDao pinnedWordDao,
            PinPlayListDomainMapper pinPlayListDomainMapper
    ) {
        return new PinPlayListServiceImpl(
                pinnedWordDao,
                pinPlayListDomainMapper
        );
    }

    @Bean
    WordPlayListService wordPlayListServiceImpl(
            WordPlayListSearchMapper searchMapper,
            WordPlayListDao dao,
            PinnedWordDao pinnedWordDao,
            WordPlayListDomainMapper wordPlayListDomainMapper,
            LearningHistoryDao learningHistoryDao
    ) {
        return new WordPlayListServiceImpl(
                searchMapper,
                dao,
                pinnedWordDao,
                wordPlayListDomainMapper,
                learningHistoryDao
        );
    }
}
