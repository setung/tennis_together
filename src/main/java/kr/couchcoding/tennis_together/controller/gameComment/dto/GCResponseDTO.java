package kr.couchcoding.tennis_together.controller.gameComment.dto;

import kr.couchcoding.tennis_together.domain.game.model.GameComment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GCResponseDTO {

    private Long commentNo;
    private String reviewContent;
    private Integer depth;
    private Integer grpNo;
    private LocalDateTime regDtm;
    private LocalDateTime updDtm;


    public GCResponseDTO(GameComment gameComment) {
        this.commentNo = gameComment.getCommentNo();
        this.reviewContent = gameComment.getReviewContent();
        this.depth = gameComment.getDepth();
        this.grpNo = gameComment.getGrpNo();
        this.regDtm = gameComment.getRegDtm();
        this.updDtm = gameComment.getUpdDtm();
    }

//
//    comtWriteUser
//            title
//    content
//            genderType
//    ageType
//            strDt
//    endDt
//            regDtm
//    locCd
//            stDvCd
//    commentedGame


}
