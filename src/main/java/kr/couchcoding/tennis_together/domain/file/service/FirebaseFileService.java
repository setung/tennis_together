package kr.couchcoding.tennis_together.domain.file.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import kr.couchcoding.tennis_together.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FirebaseFileService {

    private final Storage storage;
    private final UserService userService;

    public String save(String uid, MultipartFile multipartFile) throws IOException {
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