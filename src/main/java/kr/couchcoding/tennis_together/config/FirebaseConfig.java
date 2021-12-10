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
        ClassPathResource resource = new ClassPathResource("config/firebaseKey2.json");
        if (resource.exists())
            return resource.getInputStream();

        JSONObject jsonObject = new JSONObject(getEnvMap());
        return new ByteArrayInputStream(jsonObject.toString().getBytes());
    }

    private Map getEnvMap() {
        Map<String, String> map = new HashMap<>();
        /*map.put("type", System.getenv().get("type"));
        map.put("project_id", System.getenv().get("project_id"));
        map.put("private_key_id", System.getenv().get("private_key_id"));
        map.put("private_key", System.getenv().get("private_key"));
        map.put("client_email", System.getenv().get("client_email"));
        map.put("client_id", System.getenv().get("client_id"));
        map.put("auth_uri", System.getenv().get("auth_uri"));
        map.put("token_uri", System.getenv().get("token_uri"));
        map.put("auth_provider_x509_cert_url", System.getenv().get("auth_provider_x509_cert_url"));
        map.put("client_x509_cert_url", System.getenv().get("client_x509_cert_url"));*/
        map.put("type", "service_account");
        map.put("project_id", "tennis-together-91e65");
        map.put("private_key_id", "e4c9a56ded20f8c57adee764ff7f73865f1edcb2");
        map.put("private_key", "-----BEGIN PRIVATE KEY-----\nMIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDLiK6eUgw7R4GI\nBO9EWriw+wBvBhNLOGMnlP8zKSUTcCRT/CZSiXXoAnfo2/D1uO3LmeVfnRUYNSNA\nHoJjg+8x4r/m8sktU1ceXSBXK72bbAcC9KvAfEqDWo17UBpLq7BVwOBrdCAYPQvF\nEJre0vz8F2ouShUXYBXn8m1YMIZhTp3aLmm3tHhIin7s2GAZmTOEpDb919WL5djV\nzSbP7SEeqSR9LQKnfMaAjp8JLd2nSjK/GEUEPQDV0lS+FO8HsPIFv8wS7uwtXmHS\np9oRmSjNjIY+9/UgRCbMcftB0zKDkre/HqvdaWTxHHJQSh/3Kt5bA+Af75hbspyA\nHpVlLwdXAgMBAAECggEAGevz0VYXGLWoBEVx86gE6Fq6A2nSqzx95Fyljpkpj6rd\n9ZRM4oDUnhKHvbBv+oZC7YX8IIvw/n7wrl80XALsQ9ONJ+y5PSc6wTM7GzRcQZ8l\n2sqD1U//GBuVnqQ6X8jN/HNEIcCjFciF5ziQ1jAo6DJTOPIBHakEdotO7HHBq5gN\nfXNRs7exHft9Cf7dBvN2SelnjWxxIJmmT34UpQefNz37J0scFW8VUWBDV8sDYVxD\niy/Ho/UHnmBIjiD3ufat+s+PO4yFKUuHJjrj9PFpMMZfPxh7Y0OvUtWEd3QzH4hY\nnGIn58ZT4agLyWsaEvL+ztCLXqRz4tHjEP5+66nCAQKBgQDl7Mhgj4kuT4viBRzu\nK2V3bZNFNRzWOVL7GKHstvv112rOmd0+Xig9uBG0gCvFm7/EpQsniBKuefkFco9a\nGTpYAQz9j3NbPkkB8vsKp+2Wl2VRTMXOlOLmqPhXgteqwfFRnanHKibG6Z24MJMV\nsV9u3JXkN58FO4OK8il5PYidJwKBgQDinbYNMbIXvl/EfIzv3vlsBG18IB9QtjCB\n2s4wBhTcy6oPYzOHF2xqBC+53nJLB3ZSuCEmCkDeHR6eMD1Dn/6eY6Y689Pgaw3/\n9oNwiuAjEykrSnRnx3EJy1JSDL5a0kpz48jXTzRbC2HaI+ua74Pl3nhN4phO2qZM\n2FZxEU0CUQKBgDJs1KFAuYA7l90Q7ABb6hvgHy1Nca6V0doZ0+FbXDTVcqou2nPE\nKKUoilGAzpi/CLayz5hy4k37mB6WulOzNrV4PYYagN0g8tgHlwEGRTgCqgjDgppE\n0uJtalGe83eSoz8YCdURLIHxWFK97HQUe26P2Bu+CxUVsUq2w1vAoAZ/AoGAQooC\nSW99FmGspvnXPBthaykFqLuZ1k4YghhoEEvI1mfqoCgFmjXzfFGMXewF7Sfcwz8T\nZKMAFve/5LzPUErrLUCfL5ImdS+Z5b1RLLtqQoI+xVpQQaSa+UA9BKB/SBWVDOAm\nOaok+lKmQbHoXQ7U0Kr48g2FoSmsSMYA46Qr03ECgYB39pRPHojrv8Ctzwm19CT6\nYqDwgfQ/dIV8vSgGg726v50ZL0y++32H7ucqDLJjM7TuFDf6jGld1VgARGDe/JHh\n2zm2xa8EwGDJh8IuamaYJZBYpR+LAuxQR/ls/5DovZgRLQu40/+LGBCICSNaOZJP\no8G8m6w9tGqFntfkMX9SrQ==\n-----END PRIVATE KEY-----\n");
        map.put("client_email", "firebase-adminsdk-t0esz@tennis-together-91e65.iam.gserviceaccount.com");
        map.put("client_id", "114520601346018714769");
        map.put("auth_uri", "https://accounts.google.com/o/oauth2/auth");
        map.put("token_uri", "https://oauth2.googleapis.com/token");
        map.put("auth_provider_x509_cert_url", "https://www.googleapis.com/oauth2/v1/certs");
        map.put("client_x509_cert_url", "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-t0esz%40tennis-together-91e65.iam.gserviceaccount.com");
        log.info("map={}", map);
        return map;
    }
}
