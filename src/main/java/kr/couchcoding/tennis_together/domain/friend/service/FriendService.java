package kr.couchcoding.tennis_together.domain.friend.service;


import kr.couchcoding.tennis_together.controller.court.specification.CourtSpecification;
import kr.couchcoding.tennis_together.controller.gameComment.dto.GCRequestDTO;
import kr.couchcoding.tennis_together.controller.user.dto.UserDTO;
import kr.couchcoding.tennis_together.domain.court.dto.CourtDto;
import kr.couchcoding.tennis_together.domain.court.model.Court;
import kr.couchcoding.tennis_together.domain.friend.dao.FriendDAO;
import kr.couchcoding.tennis_together.domain.friend.model.FrdList;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.model.GameComment;
import kr.couchcoding.tennis_together.domain.user.dao.UserRepository;
import kr.couchcoding.tennis_together.domain.user.model.User;
import kr.couchcoding.tennis_together.domain.user.service.UserService;
import kr.couchcoding.tennis_together.exception.CustomException;
import kr.couchcoding.tennis_together.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
@Slf4j
public class FriendService {
    
    @Autowired
    FriendDAO friendDAO; // DAO 주입,삽입
    @Autowired
    UserRepository userRepository;


    @Transactional
    public void followFriend(User user, User friend) {// 어떤유저가 누굴 팔로하는지

        FrdList existData = friendDAO.findByUserAndFrdUser(user, friend); // 친구를 가져오는 로직
        if(existData == null) {
            FrdList frdList = FrdList.builder().frdUser(friend).user(user).build(); // 친구목록 한 줄 추가
            friendDAO.save(frdList); // 저장
        } else {
            throw new CustomException(ErrorCode.EXIST_FRIEND);
        }
    }


    public Page<FrdList> getFollowList(User user, Pageable pageable) {
        return friendDAO.findByUser(user,pageable); // jpa에서 get의 동작방식이 다르기 때문에 jpa에서는 -> find
    }


    public void deleteFriend(User user, Long frdRelNo) {
        FrdList existData = friendDAO.findByUserAndFrdRelNo(user, frdRelNo); // 다른친구 삭제하면 안되니까 user도 받아야함
        if(existData != null) {
            friendDAO.delete(existData);
        } else {
            throw new CustomException(ErrorCode.NOT_FOUND_FRIEND);
        }
    }


    public Page<User> recommendedFriend(User user, Pageable pageable) {
        return userRepository.findByLocCd(user.getLocCd(), pageable);
    }


}
