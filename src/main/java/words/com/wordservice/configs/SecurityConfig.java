package words.com.wordservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import words.backend.authmodule.domain.factories.JwtAuthOncePerRequestFilterFactory;
import words.backend.authmodule.domain.httpfilters.JwtAuthOncePerRequestFilter;
import words.backend.authmodule.net.models.Role;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtAuthOncePerRequestFilterFactory filterFactory
    ) throws Exception {

        http.httpBasic(HttpBasicConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(HttpMethod.OPTIONS).permitAll()

                                .requestMatchers(HttpMethod.POST, "/words")
                                .hasAuthority(Role.ADMINISTRATION.getAuthority())
                                .requestMatchers(HttpMethod.DELETE, "/words")
                                .hasAuthority(Role.ADMINISTRATION.getAuthority())

                                .requestMatchers(HttpMethod.GET,
                                        "/", "/swagger-ui.html", "/swagger-ui/**",
                                        "/v3/api-docs/**", "/actuator/info", "/words"
                                ).permitAll()
                                .anyRequest()
                                .authenticated())
                .addFilterBefore(filterFactory.create(),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
