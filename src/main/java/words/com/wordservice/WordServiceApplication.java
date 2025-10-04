package words.com.wordservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import words.backend.authmodule.domain.httpfilters.JwtAuthFilter;

@SpringBootApplication(
        exclude = {UserDetailsServiceAutoConfiguration.class}
)
public class WordServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WordServiceApplication.class, args);
    }

}
