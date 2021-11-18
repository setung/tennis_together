package kr.couchcoding.tennis_together.domain.user.service;

import java.util.Optional;

import kr.couchcoding.tennis_together.controller.user.dto.UpdateUserDTO;
import kr.couchcoding.tennis_together.domain.location.model.LocCd;
import kr.couchcoding.tennis_together.domain.location.service.LocCdService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    LocCdService locCdService;

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
    // regDtm, updDtm 생성 인서트 안되는 문제 확인
    public User register(String uid, String phone, String birth, String gender
            , Integer history, String nickname, LocCd locCd){
        User registeredUser = User.builder()
                .uid(uid)
                .phone(phone)
                .birth(birth)
                .gender(gender)
                .history(history)
                .nickname(nickname)
                .locCd(locCd)
                .score(0L)
                .build();
        userRepository.save(registeredUser);

        return registeredUser;
    }

    // 유저 전체 조회
    public Page<User> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);

        if (users.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        return users;
    }

    // 유저 상세조회
    public User findById(String uid){
        Optional<User> user = userRepository.findById(uid);

        user.ifPresentOrElse(
                findUser -> {
                    if (findUser.getActDvCd() != '1')
                        throw new CustomException(ErrorCode.DELETED_USER);
                },
                () -> {
                    throw new CustomException(ErrorCode.NOT_FOUND_USER);
                });
        return user.get();
    }

    // 유저 수정
    public void updateUser(String uid, UpdateUserDTO updateUserDTO){
        Optional<User> user = userRepository.findById(uid);
        LocCd loccd = new LocCd();
        if (updateUserDTO.getLocSd() != null && updateUserDTO.getLocSkk() != null){
            loccd = locCdService.findByLocSdAndLocSkk(updateUserDTO.getLocSd(), updateUserDTO.getLocSkk());
        }

        User updatedUser = User.builder()
                .phone(updateUserDTO.getPhone())
                .birth(updateUserDTO.getBirth())
                .gender(updateUserDTO.getGender())
                .history(updateUserDTO.getHistory())
                .nickname(updateUserDTO.getNickname())
                .locCd(loccd)
                .profileUrl(updateUserDTO.getProfileUrl())
                .build();
        user.get().UpdateUser(updatedUser);
    }

    // 유저 삭제
    public void deleteUser(String uid){
        Optional<User> user = userRepository.findById(uid);

        if (user.get().getActDvCd() != '1'){
            throw new CustomException(ErrorCode.DELETED_USER);
        }

        user.get().updateUserActDvCd('2');
    }
}
