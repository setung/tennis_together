package kr.couchcoding.tennis_together.domain.user.service;

import java.util.Optional;

import kr.couchcoding.tennis_together.domain.location.model.LocCd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.couchcoding.tennis_together.domain.user.dao.UserRepository;
import kr.couchcoding.tennis_together.domain.user.model.User;
import kr.couchcoding.tennis_together.exception.CustomException;
import kr.couchcoding.tennis_together.exception.ErrorCode;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    /**
     * User uid로 가져온다(오버라이드용)
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(username);
        return user.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }



    // 유저 등록
    @Transactional
    public User register(String uid, String phone, String name, Character gender
            , Integer history, String nickname, LocCd locCd){
        User registeredUser = User.builder()
                .uid(uid)
                .phone(phone)
                .name(name)
                .gender(gender)
                .history(history)
                .nickname(nickname)
                .locCd(locCd)
                .build();
        userRepository.save(registeredUser);

        return registeredUser;
    }

}
