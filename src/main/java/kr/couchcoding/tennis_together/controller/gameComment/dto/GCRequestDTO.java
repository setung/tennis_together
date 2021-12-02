package kr.couchcoding.tennis_together.controller.gameComment.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GCRequestDTO {

    @ApiModelProperty(value = "댓글 내용", example = "안녕하세요", required = true)
    private String reviewContents;
}
