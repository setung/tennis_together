package kr.couchcoding.tennis_together.controller.userreview.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUserReviewDTO {

    @ApiModelProperty(value = "평점 대상 게임 아이디", example = "1", required = true)
    private Long gameNo;
    @ApiModelProperty(value = "평점 내용", example = "짱고수다!", required = true)
    private String reviewContent;
    @ApiModelProperty(value = "평점", example = "5", required = true)
    private Long score;
}
