package kr.couchcoding.tennis_together.controller.sample;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.couchcoding.tennis_together.domain.user.model.User;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/sample")
@Slf4j
public class SampleController {
    @GetMapping()
    public User sample(Authentication authentication) {
        log.info("sample controller");
        User user = ((User)authentication.getPrincipal());
        log.info("login user : " + user);
        return user;
    }
}
