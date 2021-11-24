package kr.couchcoding.tennis_together.config.auth;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.cloud.StorageClient;
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
    public AuthFilterContainer mockAuthFilter() {
        AuthFilterContainer authFilterContainer = new AuthFilterContainer();
        authFilterContainer.setAuthFilter(new MockAuthFilter(userService));
        return authFilterContainer;
    }

    @Bean
    @Profile("!local")
    public AuthFilterContainer jwtAuthFilter() throws IOException {
        AuthFilterContainer authFilterContainer = new AuthFilterContainer();
        authFilterContainer.setAuthFilter(new JwtFilter(userService, firebaseAuth()));
        return authFilterContainer;
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
