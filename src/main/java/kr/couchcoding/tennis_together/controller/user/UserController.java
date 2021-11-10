package kr.couchcoding.tennis_together.controller.user;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserInfo;
import kr.couchcoding.tennis_together.controller.user.dto.RegisterDTO;
import kr.couchcoding.tennis_together.domain.user.model.User;
import kr.couchcoding.tennis_together.domain.user.service.UserService;
import kr.couchcoding.tennis_together.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    FirebaseAuth firebaseAuth;

    @Autowired
    private UserService userService;

    // 회원 등록
    @PostMapping("")
    public void register(@RequestHeader("Authorization") String authorization,
                             @RequestBody RegisterDTO registerDTO){

        FirebaseToken decodedToken;

        // 토큰 검증
        try {
            String token = RequestUtil.getAuthorizationToken(authorization);
            decodedToken = firebaseAuth.verifyIdToken(token);
        } catch (IllegalArgumentException | FirebaseAuthException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "{\"code\":\"INVALID_TOKEN\", \"message\":\"" + e.getMessage() + "\"}");
        }

        // 사용자 등록
        userService.register(
                decodedToken.getUid()
                , registerDTO.getPhone()
                , registerDTO.getBirth()
                , registerDTO.getGender()
                , registerDTO.getHistory()
                , registerDTO.getNickname()
                , registerDTO.getLocCd()
            );
    }



    // 로그인 (상세조회와 같다?)
    @GetMapping("/{uid}")
    public User login(Authentication authentication) {
        User loginUser = (User) authentication.getPrincipal();

        return loginUser;
    }
}
