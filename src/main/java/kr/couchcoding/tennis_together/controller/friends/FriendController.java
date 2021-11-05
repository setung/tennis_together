package kr.couchcoding.tennis_together.controller.friends;

import kr.couchcoding.tennis_together.controller.friends.dto.FollowRequestDTO;
import kr.couchcoding.tennis_together.domain.friend.dao.FriendDAO;
import kr.couchcoding.tennis_together.domain.friend.service.FriendService;
import kr.couchcoding.tennis_together.domain.user.model.User;
import kr.couchcoding.tennis_together.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/me/friends")
public class FriendController {

    @Autowired
    FriendService friendService;

    @Autowired
    UserService userService;

    @PostMapping("")
    public void followFriend(@RequestBody FollowRequestDTO requestDTO, Authentication authentication) { // 받고 싶은 것 계속 추가
        User user = ((User)authentication.getPrincipal()); // 인증유저를 갖고온다.
        User friend = (User) userService.loadUserByUsername(requestDTO.getAddedUserUid());
        friendService.followFriend(user, friend);
    }


}
