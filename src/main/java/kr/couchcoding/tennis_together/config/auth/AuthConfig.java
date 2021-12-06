package kr.couchcoding.tennis_together.config.auth;

import com.google.firebase.auth.FirebaseAuth;
import kr.couchcoding.tennis_together.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class AuthConfig {

    private final UserService userService;
    private final FirebaseAuth firebaseAuth;

    @Bean
    @Profile("local")
    public AuthFilterContainer mockAuthFilter() {
        log.info("Initializing Mock AuthFilter");
        AuthFilterContainer authFilterContainer = new AuthFilterContainer();
        authFilterContainer.setAuthFilter(new MockAuthFilter(userService));
        return authFilterContainer;
    }

    @Bean
    @Profile("!local")
    public AuthFilterContainer jwtAuthFilter() {
        log.info("Initializing Firebase AuthFilter");
        AuthFilterContainer authFilterContainer = new AuthFilterContainer();
        authFilterContainer.setAuthFilter(new JwtFilter(userService, firebaseAuth));
        return authFilterContainer;
    }

}
