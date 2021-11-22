package kr.couchcoding.tennis_together.domain.file.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import kr.couchcoding.tennis_together.controller.user.dto.UpdateUserDTO;
import kr.couchcoding.tennis_together.domain.user.model.User;
import kr.couchcoding.tennis_together.domain.user.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseFileService {

    private Storage storage;

    @Autowired
    private UserService userService;

    @EventListener
    public void init(ApplicationReadyEvent event) {
        try {
            ClassPathResource serviceAccount = new ClassPathResource("config/firebaseKey.json");
            storage = StorageOptions.newBuilder().
                    setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream())).
                    setProjectId("tennis-together-91e65.appspot.com").build().getService();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String save(String uid, MultipartFile multipartFile) throws IOException{

        File file = new File(multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        String filePath = file.getAbsoluteFile().toString();


        String imageName = generateFileName(multipartFile.getOriginalFilename());
        Map<String, String> map = new HashMap<>();
        map.put("firebaseStorageDownloadTokens", imageName);
        //tennis-together-91e65.appspot.com
        BlobId blobId = BlobId.of("tennis-together-91e65.appspot.com", imageName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setMetadata(map)
                .setContentType(multipartFile.getContentType())
                .build();

        // 파일 저장
        storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));


        // 접근 url
        String urlPath = blobId.toGsUtilUri();
        urlPath = urlPath.replace("gs://", "https://storage.googleapis.com/");

        userService.uploadImage(uid, urlPath);

        return imageName;
    }

    private String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "." + getExtension(originalFileName);
    }

    private String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }
}