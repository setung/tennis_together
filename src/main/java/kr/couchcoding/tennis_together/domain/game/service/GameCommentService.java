package kr.couchcoding.tennis_together.domain.game.service;

import kr.couchcoding.tennis_together.controller.gameComment.dto.GCRequestDTO;
import kr.couchcoding.tennis_together.domain.friend.model.FrdList;
import kr.couchcoding.tennis_together.domain.game.dao.GameCommentRepository;
import kr.couchcoding.tennis_together.domain.game.dao.GameRepository;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.model.GameComment;
import kr.couchcoding.tennis_together.domain.game.status.GameStatus;
import kr.couchcoding.tennis_together.domain.user.model.User;
import kr.couchcoding.tennis_together.exception.CustomException;
import kr.couchcoding.tennis_together.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.events.Event;

import java.util.Optional;

@Service
public class GameCommentService {

    @Autowired
    GameCommentRepository gameCommentRepository; // 클래스명 사용하지않고 gameCommentRepository사용!

    @Autowired
    GameRepository gameRepository;

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

    @Transactional
    public Page<GameComment> getGameCommentList(Game game, Pageable pageable) {
        return gameCommentRepository.findByCommentedGame(game,pageable);
    }



    @Transactional
    public void deleteComment(User user,Long gameNo, Long commentNo) {

        // ==  Optional<GameComment> optionalComment = gameCommentRepository.findById(commentNo);
        // GameComment gameComment = optionalComment.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_GAME_COMMENT));

        GameComment gameComment = gameCommentRepository.findById(commentNo)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_GAME_REPLY));

        Game game = gameRepository.findById(gameNo).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_GAME));

        if(gameComment.getComtWriteUser().equals(user) && gameComment.getCommentedGame().equals(game)) {
            gameCommentRepository.delete(gameComment);
        } else {
            throw new CustomException(ErrorCode.FORBIDDEN_USER);
        }

    }

}
