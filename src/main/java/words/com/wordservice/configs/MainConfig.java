package words.com.wordservice.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import words.com.wordservice.db.daos.impls.DbDaoConfig;
import words.com.wordservice.domain.services.impls.DomainServiceConfig;

@Configuration
@Import({
        DbDaoConfig.class,
        DomainServiceConfig.class
})
public class MainConfig {
}
