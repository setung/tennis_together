package kr.couchcoding.tennis_together.controller.user;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import kr.couchcoding.tennis_together.controller.user.dto.RegisterDTO;
import kr.couchcoding.tennis_together.controller.user.dto.UserDTO;
import kr.couchcoding.tennis_together.domain.location.model.LocCd;
import kr.couchcoding.tennis_together.domain.location.service.LocCdService;
import kr.couchcoding.tennis_together.domain.user.model.User;
import kr.couchcoding.tennis_together.domain.user.service.UserService;
import kr.couchcoding.tennis_together.exception.CustomException;
import kr.couchcoding.tennis_together.exception.ErrorCode;
import kr.couchcoding.tennis_together.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Random;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    FirebaseAuth firebaseAuth;

    @Autowired
    private UserService userService;

    @Autowired
    private LocCdService locCdService;

    // 회원 등록
    @PostMapping("")
    public void register(@RequestHeader("Authorization") String authorization,
                             @RequestBody RegisterDTO registerDTO){

        FirebaseToken decodedToken;
        String uid;
        // 토큰 검증
        try {
            String token = RequestUtil.getAuthorizationToken(authorization);
            // 테스트 시 검증 스킵
            if (token.startsWith("test")){

                // 랜덤 스트링을 생성해 uid 생성
                int leftLimit = 97;
                int rightLimit = 122;
                int length = 10;
                Random random = new Random();
                String generatedString = random.ints(leftLimit, rightLimit + 1)
                        .limit(length)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
                uid = generatedString;

            } else {
                decodedToken = firebaseAuth.verifyIdToken(token);
                uid = decodedToken.getUid();
            }

        } catch (IllegalArgumentException | FirebaseAuthException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "{\"code\":\"INVALID_TOKEN\", \"message\":\"" + e.getMessage() + "\"}");
        }
        // 등록된 사용자인지 조회
        try {
            userService.loadUserByUsername(uid);
            throw new CustomException(ErrorCode.EXIST_USER);
        } catch (CustomException e) {
            // 등록안된 사용자
        }


        LocCd loccd = locCdService.findByLocSdAndLocSkk(registerDTO.getLocSd(), registerDTO.getLocSkk());
        Character gender = '1';
        if (registerDTO.getGender().equals("여성")){
            gender = '2';
        }

        // 사용자 등록
        userService.register(
                uid
                , registerDTO.getPhone()
                , registerDTO.getBirth()
                , gender
                , registerDTO.getHistory()
                , registerDTO.getNickname()
                , loccd
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