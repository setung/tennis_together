package kr.couchcoding.tennis_together.domain.game.service;

import kr.couchcoding.tennis_together.controller.gameComment.dto.GCRequestDTO;
import kr.couchcoding.tennis_together.domain.game.dao.GameCommentRepository;
import kr.couchcoding.tennis_together.domain.game.dao.GameRepository;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.model.GameComment;
import kr.couchcoding.tennis_together.domain.user.model.User;
import kr.couchcoding.tennis_together.exception.CustomException;
import kr.couchcoding.tennis_together.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameCommentService {

    @Autowired
    GameCommentRepository gameCommentRepository; // 클래스명 사용하지않고 gameCommentRepository사용!

    @Autowired
    GameRepository gameRepository;


    //게임 댓글 등록
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

    //게임 댓글 조회
    @Transactional
    public Page<GameComment> getGameCommentList(Game game, Pageable pageable) {
        return gameCommentRepository.findByCommentedGame(game,pageable);
    }


    //게임 댓글 삭제
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


    //게임 댓글 수정
    @Transactional
    public void updateGameComment(User user, Long gameNo, Long commentNo,GCRequestDTO updatedGameCommentDTO) {

        GameComment gameComment = gameCommentRepository.findById(commentNo)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_GAME_REPLY));

        Game game = gameRepository.findById(gameNo).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_GAME));


        if(!gameComment.getComtWriteUser().equals(user)){
            throw new CustomException(ErrorCode.FORBIDDEN_USER);
        } else if (!gameComment.getCommentedGame().equals(game)){
            throw new CustomException(ErrorCode.BAD_REQUEST_PARAM);
        } else {
            gameComment.updateComment(updatedGameCommentDTO.getReviewContents());
        }
    }

    public void createReplyComment(User user, Game game, Long commentNo, GCRequestDTO gcRequestDTO) {

        GameComment gameComment = gameCommentRepository.findById(commentNo)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_GAME_REPLY,"댓글 id와 game id가 일치하지 않습니다"));

        if(!gameComment.getCommentedGame().getGameNo().equals(game.getGameNo())) { // 부모댓글과 같은게임에 있는지 검증
            throw new CustomException(ErrorCode.NOT_FOUND_GAME);
        } else {
            GameComment gc = GameComment.builder()
                    .comtWriteUser(user)
                    .commentedGame(game)
                    .reviewContent(gcRequestDTO.getReviewContents())
                    .depth(gameComment.getDepth() +1)
                    .parentNo(commentNo)
                    .build();

            gameCommentRepository.save(gc);
        }

    }
}
