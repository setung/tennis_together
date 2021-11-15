package kr.couchcoding.tennis_together.domain.game.service;

import kr.couchcoding.tennis_together.controller.gameComment.dto.GCRequestDTO;
import kr.couchcoding.tennis_together.domain.game.dao.GameCommentRepository;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.model.GameComment;
import kr.couchcoding.tennis_together.domain.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameCommentService {

    @Autowired
    GameCommentRepository gameCommentRepository;

    @Transactional
    public void createComment(User user, Game game, GCRequestDTO gcRequestDTO) {


        GameComment gc = GameComment.builder()
                .comtWriteUser(user)
                .commentedGame(game)
                .reviewContent(gcRequestDTO.getReviewContents())
                .depth(0)
                .build();

        gameCommentRepository.save(gc);

    }
}
