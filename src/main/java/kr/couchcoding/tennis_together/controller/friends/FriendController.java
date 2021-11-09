package kr.couchcoding.tennis_together.controller.friends;

import kr.couchcoding.tennis_together.controller.friends.dto.FollowRequestDTO;
import kr.couchcoding.tennis_together.controller.friends.dto.FollowResponseDTO;
import kr.couchcoding.tennis_together.domain.court.dto.CourtDto;
import kr.couchcoding.tennis_together.domain.friend.model.FrdList;
import kr.couchcoding.tennis_together.domain.friend.service.FriendService;
import kr.couchcoding.tennis_together.domain.user.model.User;
import kr.couchcoding.tennis_together.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/me/friends")
public class FriendController {

    @Autowired
    FriendService friendService;

    @Autowired
    UserService userService;

    //팔로우
    @PostMapping("")
    public void followFriend(@RequestBody FollowRequestDTO requestDTO, Authentication authentication) { // 받고 싶은 것 계속 추가
        User user = ((User)authentication.getPrincipal()); // 인증유저를 갖고온다.
        User friend = (User) userService.loadUserByUsername(requestDTO.getAddedUserUid());
        friendService.followFriend(user, friend);
    }

    //팔로우 친구 조회
    @GetMapping("")
    public Page<FollowResponseDTO> getFollowList(Authentication authentication, Pageable pageable) {
        // header에 user포함, RequestParam으로 받을 필요 없음
        User user = ((User) authentication.getPrincipal()); // 인증유저를 갖고온다.
        return friendService.getFollowList(user, pageable).map(frdList -> new FollowResponseDTO(frdList));
        //map은 item을 바꿔주는 method

    }

    //팔로우 친구 삭제
    @DeleteMapping("/{frdRelNo}")
    public void deleteFriend(@PathVariable(value = "frdRelNo") Long frdRelNo, Authentication authentication) {
        User user = ((User)authentication.getPrincipal());
        friendService.deleteFriend(user,frdRelNo);
    }
}
