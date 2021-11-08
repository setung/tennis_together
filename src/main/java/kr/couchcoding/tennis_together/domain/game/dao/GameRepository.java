package kr.couchcoding.tennis_together.domain.game.dao;

import kr.couchcoding.tennis_together.domain.game.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GameRepository extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game> {
}
