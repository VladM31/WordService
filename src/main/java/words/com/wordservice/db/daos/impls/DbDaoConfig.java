package words.com.wordservice.db.daos.impls;

import org.springframework.context.annotation.Bean;
import words.com.wordservice.db.daos.*;

public class DbDaoConfig {

    @Bean
    WordDao wordDaoImpl(
            WordRepository wordRepository
    ) {
        return new WordDaoImpl(wordRepository);
    }

    @Bean
    UserWordDao userWordDaoImpl(
            UserWordRepository userWordRepository,
            WordRepository wordRepository
    ) {
        return new UserWordDaoImpl(userWordRepository,wordRepository);
    }

    @Bean
    PinnedWordDao pinnedWordDaoImpl(
            PinnedWordRepository pinnedWordRepository
    ) {
        return new PinnedWordDaoImpl(pinnedWordRepository);
    }

    @Bean
    WordPlayListDao wordPlayListDaoImpl(
            WordPlayListRepository wordPlayListRepository
    ) {
        return new WordPlayListDaoImpl(wordPlayListRepository);
    }

    @Bean
    LearningPlanDao learningPlanDaoImpl(
            LearningPlanRepository learningPlanRepository
    ) {
        return new LearningPlanDaoImpl(learningPlanRepository);
    }

    @Bean
    LearningHistoryDao learningHistoryDaoImpl(
            LearningHistoryRepository learningHistoryRepository
    ) {
        return new LearningHistoryDaoImpl(learningHistoryRepository);
    }
}
