package kr.couchcoding.tennis_together.domain.friend.dao;

import kr.couchcoding.tennis_together.domain.friend.model.FrdList;
import kr.couchcoding.tennis_together.domain.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendDAO extends JpaRepository<FrdList,Long> { // type과 idtype
    FrdList findByUserAndFrdUser(User user, User FrdUser);
}

