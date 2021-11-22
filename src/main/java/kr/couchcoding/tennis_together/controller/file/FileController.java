package kr.couchcoding.tennis_together.controller.file;

import kr.couchcoding.tennis_together.domain.file.service.FirebaseFileService;
import kr.couchcoding.tennis_together.domain.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FileController {



    @Autowired
    private FirebaseFileService firebaseFileServicefileService;

    @PostMapping("/profile/pic")
    public ResponseEntity create(@RequestParam(value = "file", required = false) MultipartFile file
                                 , @RequestParam(value = "uid") String uid) {
        try {
            String fileName = firebaseFileServicefileService.save(uid, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }
}
