package kr.couchcoding.tennis_together.controller.userreview.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUserReviewDTO {

    private Long gameNo;
    private String reviewContent;
    private Long score;
}
