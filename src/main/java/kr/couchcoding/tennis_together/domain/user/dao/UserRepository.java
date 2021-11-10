package kr.couchcoding.tennis_together.domain.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.couchcoding.tennis_together.domain.user.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>{

}
