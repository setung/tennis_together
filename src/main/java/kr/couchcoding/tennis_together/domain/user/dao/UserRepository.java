package kr.couchcoding.tennis_together.domain.user.dao;

import kr.couchcoding.tennis_together.domain.location.model.LocCd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.couchcoding.tennis_together.domain.user.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

    Page<User> findByLocCd(LocCd locCd, Pageable pageable);

}
