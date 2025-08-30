package words.com.wordservice.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import words.com.wordservice.db.daos.impls.DbDaoConfig;

@Configuration
@Import({
        DbDaoConfig.class
})
public class MainConfig {
}
