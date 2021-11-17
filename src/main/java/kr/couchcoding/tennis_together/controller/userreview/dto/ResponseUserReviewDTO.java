package kr.couchcoding.tennis_together.controller.userreview.dto;

import kr.couchcoding.tennis_together.controller.game.dto.ResponseGameDTO;
import kr.couchcoding.tennis_together.controller.user.dto.UserDTO;
import kr.couchcoding.tennis_together.domain.userreview.model.UserReview;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseUserReviewDTO {

    private Long reviewNo;
    private UserDTO writtenUser;
    private UserDTO recipient;
    private ResponseGameDTO game;
    private String reviewContent;
    private LocalDateTime regDtm;
    private LocalDateTime updDtm;
    private Long score;

    public ResponseUserReviewDTO(UserReview userReview) {
        reviewNo = userReview.getReviewNo();
        writtenUser = new UserDTO(userReview.getWrittenUser());
        recipient = new UserDTO(userReview.getRecipient());
        game = new ResponseGameDTO(userReview.getGame());
        reviewContent = userReview.getReviewContent();
        regDtm = userReview.getRegDtm();
        updDtm = userReview.getUpdDtm();
        score = userReview.getScore();
    }
}
