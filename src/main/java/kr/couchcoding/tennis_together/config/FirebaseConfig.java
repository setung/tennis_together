package kr.couchcoding.tennis_together.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class FirebaseConfig {

    @Bean
    public FirebaseAuth firebaseAuth() throws IOException {

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream((getFirebaseIs())))
                .build();

        FirebaseApp.initializeApp(options);
        return FirebaseAuth.getInstance(FirebaseApp.getInstance());
    }

    @Bean
    public Storage storage() throws IOException {
        Storage storage = StorageOptions.newBuilder().
                setCredentials(GoogleCredentials.fromStream(getFirebaseIs())).
                setProjectId("tennis-together-91e65.appspot.com").build().getService();

        return storage;
    }

    private InputStream getFirebaseIs() throws IOException {
        ClassPathResource resource = new ClassPathResource("config/firebaseKey.json");
        if (resource.exists())
            return resource.getInputStream();

        JSONObject jsonObject = new JSONObject(getEnvMap());
        return new ByteArrayInputStream(jsonObject.toString().getBytes());
    }

    private Map getEnvMap() {
        Map<String, String> map = new HashMap<>();
        map.put("type", System.getenv().get("type"));
        map.put("project_id", System.getenv().get("project_id"));
        map.put("private_key_id", System.getenv().get("private_key_id"));
        map.put("private_key", System.getenv().get("private_key"));
        map.put("client_email", System.getenv().get("client_email"));
        map.put("client_id", System.getenv().get("client_id"));
        map.put("auth_uri", System.getenv().get("auth_uri"));
        map.put("token_uri", System.getenv().get("token_uri"));
        map.put("auth_provider_x509_cert_url", System.getenv().get("auth_provider_x509_cert_url"));
        map.put("client_x509_cert_url", System.getenv().get("client_x509_cert_url"));
        log.info("map={}", map);
        return map;
    }
}
