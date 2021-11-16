package kr.couchcoding.tennis_together.domain.game.dao;


import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.model.GameComment;
import kr.couchcoding.tennis_together.domain.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameCommentRepository extends JpaRepository<GameComment,Long>  { // <Entity,id type(pk)>


    Page<GameComment> findByCommentedGame(Game commentedGame, Pageable pageable); // Entity에서 gameNo = CommentedGame

    Optional<GameComment> findByCommentNo(Long commentNo);

}
