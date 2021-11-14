package kr.couchcoding.tennis_together.domain.game.dao;

import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.model.GameUserList;
import kr.couchcoding.tennis_together.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameUserListRepository extends JpaRepository<GameUserList, Long> {
    Optional<GameUserList> findByGameUserAndJoinedGame(User gameUser, Game joinedGame);
}
