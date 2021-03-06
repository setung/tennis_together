package kr.couchcoding.tennis_together.controller.gameComment.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.couchcoding.tennis_together.controller.user.dto.UserDTO;
import kr.couchcoding.tennis_together.domain.game.model.GameComment;
import lombok.Data;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Data
public class GCResponseDTO {

    @ApiModelProperty(value = "댓글 아이디", example = "1", required = true)
    private Long commentNo;
    @ApiModelProperty(value = "댓글 내용", example = "안녕하세요~", required = true)
    private String reviewContent;
    @ApiModelProperty(value = "댓글 Depth", example = "1", required = true)
    private Integer depth;
    @ApiModelProperty(value = "댓글 그룹", example = "1", required = true)
    private Integer grpNo;
    @ApiModelProperty(value = "댓글 등록일", example = "2021-11-22 00:47:51", required = true)
    private LocalDateTime regDtm;
    @ApiModelProperty(value = "댓글 수정일", example = "2021-11-22 00:47:51", required = true)
    private LocalDateTime updDtm;
    @ApiModelProperty(value = "부모 댓글", example = "1", required = true)
    private Long parentNo;
    @ApiModelProperty(value = "작성자", example = "1", required = true)
    private UserDTO comtWriteUser;

    public GCResponseDTO(GameComment gameComment) {
        this.commentNo = gameComment.getCommentNo();
        this.reviewContent = gameComment.getReviewContent();
        this.depth = gameComment.getDepth();
        this.grpNo = gameComment.getGrpNo();
        this.regDtm = gameComment.getRegDtm();
        this.updDtm = gameComment.getUpdDtm();
        this.parentNo = gameComment.getParentNo();
        this.comtWriteUser = new UserDTO(gameComment.getComtWriteUser());
    }

}
