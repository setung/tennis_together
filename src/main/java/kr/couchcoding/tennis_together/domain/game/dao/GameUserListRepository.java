package kr.couchcoding.tennis_together.domain.game.dao;

import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.model.GameUserList;
import kr.couchcoding.tennis_together.domain.game.status.GameUserListStatus;
import kr.couchcoding.tennis_together.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameUserListRepository extends JpaRepository<GameUserList, Long>, JpaSpecificationExecutor<GameUserList> {

    Optional<GameUserList> findByGameUserAndJoinedGame(User gameUser, Game joinedGame);

    Optional<GameUserList> findByJoinedGameAndStatus(Game game, GameUserListStatus status);
}
