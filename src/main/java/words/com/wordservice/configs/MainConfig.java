package words.com.wordservice.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import words.backend.authmodule.configs.WordSecurityMainConfig;
import words.com.wordservice.db.daos.impls.DbDaoConfig;
import words.com.wordservice.domain.services.impls.DomainServiceConfig;

@Configuration
@Import({
        WordSecurityMainConfig.class,
        DbDaoConfig.class,
        DomainServiceConfig.class
})
public class MainConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Words API").version("2.0"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
