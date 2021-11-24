package kr.couchcoding.tennis_together.controller.userreview.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.couchcoding.tennis_together.controller.game.dto.ResponseGameDTO;
import kr.couchcoding.tennis_together.controller.user.dto.UserDTO;
import kr.couchcoding.tennis_together.domain.userreview.model.UserReview;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseUserReviewDTO {

    @ApiModelProperty(value = "평점 아이디", example = "1", required = true)
    private Long reviewNo;
    @ApiModelProperty(value = "평점 작성자 정보", required = true)
    private UserDTO writtenUser;
    @ApiModelProperty(value = "평점 대상자 정보", required = true)
    private UserDTO recipient;
    @ApiModelProperty(value = "평점 대상 게임 정보", required = true)
    private ResponseGameDTO game;
    @ApiModelProperty(value = "평점 내용", example = "짱고수다", required = true)
    private String reviewContent;
    @ApiModelProperty(value = "평점 등록일", example = "2021-11-22 00:47:51", required = true)
    private LocalDateTime regDtm;
    @ApiModelProperty(value = "평점 수정일", example = "2021-11-22 00:47:51", required = true)
    private LocalDateTime updDtm;
    @ApiModelProperty(value = "평점", example = "5", required = true)
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
