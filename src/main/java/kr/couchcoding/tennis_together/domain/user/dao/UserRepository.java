package kr.couchcoding.tennis_together.domain.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.couchcoding.tennis_together.domain.user.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>{

    @Query("SELECT u FROM User u WHERE u.uid.uid = :uid")
    Optional<User> findById(@Param("uid") String uid);

    User save(User user);

}
