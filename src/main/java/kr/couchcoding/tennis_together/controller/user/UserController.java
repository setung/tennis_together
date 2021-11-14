package kr.couchcoding.tennis_together.controller.user;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserInfo;
import kr.couchcoding.tennis_together.controller.user.dto.RegisterDTO;
import kr.couchcoding.tennis_together.controller.user.dto.UserDTO;
import kr.couchcoding.tennis_together.domain.user.model.User;
import kr.couchcoding.tennis_together.domain.user.service.UserService;
import kr.couchcoding.tennis_together.exception.CustomException;
import kr.couchcoding.tennis_together.exception.ErrorCode;
import kr.couchcoding.tennis_together.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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
        // 등록된 사용자인지 조회
        if (userService.loadUserByUsername(decodedToken.getUid()) instanceof  User) {
            // 이미 등록된 사용자
            throw new CustomException(ErrorCode.EXIST_USER);
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

    // 로그인
    @GetMapping("/me")
    public UserDTO login(Authentication authentication){
        User loginUser = (User) authentication.getPrincipal();
        return new UserDTO(loginUser);
    }



    // 상세조회
    @GetMapping("/{uid}")
    public UserDTO findByUid(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return new UserDTO(user);
    }
}
