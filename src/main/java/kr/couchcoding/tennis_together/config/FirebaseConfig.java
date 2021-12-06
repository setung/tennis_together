package kr.couchcoding.tennis_together.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class FirebaseConfig {

    ClassPathResource resource = new ClassPathResource("config/firebaseKey.json");

    @Bean
    public FirebaseAuth firebaseAuth() {
        FirebaseOptions options = null;
        try {
            options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FirebaseAuth.getInstance(FirebaseApp.getInstance());
    }

    @Bean
    public Storage storage() {
        Storage storage = null;
        try {
            storage = StorageOptions.newBuilder().
                    setCredentials(GoogleCredentials.fromStream(resource.getInputStream())).
                    setProjectId("tennis-together-91e65.appspot.com").build().getService();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return storage;
    }
}
