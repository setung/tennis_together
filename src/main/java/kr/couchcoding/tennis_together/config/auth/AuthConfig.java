package kr.couchcoding.tennis_together.config.auth;

import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import kr.couchcoding.tennis_together.domain.user.service.UserService;

@Configuration
public class AuthConfig {
    @Autowired
    private UserService userService;

    @Bean
    @Profile("local")
    public AuthFilter mockAuthFilter() {
        return new MockAuthFilter(userService);
    }

    @Bean
    @Profile("!local")
    public AuthFilter jwtAuthFilter() throws IOException {
        return new JwtFilter(userService, firebaseAuth());
    }

    @Bean
    public FirebaseAuth firebaseAuth() throws IOException {
        ClassPathResource resource = new ClassPathResource("config/firebaseKey.json");
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                .build();
        FirebaseApp.initializeApp(options);
        return FirebaseAuth.getInstance(FirebaseApp.getInstance());
    }
}
